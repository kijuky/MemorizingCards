package kijuky.fmfactory.memorizingcards;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

import kijuky.fmfactory.memorizingcards.utils.DatabaseAssetsHelper;
import kijuky.fmfactory.memorizingcards.utils.DatabaseHandler;

public class CardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        final TextView questionSettings = (TextView)findViewById(R.id.textView_question_setting);
        final TextView question = (TextView)findViewById(R.id.textView_question);
        final RadioButton answer1 = (RadioButton)findViewById(R.id.radioButton);
        final RadioButton answer2 = (RadioButton)findViewById(R.id.radioButton2);
        final RadioButton answer3 = (RadioButton)findViewById(R.id.radioButton3);
        final RadioButton answer4 = (RadioButton)findViewById(R.id.radioButton4);
        final RadioButton answer5 = (RadioButton)findViewById(R.id.radioButton5);
        assert questionSettings != null;
        assert question != null;
        assert answer1 != null;
        assert answer2 != null;
        assert answer3 != null;
        assert answer4 != null;
        assert answer5 != null;

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final int id = 2;
            final QuestionRecord record = getQuestionRecord(id);
            questionSettings.setText(record.setting);
            question.setText(record.q);
            answer1.setText(record.answer1);
            answer2.setText(record.answer2);
            answer3.setText(record.answer3);
            answer4.setText(record.answer4);
            answer5.setText(record.answer5);
        }
    }

    @NonNull
    private QuestionRecord getQuestionRecord(final int id) {
        return QuestionDatabaseHelper.read(this, new DatabaseHandler<QuestionRecord>() {
            @Override
            public QuestionRecord process(final SQLiteDatabase db) {
                return QuestionRecord.get(db, id);
            }
        });
    }
}
