package com.medicate.medicatemanager;

public class DataItems {
    String arabic;
    float price;

    public DataItems(int price, String arabic) {
        this.price = price;
        this.arabic = arabic;
    }
    public float getPrice() {
        return price;
    }
    public String getArabic() {
        return arabic;
    }

}
