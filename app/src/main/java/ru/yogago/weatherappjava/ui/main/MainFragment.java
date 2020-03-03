package ru.yogago.weatherappjava.ui.main;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.yogago.weatherappjava.R;

public class MainFragment extends Fragment {

    private final String LOG_TAG = "myLog";
    private MainViewModel mViewModel;
    private String cityName;
    private View loadProgress = null;
    private ProgressDialog progressDialog;

    public static MainFragment newInstance() {
        return new MainFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        Log.d(LOG_TAG, "" + mViewModel.hashCode());
        View root = inflater.inflate(R.layout.main_fragment, container, false);
        final TextView textView = root.findViewById(R.id.message);
        final TextView textService = root.findViewById(R.id.textService);
        final EditText evCity = root.findViewById(R.id.cityName);
        final Button buttonStart = root.findViewById(R.id.button);

        mViewModel.createDataBase(this.getContext());

        buttonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String city = evCity.getText().toString();
                mViewModel.setContent(city);
            }
        });
        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMax(100);
        progressDialog.setMessage("Прогресс");
        progressDialog.setTitle("Загрузка данных");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        mViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        mViewModel.getCityName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                evCity.setText(s);
            }
        });
        mViewModel.getToast().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textService.setText(s);
            }
        });
        mViewModel.getLoadProgress().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                loadProgress(aBoolean);
            }
        });
        mViewModel.getCities().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                MainListFragment mainListFragment = MainListFragment.newInstance(strings);
                FragmentTransaction fTrans = getFragmentManager().beginTransaction();
                fTrans.replace(R.id.frgmCont, mainListFragment).commit();
            }
        });

        return root;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void loadProgress(boolean flag){
        if (flag) progressDialog.show();
        else  progressDialog.dismiss();
    }
}
