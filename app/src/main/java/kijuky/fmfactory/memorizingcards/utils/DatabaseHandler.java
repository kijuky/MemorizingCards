package kijuky.fmfactory.memorizingcards.utils;

import android.database.sqlite.SQLiteDatabase;

public interface DatabaseHandler<T> {
    T process(SQLiteDatabase db);
}
