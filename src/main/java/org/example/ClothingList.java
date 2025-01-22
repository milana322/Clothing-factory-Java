//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.util.ArrayList;

public class ClothingList extends ArrayList<ClothingItem> {
    public ClothingList() {
    }

    public void addClothingItem(ClothingItem item) {
        this.add(item);
    }

    public boolean deleteClothingItem(int id) {
        return this.removeIf((item) -> {
            return item.getId() == id;
        });
    }
}
