package net.simplenotes.tool;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;

import net.simplenotes.data.Notes;

/**
 * Created by lichengcai on 2016/6/21.
 */
public class DataUtils {
    private static final String TAG = "DataUtils";

    public static boolean visibleInNoteDatabase(ContentResolver resolver,long noteId,int type) {
        Cursor cursor = resolver.query(ContentUris.withAppendedId(Notes.CONTENT_NOTE_URI, noteId),
                null,
                Notes.NoteColumns.TYPE + "=? AND " + Notes.NoteColumns.PARENT_ID + "<>" + Notes.ID_TRASH_FOLER,
                new String [] {String.valueOf(type)},
                null);

        boolean exist = false;
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                exist = true;
            }
            cursor.close();
        }
        return exist;
    }

    public static long getNoteIdByPhoneNumberAndCallDate(ContentResolver resolver, String phoneNumber, long callDate) {
        Cursor cursor = resolver.query(Notes.CONTENT_DATA_URI,
                new String [] { Notes.CallNote.NOTE_ID },
                Notes.CallNote.CALL_DATE + "=? AND " + Notes.CallNote.MIME_TYPE + "=? AND PHONE_NUMBERS_EQUAL("
                        + Notes.CallNote.PHONE_NUMBER + ",?)",
                new String [] { String.valueOf(callDate), Notes.CallNote.CONTENT_ITEM_TYPE, phoneNumber },
                null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                try {
                    return cursor.getLong(0);
                } catch (IndexOutOfBoundsException e) {
                    LogUtils.e("Get call note id fails " + e.toString());
                }
            }
            cursor.close();
        }
        return 0;
    }
}
