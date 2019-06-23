package com.siddharth.searchrepogithub.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.siddharth.searchrepogithub.DatabaseConstants;
import com.siddharth.searchrepogithub.database.daos.RepoDao;
import com.siddharth.searchrepogithub.database.entitymodels.RepoDetails;

@Database(entities = {RepoDetails.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase INSTANCE;

    public abstract RepoDao repoDao();

    /**
     * creating database instance
     *
     * @param context
     * @return instance of ShifuDatabase
     */
    public static MyDatabase getApplicationDatabase(Context context) {

        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class,
                            DatabaseConstants.MyDatabase.DATABASE_NAME).allowMainThreadQueries()
                            .allowMainThreadQueries().build();

                    return INSTANCE;

                }

            }
        }

        return INSTANCE;
    }


    /**
     * Deletes all rows from all the tables that are registered to this database as
     * {@link Database#entities()}.
     *
     * @param context
     */
    public void resetShifuDatabase(@NonNull Context context) {
        getApplicationDatabase(context).clearAllTables();
    }

}
