//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.util.Date;

class Pants extends ClothingItem {
    private String fit;

    public Pants(int id, String model, double price, Date releaseDate, String fit) {
        super(id, "Pants", model, price, releaseDate);
        this.fit = fit;
    }

    public String getFit() {
        return this.fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }

    public String toString() {
        String var10000 = super.toString();
        return var10000 + ", Fit: " + this.fit;
    }
}
