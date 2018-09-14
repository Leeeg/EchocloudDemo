package ctyon.com.logcatproject;

import android.app.Application;
import android.os.Environment;

import ctyon.com.logcatproject.huishengyun.utils.MediaManager;

public class MyApplication extends Application {

    private static MyApplication application;

    public static MyApplication getInstance(){
        return application;
    }

    private MediaManager recordManager;

    public MediaManager getRecordManager() {
        return recordManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

//        recordManager = MediaManager.getInstance(getExternalFilesDir(Environment.DIRECTORY_MUSIC));
        recordManager = MediaManager.getInstance(Environment.getExternalStorageDirectory());

    }

}
