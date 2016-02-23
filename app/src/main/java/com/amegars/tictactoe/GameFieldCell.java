package com.amegars.tictactoe;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageButton;

import java.util.Objects;

/**
 * Created by Amegar on 21.02.2016.
 */
public class GameFieldCell extends ImageButton {
    private String currentSymbol;

    public GameFieldCell(Context context) {
        super(context);
    }

    public GameFieldCell(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameFieldCell(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public void resetState(){
        this.setImageDrawable(null);
        this.currentSymbol = null;
    }

    public void setCurrentSymbol (String currentSymbol){
        this.currentSymbol = currentSymbol;
    }

    public String getCurrentSymbol() {
        return currentSymbol;
    }

    @Override
    public boolean equals (Object o){
        if (o != null){
            GameFieldCell temp = (GameFieldCell)o;
            if (temp.currentSymbol == null) return false;
            if (temp.currentSymbol.equals(this.currentSymbol)){
                return true;
            } else{
                return false;
            }

        }
        return false;


    }

}
