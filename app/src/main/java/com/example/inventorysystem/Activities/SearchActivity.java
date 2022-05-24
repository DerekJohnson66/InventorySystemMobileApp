package com.example.inventorysystem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorysystem.Adapters.InventoryItemAdapter;
import com.example.inventorysystem.InventoryItem;
import com.example.inventorysystem.R;
import com.example.inventorysystem.ViewModels.CategoryViewModel;
import com.example.inventorysystem.ViewModels.ItemViewModel;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    public static final String  EXTRA_USER_ID =
            "com.example.inventorysystem.Activities.EXTRA_USER_ID";
    public static final String  EXTRA_CATEGORY_ID =
            "com.example.inventorysystem.Activities.EXTRA_CATEGORY_ID";
    public static final String  EXTRA_CATEGORY_NAME =
            "com.example.inventorysystem.Activities.EXTRA_CATEGORY_NAME";

    MaterialSearchBar materialSearchBar;

    List<String> suggestionList = new ArrayList<>();
    InventoryItemAdapter adapter;

    ItemViewModel itemViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);


        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        }

        final RecyclerView recyclerView = findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new InventoryItemAdapter();
        recyclerView.setAdapter(adapter);

        final String extra = getIntent().getStringExtra(EXTRA_CATEGORY_ID);
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        itemViewModel.getAllItemsCategoryJoin().observe(this, new Observer<List<InventoryItem>>() {
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

            }

        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new InventoryItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(InventoryItem inventoryItem) {
                Intent intent = new Intent(SearchActivity.this, DetailedItemView.class);

                intent.putExtra(DetailedItemView.EXTRA_ITEM_ID, Integer.toString(inventoryItem.getItemId()));
                intent.putExtra(DetailedItemView.EXTRA_CATEGORY_ID, Integer.toString(inventoryItem.getCategoryId()));
                intent.putExtra(DetailedCategoryView.EXTRA_USER_ID, getIntent().getStringExtra(EXTRA_USER_ID));

                intent.putExtra(DetailedItemView.EXTRA_ITEM_TITLE, inventoryItem.getTitle());
                intent.putExtra(DetailedItemView.EXTRA_ITEM_DESCRIPTION, inventoryItem.getDescription());
                intent.putExtra(DetailedItemView.EXTRA_ITEM_CURRENT_AMOUNT, Integer.toString(inventoryItem.getCurrentAmount()));
                intent.putExtra(DetailedItemView.EXTRA_ITEM_TARGET_AMOUNT, Integer.toString(inventoryItem.getTargetAmount()));
                intent.putExtra(DetailedItemView.EXTRA_ITEM_MAX_AMOUNT, Integer.toString(inventoryItem.getMaxAmount()));
                intent.putExtra(DetailedItemView.EXTRA_ITEM_MIN_AMOUNT, Integer.toString(inventoryItem.getMinAmount()));
                intent.putExtra(DetailedItemView.EXTRA_CATEGORY_NAME, getIntent().getStringExtra(EXTRA_CATEGORY_NAME));

                startActivity(intent);
            }
        });


        materialSearchBar = (MaterialSearchBar)findViewById(R.id.search_bar);

        materialSearchBar.setHint("Search");
        materialSearchBar.setCardViewElevation(10);
        loadSuggestionList();
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for(String search:suggestionList)
                {
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())){
                        suggest.add(search);
                    }
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled){
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });




    }

    private void startSearch(String text) {
        itemViewModel.getItemSearchResults(text).observe(this, new Observer<List<InventoryItem>>() {
            @Override
            public void onChanged(@Nullable List<InventoryItem> inventoryItems) {
                adapter.setInventoryItems(inventoryItems);
            }

        });

    }

    private void loadSuggestionList() {
        suggestionList = itemViewModel.getItemTitles();
        materialSearchBar.setLastSuggestions(suggestionList);
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
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
