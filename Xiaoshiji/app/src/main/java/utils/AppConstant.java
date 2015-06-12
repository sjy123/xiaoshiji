package utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Window;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by db on 4/18/15.
 */
public class AppConstant {

    public static String USER_NAME="";
    public static String NICHEN="";
    public static boolean LOGIN_STATUS=false;

    /*
    腾讯地图的APPKEY
    6QLBZ-TTKAD-M4M4M-P5GUB-WWULF-OWF2Y
     */

    /*
    生成沉浸式状态栏
     */
    public static void setStatus(boolean on,Activity context){
        Window window = context.getWindow();
        WindowManager.LayoutParams layoutParams=window.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on){
            layoutParams.flags |=bits;
        }else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }
    /*
    根据Uri来获得图片，并将其转化成Bitmap
     */
    public static Bitmap getBitmap(Uri uri,Context context){
        String[] protection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri,protection,null,null,null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeFile(cursor.getString(column_index));

        return bitmap;

    }
    /*
    获取当前的时间
     */
    public static String getCurrentTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        String year = str.substring(0,4);
        String month = str.substring(5,7);
        String day = str.substring(8,10);
        return year+"."+month+"."+day;
    }
    /*
    利用正则表达式来验证输入的手机号码是否为合法的格式
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

}
