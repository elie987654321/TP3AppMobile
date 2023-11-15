package com.example.jeudepart;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.HttpURLConnection;
import java.net.URL;

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
                if (!isEmailValid(emailInput.getText().toString())){
                    emailInput.setError("Email non valide");
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

        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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
        // Create a JSONObject to hold the data
        JSONObject requestData = new JSONObject();
        try {
            // Example: Adding email and password to the request body
            requestData.put("email", emailInput.getText().toString());
            requestData.put("password", passwordInput.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://app-mobile-api.vercel.app/api/connectWithUsernameAndPassword";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestData,
                response -> {
                    try {
                        JSONArray dataArray = response.getJSONArray("data");

                        if (dataArray.length() > 0) {
                            JSONObject userData = dataArray.getJSONObject(0);

                            // Extract user details from JSON
                            int user_id = userData.getInt("user_id");
                            String prenom = userData.getString("prenom");
                            String nom = userData.getString("nom");
                            String email = userData.getString("email");
                            String password = userData.getString("password");
                            String pays = userData.getString("pays");



                            // Proceed with the user object as needed in your application
                            // For instance, you can pass this user object to the next activity
                            Intent intent = new Intent(ConnexionActivity.this, JeuxActivity.class);
                            intent.putExtra("user_id", user_id);
                            intent.putExtra("prenom", prenom);
                            intent.putExtra("nom", nom);
                            intent.putExtra("email", email);
                            intent.putExtra("password", password);
                            intent.putExtra("pays", pays);
                            startActivity(intent);
                        } else {
                            // Handle case where no user data is returned
                            Toast.makeText(ConnexionActivity.this, "Mauvais identifiants", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ConnexionActivity.this, "Error parsing user data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
            Toast.makeText(ConnexionActivity.this, "Error calling API", Toast.LENGTH_SHORT).show();
        });

        queue.add(jsonObjectRequest);
    }

    public void creationOnClick(View view){
        Intent intent = new Intent(ConnexionActivity.this, CreationActivity.class);
        startActivity(intent);
    }

    public void scoresOnClick(View view){
        Intent intent = new Intent(ConnexionActivity.this, ScoresActivity.class);
        startActivity(intent);
    }
}