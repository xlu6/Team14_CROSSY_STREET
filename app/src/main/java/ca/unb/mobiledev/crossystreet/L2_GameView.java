package ca.unb.mobiledev.crossystreet;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class L2_GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPass = false, isPlaying, isGameOver = false;
    private L2_Background background2;
    public int screenX, screenY, score = 0;
    private SharedPreferences prefs;
    private Paint paint;
    private Player player;
    private AmbulanceNight_left car1;
    private TaxiNight_right car2;
    private CarANight_left car3;
    private Random random;
    public L2_Activity activity;
    AlertDialog.Builder dialogBuilder;
    AlertDialog gameOverDialog, passDialog;
    Bitmap citizenIcon;
    Vibrator vibrator;

    public L2_GameView(L2_Activity activity, int screenX, int screenY){
        super(activity);

        this.activity = activity;

        //share preference to store new high score
        prefs = activity.getSharedPreferences("L2", Context.MODE_PRIVATE);
        this.screenX = screenX;
        this.screenY = screenY;
        background2 = new L2_Background(screenX, screenY, getResources());

        player = new Player(screenX, getResources());

        paint = new Paint();
        paint.setTextSize(80);
        paint.setColor(Color.BLACK);

        car1 = new AmbulanceNight_left(getResources());
        car2 = new TaxiNight_right(getResources());
        car3 = new CarANight_left(getResources());

        //Initialize a vibrator, when the character gets hit by a car, the phone vibrates
        vibrator = (Vibrator)activity.getSystemService(activity.VIBRATOR_SERVICE);

        citizenIcon = BitmapFactory.decodeResource(getResources(),R.drawable.citizen);
        random = new Random();
    }

    @Override
    public void run(){
        while(isPlaying){
            update();
            draw();
            sleep();
        }
    }

    private void update(){
        background2.x = 0;
        background2.y = 0;

        //when tap on screen, character move up the y-axis by 25 pixel, and score+10
        if(player.isGoingUp){
            player.y -= 25;
            score+=10;
        }

        //when the character go beyong the screen, level passed, show popup window
        if(player.y < 200){
            isPass = true;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showPassPopup();
                }
            });
        }

        if(player.y > screenY - player.height){
            player.y = screenY - player.height;
        }

        //car speed
        car1.x -= car1.speed;
        if(car1.x + car1.width < -200){
            car1.speed = random.nextInt(35);
            if(car1.speed < 10){
                car1.speed = 15;
            }
            //car1 can only go in lane y=1255 or y=890
            int r = random.nextInt(2);
            if(r == 1){
                car1.y = 1255;
            }
            if(r == 0){
                car1.y = 890;
            }
            //go back to its start position and change speed
            car1.x = 1200;
        }

        car2.x += car2.speed;
        if(car2.x + car2.width > 1250){

            car2.speed = random.nextInt(20);
            if(car2.speed < 5){
                car2.speed = 5;
            }
            //car2 can only go in lane y=730 or y=990
            int r = random.nextInt(2);
            if(r == 1){
                car2.y = 730;
            }
            if(r == 0){
                car2.y = 990;
            }
            //go back to its start position and change speed
            car2.x = -200;
        }

        car3.x -= car3.speed;
        if(car3.x + car3.width < -200){
            car3.speed = random.nextInt(20);
            if(car3.speed < 10){
                car3.speed = 10;
            }
            //car3 can only go in lane y=1150 or y=630
            int r = random.nextInt(2);
            if(r == 1){
                car3.y = 1150;
            }
            if(r == 0){
                car3.y = 630;
            }
            //go back to its start position and change speed
            car3.x = 1250;
        }

        //game over if character intersects with one of the car
        if(Rect.intersects(car1.getCollision(), player.getCollision())){
            isGameOver = true;
            vibrator.vibrate(500);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                 showGameOverPopup();
                }
        });
            return;
        }
        if(Rect.intersects(car2.getCollision(), player.getCollision())){
            isGameOver = true;
            vibrator.vibrate(500);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showGameOverPopup();
                }
            });
            return;
        }
        if(Rect.intersects(car3.getCollision(), player.getCollision())){
            isGameOver = true;
            vibrator.vibrate(500);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showGameOverPopup();
                }
            });
            return;
        }
    }

    private void draw(){
        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            canvas.drawBitmap(car1.getCar(),car1.x, car1.y, paint);
            canvas.drawBitmap(car2.getCar(),car2.x, car2.y, paint);
            canvas.drawBitmap(car3.getCar(),car3.x, car3.y, paint);

            canvas.drawText("SCORE: " + score, 600, 100, paint);

            //if gameover, draw the dead character on screen
            if(isGameOver){
                isPlaying = false;
                canvas.drawBitmap(player.getDead(), player.x, player.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveHighScore();
                return;
            }
            //if passed, stop the game, and show popup;
            if(isPass){
                isPlaying = false;
                getHolder().unlockCanvasAndPost(canvas);
                saveHighScore();
                return;
            }
            canvas.drawBitmap(player.getPlayer(), player.x, player.y, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void saveHighScore() {
        if(prefs.getInt("highscore2", 0) < score){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore2", score);
            editor.apply();
        }
    }

    private void sleep(){
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume(){
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause(){
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX() < screenY/2){
                    player.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                player.isGoingUp = false;
                break;
        }
        return true;
    }

    //content of game over popup
    public void showGameOverPopup(){
        activity.mediaPlayerCrash.start();

        TextView scoretext;
        ImageButton home_button, restart_button, select_button;
        dialogBuilder = new AlertDialog.Builder(getContext());
        final View gameOverPopup = activity.getLayoutInflater().inflate(R.layout.gameover_popup, null);
        home_button = (ImageButton)gameOverPopup.findViewById(R.id.homeImage);
        select_button = (ImageButton)gameOverPopup.findViewById(R.id.selectLevelImage);
        restart_button = (ImageButton)gameOverPopup.findViewById(R.id.restartImage);
        scoretext = (TextView)gameOverPopup.findViewById(R.id.score);
        scoretext.setText("SCORE: " + score);
        dialogBuilder.setView(gameOverPopup);
        gameOverDialog = dialogBuilder.create();
        gameOverDialog.getWindow().setGravity(Gravity.TOP);
        gameOverDialog.show();
        home_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        select_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectActivity.class);
                activity.startActivity(intent);
            }
        });
        restart_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = activity.getIntent();
                activity.finish();
                activity.startActivity(intent);
            }
        });
    }

    //content of passed popup
    public void showPassPopup() {
        TextView scoretext;
        ImageButton home_button, restart_button, select_button;
        dialogBuilder = new AlertDialog.Builder(getContext());
        final View passPopup = activity.getLayoutInflater().inflate(R.layout.pass_popup, null);
        home_button = (ImageButton)passPopup.findViewById(R.id.homeImage);
        select_button = (ImageButton)passPopup.findViewById(R.id.selectLevelImage);
        restart_button = (ImageButton)passPopup.findViewById(R.id.restartImage);
        scoretext = (TextView)passPopup.findViewById(R.id.score);
        scoretext.setText("SCORE: " + score);
        dialogBuilder.setView(passPopup);
        passDialog = dialogBuilder.create();
        passDialog.show();
        home_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        select_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectActivity.class);
                activity.startActivity(intent);
            }
        });
        restart_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = activity.getIntent();
                activity.finish();
                activity.startActivity(intent);
            }
        });
    }
}
