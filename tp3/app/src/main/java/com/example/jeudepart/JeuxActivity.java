package com.example.jeudepart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class JeuxActivity extends AppCompatActivity {

    private EditText txtNumber;
    private Button btnCompare;
    private TextView lblResult;
    private ProgressBar pgbScore;
    private TextView lblHistory;

    private int searchedValue;
    private int score;
    int user_id;
    String prenom;
    String nom;
    String email;
    String password;
    String pays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve data from intent
        Intent intent = getIntent();
        if (intent != null) {
            user_id = intent.getIntExtra("user_id", 0);
            prenom = intent.getStringExtra("prenom");
            nom = intent.getStringExtra("nom");
            email = intent.getStringExtra("email");
            password = intent.getStringExtra("password");
            pays = intent.getStringExtra("pays");
        }

        txtNumber = (EditText) findViewById(R.id.txtNumber);
        btnCompare = (Button) findViewById(R.id.btnCompare);
        lblResult = (TextView) findViewById(R.id.lblResult);
        pgbScore = (ProgressBar) findViewById(R.id.pgbScore);
        lblHistory = (TextView) findViewById(R.id.lblHistory);


        btnCompare.setOnClickListener(btnCompareListener);

        init();

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        double price = 1_000_000.01;
        Log.i("DEBUG", formatter.format(price));

        DateFormat dataFormatter = DateFormat.getDateTimeInstance();
        Log.i("DEBUG", dataFormatter.format(new Date()));
    }

    private void init() {
        score = 0;
        searchedValue = 1 + (int) (Math.random() * 100);
        Log.i("DEBUG", "Searched value : " + searchedValue);

        txtNumber.setText("");
        pgbScore.setProgress(score);
        lblResult.setText("");
        lblHistory.setText("");

        txtNumber.requestFocus();
    }

    private void congratulations() {

        createScore();

        lblResult.setText(R.string.strCongratulations);

        AlertDialog retryAlert = new AlertDialog.Builder(this).create();
        retryAlert.setTitle(R.string.app_name);
        retryAlert.setMessage(getString(R.string.strMessage, score));

        retryAlert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.strYes), new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                init();
            }
        });

        retryAlert.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.strNo), new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        retryAlert.show();
    }

    private View.OnClickListener btnCompareListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("DEBUG", "Button clicked");

            String strNumber = txtNumber.getText().toString();
            if (strNumber.equals("")) return;

            int enteredValue = Integer.parseInt(strNumber);
            lblHistory.append(strNumber + "\r\n");
            pgbScore.incrementProgressBy(1);
            score++;

            if (enteredValue == searchedValue) {
                congratulations();
            } else if (enteredValue < searchedValue) {
                lblResult.setText(R.string.strGreater);
            } else {
                lblResult.setText(R.string.strLower);
            }

            txtNumber.setText("");
            txtNumber.requestFocus();
        }
    };

    private void createScore(){
        // Create a JSONObject to hold the data
        JSONObject requestData = new JSONObject();
        try {
           requestData.put("user_id", String.valueOf(user_id));
            requestData.put("score",String.valueOf(score));
            requestData.put("scoreDate", LocalDateTime.now().toLocalDate());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://app-mobile-api.vercel.app/api/createScore";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestData,
                response -> {

                },
                error -> {
                    Toast.makeText(JeuxActivity.this, "Error calling API", Toast.LENGTH_SHORT).show();
                });

        queue.add(jsonObjectRequest);
    }
}