package com.example.inventorysystem;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_table", foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "categoryId",
        childColumns =  "cId",
        onDelete = 5))
public class InventoryItem {

    @ColumnInfo(name = "itemId")
    @PrimaryKey(autoGenerate = true)
    private int itemId;

    private String title;
    private String description;

    @ColumnInfo(name = "currentAmount")
    private int currentAmount;

    @ColumnInfo(name = "targetAmount")
    private int targetAmount;

    private int maxAmount;
    private int minAmount;

    @ColumnInfo(name = "cId")
    private int categoryId;

    @ColumnInfo(name = "uId")
    private int userId;

//    @Ignore
    private String categoryName;

//    public InventoryItem(String title, String description, int currentAmount, int targetAmount, int maxAmount, int minAmount, int categoryId) {
//        this.title = title;
//        this.description = description;
//        this.currentAmount = currentAmount;
//        this.targetAmount = targetAmount;
//        this.maxAmount = maxAmount;
//        this.minAmount = minAmount;
//        this.categoryId = categoryId;
//    }

    public InventoryItem(String title, String description, int currentAmount, int targetAmount, int maxAmount, int minAmount, int categoryId, String categoryName, int userId) {
        this.title = title;
        this.description = description;
        this.currentAmount = currentAmount;
        this.targetAmount = targetAmount;
        this.maxAmount = maxAmount;
        this.minAmount = minAmount;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.userId = userId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public int getTargetAmount() {
        return targetAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setCategoryId(int userId) {
        this.categoryId = categoryId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setItemId(int id){
        this.itemId = id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public void setTargetAmount(int targetAmount) {
        this.targetAmount = targetAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }
}
