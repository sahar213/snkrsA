package com.sahar.snkrsa.model;

import java.io.Serializable;

public class Product implements Serializable {
    protected  String id;
    private String name;
    private double price;


    String type;
    String size;
    String color;



    private String description;
    private String imageName;  // שם התמונה

    // בוני משתנים ושיטות גישה (getter / setter)


    public Product(String id, String name, double price, String type, String size, String color, String description, String imageName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.size = size;
        this.color = color;
        this.description = description;
        this.imageName = imageName;
    }

    public Product(Product product) {
        this.id = product.id;
        this.name = product.name;
        this.price = product.price;
        this.type = product.type;
        this.size = "";
        this.color = "";
        this.description = product.description;
        this.imageName = product.imageName;
    }


    public Product() {
    }


    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }



    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
