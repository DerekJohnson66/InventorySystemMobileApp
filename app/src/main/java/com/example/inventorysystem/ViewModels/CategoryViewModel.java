package com.example.inventorysystem.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.inventorysystem.Category;
import com.example.inventorysystem.Repositories.CategoryRepository;
import com.example.inventorysystem.Repositories.UserRepository;
import com.example.inventorysystem.User;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository repository;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Category>> categoriesByUser;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new CategoryRepository(application);


    }

    public void insert (Category category){
        repository.insert(category);
    }

    public void update(Category category){
        repository.update(category);
    }

    public void delete(Category category){
        repository.delete(category);
    }

    public LiveData<List<Category>> getAllCategories(){
        allCategories = repository.getAllCategories();
        return allCategories;
    }

    public LiveData<List<Category>> getCategoriesByUser(int userId){
        categoriesByUser = repository.getCategoriesByUser(userId);
        return categoriesByUser;
    }
}
