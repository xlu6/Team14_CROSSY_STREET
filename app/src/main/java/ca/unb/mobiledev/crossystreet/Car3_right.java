package ca.unb.mobiledev.crossystreet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Car3_right {
    int x, y, width, height;
    Random ran =  new Random();
    int speed = ran.nextInt(20)+10;
    Bitmap car;

    Car3_right(Resources res){
        car = BitmapFactory.decodeResource(res, R.drawable.car3_right);

        width = car.getWidth();
        height = car.getHeight();
        width /= 3;
        height /= 3;
        x=1080;
        y=2500;

        car = Bitmap.createScaledBitmap(car, width, height, false);
    }

    Bitmap getCar(){
        return car;
    }

    Rect getCollision(){
        return new Rect(x, y, x+width, y+height);
    }
}
