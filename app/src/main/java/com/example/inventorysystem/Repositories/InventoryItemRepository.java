package com.example.inventorysystem.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.inventorysystem.Dao.InventoryItemDao;
import com.example.inventorysystem.Database.AppDatabase;
import com.example.inventorysystem.InventoryItem;

import java.util.List;

public class InventoryItemRepository {

        private InventoryItemDao inventoryItemDao;
        private LiveData<List<InventoryItem>> allItems;
        private LiveData<List<InventoryItem>> listOfItems;
        private List<InventoryItem> allItemsNeedFilling;
        private LiveData<List<InventoryItem>> itemCategoryJoin;
        private LiveData<List<InventoryItem>> itemSearchResults;
        private List<String> listOfTitles;
        private int count;

        public InventoryItemRepository(Application application){
            AppDatabase database = AppDatabase.getInstance(application);
            inventoryItemDao = database.inventoryItemDao();


        }

        public void insert(InventoryItem inventoryItem){new InsertInventoryItemAsyncTask(inventoryItemDao).execute(inventoryItem);}

        public void update(InventoryItem inventoryItem){new UpdateInventoryItemAsyncTask(inventoryItemDao).execute(inventoryItem);}

        public void delete(InventoryItem inventoryItem){new DeleteInventoryItemAsyncTask(inventoryItemDao).execute(inventoryItem);}


        public LiveData<List<InventoryItem>> getItemListByUser(int categoryId){
            listOfItems = inventoryItemDao.getItemListByCategory(categoryId);
            return listOfItems;
        }

        public List<String> getItemTitles() {
            listOfTitles = inventoryItemDao.getItemTitles();
            return listOfTitles;
        }

        public LiveData<List<InventoryItem>> getAllItemsCategoryJoin(){
            itemCategoryJoin = inventoryItemDao.getAllItemsCategoryJoin();
            return  itemCategoryJoin;
        }

        public LiveData<List<InventoryItem>> getItemSearchResults(String text){
            itemSearchResults = inventoryItemDao.getItemSearchResults(text);
            return itemSearchResults;
        }

        public int getItemCountByUser(int categoryId){
            count = inventoryItemDao.getItemCountByCategory(categoryId);
            return count;
        }

        public List<InventoryItem> getAllItemsNeedFilling(){
            allItemsNeedFilling = inventoryItemDao.getAllItemsNeedFilling();
            return allItemsNeedFilling;
        }

        public LiveData<List<InventoryItem>> getAllItems(){
            allItems = inventoryItemDao.getAllItems();
            return allItems;
        }

        private static class InsertInventoryItemAsyncTask extends AsyncTask<InventoryItem, Void, Void> {
            private InventoryItemDao inventoryItemDao;

            private InsertInventoryItemAsyncTask(InventoryItemDao inventoryItemDao){
                this.inventoryItemDao = inventoryItemDao;
            }
            @Override
            protected Void doInBackground(InventoryItem... inventoryItems) {
                inventoryItemDao.insert(inventoryItems[0]);
                return null;
            }
        }

        private static class UpdateInventoryItemAsyncTask extends AsyncTask<InventoryItem, Void, Void>{
            private InventoryItemDao inventoryItemDao;

            private UpdateInventoryItemAsyncTask(InventoryItemDao inventoryItemDao){
                this.inventoryItemDao = inventoryItemDao;
            }
            @Override
            protected Void doInBackground(InventoryItem... inventoryItems) {
                inventoryItemDao.update(inventoryItems[0]);
                return null;
            }
        }

        private static class DeleteInventoryItemAsyncTask extends AsyncTask<InventoryItem, Void, Void>{
            private InventoryItemDao inventoryItemDao;

            private DeleteInventoryItemAsyncTask(InventoryItemDao inventoryItemDao){
                this.inventoryItemDao = inventoryItemDao;
            }
            @Override
            protected Void doInBackground(InventoryItem... inventoryItems) {
                inventoryItemDao.delete(inventoryItems[0]);
                return null;
            }
        }
}
