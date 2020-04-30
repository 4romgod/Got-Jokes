package com.template.got_jokes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.template.got_jokes.mvvm.model.Joke;
import com.template.got_jokes.R;

import java.util.List;

public class AdapterRecyclerViewJokes extends RecyclerView.Adapter<AdapterRecyclerViewJokes.MyViewHolder> {

    Context context;
    List<Joke> jokes;

    public AdapterRecyclerViewJokes(Context context, List<Joke> jokes) {
        this.context = context;
        this.jokes = jokes;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_joke, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Joke joke = jokes.get(position);

        holder.tvCategory.setText(joke.getCategory());
        holder.tvJoke.setText(joke.getJoke());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = holder.getAdapterPosition();
                Toast.makeText(context, "Clicked item number: "+index, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return jokes.size();
    }


    //VIEW HOLDER INNER CLASS
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvCategory, tvJoke;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategory = itemView.findViewById(R.id.text_category);
            tvJoke = itemView.findViewById(R.id.text_joke);
        }


    }       //end innerClass


}       //end class
