package com.example.jeudepart;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnexionActivity extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    private Button buttonConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion);
        initEditField();
    }

    private void initEditField() {
        // EMAIL INPUT
        emailInput = findViewById(R.id.emailEdit);
        // PASSWORD INPUT
        passwordInput = findViewById(R.id.motDePasseEdit);
        buttonConnection = findViewById(R.id.buttonConnection);

        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isEmailValid(emailInput.getText().toString()) || !isPasswordValid(passwordInput.getText().toString())) {
                    buttonConnection.setEnabled(false); // Désactiver le bouton si l'email ou le mot de passe n'est pas valide
                } else {
                    buttonConnection.setEnabled(true); // Activer le bouton si l'email et le mot de passe sont valides
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isEmailValid(emailInput.getText().toString())){
                    emailInput.setError("Adresse Courriel non valide");
                }
                if (!isPasswordValid(passwordInput.getText().toString())){
                    passwordInput.setError("Mot de passe non valide");
                }
                if (!isEmailValid(emailInput.getText().toString()) || !isPasswordValid(passwordInput.getText().toString())) {
                    buttonConnection.setEnabled(false); // Désactiver le bouton si l'email ou le mot de passe n'est pas valide
                } else {
                    buttonConnection.setEnabled(true); // Activer le bouton si l'email et le mot de passe sont valides
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Désactiver le bouton au démarrage car aucun champ n'est rempli
        buttonConnection.setEnabled(false);
    }

    private boolean isEmailValid(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 4;
    }


    private boolean isValidate(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public void connectionOnClick(View view) {
        Intent intent = new Intent(ConnexionActivity.this, JeuxActivity.class);
        startActivity(intent);
    }

    public void creationOnClick(View view){
        Intent intent = new Intent(ConnexionActivity.this, CreationActivity.class);
        startActivity(intent);
    }
}