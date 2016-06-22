package kijuky.fmfactory.memorizingcards;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import kijuky.fmfactory.memorizingcards.utils.DatabaseAssetsHelper;
import kijuky.fmfactory.memorizingcards.utils.DatabaseHandler;

public class QuestionDatabaseHelper extends DatabaseAssetsHelper {
    private static final String DB_FILE_NAME = "question.db";
    private static final int DB_VERSION = 1;

    private QuestionDatabaseHelper(final Context context) {
        super(context, DB_FILE_NAME, DB_VERSION);
    }

    @NonNull
    static <T> T read(final Context context, final DatabaseHandler<T> handler) {
        try (final SQLiteDatabase db = new QuestionDatabaseHelper(context).getWritableDatabase()) {
            return handler.process(db);
        }
    }
}
