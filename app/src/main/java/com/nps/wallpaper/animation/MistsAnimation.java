package com.nps.wallpaper.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


/**
 * 雾气
 * author : 泥菩萨
 * time   : 2021/07/14
 */
public class MistsAnimation {

    /**
     * 下面已1像素为基本单位
     * */

    private Bitmap mists; //雾气层

    private int widthOffsets; //横向偏移量

    private int positionX;//雾气当前位置X
    private int positionY;//雾气当前位置Y

    private boolean positionYjudge = true; //控制雾气纵向移动方向

    public MistsAnimation setResources(Bitmap mists){

        this.mists = mists;
        return this;
    }

    public MistsAnimation setWidthOffsets(int widthOffsets){

        this.widthOffsets = widthOffsets;
        return this;
    }

    public int getWidthOffsets(){

        return widthOffsets;
    }

    public void draw(Canvas canvas){

        if (canvas==null)return;

        if (widthOffsets>=0){

            misisRight(canvas);

        }else {

            misisLeft(canvas);
        }
    }

    private void misisRight(Canvas canvas){

        if (mists==null)return;

        //雾气图层高宽
        int misisHeight = canvas.getHeight();
        int misisWidth = canvas.getHeight()/6*10;

        //雾气图层横向滚动到底后重置起始位置
        if (positionX<-misisWidth)positionX = 0;


        //计算雾气上下浮动
        int range = misisHeight/20;

        if (positionY>=range)positionYjudge=true;
        if (positionY<=-range)positionYjudge=false;

        if (positionYjudge)positionY=positionY-1;
        if (!positionYjudge)positionY=positionY+1;

        canvas.drawBitmap(mists,null,new Rect(positionX,positionY,misisWidth+positionX,misisHeight+positionY),null);

        if (positionX<=-(misisWidth-canvas.getWidth())){

            canvas.drawBitmap(mists,null,new Rect(misisWidth+positionX,positionY,misisWidth*2+positionX,misisHeight+positionY),null);
        }

        positionX = positionX - Math.abs(widthOffsets);
    }

    private void misisLeft(Canvas canvas){

        if (mists==null)return;

        //雾气图层高宽
        int misisHeight = canvas.getHeight();
        int misisWidth = canvas.getHeight()/6*10;

        //雾气图层横向滚动到底后重置起始位置;
        if (positionX>=canvas.getWidth())positionX=canvas.getWidth()-misisWidth;


        //计算雾气上下浮动
        int range = misisHeight/20;

        if (positionY>=range)positionYjudge=true;
        if (positionY<=-range)positionYjudge=false;

        if (positionYjudge)positionY=positionY-1;
        if (!positionYjudge)positionY=positionY+1;

        canvas.drawBitmap(mists,null,new Rect(positionX,positionY,misisWidth+positionX,misisHeight+positionY),null);

        if (positionX>=0){

            canvas.drawBitmap(mists,null,new Rect(positionX-misisWidth,positionY,positionX,misisHeight+positionY),null);
        }

        positionX = positionX + Math.abs(widthOffsets);

    }


}
