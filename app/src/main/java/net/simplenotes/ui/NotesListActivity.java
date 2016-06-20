package net.simplenotes.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.simplenotes.R;

/**
 * Created by lichengcai on 2016/6/20.
 */
public class NotesListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        Button button = (Button) findViewById(R.id.btn_new_note);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesListActivity.this,NoteEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("int",10);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });
    }
}
