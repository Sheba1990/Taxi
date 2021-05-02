package com.sheba.taxi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DriverRegLoginActivity extends AppCompatActivity {

    TextView driverStatus, accountCreate;
    Button signInBtn, signUpBtn;
    EditText emailEditText, passwordEditText;

    FirebaseAuth mAuth;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_reg_login);

        driverStatus = (TextView) findViewById(R.id.statusDriver);
        accountCreate = (TextView) findViewById(R.id.accountCreate);
        signInBtn = (Button) findViewById(R.id.signInDriver);
        signUpBtn = (Button) findViewById(R.id.signUpDriver);
        emailEditText = (EditText) findViewById(R.id.driverEmail);
        passwordEditText = (EditText) findViewById(R.id.driverPassword);

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        signUpBtn.setVisibility(View.INVISIBLE);
        signUpBtn.setEnabled(false);

        accountCreate.setOnClickListener((v) -> {
            signInBtn.setVisibility(View.INVISIBLE);
            accountCreate.setVisibility(View.INVISIBLE);
            signUpBtn.setVisibility(View.VISIBLE);
            signUpBtn.setEnabled(true);
            driverStatus.setText("Registration of new driver");

        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                RegisterDriver(email, password);
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                SignInDriver(email, password);
            }
        });
    }

    private void SignInDriver(String email, String password) {
        loadingBar.setTitle("Driver Entry");
        loadingBar.setMessage("Please, Wait");
        loadingBar.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(DriverRegLoginActivity.this, "Sign In Successful!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                    Intent driverIntent = new Intent(DriverRegLoginActivity.this, DriversMapActivity.class);
                    startActivity(driverIntent);
                } else {
                    Toast.makeText(DriverRegLoginActivity.this, "Error! Try again.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });
    }

    private void RegisterDriver(String email, String password) {

        loadingBar.setTitle("Driver Registration");
        loadingBar.setMessage("Please, Wait");
        loadingBar.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(DriverRegLoginActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                    Intent driverIntent = new Intent(DriverRegLoginActivity.this, DriversMapActivity.class);
                    startActivity(driverIntent);
                } else {
                    Toast.makeText(DriverRegLoginActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });
    }
}