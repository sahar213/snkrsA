package com.sahar.snkrsa.model;

import java.util.ArrayList;

public class Cart {

    ArrayList<ItemCart>itemCarts;

    public Cart(ArrayList<ItemCart> itemCarts) {
        this.itemCarts = itemCarts;
    }

    public Cart() {
    }

    public ArrayList<ItemCart> getItemCarts() {
        return itemCarts;
    }

    public void setItemCarts(ArrayList<ItemCart> itemCarts) {
        this.itemCarts = itemCarts;
    }



    @Override
    public String toString() {
        return "Cart{" +
                "itemCarts=" + itemCarts +
                '}';
    }
}
