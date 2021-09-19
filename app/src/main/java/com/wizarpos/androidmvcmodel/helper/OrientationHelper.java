
package com.wizarpos.androidmvcmodel.helper;

import android.content.Context;
import android.content.res.Configuration;

public class OrientationHelper {

    /**
     * 是否为横屏
     */
    public static boolean isLandscape(Context context) {
        boolean isLandscape = false;
        Configuration mConfiguration = context.getResources().getConfiguration(); // 获取设置的配置信息
        int ori = mConfiguration.orientation; // 获取屏幕方向

        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            isLandscape = true;
        }
        return isLandscape;
    }
}
