package kijuky.fmfactory.memorizingcards;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        TextView questionSettings = (TextView)findViewById(R.id.textView_question_setting);
        TextView question = (TextView)findViewById(R.id.textView_question);
        assert questionSettings != null;
        assert question != null;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            DataBaseHelper databaseHelper = new DataBaseHelper(this);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            try {
                Cursor c = db.query("question_t", null, "id=1", null, null, null, null);
                c.moveToFirst();

                //int id = c.getInt(0);
                //int year = c.getInt(1);
                //int month = c.getInt(2);
                //int number = c.getInt(3);
                String qs = c.getString(4);
                String q = c.getString(5);
                String a1 = c.getString(6);
                String a2 = c.getString(7);
                String a3 = c.getString(8);
                String a4 = c.getString(9);
                String a5 = c.getString(10);
                int a = c.getInt(11);

                c.close();
            } finally {
                db.close();
            }

            questionSettings.setText("問題設定");
            question.setText("問題");
        }
    }
}
