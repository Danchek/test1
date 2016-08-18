package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.myapplication.R;



/**
 * Created by dedo on 8/18/2016.
 */
public class PropertyActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    Button buttonApply, buttonBack;
    RadioButton radioButton, radioButtonOn, radioButtonOff;
    RadioGroup radioGroup;
    final String TAG="myLogs";
    SeekBar seekBar;
    TextView textView;
    protected int level=1, progress=1;
    boolean sound = false;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.property_activity);
        Intent getIntent = getIntent();
        level=getIntent.getIntExtra("level",1);
        sound=getIntent.getBooleanExtra("sound", sound);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        buttonApply = (Button) findViewById(R.id.buttonApply);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textViewLevel);
        radioButtonOn = (RadioButton) findViewById(R.id.radioButtonOn);
        radioButtonOff = (RadioButton)findViewById(R.id.radioButtonOff);
        ////////////////////////////////////
        textView.setText(""+level);
        seekBar.setProgress(level-1);
        if (sound){radioButtonOn.setChecked(true); radioButtonOff.setChecked(false);}
        else {radioButtonOn.setChecked(false); radioButtonOff.setChecked(true);}
        buttonApply.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                buttonApply.setEnabled(true);
                radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonApply:
                level = progress;
                Log.d(TAG, radioButton.getText().toString());
                if(radioButton.getText().equals("On") || radioButton.getText().equals("Вкл")){sound=true;}
                break;
            case R.id.buttonBack:
                intent = new Intent(PropertyActivity.this, MenuActivity.class);
                intent.putExtra("sound", sound);
                intent.putExtra("level", level);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.progress=progress;
        textView.setText(""+(progress+1));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(level!=progress){buttonApply.setEnabled(true);}
    }
}
