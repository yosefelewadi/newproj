package com.notesApps.firebaseauth;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.notesApps.firebaseauth.adapters.NotesAdapter;
import com.notesApps.firebaseauth.models.Note;
import com.notesApps.firebaseauth.models.NoteBook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import static com.notesApps.firebaseauth.Constants.NOTEBOOKS_NODE;

public class NotebookActivity extends AppCompatActivity {

    private static final String TAG = "NotebookActivity";
    NoteBook noteBook;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    RecyclerView notesRec;
    NotesAdapter notesAdapter;
    ArrayList<Note> notes;
    private String userID;
    private TextView noNotesTextView;
    private ProgressBar notesProgress;
    private Intent intent;

    String noteBookID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook);

        initUI();
    }

    private void initUI() {


        intent = getIntent();
        if (intent.getStringExtra(Constants.NOTEBOOK_ID_INTENT) != null) {
            noteBookID = intent.getStringExtra(Constants.NOTEBOOK_ID_INTENT);
            Log.d(TAG, "initUI: "+noteBookID);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                toolbar.setTitle(dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(NOTEBOOKS_NODE)
                        .child(noteBookID)
                        .child(Constants.NotebookName).getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotebookActivity.this, AddNoteActivity.class);
                intent.putExtra(Constants.NOTEBOOK_ID_INTENT,noteBookID);
                startActivity(intent);
            }
        });
        notesRec = findViewById(R.id.notesRec);
        noNotesTextView = findViewById(R.id.noNotesTextView);
        notesProgress = findViewById(R.id.noteProgress);


        notes = new ArrayList<Note>();

        notesRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        notesAdapter = new NotesAdapter(notes, this);
        notesRec.setAdapter(notesAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        fireBaseUpdate();

    }

    private void fireBaseUpdate() {


        noNotesTextView.setVisibility(View.GONE);
        notesProgress.setVisibility(View.VISIBLE);


        userID = firebaseUser.getUid();
        Log.d(TAG, "fireBaseUpdate: " + userID);
//        databaseReference.child(userID).
//        Query query = databaseReference.child(userID).child(Constants.NOTES_NODE);
//                .orderByChild(Constants.NOTEBOOK_ID_OF_NOTE).endAt(noteBookID);

//        query.addListenerForSingleValueEvent(new ValueEventListener() {

        Log.d(TAG, "fina "+userID+ "  "+noteBookID);

        databaseReference.child(userID) .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                Toast.makeText(MainActivity.this, "dataupdates", Toast.LENGTH_SHORT).show();
                ArrayList<Note> tmpNotes= new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.child(Constants.NOTES_NODE).getChildren()) {


                    Note note = postSnapshot.getValue(Note.class);
                    if (note.getNotebookID()!=null&&note.getNotebookID().equals(noteBookID)){

                        tmpNotes.add(note);
                    }

                }
                notesProgress.setVisibility(View.GONE);
                notes.clear();
                Collections.reverse(tmpNotes);
                notes.addAll(tmpNotes);
                notesAdapter.notifyDataSetChanged();


                noNotesTextView.setVisibility((tmpNotes.size()==0)?View.VISIBLE:View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//
//        databaseReference.child(userID).child(Constants.NOTES_NODE)
//        .addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
////                Toast.makeText(NotebookActivity.this, "data updates", Toast.LENGTH_SHORT).show();
//                ArrayList<Note> tmpNotes = new ArrayList<>();
//                for (DataSnapshot postSnapshot : dataSnapshot.child(Constants.NOTES_NODE).getChildren()) {
//
//                    Note note = postSnapshot.getValue(Note.class);
//
////                    if (note.getNotebookID().equals(noteBookID)){
//
//                        tmpNotes.add(postSnapshot.getValue(Note.class));
////                    }
//            }
//                Collections.reverse(tmpNotes);
//
//                notesProgress.setVisibility(View.GONE);
//                notes.clear();
//                notes.addAll(tmpNotes);
//                notesAdapter.notifyDataSetChanged();
//
//                noNotesTextView.setVisibility((tmpNotes.size() == 0) ? View.VISIBLE : View.GONE);
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }


}
