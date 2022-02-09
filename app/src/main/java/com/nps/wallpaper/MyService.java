package com.nps.wallpaper;

import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.nps.wallpaper.animation.AnimationControl;
import com.nps.wallpaper.animation.MistsAnimation;

import java.util.HashMap;
import java.util.Map;

/**
 * author : 泥菩萨
 * time   : 2021/07/13
 */
public class MyService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new MyEngine();
    }

    class MyEngine extends MyService.Engine{

        //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
        private float x1 = 0;
        private float y1 = 0;
        private float x2 = 0;
        private float y2 = 0;

        private volatile boolean judge = true; //控制动效绘制，壁纸不可见时不绘制

        private AnimationControl mAnimationControl; //雾气类

        //获取SurfaceHolder时调用
        @Override
        public SurfaceHolder getSurfaceHolder() {
            return super.getSurfaceHolder();
        }

        //Surface创建时回调
        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);

            if (this.mAnimationControl==null)start(holder);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);

            if (mAnimationControl!=null){

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    //当手指按下的时候
                    x1 = event.getX();
                    y1 = event.getY();
                }

                if(event.getAction() == MotionEvent.ACTION_UP) {

                    //当手指离开的时候
                    x2 = event.getX();
                    y2 = event.getY();

                    if(y1 - y2 > 10) { //向上滑

                    }

                    if(y1 - y2 > 10){ //向下滑

                    }

                    if(y2 - y1 > 10){ //向左滑

                        if(mAnimationControl!=null)mAnimationControl.setWidthOffsets(Math.abs(mAnimationControl.getWidthOffsets()));
                    }

                    if(x2 - x1 > 10){ //向右滑

                        if(mAnimationControl!=null)mAnimationControl.setWidthOffsets(-Math.abs(mAnimationControl.getWidthOffsets()));
                    }

                }
            }
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);

            judge = visible;
        }

        //Surface销毁时回调
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
        }


        public void start(SurfaceHolder holder){

            mAnimationControl = new AnimationControl(getApplicationContext())
                    .setHolder(holder)
                    .setResources(getResources());


            new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true){

                        try {

                            Thread.sleep(10);
                            if (judge)mAnimationControl.draw();

                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }

                    }

                }
            }).start();
        }

    }
}
