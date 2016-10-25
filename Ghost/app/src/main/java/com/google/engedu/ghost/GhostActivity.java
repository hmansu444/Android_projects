package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private FastDictionary simple_fast_dictionary;
    ///////
    private boolean userTurn = false;
    private Random random = new Random();
    private InputStream is = null;
    private TextView tvGhostText, label;
    private Button bChallenge, bRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);

        tvGhostText =(TextView)findViewById(R.id.ghostText) ;
        label=(TextView)findViewById(R.id.gameStatus);
        bChallenge=(Button)findViewById(R.id.vChallenge);
        bRestart=(Button)findViewById(R.id.vRestart);

        bChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChallengeMethod();
            }
        });

        bRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestartMethod();
            }
        });

        AssetManager assetManager = getAssets();
        try {
            is=getAssets().open("words.txt");
            simple_fast_dictionary=new FastDictionary(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        onStart(null);

        //is=getAssets()
    }

    private void ChallengeMethod(){
       // TextView label = (TextView) findViewById(R.id.gameStatus);
        String temp_text= tvGhostText.getText().toString();
        if(simple_fast_dictionary.isWord(temp_text) && temp_text.length()>=4){
            Toast.makeText(GhostActivity.this,"You Win",Toast.LENGTH_LONG).show();
        }
        else{
            String word=simple_fast_dictionary.getAnyWordStartingWith(temp_text);
            if(word==null){
                Toast.makeText(GhostActivity.this,"You Win",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(GhostActivity.this,"Compter Wins, Word is "+word,Toast.LENGTH_LONG).show();
            }
        }
    }

    public void RestartMethod(){
        onStart(null);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        char pressedKey = (char) event.getUnicodeChar();
        if (Character.isAlphabetic(pressedKey)) {
            String currentText=tvGhostText.getText().toString();
            tvGhostText.setText(currentText+pressedKey);
            label.setText(COMPUTER_TURN);
            computerTurn();
            return true;
        }
        else{
            Toast.makeText(GhostActivity.this,"Invalid Letter",Toast.LENGTH_SHORT).show();
        }
        return  true;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        //
        String temp_text= tvGhostText.getText().toString();
        if(simple_fast_dictionary.isWord(temp_text) && temp_text.length()>=4){
            Toast.makeText(GhostActivity.this,"Computer wins!!",Toast.LENGTH_SHORT).show();
            label.setText("Restart!!");
        }
        else{
            String word=simple_fast_dictionary.getAnyWordStartingWith(temp_text);
            if(word==null){
                Toast.makeText(GhostActivity.this,"Computer wins!!",Toast.LENGTH_SHORT).show();
                label.setText("Restart!!");
            }
            else{
                temp_text=temp_text+word.charAt(temp_text.length());
                tvGhostText.setText(temp_text);
            }
        }
        // Do computer turn stuff then make it the user's turn again
        userTurn = true;
        label.setText(USER_TURN);
    }
}
