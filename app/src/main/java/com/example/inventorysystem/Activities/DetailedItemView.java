package com.example.inventorysystem.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorysystem.Adapters.InventoryItemAdapter;
import com.example.inventorysystem.Adapters.NoteAdapter;
import com.example.inventorysystem.Category;
import com.example.inventorysystem.InventoryItem;
import com.example.inventorysystem.Note;
import com.example.inventorysystem.R;
import com.example.inventorysystem.ViewModels.CategoryViewModel;
import com.example.inventorysystem.ViewModels.ItemViewModel;
import com.example.inventorysystem.ViewModels.NoteViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DetailedItemView extends AppCompatActivity {
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

    private TextView detailedTitle;
    private TextView detailedDescription;
    private TextView detailedCurrentAmount;
    private TextView detailedTargetAmount;
    private TextView detailedMaxAmount;
    private TextView detailedMinAmount;
    private EditText detailedNote;

    public static final int EDIT_ITEM_REQUEST = 2;
    private ItemViewModel itemViewModel;
    private NoteViewModel noteViewModel;
    private String tempCategoryId;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_item_view);

        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        }

        final Intent pageIntent = getIntent();

        detailedTitle = findViewById(R.id.d_item_view_title);
        detailedDescription = findViewById(R.id.d_item_view_description);
        detailedCurrentAmount = findViewById(R.id.d_item_view_current_amount);
        detailedTargetAmount = findViewById(R.id.d_item_view_target_amount);
        detailedMaxAmount = findViewById(R.id.d_item_view_max_amount);
        detailedMinAmount = findViewById(R.id.d_item_view_min_amount);
        detailedNote = findViewById(R.id.d_item_view_note);

        detailedTitle.setText(getIntent().getStringExtra(EXTRA_ITEM_TITLE));
        detailedDescription.setText(getIntent().getStringExtra(EXTRA_ITEM_DESCRIPTION));
        detailedCurrentAmount.setText(getIntent().getStringExtra(EXTRA_ITEM_CURRENT_AMOUNT));
        detailedTargetAmount.setText(getIntent().getStringExtra(EXTRA_ITEM_TARGET_AMOUNT));
        detailedMaxAmount.setText(getIntent().getStringExtra(EXTRA_ITEM_MAX_AMOUNT));
        detailedMinAmount.setText(getIntent().getStringExtra(EXTRA_ITEM_MIN_AMOUNT));

        tempCategoryId = pageIntent.getStringExtra(EXTRA_CATEGORY_ID);

        String extra = getIntent().getStringExtra(EXTRA_ITEM_ID);

        Button editItem = findViewById(R.id.d_item_view_update_button);
        editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedItemView.this, AddItem.class);
                intent.putExtra(DetailedItemView.EXTRA_CATEGORY_ID, pageIntent.getStringExtra(EXTRA_CATEGORY_ID));
                intent.putExtra(DetailedItemView.EXTRA_ITEM_ID, pageIntent.getStringExtra(EXTRA_ITEM_ID));
                intent.putExtra(DetailedItemView.EXTRA_USER_ID, pageIntent.getStringExtra(EXTRA_USER_ID));
                intent.putExtra(DetailedItemView.EXTRA_ITEM_TITLE, pageIntent.getStringExtra(EXTRA_ITEM_TITLE));
                intent.putExtra(DetailedItemView.EXTRA_ITEM_DESCRIPTION, pageIntent.getStringExtra(EXTRA_ITEM_DESCRIPTION));
                intent.putExtra(DetailedItemView.EXTRA_ITEM_CURRENT_AMOUNT, pageIntent.getStringExtra(EXTRA_ITEM_CURRENT_AMOUNT));
                intent.putExtra(DetailedItemView.EXTRA_ITEM_TARGET_AMOUNT, pageIntent.getStringExtra(EXTRA_ITEM_TARGET_AMOUNT));
                intent.putExtra(DetailedItemView.EXTRA_ITEM_MAX_AMOUNT, pageIntent.getStringExtra(EXTRA_ITEM_MAX_AMOUNT));
                intent.putExtra(DetailedItemView.EXTRA_ITEM_MIN_AMOUNT, pageIntent.getStringExtra(EXTRA_ITEM_MIN_AMOUNT));

                startActivityForResult(intent, EDIT_ITEM_REQUEST);
            }
        });

        Button addNoteButton = findViewById(R.id.d_item_view_save_note_button);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(detailedNote.getText().toString().trim().isEmpty()){
                    Toast.makeText(DetailedItemView.this, "Please insert a note", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    saveNote();
                }
            }
        });

        RecyclerView noteRecyclerView = findViewById(R.id.d_item_view_recycler_view_notes);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteRecyclerView.setHasFixedSize(true);

        final NoteAdapter noteAdapter = new NoteAdapter();
        noteRecyclerView.setAdapter(noteAdapter);

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);


        noteViewModel.getNoteListByItem(Integer.parseInt(extra)).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                noteAdapter.setNotes(notes);
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
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(DetailedItemView.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(noteRecyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == EDIT_ITEM_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddItem.EXTRA_ITEM_ID, -1);

            if (id == -1){
                Toast.makeText(this, "Item can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddItem.EXTRA_ITEM_TITLE);
            String description = data.getStringExtra(AddItem.EXTRA_ITEM_DESCRIPTION);
            int currentAmount = Integer.parseInt(data.getStringExtra(AddItem.EXTRA_ITEM_CURRENT_AMOUNT));
            int targetAmount = Integer.parseInt(data.getStringExtra(AddItem.EXTRA_ITEM_TARGET_AMOUNT));
            int maxAmount = Integer.parseInt(data.getStringExtra(AddItem.EXTRA_ITEM_MAX_AMOUNT));
            int minAmount = Integer.parseInt(data.getStringExtra(AddItem.EXTRA_ITEM_MIN_AMOUNT));

            int categoryId = Integer.parseInt(getIntent().getStringExtra(EXTRA_CATEGORY_ID));
            String categoryName = getIntent().getStringExtra(EXTRA_CATEGORY_NAME);

            InventoryItem inventoryItem = new InventoryItem(title, description, currentAmount, targetAmount, maxAmount, minAmount, categoryId, categoryName);
            inventoryItem.setItemId(id);
            itemViewModel.update(inventoryItem);

            Toast.makeText(this, "Item Updated", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DetailedItemView.this, MainActivity.class);
            intent.putExtra(DetailedItemView.EXTRA_USER_ID, getIntent().getStringExtra(EXTRA_USER_ID));
            startActivity(intent);

        }
        if(requestCode == EDIT_ITEM_REQUEST && requestCode != RESULT_OK ){
            Toast.makeText(this, "Item not saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveNote(){
        String newNote = detailedNote.getText().toString();
        int id = Integer.parseInt(getIntent().getStringExtra(EXTRA_ITEM_ID));

        Note note =  new Note(newNote, id);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.insert(note);
        detailedNote.getText().clear();

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
                Intent intent = new Intent(DetailedItemView.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
