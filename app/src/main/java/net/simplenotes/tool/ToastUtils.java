package net.simplenotes.tool;

import android.widget.Toast;

import net.simplenotes.NotesApplication;

/**
 * Created by lichengcai on 2016/6/22.
 */
public class ToastUtils {

    public static void showToast(String message) {
        Toast.makeText(NotesApplication.mContext,message,Toast.LENGTH_LONG).show();
    }
}
