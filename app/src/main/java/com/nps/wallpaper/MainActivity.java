package com.nps.wallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.io.IOException;


/**
 * author : 泥菩萨
 * time   : 2021/07/13
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToWallPaper();
        finish();
    }

    public  void setToWallPaper(){
        try {
            this.clearWallpaper();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,new ComponentName(this,MyService.class));
        startActivity(intent);
    }

}
