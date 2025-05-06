package com.sahar.snkrsa.model;

public class ItemCart {

    Product product;
    int amount;





    public ItemCart(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public ItemCart() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }



    @Override
    public String toString() {
        return "ItemCart{" +
                "product=" + product +
                ", amount=" + amount +
                '}';
    }
}
