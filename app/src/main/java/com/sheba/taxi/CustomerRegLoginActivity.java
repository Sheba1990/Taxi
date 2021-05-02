package com.sheba.taxi;

import android.app.ProgressDialog;
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

public class CustomerRegLoginActivity extends AppCompatActivity {

    TextView customerStatus, accountCreate;
    Button signInBtn, signUpBtn;
    EditText emailEditText, passwordEditText;

    FirebaseAuth mAuth;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reg_login);

        customerStatus = (TextView) findViewById(R.id.statusCustomer);
        accountCreate = (TextView) findViewById(R.id.accountCreate);
        signInBtn = (Button) findViewById(R.id.signInCustomer);
        signUpBtn = (Button) findViewById(R.id.signUpCustomer);
        emailEditText = (EditText) findViewById(R.id.customerEmail);
        passwordEditText = (EditText) findViewById(R.id.customerPassword);

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        signUpBtn.setVisibility(View.INVISIBLE);
        signUpBtn.setEnabled(false);

        accountCreate.setOnClickListener((v) -> {
            signInBtn.setVisibility(View.INVISIBLE);
            accountCreate.setVisibility(View.INVISIBLE);
            signUpBtn.setVisibility(View.VISIBLE);
            signUpBtn.setEnabled(true);
            customerStatus.setText("Registration of new customer");

        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                RegisterCustomer(email, password);
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                SignInCustomer(email, password);
            }
        });
    }

    private void SignInCustomer(String email, String password) {
        loadingBar.setTitle("Customer Entry");
        loadingBar.setMessage("Please, Wait!");
        loadingBar.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CustomerRegLoginActivity.this, "Sign In Successful!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                } else {
                    Toast.makeText(CustomerRegLoginActivity.this, "Error! Try again.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });
    }

    private void RegisterCustomer(String email, String password) {

        loadingBar.setTitle("Customer Registration");
        loadingBar.setMessage("Please, Wait!");
        loadingBar.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CustomerRegLoginActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                } else {
                    Toast.makeText(CustomerRegLoginActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });
    }
}