package com.example.myapplication.services;

import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.constants.Constants;
import com.example.myapplication.models.Shape;
import com.example.myapplication.models.impl.Dva;
import com.example.myapplication.models.impl.Kvadrat;
import com.example.myapplication.models.impl.L;
import com.example.myapplication.models.impl.Liniya;
import com.example.myapplication.models.impl.ObtatnayaL;
import com.example.myapplication.models.impl.Pyat;
import com.example.myapplication.models.impl.Treugolnik;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by dedo on 8/16/2016.
 */
public class Service {

    Constants con = new Constants();
    int[][] pole;
    ImageView[] imgNew;
    Random random = new Random();

    public Service(int[][] pole, ImageView[] imgNew) {
        this.pole = pole;
        this.imgNew = imgNew;
    }

    //метод перерисовки поля для тетриса
    public synchronized void backgr(int z) {
        for (int i = 0; i < con.ROW; i++)
            for (int j = 0; j < con.COLUMN; j++) {
                switch (pole[i][j]) {
                    case 0:
                        imgNew[j + i * con.COLUMN].setBackgroundResource(R.drawable.gray_middle);
                        break;
                    case 1:
                        imgNew[j + i * con.COLUMN].setBackgroundResource(R.drawable.blue_middle);
                        break;
                }
            }
    }

    //метод для проверки строк на удаление
    public int checkRow(){
        int cellNumber;
        for (int i=0;i<con.ROW;i++){
            cellNumber=0;
            for (int j=0;j<con.COLUMN;j++){
                if (pole[i][j]!=0){
                    cellNumber++;
                }
            }
            if (cellNumber==10){
                return i;
            }
        }
        return -1;
    }

    //метод для удаления строки (принимает номер строки, который нужно удалить)
    public void deleteRow(int row){
        for (;row>0;row--){
            for(int j=0;j<con.COLUMN;j++){
                pole[row][j]=pole[row-1][j];
            }
        }
    }

    //метод для генерации массива с кодами фигур
    public void randomShape(int[] masShapes){
        for (int i=0;i<masShapes.length;i++){
            if (masShapes[i]==0) {masShapes[i]=random.nextInt(7)+1;}
        }
    }

    //метод преобразования кода в экземпляр фигуры
    public Shape chooseShape (int x){
        switch (x){
            case 1:
                return new Dva();
            case 2:
                return new Kvadrat();
            case 3:
                return  new L();
            case 4:
                return new Liniya();
            case 5:
                return new ObtatnayaL();
            case 6:
                return new Pyat();
            default:
                return new Treugolnik();
        }
    }

    //метод генерации новой фигуры
    public void nextShape(int[] masShapes){
        for (int i=0;i<masShapes.length;i++){
            if (i==masShapes.length-1){
                masShapes[i]=random.nextInt(7)+1;
            } else {
                masShapes[i]=masShapes[i+1];
            }
        }
    }

    //метод определения скорости
    public int speed(int level){
        int speed = 1500;
        switch (level){
            case 1: speed=1500;
                break;
            case 2: speed=1400;
                break;
            case 3: speed=1300;
                break;
            case 4: speed=1200;
                break;
            case 5: speed=1100;
                break;
            case 6: speed=1000;
                break;
            case 7: speed=900;
                break;
            case 8: speed =800;
                break;
            case 9: speed =700;
                break;
            case 10: speed = 600;
                break;
            case 11: speed = 500;
                break;
            case 12: speed =400;
                break;
            case 13: speed=300;
                break;
            case 14: speed=200;
                break;
            case 15: speed=100;
                break;
        }
        return speed;
    }

    public int metodIncreasePoints (int points, int level){
        switch (level){
            case 1: points+=100;
                break;
            case 2: points+=150;
                break;
            case 3: points+=200;
                break;
            case 4: points+=250;
                break;
            case 5: points+=300;
                break;
            case 6: points+=350;
                break;
            case 7: points+=400;
                break;
            case 8: points+=450;
                break;
            case 9: points+=500;
                break;
            case 10: points+=550;
                break;
            case 11: points+=600;
                break;
            case 12: points+=700;
                break;
            case 13: points+=800;
                break;
            case 14: points+=900;
                break;
            case 15: points+=1000;
                break;
        }
        return points;
    }

    public int metodIncreaseLevel (int level){
        if(level<15){level++;}
        return level;
    }

    public int metodRow (int rowNumber){
        rowNumber++;
        return rowNumber;
    }

}
