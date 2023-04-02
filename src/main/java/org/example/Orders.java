package org.example;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity

public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order ID")
    private long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "clientId")
    private Client client = new Client();
    @ManyToMany (cascade = CascadeType.REFRESH)
    @JoinTable(name = "List_Of_Goods",
           joinColumns = @JoinColumn(name = "Order_ID"),
            inverseJoinColumns = @JoinColumn(name = "goodId"))
    private List <Goods> listOfGoods = new ArrayList<>();


    @Column(nullable = false)
    private int orderTotalSum = getOrderTotalSum();
    public Orders(Client client, List<Goods> listOfGoods, int orderTotalSum) {
        this.client = client;
        this.listOfGoods = listOfGoods;
        this.orderTotalSum = orderTotalSum;
    }

    public Orders() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Goods> getListOfGoods() {
        return listOfGoods;
    }

    public void setListOfGoods(List<Goods> listOfGoods) {
        this.listOfGoods = listOfGoods;
    }

    public int getOrderTotalSum() {
        return orderTotalSum;
    }

    public int getTotalSum() {
       int orderTotalSum = 0;
        for(Goods goods: listOfGoods ){
            orderTotalSum += goods.getPrice()* goods.getQuantity();
        }
        return orderTotalSum;
    }

    public int setOrderTotalSum() {
         return getTotalSum();
    }
    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", client=" + client +
                ", listOfGoods=" + listOfGoods +
                ", orderTotalSum=" + setOrderTotalSum() +
                '}';
    }
}
