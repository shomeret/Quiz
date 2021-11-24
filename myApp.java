package com.example.quizapp;

import android.app.Application;

public class myApp extends Application {

    public StorageManager getStorageManager() {
        return storageManager;
    }

    private StorageManager storageManager = new StorageManager();

    }


