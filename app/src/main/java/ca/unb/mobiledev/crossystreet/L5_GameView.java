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

public class L5_GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPass = false, isPlaying, isGameOver = false;
    private L5_Background background5;
    public int screenX, screenY, score = 0;
    private SharedPreferences prefs;
    private Paint paint;
    private Player player;

    private Car1_left car1;
    private Car4_right car2;
    private Car3_right car3;
    private Car2_left car4;

    private Random random;
    public L5_Activity activity;
    AlertDialog.Builder dialogBuilder;
    AlertDialog gameOverDialog, passDialog;
    Bitmap citizenIcon;
    Vibrator vibrator;

    public L5_GameView(L5_Activity activity, int screenX, int screenY){
        super(activity);

        this.activity = activity;

        //share preference to store new high score
        prefs = activity.getSharedPreferences("L5", Context.MODE_PRIVATE);
        this.screenX = screenX;
        this.screenY = screenY;
        background5 = new L5_Background(screenX, screenY, getResources());

        player = new Player(screenX, getResources());

        paint = new Paint();
        paint.setTextSize(80);
        paint.setColor(Color.BLACK);

        car1 = new Car1_left(getResources());
        car2 = new Car4_right(getResources());
        car3 = new Car3_right(getResources());
        car4 = new Car2_left(getResources());

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
        background5.x = 0;
        background5.y = 0;

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
            car1.speed = random.nextInt(45);
            if(car1.speed < 15){
                car1.speed = 15;
            }
            //car1 can only go in lane y=1640 or y=1380
            int r = random.nextInt(2);
            if(r == 1){
                car1.y = 1510;
            }
            if(r == 0){
                car1.y = 1250;
            }
            //go back to its start position and change speed
            car1.x = 1250;
        }

        car2.x += car2.speed;
        if(car2.x + car2.width > 1260){

            car2.speed = random.nextInt(45);
            if(car2.speed < 20){
                car2.speed = 20;
            }
            //car2 can only go in lane y=490 or y=750
            int r = random.nextInt(2);
            if(r == 1){
                car2.y = 620;
            }
            if(r == 0){
                car2.y = 1130;
            }
            //go back to its start position and change speed
            car2.x = -250;
        }

        car3.x += car3.speed;
        if(car3.x + car3.width > 1250){
            car3.speed = random.nextInt(50);
            if(car3.speed < 25){
                car3.speed = 25;
            }
            //car3 can only go in lane y=825 or y=570
            int r = random.nextInt(2);
            if(r == 1){
                car3.y = 1410;
            }
            if(r == 0){
                car3.y = 890;
            }
            //go back to its start position and change speed
            car3.x = -270;
        }

        car4.x -= car4.speed;
        if(car4.x + car4.width < -250){
            car4.speed = random.nextInt(45);
            if(car4.speed < 20){
                car4.speed = 20;
            }

            int r = random.nextInt(5);
            if(r == 0){
                car4.y = 730;
            }
            if(r == 3){
                car4.y = 1000;
            }
            car4.x = 1280;

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

        if(Rect.intersects(car4.getCollision(), player.getCollision())){
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
            canvas.drawBitmap(background5.background, background5.x, background5.y, paint);

            canvas.drawBitmap(car1.getCar(),car1.x, car1.y, paint);
            canvas.drawBitmap(car2.getCar(),car2.x, car2.y, paint);
            canvas.drawBitmap(car3.getCar(),car3.x, car3.y, paint);
            canvas.drawBitmap(car4.getCar(),car4.x, car4.y, paint);

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
        if(prefs.getInt("highscore5", 0) < score){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore5", score);
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
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectActivity.class);
                activity.startActivity(intent);
            }
        });
        restart_button.setOnClickListener(new View.OnClickListener() {
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
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectActivity.class);
                activity.startActivity(intent);
            }
        });
        restart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = activity.getIntent();
                activity.finish();
                activity.startActivity(intent);
            }
        });
    }
}

