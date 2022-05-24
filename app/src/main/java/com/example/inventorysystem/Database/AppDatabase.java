package com.example.inventorysystem.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.inventorysystem.Category;
import com.example.inventorysystem.Dao.CategoryDao;
import com.example.inventorysystem.Dao.InventoryItemDao;
import com.example.inventorysystem.Dao.NoteDao;
import com.example.inventorysystem.Dao.UserDao;
import com.example.inventorysystem.InventoryItem;
import com.example.inventorysystem.Note;
import com.example.inventorysystem.User;

@Database(entities = {User.class, InventoryItem.class, Note.class, Category.class}, version = 9)
public abstract class AppDatabase extends RoomDatabase{

        private static AppDatabase instance;

        public abstract UserDao userDao();
        public abstract InventoryItemDao inventoryItemDao();
        public abstract NoteDao noteDao();
        public abstract CategoryDao categoryDao();


        public static synchronized AppDatabase getInstance(Context context){
            if (instance == null){
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "app_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .allowMainThreadQueries()
                        .build();
            }
            return instance;
        }

        private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                new PopulateDbAsyncTask(instance).execute();
            }
        };

        private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
            private UserDao userDao;
            private InventoryItemDao inventoryItemDao;
            private NoteDao noteDao;



            private PopulateDbAsyncTask(AppDatabase db){
                userDao = db.userDao();
                inventoryItemDao = db.inventoryItemDao();
                noteDao = db.noteDao();
            }
            @Override
            protected Void doInBackground(Void... voids) {

                userDao.insert(new User("user1", "First 1", "Last 1", "password1"));
                userDao.insert(new User("user2", "First 2", "Last 2", "password2"));
                userDao.insert(new User("user3", "First 3", "Last 3", "password3"));

                inventoryItemDao.insert(new InventoryItem("Item 1", "description 1", 6, 12, 50, 1, 1, "Category 1", 1));
                inventoryItemDao.insert(new InventoryItem("Item 2", "description 2", 6, 12, 50, 1, 2, "Category 2", 2));
                inventoryItemDao.insert(new InventoryItem("Item 3", "description 3", 6, 12, 50, 1, 3, "Category 3", 3));

                noteDao.insert(new Note("Note 1", 1));
                noteDao.insert(new Note("Note 2", 2));
                noteDao.insert(new Note("Note 3", 3));

                return null;
            }

        }
}
