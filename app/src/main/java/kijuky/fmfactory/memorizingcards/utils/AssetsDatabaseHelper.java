package kijuky.fmfactory.memorizingcards.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by admin on 2016/06/13.
 */
public class AssetsDatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private final File path;
    private boolean initialized;

    public AssetsDatabaseHelper(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
        this.path = context.getDatabasePath(databaseName);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase db = super.getWritableDatabase();
        if (!initialized) {
            try {
                db.close();
                db = copyDatabaseFromAssets();
                initialized = true;
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        return db;
    }

    private SQLiteDatabase copyDatabaseFromAssets() throws IOException {
        InputStream input = context.getAssets().open(getDatabaseName());
        OutputStream output = new FileOutputStream(path);

        byte[] buffer = new byte[1024];
        int size;
        while ((size = input.read(buffer)) > 0) {
            output.write(buffer, 0, size);
        }
        output.flush();
        output.close();
        input.close();

        return super.getWritableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // do nothing.
    }

    public interface Process<T> {
        T process(SQLiteDatabase db);
    }
}
