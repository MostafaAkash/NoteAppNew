package com.example.noteapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabase.Callback;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Profile.class},version = 1)
public abstract class ProfileDatabase extends RoomDatabase {
    private static ProfileDatabase instance;
    public abstract ProfileDao profileDao();


    public static synchronized ProfileDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ProfileDatabase.class,"profile_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback= new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {

            super.onCreate(db);
            new PopulateAsynkTask(instance).execute();
        }
    };

    private static class  PopulateAsynkTask extends AsyncTask<Void,Void,Void>{
         private ProfileDao profileDao;

        public PopulateAsynkTask(ProfileDatabase db) {
            profileDao = db.profileDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            profileDao.insertOnePerson(new Profile("Golam Mostafa",23,"140128",3));
            profileDao.insertOnePerson(new Profile("Galib Hasan",23,"140129",1));
            profileDao.insertOnePerson(new Profile("Pabal Rana",23,"140124",2));
            return null;
        }
    }


}
