package com.fromgod.gotjokes.mvvm.view.fragment;

import android.app.AlertDialog;
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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fromgod.gotjokes.mvvm.view.AdapterRecycler;
import com.fromgod.gotjokes.R;
import com.fromgod.gotjokes.mvvm.model.Joke;
import com.fromgod.gotjokes.mvvm.view.activity.MainActivity;
import com.fromgod.gotjokes.mvvm.viewmodel.JokeViewModel;

import java.util.List;


public class FragSaved extends Fragment {
    private static final String TAG = "FragSaved";

    //VIEWS
    View layoutMain;
    Toolbar toolbar;
    ActionBar actionBar;
    RecyclerView recyclerView;

    private JokeViewModel viewModel;

    AdapterRecycler adapter;

    FragmentManager fm;


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

        fm = getFragmentManager();

        adapter = new AdapterRecycler();
        recyclerView.setAdapter(adapter);

        //progressDialog.show();
        viewModel = ViewModelProviders.of(getActivity()).get(JokeViewModel.class);
        viewModel.getAllJokes().observe(getActivity(), new Observer<List<Joke>>() {
            @Override
            public void onChanged(List<Joke> jokes) {
                Log.d(TAG, "onChanged: size: " + jokes.size());
                //progressDialog.dismiss();
                adapter.setJokeList(jokes);
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
                Log.d(TAG, "onItemClick: JOKE: "+joke.toString());
                Fragment fragment = newFragJoke(joke);

                fm.beginTransaction().add(R.id.layout_frame_main, fragment, "FRAG_JOKE").commit();

                fm.beginTransaction().hide(MainActivity.fragActive).show(fragment).commit();
                MainActivity.fragActive = fragment;
            }
        });

        return layoutMain;
    }       //end onCreateView()


    public void initViews(){
        toolbar = layoutMain.findViewById(R.id.layout_toolbar_saved);
        toolbar.setTitle("Saved Jokes");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        recyclerView = layoutMain.findViewById(R.id.layout_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

    }       //end initViews()


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_saved, menu);
        super.onCreateOptionsMenu(menu, inflater);
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


    public final Fragment newFragJoke(Joke joke){
        Fragment fragment = new FragSavedJoke();
        Bundle bundle = new Bundle();
        bundle.putSerializable("A_JOKE", joke);
        fragment.setArguments(bundle);

        return fragment;
    }

}       //end class
