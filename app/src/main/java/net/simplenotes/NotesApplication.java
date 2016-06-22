package net.simplenotes;

import android.app.Application;

/**
 * Created by lichengcai on 2016/6/22.
 */
public class NotesApplication extends Application {
    public static NotesApplication mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
