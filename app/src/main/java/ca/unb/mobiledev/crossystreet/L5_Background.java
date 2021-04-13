package ca.unb.mobiledev.crossystreet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class L5_Background {
    int x=0, y=0;
    Bitmap background;

    L5_Background (int screenx, int screeny, Resources res){
        background = BitmapFactory.decodeResource(res, R.drawable.background5);
        background = Bitmap.createScaledBitmap(background, screenx, screeny, false);
    }
}