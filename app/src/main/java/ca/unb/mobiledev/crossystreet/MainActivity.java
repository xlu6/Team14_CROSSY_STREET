package ca.unb.mobiledev.crossystreet;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView changeButton;
    private ImageView startButton;
    private ImageView leaderBoardButton;
    private Button tutButton;


    private MediaPlayer mediaPlayer;
    private int maxVolume =30;
    private float log1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log1 = (float)(Math.log(maxVolume - 10)/Math.log(maxVolume));

        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.background);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(log1, log1);
        mediaPlayer.start();


        tutButton = findViewById(R.id.tutorial);
        tutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Tutorial_Activity.class);
                startActivity(intent);
            }
        });

        startButton = findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                startActivity(intent);
            }
        });

        leaderBoardButton = (ImageView) findViewById(R.id.imageButton2);
        leaderBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LeaderBoardActivity.class));
            }
        });

        changeButton = (ImageView) findViewById(R.id.imageView);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChangeCharacter.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

