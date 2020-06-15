package com.example.inventorysystem.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.inventorysystem.Note;
import com.example.inventorysystem.Repositories.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
    }

    public void insert (Note note){
        repository.insert(note);
    }

    public void delete(Note note){
        repository.delete(note);
    }

    public LiveData<List<Note>> getNoteListByItem(int itemId){
        allNotes = repository.getNoteListByItem(itemId);
        return allNotes;
    }
}
