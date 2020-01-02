package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.style.EasyEditSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditProfileActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "com.example.noteapp.EXTRA_NAME";
    public static final String EXTRA_AGE = "com.example.noteapp.EXTRA_AGE";
    public static final String EXTRA_ROLL = "com.example.noteapp.EXTRA_ROLL";
    public static final String EXTRA_PRIORITY = "com.example.noteapp.EXTRA_PRIORITY";
    public static final String EXTRA_ID= "com.example.noteapp.EXTRA_ID";


    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextRoll;
    private NumberPicker numberPicker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        init();
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(15);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID))
        {
            setTitle("Edit Profile");
            editTextAge.setText(String.valueOf(intent.getIntExtra(EXTRA_AGE,-2)));
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextRoll.setText(intent.getStringExtra(EXTRA_ROLL));
            numberPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY,2));
        }
        else
        {
            setTitle("Add Profile");
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.saveIconIdMenuAc:
                aveProfile();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
                    
        }
        
    }

    private void aveProfile() {

        String name,age,roll;
        int priority;
        name = editTextName.getText().toString().trim();
        age = editTextAge.getText().toString().trim();
        roll = editTextRoll.getText().toString().trim();
        priority = numberPicker.getValue();
        if(name.isEmpty() || age.isEmpty() || roll.isEmpty())
        {
            Toast.makeText(this, "Please insert all Fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME,name);
        data.putExtra(EXTRA_ROLL,roll);
        data.putExtra(EXTRA_PRIORITY,priority);
        data.putExtra(EXTRA_AGE,Integer.valueOf(age));

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if(id!=-1)
        {
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,data);
        finish();

    }

    private void init() {
        editTextAge = findViewById(R.id.ageEditTextProAc);
        editTextName = findViewById(R.id.nameEditTextProAc);
        editTextRoll = findViewById(R.id.rollEditTextProAc);
        numberPicker = findViewById(R.id.priorityPickerIdProAc);

    }
}
