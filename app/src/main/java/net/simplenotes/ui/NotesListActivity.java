package net.simplenotes.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import net.simplenotes.R;
import net.simplenotes.data.Notes;

/**
 * Created by lichengcai on 2016/6/20.
 */
public class NotesListActivity extends Activity {
    private long mCurrentFolderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        Button button = (Button) findViewById(R.id.btn_new_note);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click", "click---one");
                Intent intent = new Intent(NotesListActivity.this,NoteEditActivity.class);
                intent.setAction(Intent.ACTION_INSERT_OR_EDIT);
                intent.putExtra(Notes.INTENT_EXTRA_FOLDER_ID, mCurrentFolderId);
                startActivity(intent);
            }
        });
    }
}
