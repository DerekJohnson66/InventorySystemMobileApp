package com.example.inventorysystem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorysystem.R;

public class AddCategory extends AppCompatActivity {
    public static final String  EXTRA_USER_ID =
            "com.example.inventorysystem.Activities.EXTRA_USER_ID";
    public static final String  EXTRA_CATEGORY_ID =
            "com.example.inventorysystem.Activities.EXTRA_CATEGORY_ID";
    public static final String  EXTRA_CATEGORY_NAME =
            "com.example.inventorysystem.Activities.EXTRA_CATEGORY_NAME";

    private EditText editCategoryName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        }


        Intent intent = getIntent();

//        Checks if there is a CategoryId, if so it will set the layout to the edit category layout. Otherwise it will set it to the add category layout.
        if(intent.hasExtra(EXTRA_CATEGORY_ID)){
            setContentView(R.layout.edit_category);

            Button saveCategoryButton = findViewById(R.id.edit_category_save_button);
            saveCategoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateCategory();
                }
            });

            editCategoryName = findViewById(R.id.edit_category_name);

            setTitle("Edit Category");
            editCategoryName.setText(intent.getStringExtra(EXTRA_CATEGORY_NAME));
        }else{
            setContentView(R.layout.add_category);

            Button saveCategoryButton = findViewById(R.id.add_category_save_button);
            saveCategoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveCategory();
                }
            });

            editCategoryName = findViewById(R.id.add_category_name);
            setTitle("Add Category");
        }
    }

    private void saveCategory(){
        try {
            String categoryName = editCategoryName.getText().toString();


            if(categoryName.trim().isEmpty()){
                Toast.makeText(this, "Please fill out the category name.", Toast.LENGTH_LONG).show();
                return;
            }
            String tempId = getIntent().getStringExtra(EXTRA_USER_ID);
            Intent data = new Intent();
            data.putExtra(EXTRA_CATEGORY_NAME, categoryName);
            data.putExtra(EXTRA_CATEGORY_ID, -1);
            data.putExtra(MainActivity.EXTRA_USER_ID, getIntent().getStringExtra(EXTRA_USER_ID));


            setResult(RESULT_OK, data);
            finish();
        }catch (Exception e){
            Intent intent = new Intent(AddCategory.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Category could not be saved. Try again.", Toast.LENGTH_LONG).show();
        }


    }

    private void updateCategory(){
        try {
            int id = Integer.parseInt(getIntent().getStringExtra(EXTRA_CATEGORY_ID));
            int userId = Integer.parseInt(getIntent().getStringExtra(EXTRA_USER_ID));
            String categoryName = editCategoryName.getText().toString();

            if(categoryName.trim().isEmpty()){
                Toast.makeText(this, "Please fill out the category name.", Toast.LENGTH_LONG).show();
                return;
            }

            Intent data = new Intent();
            data.putExtra(EXTRA_CATEGORY_ID, id);
            data.putExtra(EXTRA_CATEGORY_NAME, categoryName);
            data.putExtra(EXTRA_USER_ID, userId);
            data.putExtra(AddCategory.EXTRA_USER_ID, getIntent().getStringExtra(EXTRA_USER_ID));

            setResult(RESULT_OK, data);
            finish();
        } catch (Exception e){
            Intent intent = new Intent(AddCategory.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Category could not be updated. Try again.", Toast.LENGTH_LONG).show();
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
                    Intent intent = new Intent(AddCategory.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.search_page:
                    Intent intent2 = new Intent(AddCategory.this, SearchActivity.class);
                    intent2.putExtra(DetailedCategoryView.EXTRA_USER_ID, getIntent().getStringExtra(EXTRA_USER_ID));
                    startActivity(intent2);
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }

        }
}
