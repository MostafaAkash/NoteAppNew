package com.example.noteapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProfileRepository {
    private ProfileDao profileDao;
    private LiveData<List<Profile>> allProfiles;

    public ProfileRepository(Application application) {
        ProfileDatabase database = ProfileDatabase.getInstance(application);
        profileDao = database.profileDao();
        allProfiles = profileDao.getAllPersons();
    }
    public  void insertOnePerson(Profile person)
    {
        new InsertProfileAsynkTask(profileDao).execute(person);
    }
    public  void updatePerson(Profile person)
    {
        new UpdateProfileAsynkTask(profileDao).execute(person);

    }
    public void deleteOnePerson(Profile person)
    {
        new DeletetProfileAsynkTask(profileDao).execute(person);

    }
    public void deleteAllPersons()
    {
        new DeleteAllProfilesAsk(profileDao).execute();

    }
    public LiveData<List<Profile>> getAllPersons()
    {
        return allProfiles;
    }

    private static class InsertProfileAsynkTask extends AsyncTask<Profile,Void,Void>
    {
        private ProfileDao profileDao;

        public InsertProfileAsynkTask(ProfileDao profileDao) {
            this.profileDao = profileDao;
        }

        @Override
        protected Void doInBackground(Profile... profiles) {
            profileDao.insertOnePerson(profiles[0]);
            return null;
        }
    }

    private static class UpdateProfileAsynkTask extends AsyncTask<Profile,Void,Void>
    {
        private ProfileDao profileDao;

        public UpdateProfileAsynkTask(ProfileDao profileDao) {
            this.profileDao = profileDao;
        }

        @Override
        protected Void doInBackground(Profile... profiles) {
            profileDao.updatePerson(profiles[0]);
            return null;
        }
    }

    private static class DeletetProfileAsynkTask extends AsyncTask<Profile,Void,Void>
    {
        private ProfileDao profileDao;

        public DeletetProfileAsynkTask(ProfileDao profileDao) {
            this.profileDao = profileDao;
        }

        @Override
        protected Void doInBackground(Profile... profiles) {
            profileDao.deleteOnePerson(profiles[0]);
            return null;
        }
    }

    private static class DeleteAllProfilesAsk extends AsyncTask<Void,Void,Void>
    {
        private ProfileDao profileDao;

        public DeleteAllProfilesAsk(ProfileDao profileDao) {
            this.profileDao = profileDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            profileDao.deleteAllPersons();
            return null;
        }
    }
}
