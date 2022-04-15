package com.example.app2_database;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Element.class}, version = 1, exportSchema = false)
public abstract class ElementRoomDatabase extends RoomDatabase
{
    //abstrakcyjna metoda zwracająca DAO
    public abstract ElementDao elementDao();
    //implementacja singletona
    private static volatile ElementRoomDatabase INSTANCE;
    //usługa wykonawcza do wykonywania zadań w osobnym wątku
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ElementRoomDatabase getDatabase(final Context context) {
    //tworzymy nowy obiekt tylko gdy żaden nie istnieje
        if (INSTANCE == null) {
            synchronized (ElementRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder
                            (context.getApplicationContext(),
                                    ElementRoomDatabase.class,
                                    "phones_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //obiekt obsługujący wywołania zwrotne (call backs)związane ze zdarzeniami bazy danych
    //np. onCreate, onOpen
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

    //uruchamiane przy tworzeniu bazy (pierwsze
    //uruchomienie aplikacji, gdy baza nie istnieje)
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Element e1 = new Element("Google", "Pixel", "2.0", "www" );
            databaseWriteExecutor.execute(() -> {
                ElementDao dao = INSTANCE.elementDao();
                //dao.insert(e1);
            });
        }
    };
}