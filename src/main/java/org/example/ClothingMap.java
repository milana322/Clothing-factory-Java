//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.util.HashMap;

public class ClothingMap extends HashMap<Integer, ClothingItem> {
    public ClothingMap() {
    }

    public void addClothingItem(ClothingItem item) {
        this.put(item.getId(), item);
    }

    public ClothingItem findById(int id) {
        return (ClothingItem)this.get(id);
    }

    public ClothingItem deleteClothingItem(int id) {
        return (ClothingItem)this.remove(id);
    }
}
