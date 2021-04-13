// get the bitmap of character from drawable
package ca.unb.mobiledev.crossystreet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;

public class Player {
    boolean isGoingUp = false;
    int x, y, width, height, walk = 0;

    //player1+player2+player3 to make the character looks like he is walking forward
    //and dead for when character is hit by car
    Bitmap player1, player2, player3, dead, playerLeft1, playerLeft2, playerLeft3, playerRight1, playerRight2, playerRight3;

    Player(int screenx, Resources res){
        player1 = BitmapFactory.decodeResource(res, R.drawable.walk1);
        player2 = BitmapFactory.decodeResource(res, R.drawable.walk2);
        player3 = BitmapFactory.decodeResource(res, R.drawable.walk3);

        width = player1.getWidth();
        height = player1.getHeight();

        //size of the character
        width /= 10;
        height /= 10;

        player1 = Bitmap.createScaledBitmap(player1, width, height, false);
        player2 = Bitmap.createScaledBitmap(player2, width, height, false);
        player3 = Bitmap.createScaledBitmap(player3, width, height, false);

        dead = BitmapFactory.decodeResource(res, R.drawable.dead);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);

        //start point of the character
        y = 1750;
        x = 450;
    }

    Bitmap getPlayer(){
        if(walk == 0){
            walk++;
            return player1;
        }
        if(walk == 1){
            walk++;
            return player2;
        }
            walk=0;
            return player3;
    }

    Rect getCollision(){
        return new Rect(x+70, y+170, x+width-60, y+height-80);
    }

    Bitmap getDead(){
        return dead;
    }
}
