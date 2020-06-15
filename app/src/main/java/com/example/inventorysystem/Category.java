package com.example.inventorysystem;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "category_table", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns =  "userId",
        onDelete = 5))
public class Category {

    @ColumnInfo(name = "categoryId")
    @PrimaryKey(autoGenerate = true)
    private int categoryId;

    private String categoryName;

    @ColumnInfo(name = "userId")
    private int userId;

    public Category(String categoryName, int userId) {
        this.categoryName = categoryName;
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getUserId() {
        return userId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
