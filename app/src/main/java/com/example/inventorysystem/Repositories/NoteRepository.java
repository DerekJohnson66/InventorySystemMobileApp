package com.example.inventorysystem.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.inventorysystem.Dao.NoteDao;
import com.example.inventorysystem.Database.AppDatabase;
import com.example.inventorysystem.Note;

import java.util.List;

public class NoteRepository {

        private NoteDao noteDao;
        private LiveData<List<Note>> allNotes;

        public NoteRepository(Application application) {
            AppDatabase database = AppDatabase.getInstance(application);
            noteDao = database.noteDao();
        }

        public LiveData<List<Note>> getNoteListByItem(int itemId) {
            allNotes = noteDao.getNoteListByItem(itemId);
            return allNotes;
        }

        public void insert(Note note) {
            new InsertNoteAsyncTask(noteDao).execute(note);
        }

        public void delete(Note note) {
            new DeleteNoteAsyncTask(noteDao).execute(note);
        }

        private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
            private NoteDao noteDao;

            private InsertNoteAsyncTask(NoteDao noteDao) {
                this.noteDao = noteDao;
            }

            @Override
            protected Void doInBackground(Note... notes) {
                noteDao.insert(notes[0]);
                return null;
            }
        }

        private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
            private NoteDao noteDao;

            private DeleteNoteAsyncTask(NoteDao noteDao) {
                this.noteDao = noteDao;
            }

            @Override
            protected Void doInBackground(Note... notes) {
                noteDao.delete(notes[0]);
                return null;
            }
        }


    }
