package com.notesApps.firebaseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.notesApps.firebaseauth.models.NoteBook;

import static com.notesApps.firebaseauth.Constants.NOTEBOOKS_NODE;

public class AddNoteBookActivity extends AppCompatActivity {


    DatabaseReference reference;
    TextInputEditText noteTitleTextInpuEditText;
    Button addNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_book);

        getSupportActionBar().setTitle("Add new Nootbook");
        noteTitleTextInpuEditText = findViewById(R.id.noteTitleTextInpuEditText);
        addNote = findViewById(R.id.addNoteButton);

        reference = FirebaseDatabase.getInstance().getReference()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(NOTEBOOKS_NODE);


        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!noteTitleTextInpuEditText.getText().toString().isEmpty()){

                        NoteBook notebook = new NoteBook();
                        notebook.setNoteBookName(noteTitleTextInpuEditText.getText().toString());
                        reference.push().setValue(notebook).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AddNoteBookActivity.this, "New Notebook Added", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });



                    Toast.makeText(AddNoteBookActivity.this, "Please Fill Notebook name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
