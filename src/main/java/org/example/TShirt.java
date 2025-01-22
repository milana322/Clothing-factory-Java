//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.util.Date;

class TShirt extends ClothingItem {
    private String size;

    public TShirt(int id, String model, double price, Date releaseDate, String size) {
        super(id, "T-Shirt", model, price, releaseDate);
        this.size = size;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String toString() {
        String var10000 = super.toString();
        return var10000 + ", Size: " + this.size;
    }
}
