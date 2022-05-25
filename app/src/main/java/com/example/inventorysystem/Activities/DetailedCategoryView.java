package com.example.inventorysystem.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorysystem.Adapters.InventoryItemAdapter;
import com.example.inventorysystem.Category;
import com.example.inventorysystem.InventoryItem;
import com.example.inventorysystem.R;
import com.example.inventorysystem.ViewModels.CategoryViewModel;
import com.example.inventorysystem.ViewModels.ItemViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class DetailedCategoryView extends AppCompatActivity {
    public static final String  EXTRA_USER_ID =
            "com.example.inventorysystem.Activities.EXTRA_USER_ID";
    public static final String  EXTRA_CATEGORY_ID =
            "com.example.inventorysystem.Activities.EXTRA_CATEGORY_ID";
    public static final String  EXTRA_CATEGORY_NAME =
            "com.example.inventorysystem.Activities.EXTRA_CATEGORY_NAME";

    private TextView detailedCategoryName;

    public static final int ADD_ITEM_REQUEST = 3;
    public static final int EDIT_CATEGORY_REQUEST = 4;
    private CategoryViewModel categoryViewModel;
    private ItemViewModel itemViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_category_view);

        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        }

        final Intent pageIntent = getIntent();

        detailedCategoryName = findViewById(R.id.d_category_view_name);

        detailedCategoryName.setText(getIntent().getStringExtra(EXTRA_CATEGORY_NAME));

        Button addItem = findViewById(R.id.d_category_view_add_item_button);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedCategoryView.this, AddItem.class);

                intent.putExtra(DetailedCategoryView.EXTRA_USER_ID, pageIntent.getStringExtra(EXTRA_USER_ID));
                intent.putExtra(DetailedCategoryView.EXTRA_CATEGORY_NAME, pageIntent.getStringExtra(EXTRA_CATEGORY_NAME));
                intent.putExtra(DetailedCategoryView.EXTRA_CATEGORY_ID, pageIntent.getStringExtra(EXTRA_CATEGORY_ID));
                startActivityForResult(intent, ADD_ITEM_REQUEST);

            }
        });

        Button editCategory = findViewById(R.id.d_category_view_edit_category_button);
        editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedCategoryView.this, AddCategory.class);

                intent.putExtra(DetailedCategoryView.EXTRA_CATEGORY_NAME, pageIntent.getStringExtra(EXTRA_CATEGORY_NAME));
                intent.putExtra(DetailedCategoryView.EXTRA_USER_ID, pageIntent.getStringExtra(EXTRA_USER_ID));
                intent.putExtra(DetailedCategoryView.EXTRA_CATEGORY_ID, pageIntent.getStringExtra(EXTRA_CATEGORY_ID));

                startActivityForResult(intent, EDIT_CATEGORY_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.d_category_view_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final InventoryItemAdapter adapter = new InventoryItemAdapter();
        recyclerView.setAdapter(adapter);

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        final String extra = getIntent().getStringExtra(EXTRA_CATEGORY_ID);
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        itemViewModel.getItemListByCategory(Integer.parseInt(extra)).observe(this, new Observer<List<InventoryItem>>() {
            @Override
            public void onChanged(@Nullable List<InventoryItem> inventoryItems) {
                adapter.setInventoryItems(inventoryItems);
            }

        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                itemViewModel.delete(adapter.getInventoryItemAt(viewHolder.getAdapterPosition()));
                Toast.makeText(DetailedCategoryView.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new InventoryItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(InventoryItem inventoryItem) {
                Intent intent = new Intent(DetailedCategoryView.this, DetailedItemView.class);

                intent.putExtra(DetailedItemView.EXTRA_ITEM_ID, Integer.toString(inventoryItem.getItemId()));
                intent.putExtra(DetailedItemView.EXTRA_CATEGORY_ID, Integer.toString(inventoryItem.getCategoryId()));
                intent.putExtra(DetailedCategoryView.EXTRA_USER_ID, pageIntent.getStringExtra(EXTRA_USER_ID));

                intent.putExtra(DetailedItemView.EXTRA_ITEM_TITLE, inventoryItem.getTitle());
                intent.putExtra(DetailedItemView.EXTRA_ITEM_DESCRIPTION, inventoryItem.getDescription());
                intent.putExtra(DetailedItemView.EXTRA_ITEM_CURRENT_AMOUNT, Integer.toString(inventoryItem.getCurrentAmount()));
                intent.putExtra(DetailedItemView.EXTRA_ITEM_TARGET_AMOUNT, Integer.toString(inventoryItem.getTargetAmount()));
                intent.putExtra(DetailedItemView.EXTRA_ITEM_MAX_AMOUNT, Integer.toString(inventoryItem.getMaxAmount()));
                intent.putExtra(DetailedItemView.EXTRA_ITEM_MIN_AMOUNT, Integer.toString(inventoryItem.getMinAmount()));
                intent.putExtra(DetailedItemView.EXTRA_CATEGORY_NAME, pageIntent.getStringExtra(EXTRA_CATEGORY_NAME));

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == EDIT_CATEGORY_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddCategory.EXTRA_CATEGORY_ID, -1);

            if (id == -1){
                Toast.makeText(this, "Category can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String categoryName = data.getStringExtra(AddCategory.EXTRA_CATEGORY_NAME);
            int userId = Integer.parseInt(getIntent().getStringExtra(EXTRA_USER_ID));

            Category category = new Category(categoryName, userId);
            category.setCategoryId(id);
            categoryViewModel.update(category);

            Toast.makeText(this, "Category Updated", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DetailedCategoryView.this, MainActivity.class);
            intent.putExtra(DetailedCategoryView.EXTRA_USER_ID, getIntent().getStringExtra(EXTRA_USER_ID));
            startActivity(intent);
        }
        if(requestCode == EDIT_CATEGORY_REQUEST && resultCode != RESULT_OK){
            Toast.makeText(this, "Category not saved", Toast.LENGTH_SHORT).show();
        }



        if(requestCode == ADD_ITEM_REQUEST && resultCode == RESULT_OK){

            String title = data.getStringExtra(AddItem.EXTRA_ITEM_TITLE);
            String description = data.getStringExtra(AddItem.EXTRA_ITEM_DESCRIPTION);
            int currentAmount = data.getIntExtra(AddItem.EXTRA_ITEM_CURRENT_AMOUNT, 2);
            int targetAmount = data.getIntExtra(AddItem.EXTRA_ITEM_TARGET_AMOUNT, 20);
            int maxAmount = data.getIntExtra(AddItem.EXTRA_ITEM_MAX_AMOUNT, 100);
            int minAmount = data.getIntExtra(AddItem.EXTRA_ITEM_MIN_AMOUNT, 1);

            int categoryId = Integer.parseInt(getIntent().getStringExtra(EXTRA_CATEGORY_ID));
            String categoryName = data.getStringExtra(AddItem.EXTRA_CATEGORY_NAME);
            int userId = Integer.parseInt(getIntent().getStringExtra(EXTRA_USER_ID));

            InventoryItem inventoryItem = new InventoryItem(title, description, currentAmount, targetAmount, maxAmount, minAmount, categoryId, categoryName, userId);
            itemViewModel.insert(inventoryItem);

            Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DetailedCategoryView.this, MainActivity.class);
            intent.putExtra(DetailedCategoryView.EXTRA_USER_ID, getIntent().getStringExtra(EXTRA_USER_ID));
            startActivity(intent);

        }
        if(requestCode == ADD_ITEM_REQUEST && resultCode != RESULT_OK){
            Toast.makeText(this, "Item not saved", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_page:
                Intent intent = new Intent(DetailedCategoryView.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.search_page:
                Intent intent2 = new Intent(DetailedCategoryView.this, SearchActivity.class);
                intent2.putExtra(DetailedCategoryView.EXTRA_USER_ID, getIntent().getStringExtra(EXTRA_USER_ID));
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
