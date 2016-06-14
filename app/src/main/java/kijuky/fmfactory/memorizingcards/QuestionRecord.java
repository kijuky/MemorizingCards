package kijuky.fmfactory.memorizingcards;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.NoSuchElementException;

public class QuestionRecord {
    private static final String TABLE_NAME = "question_t";
    public final int id;
    public final int year;
    public final int month;
    public final int number;
    public final String setting;
    public final String q;
    public final String answer1;
    public final String answer2;
    public final String answer3;
    public final String answer4;
    public final String answer5;
    public final int answer;

    private QuestionRecord(final Cursor c) {
        id = c.getInt(0);
        year = c.getInt(1);
        month = c.getInt(2);
        number = c.getInt(3);
        setting = c.getString(4);
        q = c.getString(5);
        answer1 = c.getString(6);
        answer2 = c.getString(7);
        answer3 = c.getString(8);
        answer4 = c.getString(9);
        answer5 = c.getString(10);
        answer = c.getInt(11);
    }

    @NonNull
    public static QuestionRecord get(final SQLiteDatabase db, final int id) {
        if (id <= 0) {
            throw new IndexOutOfBoundsException("require: 1 <= id; id = " + id);
        }

        final Cursor c = db.query(TABLE_NAME, null, "id=" + id, null, null, null, null);
        if (c == null) {
            throw new NoSuchElementException("id = " + id);
        }

        QuestionRecord q = null;
        try {
            c.moveToFirst();
            q = new QuestionRecord(c);
        } finally {
            c.close();
        }
        return q;
    }
}
