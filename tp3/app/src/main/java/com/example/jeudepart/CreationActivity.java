package com.example.jeudepart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreationActivity extends AppCompatActivity {

    private EditText prenomEdit;
    private EditText nomEdit;
    private EditText emailEdit;
    private EditText passwordEdit;
    private Button buttonCreation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        initEditField();
    }

    private void initEditField() {
        // PRENOM INPUT
        prenomEdit = findViewById(R.id.prenomEdit);
        // NOM INPUT
        nomEdit = findViewById(R.id.nomEdit);
        // EMAIL INPUT
        emailEdit = findViewById(R.id.emailEdit);
        // PASSWORD INPUT
        passwordEdit = findViewById(R.id.passwordEdit);
        // BUTTON
        buttonCreation = findViewById(R.id.buttonCreation);

        prenomEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!areNormalInputsValid(prenomEdit.getText().toString())) {
                    prenomEdit.setError("Nom non valide");
                }

                buttonDisabler();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        nomEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!areNormalInputsValid(nomEdit.getText().toString())) {
                    nomEdit.setError("Nom non valide");
                }

                buttonDisabler();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        emailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isEmailValid(emailEdit.getText().toString())) {
                    emailEdit.setError("Email non valide");
                }

                buttonDisabler();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        passwordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!areNormalInputsValid(passwordEdit.getText().toString())) {
                    passwordEdit.setError("Mot de passe non valide");
                }

                buttonDisabler();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Désactiver le bouton au démarrage car aucun champ n'est rempli
        buttonCreation.setEnabled(false);
    }

    private boolean isEmailValid(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    private boolean areNormalInputsValid(String password) {
        return password.length() >= 4;
    }

    private void buttonDisabler() {
        if (!isEmailValid(emailEdit.getText().toString()) || !areNormalInputsValid(passwordEdit.getText().toString()) || !areNormalInputsValid(nomEdit.getText().toString()) || !areNormalInputsValid(prenomEdit.getText().toString())) {
            buttonCreation.setEnabled(false); // Désactiver le bouton si l'email ou le mot de passe n'est pas valide
        } else {
            buttonCreation.setEnabled(true); // Activer le bouton si l'email et le mot de passe sont valides
        }
    }

    public void creationCompteOnClick(View view) {
        Intent intent = new Intent(CreationActivity.this, JeuxActivity.class);
        startActivity(intent);
    }
}