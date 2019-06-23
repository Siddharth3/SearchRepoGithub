package com.siddharth.searchrepogithub.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.siddharth.searchrepogithub.DatabaseConstants;

public class DatabaseClient {

    private Context mCtx;
    private static DatabaseClient mInstance;

    //our app database object
    private MyDatabase appDatabase;

    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        appDatabase = Room.databaseBuilder(mCtx, MyDatabase.class, DatabaseConstants.MyDatabase.DATABASE_NAME).build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public MyDatabase getAppDatabase() {
        return appDatabase;
    }
}
