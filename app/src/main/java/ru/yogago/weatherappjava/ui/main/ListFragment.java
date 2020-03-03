package ru.yogago.weatherappjava.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.yogago.weatherappjava.R;

public class ListFragment extends Fragment {

    private final String LOG_TAG = "myLog";

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "ListFragment: " + this.hashCode());
        return inflater.inflate(R.layout.list_fragment, container, false);
    }
}
