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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class AllNotesActivity extends AppCompatActivity {

    FirebaseUser firebaseUser ;
    DatabaseReference databaseReference;
    RecyclerView notesRec;
    NotesAdapter notesAdapter;
    ArrayList<Note> notes;
    private String userID;
    private TextView logoutTextView,noNotesTextView;
    private ProgressBar notesProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();


    }


    private void initUI() {
        FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        notesRec = findViewById(R.id.notesRec);
        noNotesTextView = findViewById(R.id.noNotesTextView);
        notesProgress= findViewById(R.id.notesProgress);


        noNotesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AllNotesActivity.this,AddNoteActivity.class);
                startActivity(intent);

            }
        });

        notes = new ArrayList<Note>();

        notesRec.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        notesAdapter = new NotesAdapter(notes,this);
        notesRec.setAdapter(notesAdapter);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AllNotesActivity.this,AddNoteActivity.class);
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


        noNotesTextView.setVisibility(View.GONE);
        notesProgress.setVisibility(View.VISIBLE);


        userID = firebaseUser.getUid();
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


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
