package utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.db.xiaoshiji.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import beans.DishMenuInfo;
import database.MyDataBaseHelper;

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
    /*
    解析txt文件，并存储到本地数据库中
     */
    public static void readTxt(Context context) {
        try {
            InputStream inputStream0 = context.getResources().openRawResource(R.raw.dishmenu0);
            BufferedReader bufferedReader0 = new BufferedReader(new InputStreamReader(inputStream0,"utf-8"));

            InputStream inputStream1 = context.getResources().openRawResource(R.raw.dishmenu1);
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1,"utf-8"));

            InputStream inputStream2 = context.getResources().openRawResource(R.raw.dishmenu2);
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2,"utf-8"));

            InputStream inputStream3 = context.getResources().openRawResource(R.raw.dishmenu3);
            BufferedReader bufferedReader3 = new BufferedReader(new InputStreamReader(inputStream3,"utf-8"));

            String line0 = null;
            String line1 = null;
            String line2 = null;
            String line3 = null;

            MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(context);
            SQLiteDatabase sqLiteDatabase = myDataBaseHelper.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.query("dishmenu",new String[]{"diningroomname","mealtype","mealname","price"},null,null,null,null,null);
            if (cursor.getCount()==0){
            while ((line2=bufferedReader1.readLine())!=null){
                String temp0 = bufferedReader0.readLine();
                String temp1 = bufferedReader1.readLine();
                String temp2 = bufferedReader2.readLine();
                String temp3 = bufferedReader3.readLine();


                    ContentValues contentValues = new ContentValues();
                    contentValues.put("diningroomname",temp0);
                    contentValues.put("mealtype",temp1);
                    contentValues.put("mealname",temp2);
                    contentValues.put("price",temp3);
                    sqLiteDatabase.insert("dishmenu",null,contentValues);

            }
            }else {
                Log.v("jjj0", "已经存到本地数据了");
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            Log.v("lj",e.getMessage());
        }
    }
    /*
    根据食堂的名字找到对应的菜谱
     */
    public static ArrayList<DishMenuInfo> getDishMenuInfos(Context context,String name){

        ArrayList<DishMenuInfo> dishMenuInfos = new ArrayList<DishMenuInfo>();

        MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(context);
        SQLiteDatabase sqLiteDatabase = myDataBaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("dishmenu",new String[]{"diningroomname","mealtype","mealname","price"},null,null,null,null,null);
        Log.v("jjj",String.valueOf(cursor.getCount()));
        if (cursor.getCount()!=0){
            for (int i=0;i<cursor.getCount()-1;i++){
                cursor.moveToPosition(i);
                if (cursor.getString(cursor.getColumnIndex("diningroomname")).equals(name)){
                    DishMenuInfo dishMenuInfo = new DishMenuInfo();
                    dishMenuInfo.setDiningRoomName(cursor.getColumnName(cursor.getColumnIndex("diningroomname")));
                    dishMenuInfo.setMealName(cursor.getString(cursor.getColumnIndex("mealname")));
                    dishMenuInfo.setMealType(cursor.getString(cursor.getColumnIndex("mealtype")));
                    dishMenuInfo.setMealPrice(cursor.getString(cursor.getColumnIndex("price")));
                    Log.v("gg0",cursor.getColumnName(cursor.getColumnIndex("diningroomname")));
                    Log.v("gg1",cursor.getString(cursor.getColumnIndex("mealname")));
                    Log.v("gg2",cursor.getString(cursor.getColumnIndex("mealtype")));
                    Log.v("gg3",cursor.getString(cursor.getColumnIndex("price")));
                    dishMenuInfos.add(dishMenuInfo);
                }
            }
        }

        return dishMenuInfos;
    }

}
