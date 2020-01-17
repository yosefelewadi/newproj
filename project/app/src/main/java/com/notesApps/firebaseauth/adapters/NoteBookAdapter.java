package com.notesApps.firebaseauth.adapters;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.notesApps.firebaseauth.Constants;
import com.notesApps.firebaseauth.NotebookActivity;
import com.notesApps.firebaseauth.R;
import com.notesApps.firebaseauth.models.NoteBook;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NoteBookAdapter extends RecyclerView.Adapter<NoteBookAdapter.NoteBookViewHolder> {

    private Context context;
    private ArrayList<NoteBook> noteBooks;

    public NoteBookAdapter(Context context, ArrayList<NoteBook> noteBooks) {
        this.context = context;
        this.noteBooks = noteBooks;
    }

    @NonNull
    @Override
    public NoteBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notebook_cell,parent,false);

        return new NoteBookViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull NoteBookViewHolder holder, int position) {

        holder.noteBookTitle.setText(noteBooks.get(position).getNoteBookName());
        holder.cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(context, NotebookActivity.class);
                intent.putExtra(Constants.NOTEBOOK_ID_INTENT,noteBooks.get(position).getId());
                Log.d(TAG, "onClick: "+noteBooks.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteBooks.size();
    }

    class NoteBookViewHolder extends RecyclerView.ViewHolder{

        CardView cell;
        TextView noteBookTitle;
        public NoteBookViewHolder(@NonNull View itemView) {
            super(itemView);
            noteBookTitle = itemView.findViewById(R.id.noteBookTitle);
            cell=itemView.findViewById(R.id.cell);
        }
    }
}
