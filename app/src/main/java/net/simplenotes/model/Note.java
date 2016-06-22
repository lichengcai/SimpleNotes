package net.simplenotes.model;

import android.content.ContentValues;

/**
 * Created by lichengcai on 2016/6/20.
 */
public class Note {

    private class NoteData{

        private long mTextDataId;
        private ContentValues mTextDataValues;
        private long mCallDataId;
        private ContentValues mCallDataValues;
        private static final String TAG = "NoteData";

        public NoteData() {
            mTextDataValues = new ContentValues();
            mCallDataValues = new ContentValues();
            mCallDataId = 0;
            mTextDataId = 0;
        }
    }

}
