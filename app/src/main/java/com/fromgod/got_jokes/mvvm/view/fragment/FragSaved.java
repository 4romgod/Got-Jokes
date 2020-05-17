package com.fromgod.got_jokes.mvvm.view.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fromgod.got_jokes.AdapterRecycler;
import com.fromgod.got_jokes.R;
import com.fromgod.got_jokes.mvvm.model.Joke;
import com.fromgod.got_jokes.mvvm.viewmodel.JokeViewModel;
import com.fromgod.got_jokes.utils.UI;

import java.util.List;


public class FragSaved extends Fragment {
    private static final String TAG = "FragSaved";

    //VIEWS
    View layoutMain;
    Toolbar toolbar;
    ActionBar actionBar;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    private JokeViewModel viewModel;

    AdapterRecycler adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }       //end onCreate()


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(layoutMain == null){
            layoutMain = inflater.inflate(R.layout.frag_saved, null);
        }
        initViews();

        adapter = new AdapterRecycler();
        recyclerView.setAdapter(adapter);

        progressDialog.show();
        viewModel = ViewModelProviders.of(getActivity()).get(JokeViewModel.class);
        viewModel.getAllJokes().observe(getActivity(), new Observer<List<Joke>>() {
            @Override
            public void onChanged(List<Joke> jokes) {
                Log.d(TAG, "onChanged: size: " + jokes.size());
                progressDialog.dismiss();
                adapter.setJokes(jokes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                viewModel.delete(adapter.getJokeAt(position));
                Toast.makeText(getContext(), "Joke Deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new AdapterRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(Joke joke) {
                UI.replaceFragment(getFragmentManager(), new FragSavedJoke(), R.id.layout_frame_main);
            }
        });

        return layoutMain;
    }       //end onCreateView()


    public void initViews(){
        toolbar = layoutMain.findViewById(R.id.layout_toolbar_saved);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Saved Jokes");

        recyclerView = layoutMain.findViewById(R.id.layout_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
    }       //end initViews()


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_saved, menu);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            //Alert Dialog for deleting all todo
            new AlertDialog
                    .Builder(getActivity())
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete all Jokes?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            viewModel.deleteAllItems();
                            Toast.makeText(getContext(), "All Jokes deleted", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setCancelable(true).show();
        }

        return true;
    }


}       //end class
