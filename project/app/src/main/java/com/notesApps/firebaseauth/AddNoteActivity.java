package com.notesApps.firebaseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.notesApps.firebaseauth.models.Note;

import static com.notesApps.firebaseauth.Constants.NOTES_NODE;

public class AddNoteActivity extends AppCompatActivity {

    Intent intent;
    String notebookID;
    DatabaseReference reference;
    TextInputEditText noteTitleTextInpuEditText,noteContentTextInpuEditText;
    Button addNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        getSupportActionBar().setTitle("Add new note");


        intent = getIntent();

        if (intent.getStringExtra(Constants.NOTEBOOK_ID_INTENT) != null) {
            notebookID = intent.getStringExtra(Constants.NOTEBOOK_ID_INTENT);
            Log.d("test", "note new : "+notebookID);
        }
        noteTitleTextInpuEditText = findViewById(R.id.noteTitleTextInpuEditText);
        noteContentTextInpuEditText = findViewById(R.id.noteContentTextInpuEditText);
        addNote = findViewById(R.id.addNoteButton);

        reference = FirebaseDatabase.getInstance().getReference()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(NOTES_NODE);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!noteTitleTextInpuEditText.getText().toString().isEmpty()){
                    if ( !noteContentTextInpuEditText.getText().toString().isEmpty()){

                        Note note = new Note();
                        note.setNoteTitle(noteTitleTextInpuEditText.getText().toString());
                        Log.d("tedt", "onClick: "+notebookID);
                        note.setNoteContent(noteContentTextInpuEditText.getText().toString());
                        if (notebookID!=null)
                        {note.setNotebookID(notebookID);
                        }
                        reference.push().setValue(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AddNoteActivity.this, "New Note Added", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });

                    }else {
                        Toast.makeText(AddNoteActivity.this, "Please Fill Note Content", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(AddNoteActivity.this, "Please Fill Note Title", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
