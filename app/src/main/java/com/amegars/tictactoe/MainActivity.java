package com.amegars.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private boolean soundIsOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button)findViewById(R.id.button);
        Button b2 = (Button)findViewById(R.id.button2);
        Button b3 = (Button)findViewById(R.id.button3);
        b.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.button:
                i = new Intent(this, GameScreen.class);
                i.putExtra("two player", false);
                i.putExtra("soundIsOn", soundIsOn);
                startActivity(i);
                break;
            case R.id.button2:
                i = new Intent(this, GameScreen.class);
                i.putExtra("two player", true);
                i.putExtra("soundIsOn", soundIsOn);
                startActivity(i);
                break;
            case R.id.button3:
                if(soundIsOn){
                    soundIsOn = !soundIsOn;
                    ((Button)findViewById(R.id.button3)).setText("Sound Off");
                } else {
                    soundIsOn = !soundIsOn;
                    ((Button)findViewById(R.id.button3)).setText("Sound On");
                }

                break;

        }

    }

}
