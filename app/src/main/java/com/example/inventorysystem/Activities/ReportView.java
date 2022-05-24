package com.example.inventorysystem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.inventorysystem.Adapters.InventoryItemAdapter;
import com.example.inventorysystem.Adapters.ReportAdapter;
import com.example.inventorysystem.InventoryItem;
import com.example.inventorysystem.R;
import com.example.inventorysystem.ViewModels.ItemViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class ReportView extends AppCompatActivity {
    public static final String  EXTRA_USER_ID =
            "com.example.inventorysystem.Activities.EXTRA_USER_ID";

    List<InventoryItem> inventoryItems;
    ListView reportListView;
    ItemViewModel itemViewModel;
    List<InventoryItem> tempItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_view);

        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        }



        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        inventoryItems = new ArrayList<>();

        TextView timestamp = findViewById(R.id.report_list_timestamp);

        Date date = new Date(System.currentTimeMillis());
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateFormatted = formatter.format(date);
        timestamp.setText(dateFormatted);

        tempItems = itemViewModel.getAllItemsNeedFilling(Integer.parseInt(getIntent().getStringExtra(EXTRA_USER_ID)));
        inventoryItems = tempItems;
        int numRows = tempItems.size();

        if(numRows == 0) {
            Toast.makeText(ReportView.this, "There are no items below the target level.", Toast.LENGTH_LONG).show();
        }

        ReportAdapter adapter = new ReportAdapter(this, R.layout.list_adapter_view, (ArrayList<InventoryItem>) inventoryItems);
        reportListView = findViewById(R.id.report_list_view);
        reportListView.setAdapter(adapter);
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
                Intent intent = new Intent(ReportView.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.search_page:
                Intent intent2 = new Intent(ReportView.this, SearchActivity.class);
                intent2.putExtra(DetailedCategoryView.EXTRA_USER_ID, getIntent().getStringExtra(EXTRA_USER_ID));
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    }

