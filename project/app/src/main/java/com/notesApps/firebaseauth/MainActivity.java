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
import com.notesApps.firebaseauth.adapters.NoteBookAdapter;
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


public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";

    FirebaseUser firebaseUser ;
    DatabaseReference databaseReference;
    RecyclerView noteBookRec,notesRec;
    NotesAdapter notesAdapter;
    NoteBookAdapter noteBookAdapter;
    ArrayList<Note> notes;
    ArrayList<NoteBook> noteBooks;
    private String userID;
    private TextView logoutTextView,noNoteBooksTextView,noNotesTextView,seeAllNoteBooks,seeAllNotes;
    private ProgressBar notesProgress,noteBooksProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initUI();

    }

    private void initUI() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        noteBookRec = findViewById(R.id.noteBookRec);
        notesRec = findViewById(R.id.notesRec);
        noNotesTextView = findViewById(R.id.noNotesTextView);
        noNoteBooksTextView= findViewById(R.id.noNoteBooksTextView);
        notesProgress= findViewById(R.id.noteProgress);
        noteBooksProgress= findViewById(R.id.notebooksProgress);

        seeAllNoteBooks = findViewById(R.id.seeAllNoteBooks);
        seeAllNotes = findViewById(R.id.seeAllNotes);

        seeAllNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AllNotesActivity.class);
                startActivity(intent);
            }
        });
        seeAllNoteBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AllNoteBooksActivity.class);
                startActivity(intent);
            }
        });
        noNotesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,AddNoteActivity.class);
                startActivity(intent);

            }
        });
        noNoteBooksTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,AddNoteBookActivity.class);
                startActivity(intent);

            }
        });

        notes = new ArrayList<Note>();
        noteBooks = new ArrayList<NoteBook>();

        notesRec.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        noteBookRec.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));


        notesAdapter = new NotesAdapter(notes,this);
        noteBookAdapter = new NoteBookAdapter(this,noteBooks);


        notesRec.setAdapter(notesAdapter);
        noteBookRec.setAdapter(noteBookAdapter);
        logoutTextView = findViewById(R.id.logoutTextView);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this,SplashActivity.class);
                startActivity(intent);
                finish();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,AddNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fireBaseUpdate();

    }

    private void fireBaseUpdate(){


        noNoteBooksTextView.setVisibility(View.GONE);
        noNotesTextView.setVisibility(View.GONE);
        noteBooksProgress.setVisibility(View.VISIBLE);
        notesProgress.setVisibility(View.VISIBLE);


        userID = firebaseUser.getUid();
        Log.d(TAG, "fireBaseUpdate: "+userID);
        Log.d(TAG, "fireBaseUpdate: ");
        databaseReference.child(userID) .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                Toast.makeText(MainActivity.this, "dataupdates", Toast.LENGTH_SHORT).show();
                ArrayList<Note> tmpNotes= new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.child(Constants.NOTES_NODE).getChildren()) {

                    tmpNotes.add(postSnapshot.getValue(Note.class));

                }
                notesProgress.setVisibility(View.GONE);
                notes.clear();
                Collections.reverse(tmpNotes);
                notes.addAll(tmpNotes);
                notesAdapter.notifyDataSetChanged();


                noNotesTextView.setVisibility((tmpNotes.size()==0)?View.VISIBLE:View.GONE);


                ArrayList<NoteBook> tmpNoteBooks= new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.child(NOTEBOOKS_NODE).getChildren()) {

                    NoteBook noteBook = postSnapshot.getValue(NoteBook.class);
                    noteBook.setId(postSnapshot.getKey());
                    tmpNoteBooks.add(noteBook);

                }
                noteBooksProgress.setVisibility(View.GONE);
                noteBooks.clear();
                noteBooks.addAll(tmpNoteBooks);
                noteBookAdapter.notifyDataSetChanged();
                noNoteBooksTextView.setVisibility((tmpNoteBooks.size()==0)?View.VISIBLE:View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
