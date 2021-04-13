package ca.unb.mobiledev.crossystreet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Car4_left {
    int x, y, width, height;
    Random ran =  new Random();
    int speed = ran.nextInt(40)+10;
    Bitmap car;

    Car4_left(Resources res){
        car = BitmapFactory.decodeResource(res, R.drawable.car4_left);

        width = car.getWidth();
        height = car.getHeight();
        width /= 3;
        height /= 3;
        x=0;
        y=1630;

        car = Bitmap.createScaledBitmap(car, width, height, false);
    }

    Bitmap getCar(){
        return car;
    }

    Rect getCollision(){
        return new Rect(x, y, x+width, y+height);
    }
}
