package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_PROFILE_REQUEST = 1;
    public static final int EDIT_PROFILE_REQUEST = 2;
    private ProfileViewModel viewModel;
    private RecyclerView recyclerView;
    private ProfileAdapter adapter;
    private List<Profile> profileList;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton = findViewById(R.id.floatingButtonMain);
        floatingActionButton.setOnClickListener(listener);

        buildRecycleView();

        activeViewModel();
        /*
        try {
            viewModel =ViewModelProviders.of(this).get(ProfileViewModel.class);
        }
        catch (Exception e)
        {

        }


        viewModel.getAllProfiles().observe(this, new Observer<List<Profile>>() {
            @Override
            public void onChanged(List<Profile> profiles) {
                Toast.makeText(MainActivity.this, "onChanged Method Called", Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, AddEditProfileActivity.class);
            startActivityForResult(intent, ADD_PROFILE_REQUEST);
        }
    };

    private void buildRecycleView() {
        recyclerView = findViewById(R.id.recycleViewIdMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProfileAdapter();
    }

    private void activeViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        viewModel.getAllProfiles().observe(this, new Observer<List<Profile>>() {
            @Override
            public void onChanged(List<Profile> profiles) {
                String data = "";
                for (int i = 0; i < profiles.size(); i++) {
                    data = data + profiles.get(i).getId() + "\n" + profiles.get(i).getName() + "\n" + profiles.get(i).getRoll() + "\n" + profiles.get(i).getAge() + "\n" + profiles.get(i).getPriority() + "\n\n";
                }

                Toast.makeText(MainActivity.this, "onChanged Method Called" + "\n" + data, Toast.LENGTH_LONG).show();

                adapter.submitList(profiles);
                recyclerView.setAdapter(adapter);
            }
        });

        deleteItemBySwipe();
        adapter.setOnItemClickListener(new ProfileAdapter.OnitemClickListener() {
            @Override
            public void onItemClick(Profile profile) {
                Intent intent = new Intent(MainActivity.this, AddEditProfileActivity.class);

                intent.putExtra(AddEditProfileActivity.EXTRA_NAME, profile.getName());
                intent.putExtra(AddEditProfileActivity.EXTRA_AGE, profile.getAge());
                intent.putExtra(AddEditProfileActivity.EXTRA_ROLL, profile.getRoll());
                intent.putExtra(AddEditProfileActivity.EXTRA_PRIORITY, profile.getPriority());
                intent.putExtra(AddEditProfileActivity.EXTRA_ID, profile.getId());
                startActivityForResult(intent, EDIT_PROFILE_REQUEST);
                Toast.makeText(MainActivity.this, "Id: " + profile.getId(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void deleteItemBySwipe() {
        //  viewModel.deleteOneProfile(adapter.getProfileatPosition(viewHolder.getAdapterPosition()));
        // Toast.makeText(MainActivity.this, "Profile deleted", Toast.LENGTH_SHORT).show();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.deleteOneProfile(adapter.getProfileatPosition(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Profile deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PROFILE_REQUEST && resultCode == RESULT_OK) {

            viewModel.insert(setData(data));
            Toast.makeText(this, "Profile Saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_PROFILE_REQUEST && resultCode == RESULT_OK) {

          //  String st = data.getStringExtra(AddEditProfileActivity.EXTRA_ID);

            int id = data.getIntExtra(AddEditProfileActivity.EXTRA_ID,-1);
            if (id == -1) {
                Toast.makeText(this, "Profile can not be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            Profile profile = setData(data);
            profile.setId(id);
            viewModel.update(profile);
            Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this, "Profile Not Saved", Toast.LENGTH_SHORT).show();

        }
    }

    private Profile setData(Intent data) {
        String name, roll;
        int age, priority;
        name = data.getStringExtra(AddEditProfileActivity.EXTRA_NAME);
        roll = data.getStringExtra(AddEditProfileActivity.EXTRA_ROLL);
        age = data.getIntExtra(AddEditProfileActivity.EXTRA_AGE, 20);
        priority = data.getIntExtra(AddEditProfileActivity.EXTRA_PRIORITY, 1);

        Profile profile = new Profile(name, age, roll, priority);
        return profile;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAllProfileIdMainMenu:
                viewModel.deleteAllProfiles();
                Toast.makeText(this, "All profile id deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
