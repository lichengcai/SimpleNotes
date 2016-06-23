package net.simplenotes.model;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import net.simplenotes.data.Notes;
import net.simplenotes.tool.ResourceParser;

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
    private int mWidgetId;
    private int mWidgetType;
    private long mFolderId;
    private Context mContext;

    private static final String TAG = "WorkingNote";
    private boolean mIsDeleted;
    private NoteSettingChangedListener mNoteSettingChangedListener;

    public void setOnSettingStatusChangedListener(NoteSettingChangedListener l) {
        mNoteSettingChangedListener = l;
    }
    public Note getmNote() {
        return mNote;
    }

    public void setmNote(Note mNote) {
        this.mNote = mNote;
    }

    public long getmNoteId() {
        return mNoteId;
    }

    public void setmNoteId(long mNoteId) {
        this.mNoteId = mNoteId;
    }

    public int getmMode() {
        return mMode;
    }

    public void setmMode(int mMode) {
        this.mMode = mMode;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public long getmAlertDate() {
        return mAlertDate;
    }

    public void setmAlertDate(long mAlertDate) {
        this.mAlertDate = mAlertDate;
    }

    public long getmModifiedDate() {
        return mModifiedDate;
    }

    public void setmModifiedDate(long mModifiedDate) {
        this.mModifiedDate = mModifiedDate;
    }

    public int getmBgColorId() {
        return mBgColorId;
    }

    public void setmBgColorId(int mBgColorId) {
        this.mBgColorId = mBgColorId;
    }

    public int getmWidgetId() {
        return mWidgetId;
    }

    public void setmWidgetId(int mWidgetId) {
        this.mWidgetId = mWidgetId;
    }

    public int getmWidgetType() {
        return mWidgetType;
    }

    public void setmWidgetType(int mWidgetType) {
        this.mWidgetType = mWidgetType;
    }

    public long getmFolderId() {
        return mFolderId;
    }

    public void setmFolderId(long mFolderId) {
        this.mFolderId = mFolderId;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }



    public static final String[] DATA_PROJECTION = new String[] {
            Notes.DataColumns.ID,
            Notes.DataColumns.CONTENT,
            Notes.DataColumns.MIME_TYPE,
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

    private static final int DATA_ID_COLUMN = 0;
    private static final int DATA_CONTENT_COLUMN = 1;
    private static final int DATA_MIME_TYPE_COLUMN = 2;
    private static final int DATA_MODE_COLUMN =3;
    private static final int NOTE_PARENT_ID_COLUMN =0;
    private static final int NOTE_ALERTED_DATE_COLUMN = 1;
    private static final int NOTE_BG_COLOR_ID_COLUMN = 2;
    private static final int NOTE_WIDGET_ID_COLUMN = 3;
    private static final int NOTE_WIDGET_TYPE_COLUMNE = 4;
    private static final int NOTE_MODIFIED_DATE_COLUMN = 5;

    private WorkingNote(Context context,long folderId) {
        mContext = context;
        mFolderId = folderId;
        mModifiedDate = System.currentTimeMillis();
        mAlertDate = 0;
        mNote = new Note();
        mNoteId = 0;
        mIsDeleted = false;
        mMode = 0;
        mWidgetType = Notes.TYPE_WIDGET_INVALIDE;
    }

    private WorkingNote(Context context,long noteId,long folderId) {
        mContext = context;
        mNoteId = noteId;
        mFolderId = folderId;
        mIsDeleted = false;
        mNote = new Note();
        loadNote();
    }

    public synchronized boolean saveNote() {
        if (mIsDeleted || (!existInDatabase() && TextUtils.isEmpty(mContent))
                || (existInDatabase() && )) {

        }
        return false;
    }

    public boolean existInDatabase() {
        return mNoteId > 0;
    }
    public static WorkingNote load(Context context,long id) {
        return new WorkingNote(context,id);
    }
    private void loadNote() {
        Cursor cursor = mContext.getContentResolver().query(
                ContentUris.withAppendedId(Notes.CONTENT_NOTE_URI, mNoteId), NOTE_PROJECTION, null,
                null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                mFolderId = cursor.getLong(NOTE_PARENT_ID_COLUMN);
                mBgColorId = cursor.getInt(NOTE_BG_COLOR_ID_COLUMN);
                mWidgetId = cursor.getInt(NOTE_WIDGET_ID_COLUMN);
                mWidgetType = cursor.getInt(NOTE_WIDGET_TYPE_COLUMNE);
                mAlertDate = cursor.getLong(NOTE_ALERTED_DATE_COLUMN);
                mModifiedDate = cursor.getLong(NOTE_MODIFIED_DATE_COLUMN);
            }
            cursor.close();
        }else {
            Log.e(TAG,"No Note with id:" + mNoteId);
        }
        loadNoteData();
    }

    private void loadNoteData() {

    }

    public static WorkingNote createEmptyNote(Context context,long folderId,int widgetId,int widgetType,
                                              int defaultBgColorId) {
        WorkingNote note = new WorkingNote(context,folderId);
        note.setmBgColorId(defaultBgColorId);
        note.setmWidgetId(widgetId);
        note.setmWidgetType(widgetType);
        return note;
    }

    public void convertToCallNote(String phoneNumber,long callDate) {

    }

    public int getCheckListMode() {
        return mMode;
    }
    public int getBgColorResId() {
        return ResourceParser.NoteBgResources.getNoteBgResource(mBgColorId);
    }

    public int getBgColorId() {
        return mBgColorId;
    }

    public int getTitleBgResId() {
        return ResourceParser.NoteBgResources.getNoteTitleBgResource(mBgColorId);
    }
    public long getModifiedDate() {
        return mModifiedDate;
    }
    public interface NoteSettingChangedListener {
        void onBackgroundColorChanged();
        void onClockAlertChanged(long date,boolean set);
        void onWidgetChanged();
        void onCheckListModeChanged(int oldMode,int newMode);
    }
}
