package kijuky.fmfactory.memorizingcards.common.database;

import android.database.sqlite.SQLiteDatabase;

public interface DatabaseHandler<T> {
    T process(SQLiteDatabase db);
}
