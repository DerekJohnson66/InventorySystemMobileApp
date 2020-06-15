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

public class AddItem extends AppCompatActivity {
    public static final String  EXTRA_ITEM_ID =
            "com.example.inventorysystem.Activities.EXTRA_ITEM_ID";
    public static final String  EXTRA_USER_ID =
            "com.example.inventorysystem.Activities.EXTRA_USER_ID";
    public static final String  EXTRA_CATEGORY_ID =
            "com.example.inventorysystem.Activities.EXTRA_CATEGORY_ID";
    public static final String  EXTRA_ITEM_TITLE =
            "com.example.inventorysystem.Activities.EXTRA_ITEM_TITLE";
    public static final String  EXTRA_ITEM_DESCRIPTION =
            "com.example.inventorysystem.Activities.EXTRA_ITEM_DESCRIPTION";
    public static final String  EXTRA_ITEM_CURRENT_AMOUNT =
            "com.example.inventorysystem.Activities.EXTRA_ITEM_CURRENT_AMOUNT";
    public static final String  EXTRA_ITEM_TARGET_AMOUNT =
            "com.example.inventorysystem.Activities.EXTRA_ITEM_TARGET_AMOUNT";
    public static final String  EXTRA_ITEM_MAX_AMOUNT =
            "com.example.inventorysystem.Activities.EXTRA_ITEM_MAX_AMOUNT";
    public static final String  EXTRA_ITEM_MIN_AMOUNT =
            "com.example.inventorysystem.Activities.EXTRA_ITEM_MIN_AMOUNT";
    public static final String  EXTRA_CATEGORY_NAME =
            "com.example.inventorysystem.Activities.EXTRA_CATEGORY_NAME";

    private EditText editItemTitle;
    private EditText editItemDescription;
    private EditText editItemCurrentAmount;
    private EditText editItemTargetAmount;
    private EditText editItemMaxAmount;
    private EditText editItemMinAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        }


        Intent intent = getIntent();

//        Checks if there is an itemId, if so it will set the layout to the edit item layout. Otherwise it will set it to the add item layout.
        if(intent.hasExtra(EXTRA_ITEM_ID)){
            setContentView(R.layout.edit_item);

            Button saveItemButton = findViewById(R.id.edit_item_save_button);
            saveItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateItem();
                }
            });

            editItemTitle = findViewById(R.id.edit_item_title);
            editItemDescription = findViewById(R.id.edit_item_description);
            editItemCurrentAmount = findViewById(R.id.edit_item_current_amount);
            editItemTargetAmount = findViewById(R.id.edit_item_target_amount);
            editItemMaxAmount = findViewById(R.id.edit_item_max_amount);
            editItemMinAmount = findViewById(R.id.edit_item_min_amount);

            setTitle("Edit Item");
            editItemTitle.setText(intent.getStringExtra(EXTRA_ITEM_TITLE));
            editItemDescription.setText(intent.getStringExtra(EXTRA_ITEM_DESCRIPTION));
            editItemCurrentAmount.setText(intent.getStringExtra(EXTRA_ITEM_CURRENT_AMOUNT));
            editItemTargetAmount.setText(intent.getStringExtra(EXTRA_ITEM_TARGET_AMOUNT));
            editItemMaxAmount.setText(intent.getStringExtra(EXTRA_ITEM_MAX_AMOUNT));
            editItemMinAmount.setText(intent.getStringExtra(EXTRA_ITEM_MIN_AMOUNT));

        }else{
            setContentView(R.layout.add_item);

            Button saveItemButton = findViewById(R.id.add_item_save_button);
            saveItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveItem();
                }
            });

            editItemTitle = findViewById(R.id.add_item_title);
            editItemDescription = findViewById(R.id.add_item_description);
            editItemCurrentAmount = findViewById(R.id.add_item_current_amount);
            editItemTargetAmount = findViewById(R.id.add_item_target_amount);
            editItemMaxAmount = findViewById(R.id.add_item_max_amount);
            editItemMinAmount = findViewById(R.id.add_item_min_amount);
            setTitle("Add Item");
        }
    }

    private void saveItem(){
        try {
            String title = editItemTitle.getText().toString();
            String description = editItemDescription.getText().toString();
            int currentAmount = Integer.parseInt(editItemCurrentAmount.getText().toString());
            int targetAmount = Integer.parseInt(editItemTargetAmount.getText().toString());
            int maxAmount = Integer.parseInt(editItemMaxAmount.getText().toString());
            int minAmount = Integer.parseInt(editItemMinAmount.getText().toString());
            String categoryName = getIntent().getStringExtra(EXTRA_CATEGORY_NAME);


            if(title.trim().isEmpty() || description.trim().isEmpty()){
                Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_LONG).show();
                return;
            }

            Intent data = new Intent();
            data.putExtra(EXTRA_ITEM_TITLE, title);
            data.putExtra(EXTRA_ITEM_DESCRIPTION, description);
            data.putExtra(EXTRA_ITEM_CURRENT_AMOUNT, currentAmount);
            data.putExtra(EXTRA_ITEM_TARGET_AMOUNT, targetAmount);
            data.putExtra(EXTRA_ITEM_MAX_AMOUNT, maxAmount);
            data.putExtra(EXTRA_ITEM_MIN_AMOUNT, minAmount);
            data.putExtra(EXTRA_CATEGORY_ID, -1);
            data.putExtra(AddItem.EXTRA_USER_ID, getIntent().getStringExtra(EXTRA_USER_ID));


            setResult(RESULT_OK, data);
            finish();
        } catch (Exception e){
            Intent intent = new Intent(AddItem.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Could not save Item, Try again.", Toast.LENGTH_LONG).show();
        }


    }

    private void updateItem(){
        try {
            int id = Integer.parseInt(getIntent().getStringExtra(EXTRA_ITEM_ID));
            int categoryId = Integer.parseInt(getIntent().getStringExtra(EXTRA_CATEGORY_ID));
            String title = editItemTitle.getText().toString();
            String description = editItemDescription.getText().toString();
            String currentAmount = editItemCurrentAmount.getText().toString();
            String targetAmount = editItemTargetAmount.getText().toString();
            String maxAmount = editItemMaxAmount.getText().toString();
            String minAmount = editItemMinAmount.getText().toString();

            if(title.trim().isEmpty() || description.trim().isEmpty() || currentAmount.trim().isEmpty() || targetAmount.trim().isEmpty() || maxAmount.trim().isEmpty() || minAmount.trim().isEmpty()){
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_LONG).show();
                return;
            }

            Intent data = new Intent();
            data.putExtra(EXTRA_ITEM_ID, id);
            data.putExtra(EXTRA_CATEGORY_ID, categoryId);
            data.putExtra(EXTRA_ITEM_TITLE, title);
            data.putExtra(EXTRA_ITEM_DESCRIPTION, description);
            data.putExtra(EXTRA_ITEM_CURRENT_AMOUNT, currentAmount);
            data.putExtra(EXTRA_ITEM_TARGET_AMOUNT, targetAmount);
            data.putExtra(EXTRA_ITEM_MAX_AMOUNT, maxAmount);
            data.putExtra(EXTRA_ITEM_MIN_AMOUNT, minAmount);
            data.putExtra(AddItem.EXTRA_USER_ID, getIntent().getStringExtra(EXTRA_USER_ID));

            setResult(RESULT_OK, data);
            finish();
        } catch (Exception e){
            Intent intent = new Intent(AddItem.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Could not update item, Try again.", Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(AddItem.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
