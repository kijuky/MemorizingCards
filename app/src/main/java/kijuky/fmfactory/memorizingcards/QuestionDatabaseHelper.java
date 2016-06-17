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
    @SuppressWarnings("TryFinallyCanBeTryWithResources")
    public static <T> T read(final Context context, final DatabaseHandler<T> handler) {
        T result = null;
        final SQLiteDatabase db = new QuestionDatabaseHelper(context).getWritableDatabase();
        try {
            result = handler.process(db);
        } finally {
            db.close();
        }
        return result;
    }
}
