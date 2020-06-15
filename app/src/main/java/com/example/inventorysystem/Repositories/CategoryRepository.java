package com.example.inventorysystem.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.inventorysystem.Category;
import com.example.inventorysystem.Dao.CategoryDao;
import com.example.inventorysystem.Database.AppDatabase;
import com.example.inventorysystem.User;

import java.util.List;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Category>> categoriesByUser;


    public CategoryRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        categoryDao = database.categoryDao();




    }

    public void insert(Category category){
        new CategoryRepository.InsertCategoryAsyncTask(categoryDao).execute(category);
    }

    public void update(Category category){
        new CategoryRepository.UpdateCategoryAsyncTask(categoryDao).execute(category);
    }

    public void delete(Category category){
        new CategoryRepository.DeleteCategoryAsyncTask(categoryDao).execute(category);
    }

    public LiveData<List<Category>> getAllCategories(){
        allCategories = categoryDao.getAllCategories();
        return allCategories;
    }

    public LiveData<List<Category>> getCategoriesByUser(int userId){
        categoriesByUser = categoryDao.getCategoriesByUser(userId);
        return categoriesByUser;
    }

    private static class InsertCategoryAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao categoryDao;

        private InsertCategoryAsyncTask(CategoryDao categoryDao){
            this.categoryDao = categoryDao;
        }
        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.insert(categories[0]);
            return null;
        }
    }

    private static class UpdateCategoryAsyncTask extends AsyncTask<Category, Void, Void>{
        private CategoryDao categoryDao;

        private UpdateCategoryAsyncTask(CategoryDao categoryDao){
            this.categoryDao = categoryDao;
        }
        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.update(categories[0]);
            return null;
        }
    }

    private static class DeleteCategoryAsyncTask extends AsyncTask<Category, Void, Void>{
        private CategoryDao categoryDao;

        private DeleteCategoryAsyncTask(CategoryDao categoryDao){
            this.categoryDao = categoryDao;
        }
        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.delete(categories[0]);
            return null;
        }
    }

}
