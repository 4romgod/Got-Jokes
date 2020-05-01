package com.template.got_jokes.mvvm.model;

public class Joke2P{

    private int id;
    private String category;
    private String setup;
    private String delivery;


    public Joke2P(int id, String category, String setup, String delivery) {
        this.id = id;
        this.category = category;
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

}
