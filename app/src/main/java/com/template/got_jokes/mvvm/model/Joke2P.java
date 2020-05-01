package com.template.got_jokes.mvvm.model;

public class Joke2P extends Joke{

    private String setup;
    private String delivery;


    public Joke2P(int id, String category, String type, String setup, String delivery) {
        super(id, category, type);

        this.setup = setup;
        this.delivery = delivery;
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
        return "Joke2P{" +
                "setup='" + setup + '\'' +
                ", delivery='" + delivery + '\'' +
                '}';
    }
}
