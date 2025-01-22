//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.util.Date;

public abstract class ClothingItem {
    protected int id;
    protected String type;
    protected String model;
    protected double price;
    protected Date releaseDate;

    public ClothingItem(int id, String type, String model, double price, Date releaseDate) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.price = price;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String toString() {
        String var10000 = this.type;
        return var10000 + " [ID: " + this.id + ", Model: " + this.model + ", Price: " + this.price + ", Release Date: " + String.valueOf(this.releaseDate) + "]";
    }
}
