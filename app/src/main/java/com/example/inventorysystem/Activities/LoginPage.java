package com.example.inventorysystem.Activities;

import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.inventorysystem.R;
import com.example.inventorysystem.User;
import com.example.inventorysystem.ViewModels.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class LoginPage extends AppCompatActivity {
    public static final String EXTRA_USER_ID =
            "com.example.inventorysystem.Activities.EXTRA_USER_ID";
    public static final String  EXTRA_USERNAME =
            "com.example.inventorysystem.Activities.EXTRA_USERNAME";
    public static final String  EXTRA_FIRST_NAME =
            "com.example.inventorysystem.Activities.EXTRA_FIRST_NAME";
    public static final String  EXTRA_LAST_NAME =
            "com.example.inventorysystem.Activities.EXTRA_LAST_NAME";
    public static final String  EXTRA_PASSWORD =
            "com.example.inventorysystem.Activities.EXTRA_PASSWORD";

    public static final int ADD_USER_REQUEST = 1;

    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=\\S+$)$");   //password can have no white spaces.

    private TextInputLayout editTextUserName;
    private TextInputLayout editTextPassword;
    User user;
    private String currentUserId;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        }

        editTextUserName = findViewById(R.id.text_input_username);
        editTextPassword = findViewById(R.id.text_input_password);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        Button loginButton = findViewById(R.id.login_page_login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    user = userViewModel.getUserNameAndPassword(editTextUserName.getEditText().getText().toString().trim(), editTextPassword.getEditText().getText().toString().trim());
                    String userName = user.getUserName();
                    String password = user.getPassword();
                    currentUserId = Integer.toString(user.getUserId());
                    confirmInput(userName, password);
                }catch (NullPointerException e){
                    Intent intent = new Intent(LoginPage.this, LoginPage.class);
                    startActivity(intent);
                    Toast.makeText(LoginPage.this, "Something went wrong, try again.", Toast.LENGTH_LONG).show();
                }

            }
        });

        Button newUserButton = findViewById(R.id.login_page_new_user_button);
        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, AddUser.class);
                startActivityForResult(intent, ADD_USER_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_USER_REQUEST && resultCode == RESULT_OK) {
            String username = data.getStringExtra(AddUser.EXTRA_USERNAME);
            String password = data.getStringExtra(AddUser.EXTRA_PASSWORD);
            String firstName = data.getStringExtra(AddUser.EXTRA_FIRST_NAME);
            String lastName = data.getStringExtra(AddUser.EXTRA_LAST_NAME);

            User user = new User(username, firstName, lastName, password);
            userViewModel.insert(user);

            Toast.makeText(this, "User Saved", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == ADD_USER_REQUEST && resultCode != RESULT_OK) {
            Toast.makeText(this, "User not saved", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validatePassword(String password) {
        if (password.isEmpty()){
            editTextPassword.setError("Field can't be empty");
            return false;
//        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
//            editTextPassword.setError("Password can't have spaces.");
//            return false;
        }else{
            editTextPassword.setError(null);
            return true;
        }
    }

    private boolean validateUsername(String username){
        if(username.isEmpty()){
            editTextUserName.setError("Field can't be empty");
            return false;
        }else{
            editTextUserName.setError(null);
            return true;
        }
    }

    public void confirmInput (String username, String password){
        try {
            if(!validatePassword(password) | !validateUsername(username)){
                return;
            }

            if (!username.equals(editTextUserName.getEditText().getText().toString().trim()) && !password.equals(editTextPassword.getEditText().getText().toString().trim())) {
                Toast.makeText(LoginPage.this, "Could not find that username or password.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginPage.this, LoginPage.class);
                startActivity(intent);
            }
            if (username.equals(editTextUserName.getEditText().getText().toString().trim()) && password.equals(editTextPassword.getEditText().getText().toString().trim())) {
                Intent intent = new Intent(LoginPage.this, MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_USER_ID, currentUserId);
                intent.putExtra(MainActivity.EXTRA_USERNAME, getIntent().getStringExtra(EXTRA_USERNAME));
                intent.putExtra(MainActivity.EXTRA_FIRST_NAME, getIntent().getStringExtra(EXTRA_FIRST_NAME));
                intent.putExtra(MainActivity.EXTRA_LAST_NAME, getIntent().getStringExtra(EXTRA_LAST_NAME));
                intent.putExtra(MainActivity.EXTRA_PASSWORD, getIntent().getStringExtra(EXTRA_PASSWORD));
                startActivity(intent);
            } else {
                Toast.makeText(LoginPage.this, "Try using a different username or password.", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Intent intent = new Intent(LoginPage.this, LoginPage.class);
            startActivity(intent);
            Toast.makeText(LoginPage.this, "Something went wrong, try again.", Toast.LENGTH_LONG).show();
        }

    }
}

