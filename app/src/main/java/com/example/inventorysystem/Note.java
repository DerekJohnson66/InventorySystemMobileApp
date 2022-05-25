package com.example.inventorysystem;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table", foreignKeys = @ForeignKey(entity = InventoryItem.class,
        parentColumns = "itemId",
        childColumns =  "iId",
        onDelete = 5))
public class Note {

    @ColumnInfo(name = "noteId")
    @PrimaryKey(autoGenerate = true)
    private int noteId;

    private String note;

    @ColumnInfo(name = "iId")
    private int itemId;

    public Note(String note, int itemId) {
        this.note = note;
        this.itemId = itemId;
    }

    public int getNoteId() {
        return noteId;
    }

    public String getNote() {
        return note;
    }

    public int getItemId() {
        return itemId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
