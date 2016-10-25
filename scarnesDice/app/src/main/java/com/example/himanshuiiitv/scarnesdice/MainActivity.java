package com.example.himanshuiiitv.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button Roll;
    Button Hold;
    Button Reset;
    ImageView dice;
    TextView tv;
    TextView tv2;
    private Random random = new Random();
    public int k;
    public int v;
    public int g;
    public int uScore = 0;
    public int cScore = 0;
    public int point2 = 0;
    public int point1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Roll = (Button) findViewById(R.id.button);
        Hold = (Button) findViewById(R.id.button2);
        Reset = (Button) findViewById(R.id.button3);
        dice = (ImageView) findViewById(R.id.imageView);
        tv = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);

        uScore = 0;
        cScore = 0;
        Roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RollF();
            }
        });

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetF();
            }
        });

        Hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoldF();
            }
        });


        tv.setText("User_Score:0   Computer_Score:0");

        k = random.nextInt(2);
        if (k == 0) {
            userTurn();
            v = 1;
        } else {
            computerTurn();
            v = 2;
        }
    }

    public void userTurn() {
        point1 = 0;
        tv2.setText( "C'MON it's your turn Make your Move or hold"+"Your Score for this Session : "+point1);
        /*while (v == 1) {

            point1 = point1 + k;
            if (RollF() == 1) {
                dice.setImageResource(R.drawable.dice1);
                Toast.makeText(MainActivity.this, "You Lost Your Chance And Score Of this Session = 0", Toast.LENGTH_SHORT).show();
                point1 = 0;
                HoldF();
                break;
            } else if (RollF() == 2) {
                dice.setImageResource(R.drawable.dice2);
            } else if (RollF() == 3) {
                dice.setImageResource(R.drawable.dice3);
            } else if (RollF() == 4) {
                dice.setImageResource(R.drawable.dice4);
            } else if (RollF() == 5) {
                dice.setImageResource(R.drawable.dice5);
            } else if (RollF() == 6) {
                dice.setImageResource(R.drawable.dice6);
            }
        }*/


    }

    public void computerTurn() {
        point2 = 0;
        tv2.setText("Computer Turn ");
        while (point2 < 20 && v == 2) {
            RollF();
            k=g;
            //String b="";
            //Log.e(b,"point2"+point2);
            if (k == 1) {
                dice.setImageResource(R.drawable.dice1);
                point2 = 0;
                HoldF();
                //userTurn();
                //v = 1;
                break;
            } else if (k == 2) {
                dice.setImageResource(R.drawable.dice2);
                point2 = point2 + k;
            } else if (k == 3) {
                dice.setImageResource(R.drawable.dice3);
                point2 = point2 + k;
            } else if (k == 4) {
                dice.setImageResource(R.drawable.dice4);
                point2 = point2 + k;
            } else if (k == 5) {
                dice.setImageResource(R.drawable.dice5);
                point2 = point2 + k;
            } else if (k == 6) {
                dice.setImageResource(R.drawable.dice6);
                point2 = point2 + k;
            }
        }
        HoldF();
        userTurn();
    }

    public void RollF() {
        g = random.nextInt(6) + 1;
        if (v == 1) {
            if (g == 1) {
                dice.setImageResource(R.drawable.dice1);
                Toast.makeText(MainActivity.this, "You Lost Your Chance And Score Of this Session = 0", Toast.LENGTH_SHORT).show();
                point1 = 0;
                HoldF();
                //computerTurn();
            } else if (g == 2) {
                dice.setImageResource(R.drawable.dice2);
                point1 = point1 + g;
            } else if (g == 3) {
                dice.setImageResource(R.drawable.dice3);
                point1 = point1 + g;
            } else if (g == 4) {
                dice.setImageResource(R.drawable.dice4);
                point1 = point1 + g;
            } else if (g == 5) {
                dice.setImageResource(R.drawable.dice5);
                point1 = point1 + g;
            } else if (g == 6) {
                dice.setImageResource(R.drawable.dice6);
                point1 = point1 + g;
            }
            tv2.setText("C'MON it's your turn Make your Move or hold"+"Your Score for this Session : "+point1);
        }
    }


    public void ResetF() {
        uScore = 0;
        cScore = 0;
        k = random.nextInt(2);
        if (k == 0) {
            userTurn();
        } else {
            computerTurn();
        }
        tv.setText("User_Score:" + uScore + "   Computer_Score:" + cScore);
    }

    public void HoldF() {
        if (v == 1) {
            uScore = uScore + point1;
            v = 2;
            computerTurn();
            //point1=0;
        } else if (v == 2) {
            cScore = cScore + point2;
            v = 1;
            userTurn();
            //point2=0;
        }
        tv.setText("User_Score:" + uScore + "   Computer_Score:" + cScore);
    }

    public boolean ifThereIsAWinner() {
        if (uScore >= 100) {
            Toast.makeText(MainActivity.this, "Congratulations You Won !!!!", Toast.LENGTH_SHORT).show();
            ResetF();
            return true;
        } else if (cScore >= 100) {
            Toast.makeText(MainActivity.this, "You Lost Better Luck Next Time !!!!", Toast.LENGTH_SHORT).show();
            ResetF();
            return true;
        } else {
            return false;
        }
    }
}
