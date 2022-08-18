package fares.saleem.android.myprojects.hotelbooking.ClientSide.Participation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import fares.saleem.android.myprojects.hotelbooking.DB.MyHelper;
import fares.saleem.android.myprojects.hotelbooking.Model.User;
import fares.saleem.android.myprojects.hotelbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignUp extends AppCompatActivity {


    //Global variables and objects
    String Email, Username, Date, Phone, Gender, Location, Password1, Password2;
    MyHelper myHelper;
    FirebaseAuth myAuth;
    DatabaseReference reference;

    //Join checking fields
    EditText email, username, phone, password1, password2, DOB;
    RadioGroup rg;
    RadioButton male, femal;
    Spinner location;
    TextView submit, clear;
    User user;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //for SQLITE_DB and Authentication via firebase
        myHelper = new MyHelper(SignUp.this);
        myAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.edSingUpEmail);
        username = findViewById(R.id.edSignUpUsername);
        phone = findViewById(R.id.edphone);
        rg = findViewById(R.id.rg);
        male = findViewById(R.id.rbmale);
        femal = findViewById(R.id.rbfemale);
        location = findViewById(R.id.spLocation);
        password1 = findViewById(R.id.edSignUpPassword);
        password2 = findViewById(R.id.edConfirmSignUpPassword);
        DOB = findViewById(R.id.edDOB);
        submit = findViewById(R.id.tv_SignUp_btn);
        clear = findViewById(R.id.clear);
        progressBar = findViewById(R.id.progress);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setText("");
                username.setText("");
                phone.setText("");
                password1.setText("");
                password2.setText("");
                DOB.setText("");
            }
        });

        DOB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                DatePickerDialog picker = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        DOB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.setTitle("Choose Date ...");
                picker.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                Email = email.getText().toString();
                Username = username.getText().toString();
                Phone = phone.getText().toString().trim();//acting as ID
                Gender = male.isChecked() ? "Male" : "Female";
                Location = location.getSelectedItem().toString();
                Password1 = password1.getText().toString();
                Password2 = password2.getText().toString();
                Date = DOB.getText().toString();

                //name
                if (Email.equals(null) || Email.equals("")) {
                    email.setBackgroundColor(Color.RED);
                    Toast.makeText(SignUp.this, "fill the empty space", Toast.LENGTH_SHORT)
                            .show();
                    progressBar.setVisibility(View.INVISIBLE);

                    return;
                } else
                    email.setBackgroundColor(Color.WHITE);

                //username
                if (Username.equals(null) || Username.equals("")) {
                    username.setBackgroundColor(Color.RED);
                    Toast.makeText(SignUp.this, "fill the empty space", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                } else
                    username.setBackgroundColor(Color.WHITE);

                String name = Username.toLowerCase();
                boolean state = true;
                int j = 0;
                int i = 0;
                while (state && j < Username.length()) {
                    if (Username.charAt(j) < (char) 97 && Username.charAt(j)
                            != (char) 32 || Username.charAt(j) > (char) 121)
                        state = false;
                    j++;
                }
                if (!state) {
                    username.setBackgroundColor(Color.RED);
                    Toast.makeText(SignUp.this,
                            "the name must contain letters only in English", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                } else
                    username.setBackgroundColor(Color.WHITE);

                //phone
                if (Phone.equals(null) || Phone.equals("")) {
                    phone.setBackgroundColor(Color.RED);
                    Toast.makeText(SignUp.this, "fill the phone empty space",
                            Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                } else
                    phone.setBackgroundColor(Color.WHITE);

                if (Phone.length() != 10) {
                    Toast.makeText(SignUp.this, "the Phone Number must be 10 digits",
                            Toast.LENGTH_SHORT).show();
                    phone.setBackgroundColor(Color.RED);
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                } else
                    phone.setBackgroundColor(Color.WHITE);

                if (Phone.charAt(0) != '0' && Phone.charAt(1) != '5' && (Phone.charAt(2) != '9'
                        || Phone.charAt(2) != '6')) {
                    Toast.makeText(SignUp.this, "the number must begin wiht 059 or 056", Toast.LENGTH_SHORT).show();
                    phone.setBackgroundColor(Color.RED);
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                } else
                    phone.setBackgroundColor(Color.WHITE);

//                if (myHelper.ifExistPhone(Phone)) {
//                    Toast.makeText(SignUp.this,
//                            "This phone number is in use", Toast.LENGTH_SHORT).show();
//                    phone.setBackgroundColor(Color.RED);
//                    progressBar.setVisibility(View.INVISIBLE);
//                    return;
//                } else
//                    phone.setBackgroundColor(Color.WHITE);


                //DateOfBirth
                if (Date.equals(null) || Date.equals("")) {
                    DOB.setBackgroundColor(Color.RED);
                    Toast.makeText(SignUp.this, "fill the empty DOB space", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                } else
                    DOB.setBackgroundColor(Color.WHITE);

                //password
                if (Password1.equals(null) || Password1.equals("")) {
                    password1.setBackgroundColor(Color.RED);
                    Toast.makeText(SignUp.this, "fill the empty password space", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                } else
                    password1.setBackgroundColor(Color.WHITE);

                if (Password1.length() < 8) {
                    password1.setBackgroundColor(Color.RED);
                    Toast.makeText(SignUp.this,
                            "The Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                } else
                    password1.setBackgroundColor(Color.WHITE);

                String pass = Password1;
                state = false;
                for (i = 0; i < pass.length() - 1; i++)
                    for (j = i + 1; j < pass.length(); j++)
                        if (pass.charAt(i) == pass.charAt(j)) {
                            state = true;
                            break;
                        }
                if (state == true) {
                    password1.setBackgroundColor(Color.RED);
                    Toast.makeText(SignUp.this, "for Secret issues\n" +
                            "The password should not contain duplicate characters", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                } else
                    password1.setBackgroundColor(Color.WHITE);

                if (Password1.equalsIgnoreCase(Username)) {
                    Toast.makeText(SignUp.this, "for Secret issues\n" +
                            "The password should differ from your Username", Toast.LENGTH_LONG).show();
                    password1.setBackgroundColor(Color.RED);
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                if (Password1.equals(Phone)) {
                    Toast.makeText(SignUp.this, "for Secret issues\n" +
                            "The password should differ from your Phone No.", Toast.LENGTH_LONG).show();
                    password1.setBackgroundColor(Color.RED);
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                j = 0;
                state = true;
                pass = Password1.toLowerCase();
                while (state && j < pass.length()) {
                    if (pass.charAt(j) < (char) 97 && pass.charAt(j) != (char) 32 || pass.charAt(j) > (char) 121)
                        state = false;
                    j++;
                }
                if (state) {
                    password1.setBackgroundColor(Color.RED);
                    Toast.makeText(SignUp.this, "for Secret issues\n" +
                            "the password must contain at least 1 sympol", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                } else
                    password1.setBackgroundColor(Color.WHITE);

                if (Password2.equals(null) || Password2.equals("")) {
                    password2.setBackgroundColor(Color.RED);
                    Toast.makeText(SignUp.this, "fill the empty password space", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                } else
                    password2.setBackgroundColor(Color.WHITE);

                if (!Password1.equals(Password2)) {
                    Toast.makeText(SignUp.this, "The two passwords are't the same", Toast.LENGTH_SHORT).show();
                    password1.setBackgroundColor(Color.RED);
                    password2.setBackgroundColor(Color.RED);
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                } else {
                    password1.setBackgroundColor(Color.WHITE);
                    password2.setBackgroundColor(Color.WHITE);
                }

                user = new User(Email, Username, Date, Phone, Gender, Location, Password1);
                myHelper.insertUser(user);

                sign_up();

            }
        });
    }

    public void sign_up() {
        myAuth.createUserWithEmailAndPassword(Email, Password1).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        completion(task);
                    }
                });

    }

    public void completion(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            progressBar.setVisibility(View.INVISIBLE);

            //save the user on firebase
            reference = FirebaseDatabase.getInstance()
                    .getReference("Database").child("Users");
            reference.push().setValue(user);

            SendVerification();
        } else {
            Toast.makeText(SignUp.this, "something wrong ! -> " +
                    task.getException().toString(), Toast.LENGTH_LONG).
                    show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    //here we need to send verification to the user
    public void SendVerification() {
        myAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(SignUp.this, "We Send a Verification Message For Your Email", Toast.LENGTH_SHORT).show();
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent i = new Intent(getApplicationContext(), SignIn.class);
                    startActivity(i);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(SignUp.this, "failed with sending verification", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}