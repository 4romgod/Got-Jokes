package com.fromgod.got_jokes.mvvm.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "jokes_table")
public class Joke {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String category;
    private String type;
    private String joke;
    private String setup;
    private String delivery;
    private Boolean error;


    public Joke(String category, String type, String joke, String setup, String delivery, Boolean error) {
        this.category = category;
        this.type = type;
        this.joke = joke;
        this.setup = setup;
        this.delivery = delivery;
        this.error = error;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", joke='" + joke + '\'' +
                ", setup='" + setup + '\'' +
                ", delivery='" + delivery + '\'' +
                '}';
    }

}
