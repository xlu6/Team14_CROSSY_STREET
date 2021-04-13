package ca.unb.mobiledev.crossystreet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Tutorial_Background {
    int x=0, y=0;
    Bitmap background;

    Tutorial_Background (int screenx, int screeny, Resources res){
        background = BitmapFactory.decodeResource(res, R.drawable.tutorial_background);
        background = Bitmap.createScaledBitmap(background, screenx, screeny, false);
    }
}
