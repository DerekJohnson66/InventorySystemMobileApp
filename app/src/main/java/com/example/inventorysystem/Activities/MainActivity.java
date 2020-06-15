package com.example.inventorysystem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorysystem.Adapters.CategoryAdapter;
import com.example.inventorysystem.Category;
import com.example.inventorysystem.R;
import com.example.inventorysystem.ViewModels.CategoryViewModel;
import com.example.inventorysystem.ViewModels.ItemViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String  EXTRA_USER_ID =
            "com.example.inventorysystem.Activities.EXTRA_USER_ID";

    public static final int ADD_CATEGORY_REQUEST = 1;
    private CategoryViewModel categoryViewModel;
    private ItemViewModel itemViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        }

        Intent appIntent = getIntent();

        Button buttonAddCategory = findViewById(R.id.main_activity_add_category_button);
        buttonAddCategory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, AddCategory.class);
                startActivityForResult(intent, ADD_CATEGORY_REQUEST);
            }
        });

        Button createReportButton = findViewById(R.id.main_activity_create_report_button);
        createReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReportView.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.main_activity_category_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CategoryAdapter adapter = new CategoryAdapter();
        recyclerView.setAdapter(adapter);

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        categoryViewModel.getCategoriesByUser(Integer.parseInt(appIntent.getStringExtra(EXTRA_USER_ID))).observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                adapter.setCategories(categories);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                0) {

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder){
                Category tempCategory = adapter.getCategoryAt(viewHolder.getAdapterPosition());
                int tempCategoryId = tempCategory.getCategoryId();
                int tempIsEmpty = itemViewModel.getItemCountByCategory(tempCategoryId);
                if (tempIsEmpty == 0){
                    final int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int dragFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }else{
                    final int dragFlags = 0;
                    final int swipeFlags = 0;
                    Toast.makeText(MainActivity.this, "Category could  not be deleted, there are items tied to it", Toast.LENGTH_LONG).show();
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                categoryViewModel.delete(adapter.getCategoryAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();

            }


        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                Intent intent = new Intent(MainActivity.this, DetailedCategoryView.class);

                intent.putExtra(DetailedCategoryView.EXTRA_CATEGORY_NAME, category.getCategoryName());
                intent.putExtra(DetailedCategoryView.EXTRA_CATEGORY_ID, Integer.toString(category.getCategoryId()));
                intent.putExtra(DetailedCategoryView.EXTRA_USER_ID, Integer.toString(category.getUserId()));
                startActivity(intent);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_CATEGORY_REQUEST && resultCode == RESULT_OK){
            String categoryName = data.getStringExtra(AddCategory.EXTRA_CATEGORY_NAME);
            int userId = data.getIntExtra(AddCategory.EXTRA_USER_ID,1);


            Category category = new Category(categoryName, userId);
            categoryViewModel.insert(category);

            Toast.makeText(this, "Category Saved", Toast.LENGTH_SHORT).show();
        }

        if(requestCode == ADD_CATEGORY_REQUEST && resultCode != RESULT_OK){
            Toast.makeText(this, "Category not saved", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.search_page:
                Intent intent2 = new Intent(MainActivity.this, SearchActivity.class);
                intent2.putExtra(DetailedCategoryView.EXTRA_USER_ID, getIntent().getStringExtra(EXTRA_USER_ID));
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
