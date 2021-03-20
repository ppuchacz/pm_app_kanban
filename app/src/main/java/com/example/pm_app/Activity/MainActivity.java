package com.example.pm_app.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pm_app.Entity.User;
import com.example.pm_app.LocalDatabase;
import com.example.pm_app.R;
import com.example.pm_app.Utils;

public class MainActivity extends AppCompatActivity {
    static final int REGISTER_USER_REQUEST = 1;

    EditText cInputLogin, cInputPassword;
    Button cLoginButton;

    private LocalDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.database = LocalDatabase.getInstance(getApplicationContext());

        cInputLogin = findViewById(R.id.input_login);
        cInputPassword = findViewById(R.id.input_password);
        cLoginButton = findViewById(R.id.btn_login);
    }

    public void onLoginButtonClicked(View view) {
        String username = cInputLogin.getText().toString();
        String password = cInputPassword.getText().toString();

        User userFound = database.userDao().findOneWithCredentials(username, Utils.md5(password));

        if (userFound != null) {
            System.out.println("------- Logged in user: " + userFound.id + " " + userFound.username);
            showToastMessage("Zalogowano pomyślnie");
        } else {
            showToastMessage("Błędne dane logowania");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REGISTER_USER_REQUEST && resultCode == RESULT_OK && data != null) {
            String username = data.getDataString();
            cInputLogin.setText(username);
            cInputPassword.requestFocus();
        }
    }

    public void onRegisterButtonClicked(View view) {
        startRegisterUserActivity();
    }

    public void startRegisterUserActivity() {
        Intent registerUserIntent = new Intent(this, RegisterActivity.class);
        startActivityForResult(registerUserIntent, REGISTER_USER_REQUEST);
    }

    private void showToastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}