package ca.unb.mobiledev.crossystreet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class CarVanNight_left {
    int x, y, width, height;
    Random ran =  new Random();
    int speed = ran.nextInt(20)+10;
    Bitmap car;
    CarVanNight_left(Resources res){
        car = BitmapFactory.decodeResource(res, R.drawable.van_night_right);

        width = car.getWidth();
        height = car.getHeight();
        width /= 3;
        height /= 3;
        x=0;
        y=750;

        car = Bitmap.createScaledBitmap(car, width, height, false);
    }

    Bitmap getCar(){return car;}

    Rect getCollision(){return new Rect(x, y, x+width, y+height);}
}
