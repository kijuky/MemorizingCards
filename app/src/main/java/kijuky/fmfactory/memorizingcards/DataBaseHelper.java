package kijuky.fmfactory.memorizingcards;

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
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_FILE_NAME = "question.db";
    private static final String DB_NAME = "question";
    private static final int DB_VERSION = 1;

    private Context context;
    private File path;
    private boolean initialized;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        this.path = context.getDatabasePath(DB_NAME);
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
                e.printStackTrace();
            }
        }
        return db;
    }

    private SQLiteDatabase copyDatabaseFromAssets() throws IOException {
        InputStream input = context.getAssets().open(DB_FILE_NAME);
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
}
