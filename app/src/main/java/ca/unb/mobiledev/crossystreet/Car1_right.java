package ca.unb.mobiledev.crossystreet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Car1_right {
    int x, y, width, height;
    Random ran =  new Random();
    int speed = ran.nextInt(35)+15;
    Bitmap car;
    Car1_right(Resources res){
        car = BitmapFactory.decodeResource(res, R.drawable.car1_right);

        width = car.getWidth();
        height = car.getHeight();
        width /= 3;
        height /= 3;
        x=1080;
        y=845;

        car = Bitmap.createScaledBitmap(car, width, height, false);
    }

    Bitmap getCar(){return car;}

    Rect getCollision(){return new Rect(x, y, x+width, y+height);}
}
