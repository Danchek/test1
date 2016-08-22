package com.example.myapplication.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;


public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonStart, buttonProp, buttonRule, buttonHelp;
    int level=1;
    boolean sound=false;
    final String TAG="myLogs";
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent getIntent = getIntent();
        sound=getIntent.getBooleanExtra("sound", false);
        level=getIntent.getIntExtra("level", 1);
        buttonStart = (Button)findViewById(R.id.button);
        buttonProp = (Button)findViewById(R.id.button2);
        buttonRule = (Button)findViewById(R.id.button3);
        buttonHelp = (Button)findViewById(R.id.button4);
        buttonStart.setTypeface(Typeface.createFromAsset(getAssets(), "telegraphLine.ttf"));
        buttonProp.setTypeface(Typeface.createFromAsset(getAssets(), "telegraphLine.ttf"));
        buttonRule.setTypeface(Typeface.createFromAsset(getAssets(), "telegraphLine.ttf"));
        buttonHelp.setTypeface(Typeface.createFromAsset(getAssets(), "telegraphLine.ttf"));
        buttonStart.setOnClickListener(this);
        buttonProp.setOnClickListener(this);
        buttonRule.setOnClickListener(this);
        buttonHelp.setOnClickListener(this);
        //Toast.makeText(this,sound + " "+level,Toast.LENGTH_SHORT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra("sound", sound);
                intent.putExtra("level",level);
                Log.d(TAG, sound + " "+level);
                startActivity(intent);
                break;
            case R.id.button2:
                intent = new Intent(this, PropertyActivity.class);
                intent.putExtra("sound", sound);
                intent.putExtra("level",level);
                startActivity(intent);
                break;
            case R.id.button3:
                intent = new Intent(MenuActivity.this, Rules.class);
                break;
            case R.id.button4:
                intent = new Intent(MenuActivity.this, Help.class);
                break;
        }
        //startActivity(intent);
    }
}
