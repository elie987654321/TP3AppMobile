package com.example.jeudepart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreationActivity extends AppCompatActivity {

    private EditText prenomEdit;
    private EditText nomEdit;
    private EditText emailEdit;
    private EditText passwordEdit;
    private Button buttonCreation;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        initEditField();

        // Initialize Spinner
        spinner = findViewById(R.id.spinner);

        ArrayList<SpinnerItem> spinnerItems = new ArrayList<>();
        spinnerItems.add(new SpinnerItem("canada", R.drawable.canada));
        spinnerItems.add(new SpinnerItem("france", R.drawable.france));
        spinnerItems.add(new SpinnerItem("etats-unis", R.drawable.united_states_of_america));
        spinnerItems.add(new SpinnerItem("allemagne", R.drawable.germany));
        spinnerItems.add(new SpinnerItem("russie", R.drawable.russian_federation));

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, spinnerItems);
        spinner.setAdapter(adapter);
    }

    private class CustomSpinnerAdapter extends ArrayAdapter<SpinnerItem> {

        CustomSpinnerAdapter(Context context, ArrayList<SpinnerItem> spinnerItems) {
            super(context, 0, spinnerItems);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createItemView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createItemView(position, convertView, parent);
        }

        private View createItemView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_item, parent, false);
            }

            ImageView imageView = convertView.findViewById(R.id.spinnerItemImageView);
            TextView textView = convertView.findViewById(R.id.spinnerItemTextView);

            SpinnerItem currentItem = getItem(position);
            if (currentItem != null) {
                imageView.setImageResource(currentItem.getImageResId());
                textView.setText(currentItem.getText());
            }

            return convertView;
        }
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
        if (!buttonCreation.isEnabled()) {
            return; // If the button is disabled, return without making the API call
        }

        // Create a JSONObject to hold the user data
        JSONObject userData = new JSONObject();
        try {
            userData.put("prenom", prenomEdit.getText().toString());
            userData.put("nom", nomEdit.getText().toString());
            userData.put("email", emailEdit.getText().toString());
            userData.put("password", passwordEdit.getText().toString());
            userData.put("pays", ((SpinnerItem) spinner.getSelectedItem()).getText()); // Assuming SpinnerItem has getText() method
        } catch (JSONException e) {
            e.printStackTrace();
            return; // If JSON creation fails, return without making the API call
        }

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://app-mobile-api.vercel.app/api/createUser";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, userData,
                response -> {
                    // Handle successful API response
                    Toast.makeText(CreationActivity.this, "User created successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreationActivity.this, ConnexionActivity.class);
                    startActivity(intent);
                },
                error -> {
                    // Handle error response
                    Toast.makeText(CreationActivity.this, "Error creating user", Toast.LENGTH_SHORT).show();
                });

        queue.add(jsonObjectRequest);
    }
}