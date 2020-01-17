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
import com.notesApps.firebaseauth.models.NoteBook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import static com.notesApps.firebaseauth.Constants.NOTEBOOKS_NODE;

public class AllNoteBooksActivity extends AppCompatActivity {


    private static final String TAG = "AllNoteBooksActivity";
    FirebaseUser firebaseUser ;
    DatabaseReference databaseReference;
    RecyclerView noteBookRec;
    NoteBookAdapter noteBookAdapter;
    ArrayList<NoteBook> noteBooks;
    private String userID;
    private TextView noNoteBooksTextView;
    private ProgressBar noteBooksProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_note_books);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Your Notebooks");

        FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        noteBookRec = findViewById(R.id.notebooksRec);
        noNoteBooksTextView = findViewById(R.id.noNoteBooksTextView);
        noteBooksProgress = findViewById(R.id.notebookProgress);
        FloatingActionButton fab = findViewById(R.id.fab);
        noteBooks= new ArrayList<>();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AllNoteBooksActivity.this,AddNoteBookActivity.class);
                startActivity(intent);
            }
        });

        noNoteBooksTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AllNoteBooksActivity.this,AddNoteBookActivity.class);
                startActivity(intent);

            }
        });


        noteBookAdapter = new NoteBookAdapter(this,noteBooks);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3 ,RecyclerView.VERTICAL,false);
        noteBookRec.setLayoutManager(gridLayoutManager);
        noteBookRec.setAdapter(noteBookAdapter);



        fireBaseUpdate();
    }



    private void fireBaseUpdate(){


        noNoteBooksTextView.setVisibility(View.GONE);
        noteBooksProgress.setVisibility(View.VISIBLE);


        userID = firebaseUser.getUid();
        Log.d(TAG, "fireBaseUpdate: "+userID);
        databaseReference.child(userID) .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


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
