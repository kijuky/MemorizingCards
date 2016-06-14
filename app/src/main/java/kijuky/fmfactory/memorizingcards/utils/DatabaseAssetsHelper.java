package kijuky.fmfactory.memorizingcards.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseAssetsHelper extends SQLiteOpenHelper {
    private final Context context;
    private final File path;
    private boolean initialized;

    protected DatabaseAssetsHelper(final Context context, final String databaseName, final int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
        this.path = context.getDatabasePath(databaseName);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
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
            } catch (final IOException e) {
                throw new IllegalStateException(e);
            }
        }
        return db;
    }

    @NonNull
    private SQLiteDatabase copyDatabaseFromAssets() throws IOException {
        final InputStream input = context.getAssets().open(getDatabaseName());
        final OutputStream output = new FileOutputStream(path);

        final byte[] buffer = new byte[1024];
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
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        // do nothing.
    }
}
