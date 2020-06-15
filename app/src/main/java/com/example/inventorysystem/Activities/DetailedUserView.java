package com.example.inventorysystem.Activities;

import android.content.Intent;
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
import com.example.inventorysystem.User;
import com.example.inventorysystem.ViewModels.CategoryViewModel;
import com.example.inventorysystem.ViewModels.ItemViewModel;
import com.example.inventorysystem.ViewModels.UserViewModel;

import java.util.List;

public class DetailedUserView  extends AppCompatActivity {
    public static final String  EXTRA_USER_ID =
            "com.example.inventorysystem.Activities.EXTRA_USER_ID";
    public static final String  EXTRA_USER_USERNAME =
            "com.example.inventorysystem.Activities.EXTRA_USER_USERNAME";
    public static final String  EXTRA_USER_PASSWORD =
            "com.example.inventorysystem.Activities.EXTRA_USER_PASSWORD";
    public static final String  EXTRA_USER_FIRST_NAME =
            "com.example.inventorysystem.Activities.EXTRA_USER_FIRST_NAME";
    public static final String  EXTRA_USER_LAST_NAME =
            "com.example.inventorysystem.Activities.EXTRA_USER_LAST_NAME";

    private TextView detailedUserName;
    private TextView detailedPassword;
    private TextView detailedFirstName;
    private TextView detailedLastName;

    public static final int EDIT_USER_REQUEST = 4;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_user_view);

        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        }

        final Intent pageIntent = getIntent();

        detailedUserName = findViewById(R.id.d_user_view_user_name);
        detailedPassword = findViewById(R.id.d_user_view_password);
        detailedFirstName = findViewById(R.id.d_user_view_first_name);
        detailedLastName = findViewById(R.id.d_user_view_last_name);

        detailedUserName.setText(getIntent().getStringExtra(EXTRA_USER_USERNAME));
        detailedPassword.setText(getIntent().getStringExtra(EXTRA_USER_PASSWORD));
        detailedFirstName.setText(getIntent().getStringExtra(EXTRA_USER_FIRST_NAME));
        detailedLastName.setText(getIntent().getStringExtra(EXTRA_USER_LAST_NAME));

        Button editUserButton = findViewById(R.id.d_user_view_update_button);
        editUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedUserView.this, AddUser.class);

                intent.putExtra(DetailedUserView.EXTRA_USER_ID, pageIntent.getStringExtra(EXTRA_USER_ID));
                intent.putExtra(DetailedUserView.EXTRA_USER_USERNAME, pageIntent.getStringExtra(EXTRA_USER_USERNAME));
                intent.putExtra(DetailedUserView.EXTRA_USER_PASSWORD, pageIntent.getStringExtra(EXTRA_USER_PASSWORD));
                intent.putExtra(DetailedUserView.EXTRA_USER_FIRST_NAME, pageIntent.getStringExtra(EXTRA_USER_FIRST_NAME));
                intent.putExtra(DetailedUserView.EXTRA_USER_LAST_NAME, pageIntent.getStringExtra(EXTRA_USER_LAST_NAME));

                startActivityForResult(intent, EDIT_USER_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == EDIT_USER_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddUser.EXTRA_USER_ID, -1);

            if (id == -1){
                Toast.makeText(this, "User can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String userName = data.getStringExtra(AddUser.EXTRA_USERNAME);
            int userId = Integer.parseInt(getIntent().getStringExtra(EXTRA_USER_ID));
            String password = data.getStringExtra(AddUser.EXTRA_PASSWORD);
            String firstName = data.getStringExtra(AddUser.EXTRA_FIRST_NAME);
            String lastName = data.getStringExtra(AddUser.EXTRA_LAST_NAME);

            User user = new User(userName, firstName, lastName, password);
            user.setUserId(id);
            userViewModel.update(user);

            Toast.makeText(this, "User Updated", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DetailedUserView.this, MainActivity.class);
            intent.putExtra(DetailedCategoryView.EXTRA_USER_ID, getIntent().getStringExtra(EXTRA_USER_ID));
            startActivity(intent);
        }
        if(requestCode == EDIT_USER_REQUEST && resultCode != RESULT_OK){
            Toast.makeText(this, "User not saved", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(DetailedUserView.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
