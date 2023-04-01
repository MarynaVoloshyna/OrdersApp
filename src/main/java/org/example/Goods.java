package org.example;

import jakarta.persistence.*;

@Entity
@Table(name = "Goods")

public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="goodId", unique = true, nullable = false)
    private long id;
    @Column(name = "Good's name")
    private String name;
    @Column(name = "Good's price")
    private int price;
    private int quantity = 1;

    public Goods(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Goods() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + this.quantity +
                '}';
    }
}
