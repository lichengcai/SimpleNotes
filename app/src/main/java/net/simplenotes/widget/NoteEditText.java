package net.simplenotes.widget;

import android.content.Context;
import android.graphics.Rect;
import android.text.Layout;
import android.text.Selection;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.EditText;

import net.simplenotes.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lichengcai on 2016/6/20.
 */
public class NoteEditText extends EditText {
    private static final String TAG = "NoteEditText";
    private int mIndex;
    private int mSelectionStartBeforeDelete;
    private OnTextViewChangeListener mOntextViewChangeListener;

    private static final String SCHEME_TEL = "tel:";
    private static final String SCHEME_HTTP = "http:";
    private static final String SCHEME_EMALL = "mailto:";

    private static final Map<String,Integer> sSchemaActionResMap = new HashMap<>();
    static {
        sSchemaActionResMap.put(SCHEME_TEL, R.string.note_link_tel);
        sSchemaActionResMap.put(SCHEME_HTTP,R.string.note_link_web);
        sSchemaActionResMap.put(SCHEME_EMALL,R.string.note_link_email);
    }

    public NoteEditText(Context context) {
        super(context);
        mIndex = 0;
    }

    public NoteEditText(Context context, AttributeSet attrs) {
        super(context, attrs,android.R.attr.editTextStyle);
    }

    public NoteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    public void setOnTextViewChangeListener(OnTextViewChangeListener onTextViewChangeListener) {
        mOntextViewChangeListener = onTextViewChangeListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                x -= getTotalPaddingLeft();
                y -= getTotalPaddingTop();
                x += getScrollX();
                y += getScrollY();

                Layout layout = getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line,x);
                Selection.setSelection(getText(),off);
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                if (mOntextViewChangeListener != null) {
                    return false;
                }
                break;
            case KeyEvent.KEYCODE_DEL:
                mSelectionStartBeforeDelete = getSelectionStart();
                Log.d(TAG,"getSelectionStart:---" + getSelectionStart());
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                if (mOntextViewChangeListener != null) {
                    int selectionStart = getSelectionStart();
                    String text = getText().subSequence(selectionStart,length()).toString();
                    setText(getText().subSequence(0,selectionStart));
                    mOntextViewChangeListener.onEditTextEnter(mIndex + 1,text);
                }
                break;
            case KeyEvent.KEYCODE_DEL:
                if (mOntextViewChangeListener != null) {
                    if (mSelectionStartBeforeDelete == 0 && mIndex != 0) {
                        mOntextViewChangeListener.onEditTextDelete(mIndex,getText().toString());
                        return true;
                    }
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        Log.d(TAG,"onFocusChanged focused:---" + focused);
        Log.d(TAG,"onFocusChanged:---" + direction);
        if (mOntextViewChangeListener != null ) {
            if (!focused && TextUtils.isEmpty(getText())) {
                mOntextViewChangeListener.onTextChange(mIndex,false);
            }else {
                mOntextViewChangeListener.onTextChange(mIndex,true);
            }
        }
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        if (getText() !=null) {
            int selStart = getSelectionStart();
            int selEnd = getSelectionEnd();

            int min = Math.min(selEnd,selStart);
            int max = Math.max(selEnd,selStart);

            final URLSpan[] urls = getText().getSpans(min,max,URLSpan.class);
            if (urls.length == 1) {
                int defaultResId = 0;
                for (String schema : sSchemaActionResMap.keySet()) {
                    if (urls[0].getURL().contains(schema)) {
                        defaultResId = sSchemaActionResMap.get(schema);
                        break;
                    }
                }

                if (defaultResId == 0) {
                    defaultResId = R.string.note_link_other;
                }

                menu.add(0,0,0,defaultResId).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        urls[0].onClick(NoteEditText.this);
                        return true;
                    }
                });
            }

        }
        super.onCreateContextMenu(menu);
    }

    public interface OnTextViewChangeListener {
        void onEditTextDelete(int index,String text);
        void onEditTextEnter(int index,String text);
        void onTextChange(int index,boolean hasText);
    }

}
