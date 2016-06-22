package kijuky.fmfactory.memorizingcards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import kijuky.fmfactory.memorizingcards.utils.AbstractActivity;

public class MainActivity extends AbstractActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = getViewById(R.id.button_start);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = createIntent(CardActivity.class);
                intent.putExtra(CardActivity.EXTRA_QUESTION_ID, 1);
                startActivityForResult(intent, 0);
            }
        });
    }
}
