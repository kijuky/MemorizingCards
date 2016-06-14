package kijuky.fmfactory.memorizingcards;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import kijuky.fmfactory.memorizingcards.utils.AssetsDatabaseHelper;

public class QuestionAssetsDatabaseHelper extends AssetsDatabaseHelper {
    private static final String DB_FILE_NAME = "question.db";
    private static final int DB_VERSION = 1;

    public QuestionAssetsDatabaseHelper(Context context) {
        super(context, DB_FILE_NAME, DB_VERSION);
    }

    public static <T> T read(Context context, AssetsDatabaseHelper.Handler<T> handler) {
        T result = null;
        SQLiteDatabase db = new QuestionAssetsDatabaseHelper(context).getWritableDatabase();
        try {
            result = handler.process(db);
        } finally {
            db.close();
        }
        return result;
    }
}
