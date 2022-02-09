package com.nps.wallpaper.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * author : 泥菩萨
 * time   : 2021/07/20
 */
public class ImageProcessing {

    /**
     * @param bm
     * @param hue 色相
     * @param saturation 饱和度
     * @param lum 亮度
     * @return
     */
    public static Bitmap ImageEffect(Bitmap bm, Float hue, Float saturation, Float lum){

        Bitmap bitmap = Bitmap.createBitmap(bm.getWidth(),bm.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        ColorMatrix ImageMatrix = new ColorMatrix();

        //色相调节
        if (hue!=null){

            ColorMatrix hueMatrix = new ColorMatrix();
            hueMatrix.setRotate(0, hue);
            hueMatrix.setRotate(1, hue);
            hueMatrix.setRotate(2, hue);
            ImageMatrix.postConcat(hueMatrix);
        }

        //饱和度调节
        if (saturation!=null){

            ColorMatrix saturationColorMatrix = new ColorMatrix();
            saturationColorMatrix.setSaturation(saturation);
            ImageMatrix.postConcat(saturationColorMatrix);
        }

        //亮度调节
        if (lum!=null){

            ColorMatrix lumMatrix = new ColorMatrix();
            lumMatrix.setScale(lum, lum, lum, 1);
            ImageMatrix.postConcat(lumMatrix);
        }

        paint.setColorFilter(new ColorMatrixColorFilter(ImageMatrix));
        canvas.drawBitmap(bm, 0, 0, paint);

        return bitmap;
    }
}
