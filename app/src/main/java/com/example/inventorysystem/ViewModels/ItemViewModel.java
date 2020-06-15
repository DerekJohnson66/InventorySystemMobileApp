package com.example.inventorysystem.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.inventorysystem.InventoryItem;
import com.example.inventorysystem.Repositories.InventoryItemRepository;

import java.util.List;

import kotlin.NotImplementedError;

public class ItemViewModel extends AndroidViewModel {
    private InventoryItemRepository repository;
    private LiveData<List<InventoryItem>> allItems;
    private LiveData<List<InventoryItem>> itemsByCategory;
    private List<InventoryItem> allItemsNeedFilling;
    private LiveData<List<InventoryItem>> itemSearchResults;
    private LiveData<List<InventoryItem>> joinItemAndCategory;
    private List<String> allItemTitles;
    private int count;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        repository = new InventoryItemRepository(application);
    }

    public void insert (InventoryItem inventoryItem){ repository.insert(inventoryItem); }

        public void update(InventoryItem inventoryItem){
            repository.update(inventoryItem);
        }

        public void delete(InventoryItem inventoryItem){
            repository.delete(inventoryItem);
        }

    public LiveData<List<InventoryItem>> getItemListByCategory(int categoryId){
        itemsByCategory = repository.getItemListByUser(categoryId);
        return itemsByCategory;
    }

    public List<InventoryItem> getAllItemsNeedFilling(){
        allItemsNeedFilling = repository.getAllItemsNeedFilling();
        return allItemsNeedFilling;
    }

    public int getItemCountByCategory(int categoryId){
        count = repository.getItemCountByUser(categoryId);
        return count;
    }

    public List<String> getItemTitles(){
        allItemTitles = repository.getItemTitles();
        return allItemTitles;
    }

    public LiveData<List<InventoryItem>> getItemSearchResults(String text){
        itemSearchResults = repository.getItemSearchResults(text);
        return itemSearchResults;
    }


    public LiveData<List<InventoryItem>> getAllItems(){
        allItems = repository.getAllItems();
        return (LiveData<List<InventoryItem>>) allItems;
    }

    public LiveData<List<InventoryItem>> getAllItemsCategoryJoin() {
        joinItemAndCategory = repository.getAllItemsCategoryJoin();
        return joinItemAndCategory;
    }
}
