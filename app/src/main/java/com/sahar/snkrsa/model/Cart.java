package com.sahar.snkrsa.model;

import java.util.ArrayList;

public class Cart {

    ArrayList<ItemCart>itemCarts;


double total;

    public Cart(ArrayList<ItemCart> itemCarts) {
        this.itemCarts = itemCarts;
    }




    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


    public Cart() {
        itemCarts=new ArrayList<>();


    }

    public  void  addItemToCart(ItemCart itemCart){

     //   if(this.itemCarts==null)
      //      this.itemCarts=new ArrayList<>();


        this.itemCarts.add(itemCart);
    }

    public ArrayList<ItemCart> getItemCarts() {
        return itemCarts;
    }

    public void setItemCarts(ArrayList<ItemCart> itemCarts) {
        this.itemCarts = itemCarts;
    }

    public double getTotalCart(){
        double sum=0;
        for (int i=0; i<this.itemCarts.size();i++){
            sum+=this.itemCarts.get(i).amount*this.itemCarts.get(i).getProduct().getPrice();



        }
        return sum;

    }


    public double getTotall() {
        return total;
    }

    public void setTotall(double totall) {
        this.total = totall;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "itemCarts=" + itemCarts +
                ", total=" + total +
                '}';
    }
}
