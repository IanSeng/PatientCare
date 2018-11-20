package com.example.mukha.picmymedcode.View;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mukha.picmymedcode.Controller.PicMyMedApplication;
import com.example.mukha.picmymedcode.Controller.PicMyMedController;
import com.example.mukha.picmymedcode.R;
import com.example.mukha.picmymedcode.Model.Login;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button loginBtn = (Button) findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText enteredUsername = (EditText) findViewById(R.id.enteredUID);
                String username = enteredUsername.getText().toString();
                Login login = new Login();
                if (PicMyMedController.checkLogin(username) == 1) {
                    if(PicMyMedApplication.getLoggedInUser().isPatient()){
                        Intent problemIntent = new Intent(MainActivity.this, ProblemActivity.class);
                        startActivity(problemIntent);
                    }
                    else {
                        //toastMessage("Careprovider activity to be implemented.");
                        Intent patientIntent = new Intent(MainActivity.this, CareProviderActivity.class);
                        startActivity(patientIntent);
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Invalid username",
                            Toast.LENGTH_LONG).show();
                }

            }
        });


        Button signupBtn = (Button) findViewById(R.id.signUpButton);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              //  signupPopUpWindow();

                Intent problemIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(problemIntent);
                finish();
            }
        });
    }

    public void signupPopUpWindow() {
        final Dialog signupPopUp = new Dialog(MainActivity.this);
        signupPopUp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        signupPopUp.setContentView(R.layout.signup_activity);
        signupPopUp.setTitle("Sign Up");

        Button patient = (Button) signupPopUp.findViewById(R.id.patientButton);
        Button careProvider = (Button) signupPopUp.findViewById(R.id.careProviderButton);

        patient.setEnabled(true);
        careProvider.setEnabled(true);

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupPopUp.cancel();
                Toast.makeText(MainActivity.this, "User is a patient", Toast.LENGTH_LONG).show();
                usernamePopUpWindow();

                //setContentView(R.layout.addproblem_activity);
            }
        });

        careProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupPopUp.cancel();
                Toast.makeText(getApplicationContext(), "User is a care provider", Toast.LENGTH_LONG).show();
                String username = usernamePopUpWindow();
                Toast.makeText(getApplicationContext(), username, Toast.LENGTH_LONG).show();
                setContentView(R.layout.newcareprovider_activity);
                TextView setUserName = (TextView)findViewById(R.id.careProviderName);
                String welcome = "Welcome " + username ;
                setUserName.setText(welcome);
            }
        });

        signupPopUp.show();

    }


    public String usernamePopUpWindow() {
        final Dialog usernamePopUp = new Dialog(MainActivity.this);
        usernamePopUp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        usernamePopUp.setContentView(R.layout.newusername_activity);
        usernamePopUp.setTitle("Pick a Username");

        Button submit = (Button) usernamePopUp.findViewById(R.id.signUpButton);

        submit.setEnabled(true);
        EditText enteredUsername = (EditText) usernamePopUp.findViewById(R.id.enteredUID);
        String username = enteredUsername.getText().toString();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernamePopUp.cancel();
            }
        });
        usernamePopUp.show();
        return username;

    }
    public void toastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
