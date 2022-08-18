package fares.saleem.android.myprojects.hotelbooking.ClientSide.Participation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import fares.saleem.android.myprojects.hotelbooking.DB.MyHelper;
import fares.saleem.android.myprojects.hotelbooking.ClientSide.NavBarActivity;
import fares.saleem.android.myprojects.hotelbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    FirebaseAuth myAuth;
    String email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        myAuth = FirebaseAuth.getInstance();
        sign_in();
    }

    public void sign_in() {
        findViewById(R.id.btn_SignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar progressBar = findViewById(R.id.progress);
                progressBar.setVisibility(View.VISIBLE);

                EditText edemail = findViewById(R.id.edSingInEmail);
                EditText edPassword = findViewById(R.id.edSignInPassword);

                if (edemail.getText().toString().length() == 0) {
                    edemail.setBackgroundColor(Color.RED);
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(SignIn.this, "fill the empty space", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    edemail.setBackgroundColor(Color.WHITE);
                }
                if (edPassword.getText().toString().length() == 0) {
                    edPassword.setBackgroundColor(Color.RED);
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(SignIn.this, "fill the empty space", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    edPassword.setBackgroundColor(Color.WHITE);
                }

                if (edemail != null && edPassword != null)
                    if (edemail.getText().toString().length() != 0
                            && edPassword.getText().toString().length() != 0) {
                        email = edemail.getText().toString();
                        pass = edPassword.getText().toString();

                        myAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    completion();
                                } else {
                                    Toast.makeText(SignIn.this, "something Wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else
                        Toast.makeText(SignIn.this, "please fill the fields", Toast.LENGTH_SHORT).show();
                findViewById(R.id.progress).setVisibility(View.INVISIBLE);
            }
        });
    }

    public void completion() {
        verify();
    }

    //here we need to verify the user's input data
    public void verify() {
        if (myAuth.getCurrentUser().isEmailVerified()) {
            String id = new MyHelper(getApplicationContext()).getIDofSpecificUser(email);
            SharedPreferences sp = getSharedPreferences("User_SP", MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("User_ID", id);
            edit.commit();
            Intent i = new Intent(getApplicationContext(), NavBarActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "please verify your account ...", Toast.LENGTH_SHORT).show();
        }
    }
}