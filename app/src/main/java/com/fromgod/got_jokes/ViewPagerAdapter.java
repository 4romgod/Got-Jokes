package com.fromgod.got_jokes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.fromgod.got_jokes.mvvm.model.Joke;

import java.util.ArrayList;


public class ViewPagerAdapter extends PagerAdapter {

    LayoutInflater inflater;

    private Context context;
    private ArrayList<Joke> listJokes = new ArrayList<>();


    //public int[] listColors = {};


    public ViewPagerAdapter(Context context) {
        this.context = context;
        listJokes.add(new Joke("Programming", "single", "some funny things that i feel like sharing with the world", "", "", false));
    }

    @Override
    public int getCount() {
        return listJokes.size();
    }


    public void addJoke(Joke joke) {
        listJokes.add(joke);
        notifyDataSetChanged();
    }

    public ArrayList<Joke> getListJokes() {
        return listJokes;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.layout_item_joke, container, false);

        LinearLayout layoutItem = itemView.findViewById(R.id.layout_item);

        TextView textCategory = (TextView) itemView.findViewById(R.id.text_category);
        TextView textSetup = (TextView) itemView.findViewById(R.id.text_setup);
        TextView textDelivery = (TextView) itemView.findViewById(R.id.text_delivery);

        //layoutItem.setBackgroundColor(listColors[position]);

        if (listJokes.get(position).getError() == true) {
            textCategory.setText("No matching joke found...");
            textSetup.setText("");
            textDelivery.setText("");
        }
        else {

            if (listJokes.get(position).getType().equalsIgnoreCase(context.getString(R.string.single))) {
                textCategory.setText(listJokes.get(position).getCategory());
                textSetup.setText(listJokes.get(position).getJoke());
            }
            else if (listJokes.get(position).getType().equalsIgnoreCase(context.getString(R.string.twopart))) {
                textCategory.setText(listJokes.get(position).getCategory());
                textSetup.setText(listJokes.get(position).getSetup());
                textDelivery.setText(listJokes.get(position).getDelivery());
            }

        }

        container.addView(itemView);        // important line

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }


}       //end class
