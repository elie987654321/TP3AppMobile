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
        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!isValidate(emailInput.getText().toString())) {
                    emailInput.setError("Addresse Courriel non valide");
                  //  buttonConnection.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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