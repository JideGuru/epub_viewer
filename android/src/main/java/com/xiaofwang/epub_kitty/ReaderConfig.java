package com.xiaofwang.epub_kitty;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.folioreader.Config;
import com.folioreader.util.AppUtil;

public class ReaderConfig {
    private String identifier;
    private String themeColor;
    private String scrollDirection;
    private boolean allowSharing;

    public Config config;

    public ReaderConfig(Context context, String identifier, String themeColor,
                        String scrollDirection, boolean allowSharing){

        config = AppUtil.getSavedConfig(context);
        if (config == null)
            config = new Config();
        Log.e("Reader", "scrollDirection.equals(\"vertical\") " + scrollDirection);
        Log.e("Reader", "scrollDirection.equals(\"vertical\") " + scrollDirection.equals("vertical"));

        config.setAllowedDirection(Config.AllowedDirection.VERTICAL_AND_HORIZONTAL);

//        if (scrollDirection.equals("vertical")){
//            config.setAllowedDirection(Config.AllowedDirection.ONLY_VERTICAL);
//        }else {
//            config.setAllowedDirection(Config.AllowedDirection.ONLY_HORIZONTAL);
//        }
        config.setThemeColorInt(Color.parseColor(themeColor));
    }
}
