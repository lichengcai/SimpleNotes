package net.simplenotes.model;

import android.content.Context;

import net.simplenotes.data.Notes;

/**
 * Created by lichengcai on 2016/6/20.
 */
public class WorkingNote {
    private Note mNote;
    private long mNoteId;
    private String mContent;
    private int mMode;
    private long mAlertDate;
    private long mModifiedDate;
    private int mBgColorId;
    private int mWidgetType;
    private long mFolderId;
    private Context mContext;

    private static final String TAG = "WorkingNote";
    private boolean mIsDeleted;
    private NoteSettingChangedListener mNoteSettingChangedListener;

    public static final String[] DATA_PROJECTION = new String[] {
            Notes.DataColumns.ID,
            Notes.DataColumns.CONTENT,
            Notes.DataColumns.MINE_TYPE,
            Notes.DataColumns.DATA1,
            Notes.DataColumns.DATA2,
            Notes.DataColumns.DATA3,
            Notes.DataColumns.DATA4,
    };

    public static final String[] NOTE_PROJECTION = new String[]{
            Notes.NoteColumns.PARENT_ID,
            Notes.NoteColumns.ALERTED_DATE,
            Notes.NoteColumns.BG_COLOR_ID,
            Notes.NoteColumns.WIDGET_ID,
            Notes.NoteColumns.WIDGET_TYPE,
            Notes.NoteColumns.MODIFIED_DATE
    };


    public interface NoteSettingChangedListener {
        void onBackgroundColorChanged();
        void onClockAlertChanged(long date,boolean set);
        void onWidgetChanged();
        void onCheckListModeChanged(int oldMode,int newMode);
    }
}
