package kijuky.fmfactory.memorizingcards;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import kijuky.fmfactory.memorizingcards.utils.DatabaseHandler;

public class CardActivity extends AppCompatActivity {
    public static final String EXTRA_QUESTION_ID = "QUESTION";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        final int answer_id[] = new int[] {
                R.id.radioButton,
                R.id.radioButton2,
                R.id.radioButton3,
                R.id.radioButton4,
                R.id.radioButton5,
        };

        // body
        final TextView questionSettings = getViewById(TextView.class, R.id.textView_question_setting);
        final TextView question = getViewById(TextView.class, R.id.textView_question);
        final RadioButton answer[] = new RadioButton[answer_id.length];
        for (int i = 0; i < answer_id.length; i++) {
            answer[i] = getViewById(RadioButton.class, answer_id[i]);
        }

        // footer
        final Button showAnswer = getViewById(Button.class, R.id.button2);
        final Button prev = getViewById(Button.class, R.id.button);
        final Button next = getViewById(Button.class, R.id.button3);

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // intent
            final int id = extras.getInt(EXTRA_QUESTION_ID, 1);

            // database
            final QuestionRecord record = getQuestionRecord(id);

            // text
            questionSettings.setText(record.setting);
            question.setText("問．" + record.q);
            for (int i = 0; i < answer.length; i++) {
                answer[i].setText(i + ". " + record.answer[i]);
            }

            // enable
            if (id <= 1) {
                prev.setEnabled(false);
            }

            // click
            prev.setOnClickListener(new ButtonOnClickListener(ButtonType.PREV, id));
            next.setOnClickListener(new ButtonOnClickListener(ButtonType.NEXT, id));
            showAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // 解答表示ボタンを無効化する
                    showAnswer.setEnabled(false);

                    // 答えのラジオボタンにボーダーを付ける
                    answer[record.answerId].setBackgroundResource(R.drawable.answer_border);

                    // 答えまでスクロールする
                    final ScrollView scrollView = getViewById(ScrollView.class, R.id.scrollView);
                    scrollView.scrollTo(
                            (int)answer[record.answerId].getX(),
                            (int)answer[record.answerId].getY());
                }
            });
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
