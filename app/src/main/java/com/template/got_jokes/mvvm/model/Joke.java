package com.template.got_jokes.mvvm.model;

public class Joke {

    private int id;
    private String category;
    private String type;


    public Joke(int id, String category, String type) {
        this.id = id;
        this.category = category;
        this.type = type;
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

    @Override
    public String toString() {
        return "Joke{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
