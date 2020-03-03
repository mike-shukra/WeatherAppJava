package ru.yogago.weatherappjava.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.yogago.weatherappjava.R;

public class DetailFragment extends Fragment {

    private final String LOG_TAG = "myLog";
    private String string;
    private TextView detailTextView;

    public static DetailFragment newInstance(String string) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString("string", string);
        detailFragment.setArguments(args);
        return detailFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.detail_fragment, container, false);
        this.detailTextView = root.findViewById(R.id.tvDetail);
        return root;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.string = bundle.getString("string");
        }
        this.detailTextView.setText(this.string);

    }
}
