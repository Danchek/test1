package com.example.myapplication.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

/**
 * Created by dedo on 8/18/2016.
 */
public class Menu_activity extends AppCompatActivity implements View.OnClickListener {
    Button buttonStart, buttonProp, buttonRule, buttonHelp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        buttonStart = (Button)findViewById(R.id.button);
        buttonStart.setTypeface(Typeface.createFromAsset(getAssets(), "telegraphLine.ttf"));
        buttonProp = (Button)findViewById(R.id.button2);
        buttonProp.setTypeface(Typeface.createFromAsset(getAssets(), "telegraphLine.ttf"));
        buttonRule = (Button)findViewById(R.id.button3);
        buttonRule.setTypeface(Typeface.createFromAsset(getAssets(), "telegraphLine.ttf"));
        buttonHelp = (Button)findViewById(R.id.button4);
        buttonHelp.setTypeface(Typeface.createFromAsset(getAssets(), "telegraphLine.ttf"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                Intent intent = new Intent(Menu_activity.this,MainActivity.class );
                startActivity(intent);
                break;

        }
    }
}
