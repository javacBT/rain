package com.nps.wallpaper.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 下雨
 * author : 泥菩萨
 * time   : 2021/07/17
 */
public class RainAnimation {

    private List<Rain> Rains = new ArrayList<>(); //水滴集合
    private List<Rain> residuals = new ArrayList<>(); //雨滴划过后残留水滴集合

    private Bitmap RoundBitmap;

    private float rainRadiusMin,rainRadiusMax,rainRadiusMiddle; //雨滴最小最大值,中间值

    public RainAnimation setRainImg(Bitmap bgImg){

        this.RoundBitmap = getRoundBitmap(bgImg);

        return this;
    }

    public void draw(Canvas canvas){

        //计算雨滴大小范围
        if (rainRadiusMin==0 && rainRadiusMax==0){

            rainRadiusMin = canvas.getWidth()/200;
            rainRadiusMiddle = canvas.getWidth()/160;
            rainRadiusMax = canvas.getWidth()/50;
        }

        //生成雨滴方法
        generateRain(canvas.getWidth(),canvas.getHeight());

        //雨滴运动后的残留
        for (int i=residuals.size()-1;i>=0;i--){

            drawRain(canvas,residuals.get(i));
        }

        //雨滴运动
        for (int i=Rains.size()-1;i>=0;i--){

            if (Rains.get(i).getPositionY()>canvas.getHeight()){

                Rains.remove(i);

            }else {

                drawRain(canvas,Rains.get(i));
            }
        }
    }

    //单个雨滴绘制
    private void drawRain(Canvas canvas,Rain mRain){ //渲染雨滴

        //绘制雨滴
        canvas.drawBitmap(
                RoundBitmap,
                null,
                new RectF(
                  mRain.getPositionX()-mRain.getRadius(),
                  mRain.getPositionY()-mRain.getRadius(),
                  mRain.getPositionX()+mRain.getRadius(),
                  mRain.getPositionY()+mRain.getRadius())
                ,null);



        if (mRain.getMax()<=0)return;

        if (mRain.getRadius()>=mRain.getMax()){

            //滑动过程中融合不是水滴自己产生的被自己覆盖的残留
            for (int j=residuals.size()-1;j>=0;j--){

                float Range = (mRain.getPositionX()-residuals.get(j).getPositionX()) * (mRain.getPositionX()-residuals.get(j).getPositionX());
                Range = Range + (mRain.getPositionY()-residuals.get(j).getPositionY()) * (mRain.getPositionY()-residuals.get(j).getPositionY());

                if (Range>mRain.getRadius()*mRain.getRadius() || mRain.getAddTime()==residuals.get(j).getAddTime()){

                    continue;

                }else {

                    residuals.remove(j);
                }

            }

            //滑动过程10/1几率随机产生残留
            int probability = (int)(Math.random()*(10-1)+1);
            if (probability==7){

                Rain residualsRain = new Rain();

                residualsRain.setPositionX(mRain.getPositionX());
                residualsRain.setPositionY(mRain.getPositionY());
                residualsRain.setRadius((float) (Math.random()*(rainRadiusMin-rainRadiusMin/10)+rainRadiusMin/10));
                residualsRain.setAddTime(mRain.getAddTime());
                if (residuals.size()<=2000)residuals.add(residualsRain);
            }

            //到达最大值向下滑动
            mRain.setPositionY(mRain.getPositionY()+mRain.getFall());

        }else{

            //没有到达最大值根据雨滴步长继续增长
            mRain.setRadius(mRain.getRadius()+mRain.getGrowUp());
        }
    }

    //生成雨滴
    private int speedGenerate; //控制雨滴生成速度
    private void generateRain(float x,float y){

        speedGenerate = speedGenerate+1;

        if (speedGenerate>=10){

            speedGenerate=0;

        }else {
            return;
        }

        if (Rains.size()>=70)return; //屏幕最多70个水滴


        Rain mRain = new Rain();

        mRain.setPositionX((float) Math.random()*x);
        mRain.setPositionY((float) Math.random()*y);
        mRain.setRadius((float) (Math.random()*(rainRadiusMax-rainRadiusMin)+rainRadiusMin));
        mRain.setMax((float)(Math.random()*(rainRadiusMax-rainRadiusMiddle)+rainRadiusMiddle));
        mRain.setGrowUp((float) (Math.random()*(0.005-0.001)+0.001));
        mRain.setFall((float) (Math.random()*(y/80-y/120)+y/120));
        mRain.setAddTime(new Date().getTime());

        Rains.add(mRain);
    }

    //画出雨滴图形
    public Bitmap getRoundBitmap(Bitmap bitmap) {

        int roundPx = bitmap.getWidth()/2;

        int left,top,right,bottom;

        top = 0;
        left = 0;
        right = bitmap.getWidth();
        bottom = bitmap.getHeight();

        Rect rect = new Rect(top,left,right,bottom); //背景图裁剪区域
        RectF rectF = new RectF(0,0,bitmap.getWidth(), bitmap.getWidth()); //显示区域

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        Canvas RainCanvas = new Canvas(output);
        RainCanvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas

        Paint paint = new Paint();
        paint.setAntiAlias(true);// 设置画笔无锯齿


        RainCanvas.drawRoundRect(rectF,roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式
        RainCanvas.drawBitmap(bitmap, rect, rectF, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        //图片镜像翻转
        Matrix matrix = new Matrix();

        matrix.postScale(-1f, 1f);
        output = Bitmap.createBitmap(output, 0, 0, output.getWidth(), output.getHeight(),matrix , true);

        matrix.postScale(1f, -1f);
        output = Bitmap.createBitmap(output, 0, 0, output.getWidth(), output.getHeight(),matrix , true);

        return output;
    }

    //雨滴类
    public class Rain{

        private float positionX;//水滴位置
        private float positionY;

        private float radius;//水滴半径

        private float max; //雨滴最大范围

        private float growUp;//水滴成长步长

        private float fallMax; //最大坠落速度

        private long addTime; //创建时间

        public float getPositionX() {
            return positionX;
        }

        public void setPositionX(float positionX) {
            this.positionX = positionX;
        }

        public float getPositionY() {
            return positionY;
        }

        public void setPositionY(float positionY) {
            this.positionY = positionY;
        }

        public float getRadius() {
            return radius;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }

        public float getMax() {
            return max;
        }

        public void setMax(float max) {
            this.max = max;
        }

        public float getGrowUp() {
            return growUp;
        }

        public void setGrowUp(float growUp) {
            this.growUp = growUp;
        }

        public float getFall() {

            float whereabouts = (float) Math.random()*fallMax;

            return whereabouts;
        }

        public void setFall(float fallMax) {
            this.fallMax = fallMax;
        }

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }
    }
}
