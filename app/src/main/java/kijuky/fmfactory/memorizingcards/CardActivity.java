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

        final TextView questionSettings = getViewById(TextView.class, R.id.textView_question_setting);
        final TextView question = getViewById(TextView.class, R.id.textView_question);
        final RadioButton answer1 = getViewById(RadioButton.class, R.id.radioButton);
        final RadioButton answer2 = getViewById(RadioButton.class, R.id.radioButton2);
        final RadioButton answer3 = getViewById(RadioButton.class, R.id.radioButton3);
        final RadioButton answer4 = getViewById(RadioButton.class, R.id.radioButton4);
        final RadioButton answer5 = getViewById(RadioButton.class, R.id.radioButton5);
        final Button answer = getViewById(Button.class, R.id.button2);
        final Button prev = getViewById(Button.class, R.id.button);
        final Button next = getViewById(Button.class, R.id.button3);

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

            prev.setOnClickListener(new ButtonOnClickListener(ButtonType.PREV, id));
            next.setOnClickListener(new ButtonOnClickListener(ButtonType.NEXT, id));
        }
    }

    class ButtonOnClickListener implements View.OnClickListener {
        final ButtonType type;
        final int id;
        ButtonOnClickListener(final ButtonType type, final int currId) {
            this.type = type;
            id = currId + type.dirId;
        }

        @Override
        public void onClick(final View v) {
            final Intent intent = new Intent(CardActivity.this, CardActivity.class);
            intent.putExtra(EXTRA_QUESTION_ID, id);
            startActivityForResult(intent, 0);
            overridePendingTransition(type.enterAnimId, type.exitAnimId);
        }
    }

    enum ButtonType {
        PREV(-1, R.anim.in_left, R.anim.out_right),
        NEXT(1, R.anim.in_right, R.anim.out_left);
        public final int dirId, enterAnimId, exitAnimId;
        ButtonType(final int dirId, final int enterAnimId, final int exitAnimId) {
            this.dirId = dirId;
            this.enterAnimId = enterAnimId;
            this.exitAnimId = exitAnimId;
        }
    }

    @NonNull
    private <V extends View> V getViewById(final Class<V> clazz, final int id) {
        View ret = findViewById(id);
        assert ret != null;
        return clazz.cast(ret);
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
