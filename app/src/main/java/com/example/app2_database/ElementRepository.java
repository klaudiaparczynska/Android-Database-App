package com.example.app2_database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ElementRepository {
    private ElementDao mElementDao;
    private LiveData<List<Element>> mAllElements;
    ElementRepository(Application application) {
        ElementRoomDatabase elementRoomDatabase = ElementRoomDatabase.getDatabase(application);
        mElementDao = elementRoomDatabase.elementDao();
        mAllElements = mElementDao.getAlphabetizedElements();

    }
    LiveData<List<Element>> getAllElements() {
        return mAllElements;
    }
    void deleteAll() {
        ElementRoomDatabase.databaseWriteExecutor.execute(() -> {
            mElementDao.deleteAll();
        });
    }
    void insert(Element element) {
        //Runnable -> lambda
        ElementRoomDatabase.databaseWriteExecutor.execute(() -> {
            mElementDao.insert(element);
        });
    }
    void deleteElement(Element element)
    {
        ElementRoomDatabase.databaseWriteExecutor.execute(() -> {
            mElementDao.deleteElement(element);
        });
    }
    void update(Element element)
    {
        ElementRoomDatabase.databaseWriteExecutor.execute(() -> {
            mElementDao.update(element);
        });
    }
}