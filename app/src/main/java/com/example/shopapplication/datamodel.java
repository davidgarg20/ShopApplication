package com.example.shopapplication;

public class datamodel {
    private int itemid;
    private int price;
    private int distprice;
    private String itemname;
    private  String imgeresource;

    public datamodel(int itemid, int price, int distprice, String itemname, String imgeresource) {
        this.itemid = itemid;
        this.price = price;
        this.distprice = distprice;
        this.itemname = itemname;
        this.imgeresource = imgeresource;
    }

    public int getItemid() {
        return itemid;
    }

    public int getPrice() {
        return price;
    }

    public int getDistprice() {
        return distprice;
    }

    public String getIntname() {
        return itemname;
    }

    public String getImgeresource() {
        return imgeresource;
    }
}
