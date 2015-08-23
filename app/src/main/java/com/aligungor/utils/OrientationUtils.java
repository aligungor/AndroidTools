package com.aligungor.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

/**
 * Created by AliGungor on 23/08/15.
 */
public class OrientationUtils {

    public static void setOrientationLandscape(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public static void setOrientationPortrait(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static void setOrientationAuto(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    public static void lockRotation(Activity activity) {
        switch (activity.getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                setOrientationPortrait(activity);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                setOrientationLandscape(activity);
                break;
        }
    }

    public static boolean isOrientationPortrait(Activity activity) {
        return activity.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;
    }

    public static boolean isOrientationLandscape(Activity activity) {
        return activity.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

}
