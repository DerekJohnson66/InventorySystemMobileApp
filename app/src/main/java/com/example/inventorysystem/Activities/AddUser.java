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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddUser extends AppCompatActivity {
    public static final String  EXTRA_USER_ID =
            "com.example.inventorysystem.Activities.EXTRA_USER_ID";
    public static final String  EXTRA_USERNAME =
            "com.example.inventorysystem.Activities.EXTRA_USERNAME";
    public static final String  EXTRA_FIRST_NAME =
            "com.example.inventorysystem.Activities.EXTRA_FIRST_NAME";
    public static final String  EXTRA_LAST_NAME =
            "com.example.inventorysystem.Activities.EXTRA_LAST_NAME";
    public static final String  EXTRA_PASSWORD =
            "com.example.inventorysystem.Activities.EXTRA_PASSWORD";

    private EditText editUsername;
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        }


        Intent intent = getIntent();

//        Checks if there is a UserId, if so it will set the layout to the edit user layout. Otherwise it will set it to the add user layout.
        if(intent.hasExtra(EXTRA_USER_ID)){
            setContentView(R.layout.edit_user);

            Button saveUserButton = findViewById(R.id.edit_user_save_button);
            saveUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateUser();
                }
            });

            editUsername = findViewById(R.id.edit_user_user_name);
            editPassword = findViewById(R.id.edit_user_password);
            editFirstName = findViewById(R.id.edit_user_first_name);
            editLastName = findViewById(R.id.edit_user_last_name);

            setTitle("Edit User");
            editUsername.setText(intent.getStringExtra(EXTRA_USERNAME));
            editPassword.setText(intent.getStringExtra(EXTRA_PASSWORD));
            editFirstName.setText(intent.getStringExtra(EXTRA_FIRST_NAME));
            editLastName.setText(intent.getStringExtra(EXTRA_LAST_NAME));

        }else{
            setContentView(R.layout.add_user);

            Button saveUserButton = findViewById(R.id.add_user_save_button);
            saveUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveUser();
                }
            });

            editUsername = findViewById(R.id.add_user_user_name);
            editPassword = findViewById(R.id.add_user_password);
            editFirstName = findViewById(R.id.add_user_first_name);
            editLastName = findViewById(R.id.add_user_last_name);
            setTitle("Add User");
        }
    }

    private void saveUser(){
        try {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            String firstName = editFirstName.getText().toString();
            String lastName = editLastName.getText().toString();

            if(username.trim().isEmpty() || password.trim().isEmpty() || firstName.trim().isEmpty() || lastName.trim().isEmpty()){
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_LONG).show();
                return;
            }

            Intent data = new Intent();
            data.putExtra(EXTRA_USERNAME, username);
            data.putExtra(EXTRA_PASSWORD, password);
            data.putExtra(EXTRA_FIRST_NAME, firstName);
            data.putExtra(EXTRA_LAST_NAME, lastName);
            data.putExtra(EXTRA_USER_ID, -1);

            setResult(RESULT_OK, data);
            finish();
        } catch (Exception e){
            Intent intent = new Intent(AddUser.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Could not save user, Try again.", Toast.LENGTH_LONG).show();
        }


    }

    private void updateUser(){
        try {
            int id = Integer.parseInt(getIntent().getStringExtra(EXTRA_USER_ID));
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            String firstName = editFirstName.getText().toString();
            String lastName = editLastName.getText().toString();

            if(username.trim().isEmpty() || password.trim().isEmpty() || firstName.trim().isEmpty() || lastName.trim().isEmpty()){
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_LONG).show();
                return;
            }

            Intent data = new Intent();
            data.putExtra(EXTRA_USER_ID, id);
            data.putExtra(EXTRA_USERNAME, username);
            data.putExtra(EXTRA_PASSWORD, password);
            data.putExtra(EXTRA_FIRST_NAME, firstName);
            data.putExtra(EXTRA_LAST_NAME, lastName);

            setResult(RESULT_OK, data);
            finish();
        } catch (Exception e){
            Intent intent = new Intent(AddUser.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Could not save user, Try again.", Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(AddUser.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
