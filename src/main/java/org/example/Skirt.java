//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.util.Date;

class Skirt extends ClothingItem {
    private String length;

    public Skirt(int id, String model, double price, Date releaseDate, String length) {
        super(id, "Skirt", model, price, releaseDate);
        this.length = length;
    }

    public String getLength() {
        return this.length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String toString() {
        String var10000 = super.toString();
        return var10000 + ", Length: " + this.length;
    }
}
