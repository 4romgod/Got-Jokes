package com.template.got_jokes.mvvm.model;

public class Joke1P extends Joke {

    String joke;

    public Joke1P(int id, String category, String type, String joke) {
        super(id, category, type);

        this.joke = joke;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }


    @Override
    public String toString() {
        return "Joke1P{" +
                "joke='" + joke + '\'' +
                '}';
    }

}
