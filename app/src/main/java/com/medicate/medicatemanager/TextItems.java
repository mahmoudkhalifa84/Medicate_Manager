package com.medicate.medicatemanager;

public class TextItems {
    String txt;
    int id;

    public TextItems(String txt, int id) {
        this.id = id;
        this.txt = txt;
    }

    public TextItems(String txt) {
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }

    public int getId() {
        return id;
    }
}
