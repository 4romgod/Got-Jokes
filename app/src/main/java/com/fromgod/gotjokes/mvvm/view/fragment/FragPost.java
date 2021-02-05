package com.fromgod.gotjokes.mvvm.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.fromgod.gotjokes.R;

public class FragPost extends Fragment implements AdapterView.OnItemSelectedListener {

    View layoutMain;
    Toolbar toolbar;
    Spinner spinCat;
    Spinner spinType;

    String[] categories = new String[4];
    String[] types = new String[2];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(layoutMain == null){
            layoutMain = inflater.inflate(R.layout.frag_post, null);
        }
        initViews();

        categories[0] = "None";
        categories[1] = getString(R.string.miscellaneous);
        categories[2] = getString(R.string.programing);
        categories[3] = getString(R.string.dark);

        types[0] = getString(R.string.single);
        types[1] = getString(R.string.twopart);

        ArrayAdapter adapterCat = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, categories);
        ArrayAdapter adapterType = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, types);
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCat.setAdapter(adapterCat);
        spinType.setAdapter(adapterType);

        return layoutMain;
    }

    public void initViews(){
        toolbar = layoutMain.findViewById(R.id.layout_toolbar_post);
        toolbar.setTitle("Post Joke");

        spinCat = layoutMain.findViewById(R.id.spinCat);
        spinType = layoutMain.findViewById(R.id.spinType);
        spinCat.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        spinType.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

}
