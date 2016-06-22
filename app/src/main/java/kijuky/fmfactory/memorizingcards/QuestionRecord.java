package kijuky.fmfactory.memorizingcards;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.NoSuchElementException;

public class QuestionRecord {
    private static final String TABLE_NAME = "question_t";
    final int id;
    final int year;
    final int month;
    final int number;
    final String setting;
    final String q;
    final String[] answer = new String[5];
    final int answerId;

    private QuestionRecord(final Cursor c) {
        id = c.getInt(0);
        year = c.getInt(1);
        month = c.getInt(2);
        number = c.getInt(3);
        setting = c.getString(4);
        q = c.getString(5);
        answer[0] = c.getString(6);
        answer[1] = c.getString(7);
        answer[2] = c.getString(8);
        answer[3] = c.getString(9);
        answer[4] = c.getString(10);
        answerId = c.getInt(11) - 1;
    }

    interface Query {
        QuestionRecord from(SQLiteDatabase db);
    }

    @NonNull
    static Query indexOf(final int index) {
        if (index <= 0) {
            throw new IndexOutOfBoundsException("require: 1 <= index; index = " + index);
        }

        return new Query() {
            public QuestionRecord from(final SQLiteDatabase db) {
                final Cursor c = db.query(TABLE_NAME, null, "id=" + index, null, null, null, null);
                if (c == null) {
                    throw new NoSuchElementException("id = " + index);
                }

                return create(c);
            }
        };
    }

    @NonNull
    private static QuestionRecord create(final Cursor c) {
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
