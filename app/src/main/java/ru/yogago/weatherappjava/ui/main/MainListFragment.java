package ru.yogago.weatherappjava.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import ru.yogago.weatherappjava.R;

public class MainListFragment extends ListFragment {

    private final String LOG_TAG = "myLog";
    private ArrayList<String> strings = new ArrayList<>();

    public static MainListFragment newInstance(ArrayList<String> strings) {
        MainListFragment mainListFragment = new MainListFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("tag", strings);
        mainListFragment.setArguments(args);
        return mainListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.strings = bundle.getStringArrayList("tag");
        }
//        strings.add("test1");
//        strings.add("test2");
        setListAdapter(getAdapter(this.strings));
        super.onCreate(savedInstanceState);
    }

    public ListAdapter getAdapter(ArrayList<String> strings){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_activated_1, strings);
        return adapter;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        DetailFragment detailFragment = DetailFragment.newInstance(this.strings.get(position));
        FragmentTransaction fTrans = getFragmentManager().beginTransaction();
        fTrans.replace(R.id.frgmCont, detailFragment).commit();
    }
}
