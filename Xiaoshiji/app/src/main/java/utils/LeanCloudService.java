package utils;

import android.content.Context;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;

import beans.BringMealInfo;

/**
 * Created by db on 6/2/15.
 */
public class LeanCloudService {
    public static String appId = "d02j5d4ht58yl8s93o4x5mwtslpl0awtjoigrwfaazdwh0m5";
    public static String appKey = "edkhzmoop8ekvimzpip2l0t5vgsozgeuttz2f39sjqnhd105";
    public static void AVInit(Context context){
        AVOSCloud.initialize(context, appId, appKey);
        // 启用崩溃错误报告
        AVOSCloud.setDebugLogEnabled(true);
        AVAnalytics.enableCrashReport(context, true);
        // 注册子类
        AVObject.registerSubclass(BringMealInfo.class);
    }
}
