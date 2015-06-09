package utils;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    /*
    后台查找BringMealInfo对象
     */
    public static ArrayList<BringMealInfo> findBringMealInfos(){

        AVQuery<BringMealInfo> query=AVQuery.getQuery(BringMealInfo.class);
        query.orderByDescending("updateAt");
        query.limit(1000);
        List<BringMealInfo> bringMealInfoList = new ArrayList<BringMealInfo>();
        try {
            bringMealInfoList = query.find();
        }catch (AVException e){
            Log.e("tag", "Query todos failed.", e);
        }
        return (ArrayList<BringMealInfo>)bringMealInfoList;
    }
}
