package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "profile_table")
public class Profile {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String Name;
    private int age;
    private String roll;
    @ColumnInfo(name = "priority_number")
    private int priority;

    public Profile(String name, int age, String roll, int priority) {
        Name = name;
        this.age = age;
        this.roll = roll;
        this.priority = priority;
    }

    public Profile() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public int getAge() {
        return age;
    }

    public String getRoll() {
        return roll;
    }
}
