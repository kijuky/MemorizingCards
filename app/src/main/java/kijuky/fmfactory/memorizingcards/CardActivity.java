package kijuky.fmfactory.memorizingcards;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

        final int answerButtonId[] = new int[] {
                R.id.radioButton,
                R.id.radioButton2,
                R.id.radioButton3,
                R.id.radioButton4,
                R.id.radioButton5,
        };

        // body
        final TextView questionSettings = getViewById(R.id.textView_question_setting);
        final TextView question = getViewById(R.id.textView_question);
        final RadioButton answer[] = new RadioButton[answerButtonId.length];
        for (int i = 0; i < answerButtonId.length; i++) {
            answer[i] = getViewById(answerButtonId[i]);
        }

        // footer
        final Button showAnswer = getViewById(R.id.button2);
        final Button prev = getViewById(R.id.button);
        final Button next = getViewById(R.id.button3);

        // intent
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final int id = extras.getInt(EXTRA_QUESTION_ID, 1);

            // database
            final QuestionRecord record = getQuestionRecord(id);

            // text
            questionSettings.setText(record.setting);
            question.setText("問．" + record.q);
            for (int i = 0; i < answer.length; i++) {
                answer[i].setText((i + 1) + ". " + record.answer[i]);
            }

            // enable
            if (id <= 1) {
                prev.setEnabled(false);
            }

            // click
            prev.setOnClickListener(ButtonType.PREV.createOnClickListener(this, id));
            next.setOnClickListener(ButtonType.NEXT.createOnClickListener(this, id));
            showAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // 解答表示ボタンを無効化する
                    showAnswer.setEnabled(false);

                    // 答えのラジオボタンにボーダーを付ける
                    final RadioButton answerButton = answer[record.answerId];
                    answerButton.setBackgroundResource(R.drawable.answer_border);

                    // 答えまでスクロールする
                    final ScrollView scrollView = getViewById(R.id.scrollView);
                    scrollView.scrollTo((int)answerButton.getX(), (int)answerButton.getY());
                }
            });
        }
    }

    enum ButtonType {
        PREV(-1, R.anim.in_left, R.anim.out_right),
        NEXT(1, R.anim.in_right, R.anim.out_left);

        private final int step, enterAnimId, exitAnimId;
        ButtonType(final int step, final int enterAnimId, final int exitAnimId) {
            this.step = step;
            this.enterAnimId = enterAnimId;
            this.exitAnimId = exitAnimId;
        }

        @NonNull
        public View.OnClickListener createOnClickListener(final CardActivity self, final int id) {
            return new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final Intent intent = new Intent(self, CardActivity.class);
                    intent.putExtra(EXTRA_QUESTION_ID, id + step);
                    self.startActivityForResult(intent, 0);
                    self.overridePendingTransition(enterAnimId, exitAnimId);
                }
            };
        }
    }

    @NonNull
    private <V extends View> V getViewById(final int id) {
        final View view = findViewById(id);
        @SuppressWarnings("unchecked")
        final V v = (V)view;
        assert v != null;
        return v;
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
