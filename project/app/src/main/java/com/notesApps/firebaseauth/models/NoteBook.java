package com.notesApps.firebaseauth.models;

public class NoteBook {

    private String noteBookName;
    private String id;
    private int noteBookColor;

    public NoteBook() {
    }

    public String getNoteBookName() {
        return noteBookName;
    }

    public void setNoteBookName(String noteBookName) {
        this.noteBookName = noteBookName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNoteBookColor() {
        return noteBookColor;
    }

    public void setNoteBookColor(int noteBookColor) {
        this.noteBookColor = noteBookColor;
    }
}
