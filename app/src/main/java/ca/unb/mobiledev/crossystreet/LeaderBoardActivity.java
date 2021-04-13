package ca.unb.mobiledev.crossystreet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class LeaderBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        TextView L1_HighScore = findViewById(R.id.L1_highscore);
        SharedPreferences prefs1 = getSharedPreferences("L1", MODE_PRIVATE);
        L1_HighScore.setText("L1: " +prefs1.getInt("highscore1", 0));

        TextView L2_HighScore = findViewById(R.id.L2_highscore);
        SharedPreferences prefs2 = getSharedPreferences("L2", MODE_PRIVATE);
        L2_HighScore.setText("L2: " +prefs2.getInt("highscore2", 0));

        TextView L3_HighScore = findViewById(R.id.L3_highscore);
        SharedPreferences prefs3 = getSharedPreferences("L3", MODE_PRIVATE);
        L3_HighScore.setText("L3: " +prefs3.getInt("highscore3", 0));

        TextView L4_HighScore = findViewById(R.id.L4_highscore);
        SharedPreferences prefs4 = getSharedPreferences("L4", MODE_PRIVATE);
        L4_HighScore.setText("L4: " +prefs4.getInt("highscore4", 0));

        TextView L5_HighScore = findViewById(R.id.L5_highscore);
        SharedPreferences prefs5 = getSharedPreferences("L5", MODE_PRIVATE);
        L5_HighScore.setText("L5: " +prefs5.getInt("highscore5", 0));

        TextView L6_HighScore = findViewById(R.id.L6_highscore);
        SharedPreferences prefs6 = getSharedPreferences("L6", MODE_PRIVATE);
        L6_HighScore.setText("L6: " +prefs6.getInt("highscore6", 0));

    }
}