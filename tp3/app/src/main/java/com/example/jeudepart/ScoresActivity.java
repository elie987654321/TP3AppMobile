package com.example.jeudepart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoresActivity extends AppCompatActivity {

    LinearLayout scoresContainer;
    Map<String, ScoreDetails> userScoresMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        scoresContainer = findViewById(R.id.scoresContainer);
        userScoresMap = new HashMap<>();

        afficherLesScores();
    }

    public void afficherLesScores() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://app-mobile-api.vercel.app/api/scores";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        // Populate userScoresMap with the lowest scores and dates of each user
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject scoreObj = jsonArray.getJSONObject(i);
                            int score = scoreObj.getInt("score");
                            String scoreDate = scoreObj.getString("scoreDate");
                            String user = scoreObj.getString("user_id");

                            if (!userScoresMap.containsKey(user) || score < userScoresMap.get(user).getScore()) {
                                ScoreDetails details = new ScoreDetails(score, scoreDate);
                                userScoresMap.put(user, details);
                            }
                        }

                        // Sort the scores by ascending order
                        List<Map.Entry<String, ScoreDetails>> sortedScores = new ArrayList<>(userScoresMap.entrySet());
                        Collections.sort(sortedScores, (o1, o2) -> o1.getValue().getScore() - o2.getValue().getScore());

                        // Display the sorted and lowest scores of each user
                        for (Map.Entry<String, ScoreDetails> entry : sortedScores) {
                            String user = entry.getKey();
                            ScoreDetails details = entry.getValue();
                            String userScoreText = "User: " + user + ", Score: " + details.getScore() + ", Date: " + details.getScoreDate();
                            TextView textView = new TextView(this);
                            textView.setText(userScoreText);
                            scoresContainer.addView(textView);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(ScoresActivity.this, "Error calling API", Toast.LENGTH_SHORT).show();
                });

        queue.add(jsonObjectRequest);
    }

    // Helper class to hold score details (score and date)
    private static class ScoreDetails {
        private final int score;
        private final String scoreDate;

        public ScoreDetails(int score, String scoreDate) {
            this.score = score;
            this.scoreDate = scoreDate;
        }

        public int getScore() {
            return score;
        }

        public String getScoreDate() {
            return scoreDate;
        }
    }
}
