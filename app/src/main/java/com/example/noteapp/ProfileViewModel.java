package com.example.noteapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProfileViewModel extends AndroidViewModel {
    private ProfileRepository repository;
    private LiveData<List<Profile>> allProfiles;


    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileRepository(application);
        allProfiles = repository.getAllPersons();

    }

    private void instancetiate(Application application) {

    }

    public void insert(Profile  person)
    {
        repository.insertOnePerson(person);
    }
    public void update(Profile perdon)
    {
        repository.updatePerson(perdon);
    }
    public void deleteOneProfile(Profile person)
    {
        repository.deleteOnePerson(person);
    }
    public void deleteAllProfiles()
    {
        repository.deleteAllPersons();
    }

    public LiveData<List<Profile>> getAllProfiles() {
        return allProfiles;
    }
}
