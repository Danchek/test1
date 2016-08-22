package com.example.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.constants.Constants;

import com.example.myapplication.models.Shape;
import com.example.myapplication.models.impl.Kvadrat;
import com.example.myapplication.models.impl.L;
import com.example.myapplication.models.impl.Liniya;
import com.example.myapplication.services.Service;


import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "myLogs";
    TableLayout tableLayout;
    TableRow []tableRows;
    ImageView []imgNew;
    TextView textViewLevel, textViewRow, textViewPoints;
    int [][]pole;
    int[] masShapes = new int[5];
    int currentState, level, speed, points, rowNumber;
    Handler handler;
    Thread thread;
    Constants con = new Constants();
    ImageButton buttonR, buttonU,buttonL,buttonD, buttonB;
    Shape shape;
    Service service = new Service(pole,imgNew);
    Boolean sound=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent getIntent= getIntent();
        int numberFields=0;
        level=getIntent.getIntExtra("level",1);
        sound=getIntent.getBooleanExtra("sound",false);
        pole= new int[con.ROW][con.COLUMN];
        service.randomShape(masShapes);
        speed=service.speed(level);
        tableLayout = (TableLayout)findViewById(R.id.TableLayout);
        tableRows = new TableRow[con.ROW];
        imgNew = new ImageView[con.NUMER_OF_ELEMEMHT];
        buttonD = (ImageButton) findViewById(R.id.imageButtonDown);
        buttonD.setOnTouchListener(this);
        buttonD.setOnClickListener(this);
        buttonR = (ImageButton) findViewById(R.id.imageButtonRight);
        buttonR.setOnClickListener(this);
        buttonU = (ImageButton) findViewById(R.id.imageButtonUp);
        buttonU.setOnClickListener(this);
        buttonL = (ImageButton) findViewById(R.id.imageButtonLeft);
        buttonL.setOnClickListener(this);
        buttonB = (ImageButton) findViewById(R.id.imageButtonBig);
        buttonB.setOnClickListener(this);
        textViewLevel = (TextView) findViewById(R.id.textViewLevel);
        textViewLevel.setText(""+level);
        textViewRow = (TextView) findViewById(R.id.textViewRows);
        textViewPoints = (TextView) findViewById(R.id.textViewPoints);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0) {
                    service.backgr(pole, imgNew);
                } else if (msg.what==1){
                    textViewLevel.setText(""+level);
                    textViewRow.setText(""+rowNumber);
                    textViewPoints.setText(""+points);
                }

            }

        };

        for (int i =0;i<con.NUMER_OF_ELEMEMHT;i++){
            imgNew[i] = new ImageView(this);
            imgNew[i].setId(i);
            imgNew[i].setBackgroundResource(R.drawable.gray_middle);
            imgNew[i].setLayoutParams(new TableRow.LayoutParams(-1,-1,1.0f));
        }
        for (int i=0;i<con.ROW;i++){
            tableRows[i] = new TableRow(this);
            tableRows[i].setLayoutParams(new TableLayout.LayoutParams(-1,-1,1.0f));
            for(int j=0;j<con.COLUMN;j++){
                tableRows[i].addView(imgNew[numberFields]);numberFields++;
            }
        }
        for(int i =0;i<con.ROW;i++){
            tableLayout.addView(tableRows[i]);
        }
        for (int i=0;i<con.ROW;i++)
            for (int j=0;j<con.COLUMN;j++){
                pole[i][j]=0;
            }
        thread();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButtonDown:
                shape.down(pole,currentState); service.backgr(pole,imgNew);
                break;
            case R.id.imageButtonLeft:
                shape.left(pole, currentState); service.backgr(pole,imgNew);
                //Toast.makeText(this,"Left",Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageButtonRight:
                shape.right(pole, currentState); service.backgr(pole,imgNew);
                //Toast.makeText(this,"Right",Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageButtonUp:
                currentState=shape.turnLeft(pole,currentState); service.backgr(pole,imgNew);
                //Toast.makeText(this,"Up",Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageButtonBig:
                currentState=shape.turnRight(pole,currentState); service.backgr(pole,imgNew);
        }

    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int temp=service.speed(level);
        switch(v.getId()){
            case R.id.imageButtonDown:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        speed=100;
                        Toast.makeText(this,"DOWN",Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_UP:
                        Toast.makeText(this,"UP",Toast.LENGTH_SHORT).show();
                        speed=temp;
                        break;
                }
                break;
        }
        return false;
    }

//    synchronized void  backgr(int z){
//
//
//        for (int i=0;i<con.ROW;i++)
//            for (int j=0;j<con.COLUMN;j++){
//               // Log.d(TAG, "pole["+i+"]["+j+"] = "+pole[i][j]);
//                switch (pole[i][j]){
//                    case 0: imgNew[j+i*con.COLUMN].setBackgroundResource(R.drawable.gray_middle);
//                        //Log.d(TAG, "00000000000000000000000pole["+i+"]["+j+"] = "+pole[i][j]+" "+(i+j*con.COLUMN));
//                       // imgNew[i+j].jumpDrawablesToCurrentState();
//                        break;
//                    case 1: imgNew[j+i*con.COLUMN].setBackgroundResource(R.drawable.blue_middle);
//                        //Log.d(TAG, "111111111111111111111111pole["+i+"]["+j+"] = "+pole[i][j]+" "+(i+j*con.COLUMN));
//                       // imgNew[i+j].jumpDrawablesToCurrentState();
//                        break;
//                }
//            }
//    }

    void thread(){
        thread = new Thread(new Runnable() {
            boolean flag;
            int count;
            @Override
            public void run() {
                while (currentState!=8) {
                    flag = true;
                    shape = service.chooseShape(masShapes[0]);
                    currentState = shape.create(pole);
                    count = 0;
                    handler.sendEmptyMessage(0);
                    //service.backgr(currentState,pole,imgNew);
                    if (currentState==8){flag=false;}
                    while (flag) {
                        try {
                            flag = shape.down(pole, currentState);
                            if (count == 0)
                                handler.sendEmptyMessage(0);
                                //service.backgr(0,pole,imgNew);
                            count++;
                            TimeUnit.MILLISECONDS.sleep(speed);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            flag = false;
                        }
                        handler.sendEmptyMessage(0);
                    }
                    service.metodBeforeNextShape(pole);
                    //блок удаления полных строк и начисления баллов
                    while (service.checkRow(pole)>=0){
                        service.deleteRow(service.checkRow(pole),pole);
                        points=service.metodIncreasePoints(points,level);
                        rowNumber=service.metodRow(rowNumber);
                        if((rowNumber%20)==0){
                            level=service.metodIncreaseLevel(level);
                            speed=service.speed(level);
                        }
                        handler.sendEmptyMessage(1);
                    }
                    service.nextShape(masShapes);
                }
                //TODO здесь будет обрабатываться событие проигрыша
            }
        });
        thread.start();
    }


}
