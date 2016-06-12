package kijuky.fm.memorizingcards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

public class CardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        // ビュー
        TextView questionSettings = (TextView)findViewById(R.id.textView_question_setting);
        TextView question = (TextView)findViewById(R.id.textView_question);
        assert questionSettings != null;
        assert question != null;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            questionSettings.setText("問題設定");
            question.setText("問題");
        }
    }
}
