package ru.yogago.weatherappjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.yogago.weatherappjava.ui.main.DetailFragment;
import ru.yogago.weatherappjava.ui.main.ListFragment;
import ru.yogago.weatherappjava.ui.main.MainFragment;
import ru.yogago.weatherappjava.ui.main.MainListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frgmCont, ListFragment.newInstance())
                    .commitNow();
        }
    }
}
