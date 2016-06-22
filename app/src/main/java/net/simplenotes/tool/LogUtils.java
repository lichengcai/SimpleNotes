package net.simplenotes.tool;

import android.util.Log;

/**
 * Created by lichengcai on 2016/6/22.
 */
public class LogUtils {
    private static String TAG = "LogUtils";
    /**
     * the log level
     */
    public static final int LEVEL_NONE = 0;
    public static final int LEVEL_VERBOSE = 1;
    public static final int LEVE_DEBUG = 2;
    public static final int LEVEL_INFO = 3;
    public static final int LEVEL_WARN = 4;
    public static final int LEVEL_ERROR = 5;

    /**
     * 日志输出级别
     */
    private static int mDebuggable = LEVEL_ERROR;

    private static long mTimestamp = 0;

    public static void v(String msg) {
        if (mDebuggable >= LEVEL_VERBOSE) {
            Log.v(TAG,msg);
        }
    }

    public static void d(String msg) {
        if (mDebuggable >= LEVE_DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (mDebuggable >= LEVEL_INFO) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (mDebuggable >= LEVEL_WARN) {
            Log.w(TAG, msg);
        }
    }

    public static void w(Throwable throwable) {
        if (mDebuggable >= LEVEL_WARN) {
            Log.w(TAG,"",throwable);
        }
    }

    public static void w(Throwable throwable,String msg) {
        if (mDebuggable >= LEVEL_WARN) {
            if (msg != null) {
                Log.w(TAG,msg,throwable);
            }
        }
    }

    public static void e(String msg) {
        if (mDebuggable >= LEVEL_ERROR) {
            Log.e(TAG,msg);
        }
    }

    public static void e(Throwable throwable){
        if (mDebuggable >= LEVEL_ERROR) {
            Log.e(TAG,"",throwable);
        }
    }

    public static void e(String msg,Throwable throwable) {
        if (mDebuggable >= LEVEL_ERROR) {
            if (msg != null) {
                Log.e(TAG,msg,throwable);
            }
        }
    }

    /** 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段结束点* @param msg 需要输出的msg */

    public static void elapsed(String msg) {

        long currentTime = System.currentTimeMillis();

        long elapsedTime = currentTime - mTimestamp;

        mTimestamp = currentTime;

        e("[Elapsed：" + elapsedTime + "]" + msg);

    }
}
