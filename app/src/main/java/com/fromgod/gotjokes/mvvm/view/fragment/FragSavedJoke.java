package com.fromgod.gotjokes.mvvm.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.fromgod.gotjokes.R;
import com.fromgod.gotjokes.mvvm.model.Joke;

public class FragSavedJoke extends Fragment {
    private static final String TAG = "FragLaugh";

    View layoutMain;
    TextView textCategory, textSetup, textDelivery;
    Toolbar toolbar;

    Joke joke;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layoutMain == null) {
            layoutMain = inflater.inflate(R.layout.frag_saved_joke, null);
        }
        joke = (Joke) getArguments().getSerializable("A_JOKE");
        Log.d(TAG, "onCreateView: bundle empty: "+getArguments().isEmpty());
        Log.d(TAG, "onCreateView: bundle toString: " + joke.toString() );
        initViews();

        return layoutMain;
    }

    @Override
    public void onStart() {
        super.onStart();
        displayJoke(joke);
    }

    public void initViews() {
        setHasOptionsMenu(true);
        toolbar = layoutMain.findViewById(R.id.layout_toolbar);
        toolbar.setTitle("Saved Joke");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: backPressed");
                FragmentManager fm = getFragmentManager();

                getActivity().onBackPressed();
            }
        });

        textCategory =  layoutMain.findViewById(R.id.text_category);
        textSetup =  layoutMain.findViewById(R.id.text_setup);
        textDelivery =  layoutMain.findViewById(R.id.text_delivery);
    }

    public void displayJoke(Joke joke) {
        if (joke.getError() == true) {
            textCategory.setText("No matching joke found...");
            textSetup.setText("");
            textDelivery.setText("");
        }
        else {
            if (joke.getType().equalsIgnoreCase(getString(R.string.single))) {
                textCategory.setText(joke.getCategory());
                textSetup.setText(joke.getJoke());
            }
            else if (joke.getType().equalsIgnoreCase(getString(R.string.twopart))) {
                textCategory.setText(joke.getCategory());
                textSetup.setText(joke.getSetup());
                textDelivery.setText(joke.getDelivery());
            }
        }
    }

}
