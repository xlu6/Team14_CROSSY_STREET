package ca.unb.mobiledev.crossystreet;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class L4_Activity extends AppCompatActivity {

    private L4_GameView gameView;

    //pause popup window
    Dialog pauseDialog;

    private MediaPlayer mediaPlayerL;
    public MediaPlayer mediaPlayerCrash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mediaPlayerL = MediaPlayer.create(L4_Activity.this, R.raw.traffic1);
        mediaPlayerL.setLooping(true);
        mediaPlayerL.start();

        mediaPlayerCrash = MediaPlayer.create(L4_Activity.this, R.raw.impact_car_crash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        //create L1 gameview
        gameView = new L4_GameView(this, point.x, point.y);
        FrameLayout gameLayout = new FrameLayout(getApplicationContext());

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        gameLayout.setLayoutParams(lp);

        pauseDialog = new Dialog(this);

        //initiate pause button on Level 4
        Button pauseButton = new Button(this);
        FrameLayout.LayoutParams pause = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.LEFT|Gravity.BOTTOM
        );
        pauseButton.setLayoutParams(pause);
        pauseButton.setText("PAUSE");
        pauseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                onPause();
                showPausePopup(v);
            }
        });
        gameLayout.addView(pauseButton);
        gameLayout.addView(gameView);
        setContentView(gameLayout);

    }

    //content of the pause popup window
    public void showPausePopup(View v){
        ImageButton home_button, restart_button, resume_button;
        pauseDialog.setContentView(R.layout.pause_popup);
        home_button = (ImageButton)pauseDialog.findViewById(R.id.homeImage);
        resume_button = (ImageButton)pauseDialog.findViewById(R.id.resumeImage);
        restart_button = (ImageButton)pauseDialog.findViewById(R.id.restartImage);
        pauseDialog.show();
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        resume_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResume();
                pauseDialog.dismiss();
            }
        });
        restart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        mediaPlayerL.pause();
        gameView.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        gameView.resume();
        mediaPlayerL.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
