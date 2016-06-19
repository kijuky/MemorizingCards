package kijuky.fmfactory.memorizingcards;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import kijuky.fmfactory.memorizingcards.utils.DatabaseHandler;

public class CardActivity extends AppCompatActivity {
    public static final String EXTRA_QUESTION_ID = "QUESTION";

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
        final Button answer = (Button)findViewById(R.id.button2);
        final Button prev = (Button)findViewById(R.id.button);
        final Button next = (Button)findViewById(R.id.button3);
        assert questionSettings != null;
        assert question != null;
        assert answer1 != null;
        assert answer2 != null;
        assert answer3 != null;
        assert answer4 != null;
        assert answer5 != null;
        assert answer != null;
        assert prev != null;
        assert next != null;

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final int id = extras.getInt(EXTRA_QUESTION_ID, 1);
            final QuestionRecord record = getQuestionRecord(id);
            questionSettings.setText(record.setting);
            question.setText("問．" + record.q);
            answer1.setText("１．" + record.answer1);
            answer2.setText("２．" + record.answer2);
            answer3.setText("３．" + record.answer3);
            answer4.setText("４．" + record.answer4);
            answer5.setText("５．" + record.answer5);

            if (id <= 1) {
                prev.setEnabled(false);
            }

            prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final Intent intent = new Intent(CardActivity.this, CardActivity.class);
                    intent.putExtra(EXTRA_QUESTION_ID, id - 1);
                    startActivityForResult(intent, 0);
                    overridePendingTransition(R.anim.in_left, R.anim.out_right);
                }
            });

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final Intent intent = new Intent(CardActivity.this, CardActivity.class);
                    intent.putExtra(EXTRA_QUESTION_ID, id + 1);
                    startActivityForResult(intent, 0);
                    overridePendingTransition(R.anim.in_right, R.anim.out_left);
                }
            });
        }
    }

    @NonNull
    private QuestionRecord getQuestionRecord(final int id) {
        return QuestionDatabaseHelper.read(this, new DatabaseHandler<QuestionRecord>() {
            @Override
            public QuestionRecord process(final SQLiteDatabase db) {
                return QuestionRecord.indexOf(id).from(db);
            }
        });
    }
}
