package net.simplenotes.data;

/**
 * Created by lichengcai on 2016/6/20.
 */
public class Notes {
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
        public static final String MINE_TYPE = "mine_type";
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
}
