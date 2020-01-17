package com.notesApps.firebaseauth.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.notesApps.firebaseauth.R;
import com.notesApps.firebaseauth.models.Note;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private ArrayList<Note> notes;
    private Context context;

    public NotesAdapter(ArrayList<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_cell,parent,false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        Note note= notes.get(position);
        holder.noteTitle.setText(note.getNoteTitle());
        holder.noteDetails.setText(note.getNoteContent());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView noteTitle;
        TextView noteDate;
        TextView noteDetails;
        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle=itemView.findViewById(R.id.noteTitleTV);
            noteDate=itemView.findViewById(R.id.noteDateTv);
            noteDetails=itemView.findViewById(R.id.noteDetailsTv);
        }
    }
}
