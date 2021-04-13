package ca.unb.mobiledev.crossystreet;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.WindowManager;
import android.widget.FrameLayout;


import androidx.appcompat.app.AppCompatActivity;

public class Tutorial_Activity extends AppCompatActivity {

    private Tutorial_GameView gameView;

    private MediaPlayer mediaPlayerL;
    public MediaPlayer mediaPlayerCrash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mediaPlayerL = MediaPlayer.create(Tutorial_Activity.this, R.raw.traffic1);
        mediaPlayerL.setLooping(true);
        mediaPlayerL.setVolume(50,50);
        mediaPlayerL.start();

        mediaPlayerCrash = MediaPlayer.create(Tutorial_Activity.this, R.raw.impact_car_crash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        //create L1 gameview
        gameView = new Tutorial_GameView(this, point.x, point.y);
        FrameLayout gameLayout = new FrameLayout(getApplicationContext());

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        gameLayout.setLayoutParams(lp);
        gameLayout.addView(gameView);
        setContentView(gameLayout);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayerL.pause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        mediaPlayerL.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}