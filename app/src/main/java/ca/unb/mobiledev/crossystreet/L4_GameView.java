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
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.Random;

public class L4_GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPass = false, isPlaying, isGameOver = false;
    private L4_Background background4;
    public int screenX, screenY, score = 0;
    private SharedPreferences prefs;
    private Paint paint;
    private Player player;

    private CarVanNight_right car1;
    private CarVanNight_left car2;
    private CarANight_left car3;
    private AmbulanceNight_right car4;

    private Random random;
    public L4_Activity activity;
    AlertDialog.Builder dialogBuilder;
    AlertDialog gameOverDialog, passDialog;
    Bitmap citizenIcon;
    Vibrator vibrator;

    public L4_GameView(L4_Activity activity, int screenX, int screenY){
        super(activity);

        this.activity = activity;

        //share preference to store new high score
        prefs = activity.getSharedPreferences("L4", Context.MODE_PRIVATE);
        this.screenX = screenX;
        this.screenY = screenY;
        background4 = new L4_Background(screenX, screenY, getResources());

        player = new Player(screenX, getResources());

        paint = new Paint();
        paint.setTextSize(80);
        paint.setColor(Color.BLACK);

        car1 = new CarVanNight_right(getResources());
        car2 = new CarVanNight_left(getResources());
        car3 = new CarANight_left(getResources());
        car4 = new AmbulanceNight_right(getResources());

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
        background4.x = 0;
        background4.y = 0;

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
        car1.x += car1.speed;
        if(car1.x + car1.width > 1300){
            car1.speed = random.nextInt(35);
            if(car1.speed < 10){
                car1.speed = 15;
            }
            //car1 can only go in lane y=1640 or y=1380
            int r = random.nextInt(2);
            if(r == 1){
                car1.y = 1640;
            }
            if(r == 0){
                car1.y = 1380;
            }
            //go back to its start position and change speed
            car1.x = -100;
        }

        car2.x -= car2.speed;
        if(car2.x + car2.width < -100){

            car2.speed = random.nextInt(35);
            if(car2.speed < 8){
                car2.speed = 15;
            }
            //car2 can only go in lane y=490 or y=750
            int r = random.nextInt(2);
            if(r == 1){
                car2.y = 490;
            }
            if(r == 0){
                car2.y = 750;
            }
            //go back to its start position and change speed
            car2.x = 1200;
        }

        car3.x -= car3.speed;
        if(car3.x + car3.width < -100){
            car3.speed = random.nextInt(50);
            if(car3.speed < 20){
                car3.speed = 20;
            }
            //car3 can only go in lane y=825 or y=570
            int r = random.nextInt(2);
            if(r == 1){
                car3.y = 825;
            }
            if(r == 0){
                car3.y = 570;
            }
            //go back to its start position and change speed
            car3.x = 1200;
        }

        car4.x += car4.speed;
        if(car4.x + car4.width > 1300){
            car4.speed = random.nextInt(45);
            if(car4.speed < 35){
                car4.speed = 35;
            }

            int r = random.nextInt(5);
            if(r == 0){
                car4.y = 1520;
            }
            if(r == 3){
                car4.y = 1260;
            }else{
                car4.y = 2500;
            }

            car4.x = -100;

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
            canvas.drawBitmap(background4.background, background4.x, background4.y, paint);

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
        if(prefs.getInt("highscore4", 0) < score){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore4", score);
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
