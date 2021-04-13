package ca.unb.mobiledev.crossystreet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectActivity extends AppCompatActivity {
    private CardView level1,level2,level3,level4,level5,level6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        level1 = findViewById(R.id.Level1);
        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, L1_Activity.class);
                startActivity(intent);
            }
        });
        level2 = findViewById(R.id.Level2);
        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, L2_Activity.class);
                startActivity(intent);
            }
        });
        level3 = findViewById(R.id.Level3);
        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, L3_Activity.class);
                startActivity(intent);
            }
        });
        level4 = findViewById(R.id.Level4);
        level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, L4_Activity.class);
                startActivity(intent);
            }
        });
        level5 = findViewById(R.id.Level5);
        level5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, L5_Activity.class);
                startActivity(intent);
            }
        });
        level6 = findViewById(R.id.Level6);
        level6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, L6_Activity.class);
                startActivity(intent);
            }
        });

    }
}