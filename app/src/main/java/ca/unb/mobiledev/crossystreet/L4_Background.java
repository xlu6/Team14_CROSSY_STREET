//set Level 4 background to the apprapriate background from res.drawable
package ca.unb.mobiledev.crossystreet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class L4_Background {
    int x=0, y=0;
    Bitmap background;

    L4_Background (int screenx, int screeny, Resources res){
        background = BitmapFactory.decodeResource(res, R.drawable.background4);
        background = Bitmap.createScaledBitmap(background, screenx, screeny, false);
    }
}
