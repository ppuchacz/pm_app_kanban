package com.example.pm_app.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pm_app.Entity.User;
import com.example.pm_app.LocalDatabase;
import com.example.pm_app.R;
import com.example.pm_app.Utils;

public class RegisterActivity extends AppCompatActivity {

    EditText cInputLogin, cInputPassword, cInputRepeatPassword;
    Button cRegisterButton;

    private LocalDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.database = LocalDatabase.getInstance(getApplicationContext());

        cInputLogin = findViewById(R.id.input_new_login);
        cInputPassword = findViewById(R.id.input_new_password);
        cInputRepeatPassword = findViewById(R.id.input_new_repeat_password);
        cRegisterButton = findViewById(R.id.btn_register);
    }

    public void registerButtonClicked(View view) {
        String username = cInputLogin.getText().toString();
        String password = cInputPassword.getText().toString();
        String repeatPassword = cInputRepeatPassword.getText().toString();

        if (username.isEmpty()) {
            showToastMessage("Nazwa użytkownika nie może być pusta");
            return;
        }

        if (password.length() < 6) {
            showToastMessage("Hasło musi zawierać co najmniej 6 znaków");
            return;
        }

        if (!password.equals(repeatPassword)) {
            showToastMessage("Hasła nie są identyczne");
            return;
        }

        User userFound = database.userDao().findOneByUsername(username);
        if (userFound != null) {
            showToastMessage("Nazwa uzytkownika została już zajęta");
            return;
        }

        User newUser = new User(username, Utils.md5(password));
        database.userDao().registerUser(newUser);
        showToastMessage("Zarejestrowano pomyślnie");

        returnToLoginActivity();
    }

    public void goaBackButtonClicked(View view) {
        returnToLoginActivity();
    }

    private void returnToLoginActivity() {
        Intent result = new Intent();
        String username = cInputLogin.getText().toString();
        result.setData(Uri.parse(username));
        setResult(RESULT_OK, result);
        finish();
    }

    private void showToastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}