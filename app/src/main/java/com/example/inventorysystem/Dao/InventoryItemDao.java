package com.example.inventorysystem.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inventorysystem.InventoryItem;

import java.util.List;

@Dao
public interface InventoryItemDao {

    @Insert
    void insert(InventoryItem item);

    @Update
    void update(InventoryItem item);

    @Delete
    void delete(InventoryItem item);

    @Query("SELECT itm.*, cat.categoryName FROM item_table as itm join category_table as cat on cId=categoryId WHERE cId=:categoryId ORDER BY cId DESC")
    LiveData<List<InventoryItem>> getItemListByCategory(int categoryId);

    @Query("SELECT COUNT(*) FROM item_table WHERE cId=:categoryId")
    int getItemCountByCategory(int categoryId);

    @Query("SELECT * FROM item_table ORDER BY itemId DESC")
    LiveData<List<InventoryItem>>  getAllItems();

    @Query("SELECT itm.*, cat.categoryName FROM item_table as itm join category_table as cat on cId=categoryId WHERE uId=:userId " +
            "AND currentAmount < targetAmount ORDER BY cId")
    List<InventoryItem> getAllItemsNeedFilling(int userId);


    @Query("SELECT title FROM item_table")
    List<String> getItemTitles();

    @Query("SELECT itm.*, cat.categoryName FROM item_table as itm join category_table as cat on cId=categoryId")
    LiveData<List<InventoryItem>> getAllItemsCategoryJoin();

    @Query("SELECT itm.*, cat.categoryName FROM item_table as itm join category_table as cat on cId=categoryId WHERE title LIKE '%' || :search || '%'")
    LiveData<List<InventoryItem>> getItemSearchResults(String search);
}
