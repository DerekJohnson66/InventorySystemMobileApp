package com.example.inventorysystem.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inventorysystem.Category;
import com.example.inventorysystem.InventoryItem;
import com.example.inventorysystem.User;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

    @Query("SELECT * FROM category_table ORDER BY categoryName DESC")
    LiveData<List<Category>>  getAllCategories();

    @Query("SELECT * FROM category_table WHERE categoryId=:categoryId")
    LiveData<Category> getCategoryById(int categoryId);

    @Query("SELECT * FROM category_table WHERE userId=:userId")
    LiveData<List<Category>> getCategoriesByUser(int userId);
}
