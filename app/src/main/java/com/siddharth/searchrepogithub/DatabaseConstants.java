package com.siddharth.searchrepogithub;

public interface DatabaseConstants {

    interface MyDatabase {
        String DATABASE_NAME = "My_Database";
        int DB_VERSION = 1;
    }

    interface RepoDetail {

        String TABLENAME = "RepoDetail";

        interface Columns {
            String NAME = "name";
            String FULL_NAME = "full_name";
            String DESCRIPTION = "description";
            String CLONE_URL = "clone_url";
            String WATCHER_COUNT = "watchers_count";
            String FORKS_COUNT = "forks_count";
            String AVATAR_URL = "avatar_url";
        }


    }
}
