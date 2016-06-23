package net.simplenotes.data;

import android.net.Uri;

/**
 * Created by lichengcai on 2016/6/20.
 */
public class Notes {
    public static final String AUTHORITY = "micode_notes";
    public static final String TAG = "Notes";
    public static final int TYPE_NOTE = 0;
    public static final int TYPE_FOLDER = 1;
    public static final int TYPE_SYSTEM = 2;

    public static final String INTENT_EXTRA_ALERT_DATE = "net.micode.notes.alert_date";
    public static final String INTENT_EXTRA_BACKGROUND_ID = "net.micode.notes.background_color_id";
    public static final String INTENT_EXTRA_WIDGET_ID = "net.micode.notes.widget_id";
    public static final String INTENT_EXTRA_WIDGET_TYPE = "net.micode.notes.widget_type";
    public static final String INTENT_EXTRA_FOLDER_ID = "net.micode.notes.folder_id";
    public static final String INTENT_EXTRA_CALL_DATE = "net.micode.notes.call_date";



    public static final int TYPE_WIDGET_INVALIDE = -1;
    public static final int ID_TRASH_FOLER = -3;
    public static final Uri CONTENT_NOTE_URI = Uri.parse("content://" + AUTHORITY + "/note");
    /**
     * Uri to query data
     */
    public static final Uri CONTENT_DATA_URI = Uri.parse("content://" + AUTHORITY + "/data");

    public interface NoteColumns {
        public static final String ID = "_id";
        public static final String CREATED_DATE = "created_date";
        public static final String PARENT_ID = "parent_id";
        public static final String MODIFIED_DATE = "modified_date";
        public static final String ALERTED_DATE = "alert_date";
        public static final String SNIPPET = "snippet";
        public static final String WIDGET_ID = "widget_id";
        public static final String WIDGET_TYPE = "widget_type";
        public static final String BG_COLOR_ID = "bg_color_id";
        public static final String HAS_ATTACHMENT = "has_attachment";
        public static final String NOTES_COUNT = "notes_count";

        public static final String TYPE = "type";
        public static final String SYNC_ID = "sync_id";
        public static final String LOCAL_MODIFIED = "local_modified";
        public static final String ORIGIN_PARENT_ID = "origin_parent_id";
        public static final String GTASK_ID = "gtask_id";
        public static final String VERSION = "version";
    }

    public interface DataColumns {
        public static final String ID = "id";
        /**
         * The MIME type of the item represented by this row.
         * <P> Type: Text </P>
         */
        public static final String MIME_TYPE = "mine_type";
        public static final String NOTE_ID = "note_id";
        public static final String CREATED_DATE = "created_date";
        public static final String MODIFIED_DATE = "modified_date";

        public static final String CONTENT = "content";
        public static final String DATA1 = "data1";
        public static final String DATA2 = "data2";
        public static final String DATA3 = "data3";
        public static final String DATA4 = "data4";
        public static final String DATA5 = "data5";

    }

    public static final class CallNote implements  DataColumns {
        public static final String CALL_DATE = DATA1;
        public static final String PHONE_NUMBER = DATA3;
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/call_note";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/call_note";
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/call_note");
    }

    /**
     * Mode to indicate the text in check list mode or not
     * <P> Type:Integer 1: check list mode  0: normal mode </P>
     */
    public static final class TextNote implements DataColumns {
        public static final String MODE = DATA1;
        public static final int MODE_CHECK_LIST = 1;
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/text_note";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/text_note";
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/text_note");
    }
}
