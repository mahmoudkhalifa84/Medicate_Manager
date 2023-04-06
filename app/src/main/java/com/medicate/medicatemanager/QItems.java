package com.medicate.medicatemanager;

public class QItems {
    String name,
    gender,
    age,
    txt,mzmna,
    semp,date;

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getTxt() {
        return txt;
    }

    public String getSemp() {
        return semp;
    }

    public String getDate() {
        return date;
    }

    public String getMzmna() {
        return mzmna;
    }

    public QItems(String name, String gender, String age, String txt, String mzmna, String semp, String date) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.txt = txt;
        this.mzmna = mzmna;
        this.semp = semp;
        this.date = date;
    }
}
