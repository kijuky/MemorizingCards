package kijuky.fmfactory.memorizingcards.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class AbstractActivity extends AppCompatActivity {
    @NonNull
    protected <V extends View> V getViewById(final int id) {
        final View view = findViewById(id);
        @SuppressWarnings("unchecked")
        final V v = (V)view;
        assert v != null;
        return v;
    }

    @NonNull
    protected Intent createIntent(Class<?> clazz) {
        return new Intent(this, clazz);
    }
}
