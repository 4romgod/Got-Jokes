package com.fromgod.gotjokes.mvvm.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fromgod.gotjokes.R;
import com.fromgod.gotjokes.mvvm.model.Joke;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.JokeHolder> {

    private List<Joke> jokeList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public JokeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_recycler, parent, false);
        return new JokeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JokeHolder holder, int position) {
        Joke currentJoke = jokeList.get(position);

        if(currentJoke.getType().equalsIgnoreCase("single")) {
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
    }

    @Override
    public int getItemCount() {
        return jokeList.size();
    }

    public void setJokeList(List<Joke> jokeList){
        notifyDataSetChanged();
        this.jokeList = jokeList;
    }

    public Joke getJokeAt(int position){
        return jokeList.get(position);
    }

    public class JokeHolder extends RecyclerView.ViewHolder{
        private TextView textCategory;
        private TextView textBody;

        public JokeHolder(View itemView){
            super(itemView);
            textCategory = itemView.findViewById(R.id.text_category);
            textBody = itemView.findViewById(R.id.text_body);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if ((listener != null) && (position != RecyclerView.NO_POSITION)) {
                        listener.onItemClick(jokeList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Joke joke);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
