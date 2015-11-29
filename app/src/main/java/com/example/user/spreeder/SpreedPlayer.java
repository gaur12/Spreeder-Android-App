package com.example.user.spreeder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SpreedPlayer extends AppCompatActivity {

    private TextView spreedTextView;
    public String ray = "";
    String spreedText;
    RelativeLayout r;
    int speed;
    public boolean onClick =  false;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spreed_player);
        r = (RelativeLayout) findViewById(R.id.spreed_player);
       /* Bundle b=this.getIntent().getExtras();
        array=b.getStringArray("spreed_array");
        Toast.makeText(getApplicationContext(), String.valueOf(array.length),Toast.LENGTH_SHORT).show();
        */
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_play_pause);
        Intent intent = getIntent();
        spreedTextView = (TextView) findViewById(R.id.spreed_text);
        spreedText = intent.getExtras().getString("spreed_text");
        showText();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spreed_player, menu);
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
            p = 1;
            floatingActionButton.setImageResource(R.drawable.ic_play_arrow_white_24dp);
            onClick = false;
            Intent i = new Intent(getApplicationContext(), Settings.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    Thread thread = new Thread();
    Handler handler = new Handler();
    public int i = 0, j = 0,p=1,re=0;
    public String[] arr;
    public void showText() {

        arr = new String[spreedText.length()];
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(spreedText);
        while (matcher.find()) {
            arr[j] = matcher.group();
            j++;
        }
        spreedTextView.setText(arr[0]);
        spreedTextView.setTextSize(Integer.valueOf(MainActivity.getInstance().getSharedPreferences().getString("textSize", "30")));
        spreedTextView.setTextColor(Color.parseColor(MainActivity.getInstance().getSharedPreferences().getString("textColor", "#5a2bc3")));
        r.setBackgroundColor(Color.parseColor(MainActivity.getInstance().getSharedPreferences().getString("backgroundColor", "#a9a9a9")));
         //handler.postDelayed(run,100);
    }

    public void restart(View view){
        i=0;
        p=1;
        spreedTextView.setText(arr[0]);
        floatingActionButton.setImageResource(R.drawable.ic_play_arrow_white_24dp);
        onClick = false;
        //handler.postDelayed(run,2000);
    }


    public void doStart(View view) {
        if (!onClick) {
            onClick = true;
            p = 0;
            floatingActionButton.setImageResource(R.drawable.ic_pause_white_24dp);
            speed = 60000 / Integer.valueOf(MainActivity.getInstance().getSharedPreferences().getString("speed", "300"));
            handler.postDelayed(run, speed);
        }else {
            onClick = false;
            floatingActionButton.setImageResource(R.drawable.ic_play_arrow_white_24dp);
            p = 1;
           // handler.postDelayed(run, 100);
        }

        spreedTextView.setTextSize(Integer.valueOf(MainActivity.getInstance().getSharedPreferences().getString("textSize", "30")));
        spreedTextView.setTextColor(Color.parseColor(MainActivity.getInstance().getSharedPreferences().getString("textColor", "#5a2bc3")));
        r.setBackgroundColor(Color.parseColor(MainActivity.getInstance().getSharedPreferences().getString("backgroundColor", "#a9a9a9")));
    }
    Runnable run = new Runnable() { @Override public void run() {
        if(i!=j-1 && p==0)
        {
            for (int k=0; k<Integer.valueOf(MainActivity.getInstance().getSharedPreferences().getString("chunkSize", "1")) && i!=j-1; k++){
                i++;
                ray = ray + arr[i] + " ";
            }
            spreedTextView.setText(ray);
            ray = "";
            handler.postDelayed(run,speed); }
       } };
}

