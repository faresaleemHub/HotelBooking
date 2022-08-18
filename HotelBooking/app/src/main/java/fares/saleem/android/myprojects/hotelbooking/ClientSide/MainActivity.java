package fares.saleem.android.myprojects.hotelbooking.ClientSide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import fares.saleem.android.myprojects.hotelbooking.CP;
import fares.saleem.android.myprojects.hotelbooking.ClientSide.Participation.SignIn;
import fares.saleem.android.myprojects.hotelbooking.ClientSide.Participation.SignUp;
import fares.saleem.android.myprojects.hotelbooking.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //variable for authentication
    FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        myAuth = FirebaseAuth.getInstance();

        findViewById(R.id.up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });

        findViewById(R.id.in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignIn.class));
            }
        });

        //for me
        findViewById(R.id.btn_cp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CP.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (!getSharedPreferences("User_SP", MODE_PRIVATE)
                    .getString("User_ID", null).equals("-1")) {
                startActivity(new Intent(getApplicationContext(), NavBarActivity.class));
            }
        }catch (Exception e ){}
//        if (myAuth.getCurrentUser() != null && myAuth.getCurrentUser().isEmailVerified()) {
//            //if the user of this app wasn't login before
//            //it's like session in web
//            startActivity(new Intent(getApplicationContext(), NavBarActivity.class));
//            //هان اذا كان المستخدم مسجل دخول مسبقاً لن يتم الدخول هنا
//        }
    }
}