package kijuky.fmfactory.memorizingcards;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = getViewById(R.id.button_start);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(MainActivity.this, CardActivity.class);
                intent.putExtra(CardActivity.EXTRA_QUESTION_ID, 1);
                startActivityForResult(intent, 0);
            }
        });
    }

    @NonNull
    private <V extends View> V getViewById(final int id) {
        final View view = findViewById(id);
        @SuppressWarnings("unchecked")
        final V v = (V)view;
        assert v != null;
        return v;
    }
}
