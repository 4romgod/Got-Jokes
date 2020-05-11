package com.fromgod.got_jokes.mvvm.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fromgod.got_jokes.R;
import com.fromgod.got_jokes.mvvm.model.Joke;

import java.util.ArrayList;
import java.util.List;

class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.JokeHolder> {

    private List<Joke> jokes = new ArrayList<>();


    @NonNull
    @Override
    public JokeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_recycler, parent, false);

        return new JokeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JokeHolder holder, int position) {
        Joke currentJoke = jokes.get(position);

        if(currentJoke.getType().equalsIgnoreCase("single")){
            holder.textCategory.setText(currentJoke.getCategory());
            holder.textBody.setText(currentJoke.getJoke());
        }
        else if(currentJoke.getType().equalsIgnoreCase("twopart")){
            holder.textCategory.setText(currentJoke.getCategory());
            holder.textBody.setText(currentJoke.getSetup());
        }
        else {
            holder.textCategory.setText("Something went wrong");
        }

    }       //end onBindViewHolder()

    @Override
    public int getItemCount() {
        return jokes.size();
    }

    public void setJokes(List<Joke> jokes){
        notifyDataSetChanged();
        this.jokes = jokes;
    }



    class JokeHolder extends RecyclerView.ViewHolder{
        private TextView textCategory;
        private TextView textBody;

        public JokeHolder(View itemView){
            super(itemView);
            textCategory = itemView.findViewById(R.id.text_category);
            textBody = itemView.findViewById(R.id.text_body);
        }

    }       //end innerClass


}       //end class
