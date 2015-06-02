package utils;

import android.app.Application;

/**
 * Created by db on 6/2/15.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeanCloudService.AVInit(this);

    }
}
