package com.template.got_jokes.mvvm.model;

public class Joke {

    private int id;
    private String category;
    private String joke;


    public Joke(int id, String category, String joke) {
        this.id = id;
        this.category = category;
        this.joke = joke;
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

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    @Override
    public String toString() {
        String message = String.format("ID: %d\nCategory: %s\nJoke: %s\n", id, category, joke);
        return message;
    }

}
