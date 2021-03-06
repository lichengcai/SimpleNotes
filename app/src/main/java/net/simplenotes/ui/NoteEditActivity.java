package net.simplenotes.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.simplenotes.R;
import net.simplenotes.data.Notes;
import net.simplenotes.model.WorkingNote;
import net.simplenotes.tool.DataUtils;
import net.simplenotes.tool.LogUtils;
import net.simplenotes.tool.ResourceParser;
import net.simplenotes.tool.ToastUtils;
import net.simplenotes.widget.NoteEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by lichengcai on 2016/6/20.
 */
public class NoteEditActivity extends Activity implements View.OnClickListener,
        NoteEditText.OnTextViewChangeListener,WorkingNote.NoteSettingChangedListener{

    private class HeadViewHolder {
        public TextView tvModified;
        public ImageView tvAlertIcon;
        public TextView tvAlertDate;
        public ImageView ibSetBgColor;
    }

    private static final Map<Integer,Integer> sBgSelectorBtnsMap = new HashMap<>();
    static {
        sBgSelectorBtnsMap.put(R.id.iv_bg_yellow, ResourceParser.YELLOW);
        sBgSelectorBtnsMap.put(R.id.iv_bg_red,ResourceParser.RED);
        sBgSelectorBtnsMap.put(R.id.iv_bg_blue,ResourceParser.BLUE);
        sBgSelectorBtnsMap.put(R.id.iv_bg_green,ResourceParser.GREEN);
        sBgSelectorBtnsMap.put(R.id.iv_bg_white,ResourceParser.WHITE);
    }

    private static final Map<Integer,Integer> sBgSelectorSelectionMap = new HashMap<>();
    static {
        sBgSelectorSelectionMap.put(ResourceParser.YELLOW,R.id.iv_bg_yellow_select);
        sBgSelectorSelectionMap.put(ResourceParser.RED,R.id.iv_bg_red_select);
        sBgSelectorSelectionMap.put(ResourceParser.BLUE,R.id.iv_bg_blue_select);
        sBgSelectorSelectionMap.put(ResourceParser.GREEN,R.id.iv_bg_green_select);
        sBgSelectorSelectionMap.put(ResourceParser.WHITE,R.id.iv_bg_white_select);
    }

    private static final Map<Integer,Integer> sFontSizeBtnsMap = new HashMap<Integer,Integer>();
    static {
        sFontSizeBtnsMap.put(R.id.ll_font_large,ResourceParser.TEXT_LARGE);
        sFontSizeBtnsMap.put(R.id.ll_font_normal,ResourceParser.TEXT_MEDIUM);
        sFontSizeBtnsMap.put(R.id.ll_font_small,ResourceParser.TEXT_SMALL);
        sFontSizeBtnsMap.put(R.id.ll_font_super,ResourceParser.TEXT_SUPER);
    }

    private static final Map<Integer,Integer> sFontSelectorSelectionMap = new HashMap<>();
    static {
        sFontSelectorSelectionMap.put(ResourceParser.TEXT_LARGE,R.id.iv_large_select);
        sFontSelectorSelectionMap.put(ResourceParser.TEXT_MEDIUM,R.id.iv_medium_select);
        sFontSelectorSelectionMap.put(ResourceParser.TEXT_SMALL,R.id.iv_small_select);
        sFontSelectorSelectionMap.put(ResourceParser.TEXT_SUPER,R.id.iv_super_select);
    }

    private static final String TAG = "NoteEditActivity";

    private HeadViewHolder mNoteHeadViewHolder;

    private View mHeadViewPanel;
    private View mNoteBgColorSelector;
    private View mFontSizeSelector;
    private EditText mNoteEditor;
    private View mNoteEditorPanel;
    private WorkingNote mWorkingNote;

    private SharedPreferences mSharedPreferences;
    private int mFontSizeId;
    private static final String PREFERENCE_FONT_SIZE = "pref_font_size";
    private static final int SHORTCUT_ICON_TITLE_MAX_LEN = 10;
    public static final String TAG_CHECKED = String.valueOf('\u221A');
    public static final String TAG_UNCHECKE = String.valueOf('\u25A1');
    private LinearLayout mEditTextList;
    private String mUserQuery;
    private Pattern mPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        String action = getIntent().getAction();
        boolean equals = TextUtils.equals(Intent.ACTION_INSERT_OR_EDIT,getIntent().getAction());
        LogUtils.d("action:---" + action + "   equals:---" + equals);
        Log.d(TAG,"savedInstanceState:---" + savedInstanceState);
        if (savedInstanceState == null && !initActivityState(getIntent())) {
            finish();
            return;
        }
        initResources();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initNoteScreen();
    }

    private void initNoteScreen() {
        mNoteEditor.setTextAppearance(this, ResourceParser.TextAppearanceResources.getTextAppearanceResource(mFontSizeId));
        if (mWorkingNote.getCheckListMode() == Notes.TextNote.MODE_CHECK_LIST) {

        }else {
//            mNoteEditor.setText();
        }

        mHeadViewPanel.setBackgroundResource(mWorkingNote.getTitleBgResId());
        mNoteEditorPanel.setBackgroundResource(mWorkingNote.getBgColorResId());
        mNoteHeadViewHolder.tvModified.setText(DateUtils.formatDateTime(this, mWorkingNote.getModifiedDate(), DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME
                | DateUtils.FORMAT_SHOW_YEAR));
    }
    private void initResources(){
        mHeadViewPanel = findViewById(R.id.note_title);
        mNoteHeadViewHolder = new HeadViewHolder();
        mNoteHeadViewHolder.tvModified = (TextView) findViewById(R.id.tv_modified_date);
        mNoteHeadViewHolder.tvAlertIcon = (ImageView) findViewById(R.id.iv_alert_icon);
        mNoteHeadViewHolder.tvAlertDate = (TextView) findViewById(R.id.tv_alert_date);
        mNoteHeadViewHolder.ibSetBgColor = (ImageView) findViewById(R.id.btn_set_bg_color);
        mNoteHeadViewHolder.ibSetBgColor.setOnClickListener(this);

        mNoteEditor = (EditText) findViewById(R.id.note_edit_view);
        mNoteEditorPanel = findViewById(R.id.sv_note_edit);

        mNoteBgColorSelector = findViewById(R.id.note_bg_color_selector);
        for (int id : sBgSelectorBtnsMap.keySet()) {
            ImageView iv = (ImageView) findViewById(id);
            iv.setOnClickListener(this);
        }

        mFontSizeSelector = findViewById(R.id.font_size_selector);
        for (int id : sFontSizeBtnsMap.keySet()) {
            View view = findViewById(id);
            view.setOnClickListener(this);
        }

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mFontSizeId = mSharedPreferences.getInt(PREFERENCE_FONT_SIZE,ResourceParser.BG_DEFAULT_FONT_SIZE);

        if (mFontSizeId >= ResourceParser.TextAppearanceResources.getResourcesSize()) {
            mFontSizeId = ResourceParser.BG_DEFAULT_FONT_SIZE;
        }
        mEditTextList = (LinearLayout) findViewById(R.id.note_edit_list);

    }

    private boolean saveNote() {
        getWorkingText();
        boolean saved = false;
        if (saved) {
            setResult(RESULT_OK);
        }
        return saved;
    }

    /**
     * If the user specified the {@link Intent#ACTION_VIEW} but not provided with id,
     * then jump to the NotesListActivity
     */
    private boolean initActivityState(Intent intent) {
        mWorkingNote = null;
        if (TextUtils.equals(Intent.ACTION_VIEW,intent.getAction())) {
            long noteId = intent.getLongExtra(Intent.EXTRA_UID,0);
            mUserQuery = "";
            /**
             * Starting from the searched result
             */
            if (intent.hasExtra(SearchManager.EXTRA_DATA_KEY)) {
                noteId = Long.parseLong(intent.getStringExtra(SearchManager.EXTRA_DATA_KEY));
                mUserQuery = intent.getStringExtra(SearchManager.USER_QUERY);
            }

            if (!DataUtils.visibleInNoteDatabase(getContentResolver(),noteId, Notes.TYPE_NOTE)) {
                Intent jump = new Intent(NoteEditActivity.this,NotesListActivity.class);
                startActivity(jump);
                ToastUtils.showToast(getString(R.string.error_note_not_exist));
                finish();
                return false;
            }else {
                mWorkingNote = WorkingNote.load(this,noteId);
                if (mWorkingNote == null) {
                    Log.e(TAG, "load note fail with the noteId:--" + noteId);
                    finish();
                    return false;
                }
            }
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        }else if (TextUtils.equals(Intent.ACTION_INSERT_OR_EDIT,intent.getAction())) {
            long folderId = intent.getLongExtra(Notes.INTENT_EXTRA_FOLDER_ID,0);
            int widgetId = intent.getIntExtra(Notes.INTENT_EXTRA_WIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            int widgetType = intent.getIntExtra(Notes.INTENT_EXTRA_WIDGET_TYPE,Notes.TYPE_WIDGET_INVALIDE);
            int bgResId = intent.getIntExtra(Notes.INTENT_EXTRA_BACKGROUND_ID,ResourceParser.getDefaultBgId(this));

            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            long callDate = intent.getLongExtra(Notes.INTENT_EXTRA_CALL_DATE,0);
            if (callDate != 0 && phoneNumber != null) {
                if (TextUtils.isEmpty(phoneNumber)) {
                    LogUtils.w("the call record number is null");
                }
                long noteId = 0;
                if((noteId = DataUtils.getNoteIdByPhoneNumberAndCallDate(getContentResolver(),phoneNumber,callDate)) >= 0) {
                    mWorkingNote = WorkingNote.load(this,noteId);
                    if (mWorkingNote == null) {
                        LogUtils.e("load call note failed with note id " + noteId);
                        finish();
                        return false;
                    }
                }else {
                    mWorkingNote = WorkingNote.createEmptyNote(this,folderId,widgetId,widgetType,bgResId);

                }
            }else {
                mWorkingNote = WorkingNote.createEmptyNote(this,folderId,widgetId,widgetType,bgResId);
            }
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        }else {
            LogUtils.e("Intent not specified action, should not support");
            finish();
            return false;
        }
        mWorkingNote.setOnSettingStatusChangedListener(this);
        return true;
    }

    private boolean getWorkingText() {
        boolean hasChecked = false;
        if (mWorkingNote.getCheckListMode() == Notes.TextNote.MODE_CHECK_LIST) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i=0; i<mEditTextList.getChildCount(); i++) {
                View view = mEditTextList.getChildAt(i);
                NoteEditText editText = (NoteEditText) view.findViewById(R.id.et_edit_text);
                if (!TextUtils.isEmpty(editText.getText())) {
                    if (((CheckBox)view.findViewById(R.id.cb_edit_item)).isChecked()) {
                        stringBuffer.append(TAG_CHECKED).append(" ").append(editText.getText().append("/n"));
                        hasChecked = true;
                    }else {
                        stringBuffer.append(TAG_UNCHECKE).append(" ").append(editText.getText().append("/n"));
                    }
                }
            }
        }else {

        }
        return hasChecked;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_new_note:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (clearSettingState()) {
            return;
        }
        super.onBackPressed();

    }

    /**
     * hide the NoteBgColorSelector or FontSizeSelector
     * @return
     */
    private boolean clearSettingState() {
        if (mNoteBgColorSelector.getVisibility() == View.VISIBLE) {
            mNoteBgColorSelector.setVisibility(View.GONE);
            return true;
        }else if (mFontSizeSelector.getVisibility() == View.VISIBLE) {
            mFontSizeSelector.setVisibility(View.GONE);
            return true;
        }
        return false;
    }
    @Override
    public void onEditTextDelete(int index, String text) {

    }

    @Override
    public void onEditTextEnter(int index, String text) {

    }

    @Override
    public void onTextChange(int index, boolean hasText) {

    }

    @Override
    public void onBackgroundColorChanged() {

    }

    @Override
    public void onClockAlertChanged(long date, boolean set) {

    }

    @Override
    public void onWidgetChanged() {

    }

    @Override
    public void onCheckListModeChanged(int oldMode, int newMode) {

    }
}
