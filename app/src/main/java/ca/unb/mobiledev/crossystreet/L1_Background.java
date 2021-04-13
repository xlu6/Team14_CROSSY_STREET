//set Level 1 background to the apprapriate background from res.drawable
package ca.unb.mobiledev.crossystreet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class L1_Background {
    int x=0, y=0;
    Bitmap background;

    L1_Background (int screenx, int screeny, Resources res){
        background = BitmapFactory.decodeResource(res, R.drawable.background1);
        background = Bitmap.createScaledBitmap(background, screenx, screeny, false);
    }
}
