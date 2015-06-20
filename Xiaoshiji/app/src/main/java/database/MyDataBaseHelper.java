package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by db on 6/20/15.
 */
public class MyDataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="com.xiaoshiji.db4";
    public static final int DATABASE_VERSION=1;


    public static String CREATE_ARTICLE="create table dishmenu (_id integer primary key autoincrement,diningroomname varchar(100),mealtype varchar(100),mealname varchar(200),price varchar(100) ) ";

    public MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_ARTICLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
