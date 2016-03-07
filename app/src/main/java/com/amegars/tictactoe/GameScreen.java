package com.amegars.tictactoe;


import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameScreen extends AppCompatActivity implements View.OnClickListener{
    private boolean isPlayer1turn;
    private Integer p1score, p2score;
    boolean twoPlayer;
    boolean gameFinished;

    private GameFieldCell c00;
    private GameFieldCell c01;
    private GameFieldCell c02;
    private GameFieldCell c10;
    private GameFieldCell c11;
    private GameFieldCell c12;
    private GameFieldCell c20;
    private GameFieldCell c21;
    private GameFieldCell c22;

    private ArrayList<GameFieldCell> cells = new ArrayList<>();

    private Ai ai;

    private MediaPlayer clickMP;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        isPlayer1turn = true;
        gameFinished = false;
        twoPlayer = getIntent().getBooleanExtra("two player", false);

        c00 = (GameFieldCell)findViewById(R.id.imageButton00);
        c01 = (GameFieldCell)findViewById(R.id.imageButton01);
        c02 = (GameFieldCell)findViewById(R.id.imageButton02);
        c10 = (GameFieldCell)findViewById(R.id.imageButton10);
        c11 = (GameFieldCell)findViewById(R.id.imageButton11);
        c12 = (GameFieldCell)findViewById(R.id.imageButton12);
        c20 = (GameFieldCell)findViewById(R.id.imageButton20);
        c21 = (GameFieldCell)findViewById(R.id.imageButton21);
        c22 = (GameFieldCell)findViewById(R.id.imageButton22);

        cells.add(c00);
        cells.add(c01);
        cells.add(c02);
        cells.add(c10);
        cells.add(c11);
        cells.add(c12);
        cells.add(c20);
        cells.add(c21);
        cells.add(c22);

        p1score = new Integer(0);
        p2score = new Integer(0);

        ((TextView)findViewById(R.id.player1Score)).setText(p1score.toString());
        ((TextView)findViewById(R.id.player2Score)).setText(p2score.toString());

        clickMP = MediaPlayer.create(GameScreen.this, R.raw.click);

        ai = new Ai();



        for (GameFieldCell temp : cells){
            temp.setOnClickListener(this);
        }




    }



    @Override
    public void onClick(View v) {
        if(((GameFieldCell)v).getDrawable() == null){
            if (getIntent().getBooleanExtra("soundIsOn", true)){
                clickMP.seekTo(0);
                clickMP.start();
            }



            if(isPlayer1turn){
                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.x));
                ((GameFieldCell)v).setCurrentSymbol("x");

                if (checkWinConditions()){
                    p1score += 1;
                    ((TextView)findViewById(R.id.player1Score)).setText(p1score.toString());
                    Toast.makeText(this, "Player 1 won!", Toast.LENGTH_SHORT).show();
                    setGameFieldEnabled(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            resetGameField();
                        }
                    }, 1000);
                }
                ((TextView)findViewById(R.id.currentPlayer)).setText("Player 2 turn");

            } else {
                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.o));
                ((GameFieldCell)v).setCurrentSymbol("o");
                if (checkWinConditions()){
                    p2score += 1;
                    ((TextView)findViewById(R.id.player2Score)).setText(p2score.toString());
                    Toast.makeText(this, "Player 2 won!", Toast.LENGTH_SHORT).show();
                    setGameFieldEnabled(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            resetGameField();
                        }
                    }, 1000);


                }
                ((TextView)findViewById(R.id.currentPlayer)).setText("Player 1 turn");

            }
            isPlayer1turn = !isPlayer1turn;


            if (!twoPlayer && !gameFinished){
                ai.calculateTurn();

                if (checkWinConditions()){
                    p2score += 1;
                    ((TextView) findViewById(R.id.player2Score)).setText(p2score.toString());
                    Toast.makeText(this, "AI won!", Toast.LENGTH_SHORT).show();
                    setGameFieldEnabled(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            resetGameField();
                        }
                    }, 1000);
                }
                ((TextView)findViewById(R.id.currentPlayer)).setText("Player 1 turn");

                isPlayer1turn = !isPlayer1turn;
            }

        }



    }

    @Override
    protected void onStop (){
        super.onStop();
        clickMP.release();

    }

    private boolean checkWinConditions(){

        if ((c00.getDrawable() != null) && (c00.equals(c01)) && (c00.equals(c02))) return true;
        if ((c10.getDrawable() != null) && (c10.equals(c11)) && (c10.equals(c12))) return true;
        if ((c20.getDrawable() != null) && (c20.equals(c21)) && (c20.equals(c22))) return true;
        if ((c00.getDrawable() != null) && (c00.equals(c10)) && (c00.equals(c20))) return true;
        if ((c01.getDrawable() != null) && (c01.equals(c11)) && (c01.equals(c21))) return true;
        if ((c02.getDrawable() != null) && (c02.equals(c12)) && (c02.equals(c22))) return true;
        if ((c00.getDrawable() != null) && (c00.equals(c11)) && (c00.equals(c22))) return true;
        if ((c02.getDrawable() != null) && (c02.equals(c11)) && (c02.equals(c20))) return true;

        for (GameFieldCell temp : cells){
            if (temp.getDrawable() == null) {
                return false;
            }
        }

        Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();
        setGameFieldEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resetGameField();
            }
        }, 1000);
        return false;
    }

    private void resetGameField(){

        for (GameFieldCell temp: cells){
            temp.resetState();
        }
        setGameFieldEnabled(true);
        if (!isPlayer1turn){
            ai.calculateTurn();

            if (checkWinConditions()){
                p2score += 1;
                ((TextView) findViewById(R.id.player2Score)).setText(p2score.toString());
                Toast.makeText(this, "AI won!", Toast.LENGTH_SHORT).show();
                setGameFieldEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetGameField();
                    }
                }, 1000);
            }
            ((TextView)findViewById(R.id.currentPlayer)).setText("Player 1 turn");

            isPlayer1turn = !isPlayer1turn;
        }

    }

    private void setGameFieldEnabled(Boolean enabled){
        for(GameFieldCell c : cells){
            c.setEnabled(enabled);
        }
        gameFinished = !enabled;
    }



    public class Ai {

        private int xAmount;
        private int oAmount;

        private ArrayList<GameFieldCell> row0 = new  ArrayList<>();
        private ArrayList<GameFieldCell> row1 = new  ArrayList<>();
        private ArrayList<GameFieldCell> row2 = new  ArrayList<>();
        private ArrayList<GameFieldCell> column0 = new  ArrayList<>();
        private ArrayList<GameFieldCell> column1 = new  ArrayList<>();
        private ArrayList<GameFieldCell> column2 = new  ArrayList<>();
        private ArrayList<GameFieldCell> diagonal0 = new  ArrayList<>();
        private ArrayList<GameFieldCell> diagonal1 = new  ArrayList<>();
        private ArrayList<ArrayList> criticalPriority = new ArrayList<>();
        private ArrayList<ArrayList> highPriority = new ArrayList<>();
        private ArrayList<ArrayList> mediumPriority = new ArrayList<>();
        private ArrayList<ArrayList> lowPriority = new ArrayList<>();
        private ArrayList<GameFieldCell> selectedLine;

        private ArrayList[] lines = {row0, row1, row2, column0, column1, column2, diagonal0, diagonal1};



        private Ai(){

            row0.add(c00);
            row0.add(c01);
            row0.add(c02);

            row1.add(c10);
            row1.add(c11);
            row1.add(c12);

            row2.add(c20);
            row2.add(c21);
            row2.add(c22);

            column0.add(c00);
            column0.add(c10);
            column0.add(c20);

            column1.add(c01);
            column1.add(c11);
            column1.add(c21);

            column2.add(c02);
            column2.add(c12);
            column2.add(c22);

            diagonal0.add(c00);
            diagonal0.add(c11);
            diagonal0.add(c22);

            diagonal1.add(c02);
            diagonal1.add(c11);
            diagonal1.add(c20);


        }

        private void calculateTurn (){
            for (ArrayList<GameFieldCell> line : lines){

                xAmount = 0;
                oAmount = 0;

                for (GameFieldCell cell : line){

                    try {
                        switch (cell.getCurrentSymbol()){
                            case "x":
                                xAmount += 1;
                                break;
                            case "o":
                                oAmount += 1;
                                break;
                            default:
                                break;
                        }
                    } catch (NullPointerException e) {
                    }
                }




                if((xAmount + oAmount) == 3) {
                    continue;
                }
                if((xAmount + oAmount) == 2){
                    if (oAmount == 2){
                        criticalPriority.add(line);
                    }
                    if (xAmount == 2){
                        highPriority.add(line);
                    }
                    if (xAmount == 1) {
                        lowPriority.add(line);
                    }
                }
                if ((xAmount + oAmount) == 1){
                    if(xAmount == 1){
                        lowPriority.add(line);
                    }
                    if(oAmount == 1){
                        mediumPriority.add(line);
                    }
                }
                if ((xAmount + oAmount) == 0){
                    lowPriority.add(line);
                }

            }

            chooseLine();

            while (true){

                int i = (int) (Math.random() * selectedLine.size());
                // Log.d("while_loop", "" + i);
                GameFieldCell seletedCell = selectedLine.get(i);
                if (seletedCell.getCurrentSymbol() == null){
                    seletedCell.setCurrentSymbol("o");
                    seletedCell.setImageDrawable(getResources().getDrawable(R.drawable.o));
                    criticalPriority.clear();
                    highPriority.clear();
                    mediumPriority.clear();
                    lowPriority.clear();
                    break;
                }


            }
        }

        private void chooseLine (){
            if (!criticalPriority.isEmpty()){
                selectedLine = criticalPriority.get((int) (Math.random() * criticalPriority.size()));
            } else{
                if(!highPriority.isEmpty()){
                    selectedLine = highPriority.get((int) (Math.random() * highPriority.size()));
                    // Toast.makeText(GameScreen.this, "High " + highPriority.size(), Toast.LENGTH_SHORT).show();
                } else {
                    if (!mediumPriority.isEmpty()){
                        selectedLine = mediumPriority.get((int) (Math.random() * mediumPriority.size()));
                        // Toast.makeText(GameScreen.this, "Medium " + mediumPriority.size(), Toast.LENGTH_SHORT).show();
                    } else {
                        selectedLine = lowPriority.get((int) (Math.random()*lowPriority.size()));
                        // Toast.makeText(GameScreen.this, "Low " + lowPriority.size(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
    }
}
