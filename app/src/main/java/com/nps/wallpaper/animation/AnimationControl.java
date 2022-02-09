package com.nps.wallpaper.animation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import com.nps.wallpaper.R;
import com.nps.wallpaper.util.ImageProcessing;
import com.nps.wallpaper.util.TimeStampUtil;

import java.util.Date;

/**
 * author : 泥菩萨
 * time   : 2021/07/17
 */
public class AnimationControl {



    private SurfaceHolder holder; //壁纸绘制类

    private Bitmap BaseBgImg; //画布背景
    private Bitmap BaseRainBgImg; //画布背景
    private Bitmap BaseMists; //云雾

    private Bitmap bgImg; //画布背景
    private Bitmap RainBgImg; //画布背景
    private Bitmap mists; //云雾

    private MistsAnimation Mists; //云雾类

    private RainAnimation Rain; //下雨类

    private Context context;

    private String timeType;

    public AnimationControl(Context context){

        this.context = context;
    }

    public AnimationControl setHolder(SurfaceHolder holder) {

        this.holder = holder;
        return this;
    }

    public AnimationControl setResources(Resources mResources) { //初始化各种绘制天气的类

        this.BaseBgImg = BitmapFactory.decodeResource(mResources, R.drawable.img_bg);
        this.BaseRainBgImg = BitmapFactory.decodeResource(mResources, R.drawable.img_bg2);
        this.BaseMists = BitmapFactory.decodeResource(mResources, R.drawable.img_mists);

        Mists = new MistsAnimation()
                .setResources(BaseMists)
                .setWidthOffsets(-6);

        Rain = new RainAnimation()
                .setRainImg(BaseBgImg);

        return this;
    }

    public void setWidthOffsets(int widthOffsets){ //设置云雾横向运动参数

        if(Mists!=null)Mists.setWidthOffsets(widthOffsets);
    }

    public int getWidthOffsets(){

        return Mists==null?0:Mists.getWidthOffsets();
    }


    public void draw(){

        Canvas canvas = holder.lockCanvas();

        //获取画布设置画布背景
        if (BaseBgImg!=null && BaseRainBgImg!=null){

            canvas.drawBitmap(BaseBgImg,null,new Rect(0,0,canvas.getWidth(),canvas.getHeight()),null);
            canvas.drawBitmap(BaseRainBgImg,null,new Rect(0,0,canvas.getWidth(),canvas.getHeight()),null);
        }

        if (Mists!=null)Mists.draw(canvas);

        if (Rain!=null)Rain.draw(canvas);

        holder.unlockCanvasAndPost(canvas);
    }

    //根据时间控制亮度
    private void BgTimeColor(){


        String minute = TimeStampUtil.timeToString(new Date().getTime(),"mm");

        if (minute.equals(timeType))return;

        if (BaseBgImg==null || BaseRainBgImg==null || BaseMists==null)return;

        Integer time = Integer.parseInt(TimeStampUtil.timeToString(new Date().getTime(),"HH"));

        time = 12-time;

        float TimeRange = (float) (0.4/12);

        float Range;

        if (time>12){

            Range = (float) (1.2-TimeRange*time);

        }else {

            Range = (float) (1.2 + TimeRange*time);
        }

        if (bgImg!=null)bgImg.recycle();
        if (RainBgImg!=null)RainBgImg.recycle();
        if (mists!=null)mists.recycle();

        bgImg = ImageProcessing.ImageEffect(BaseBgImg,null,null,Range);
        RainBgImg = ImageProcessing.ImageEffect(BaseRainBgImg,null,null,Range);
        mists = ImageProcessing.ImageEffect(BaseMists,null,null,Range);

        timeType = minute;
    }
}
