package com.fromgod.got_jokes.mvvm.model;

public class Joke {

    private int id;
    private String category;
    private String type;
    private String joke;
    private String setup;
    private String delivery;


    public Joke(int id, String category, String type, String joke) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.joke = joke;
    }

    public Joke(int id, String category, String type, String setup, String delivery) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.setup = setup;
        this.delivery = delivery;
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