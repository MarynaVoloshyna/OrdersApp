package org.example;


import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientId", unique = true, nullable = false)

    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "client")
    private List<Orders> clientOrders;

    public List<Orders> getClientOrders() {
        return clientOrders;
    }

    public void setClientOrders(List<Orders> clientOrders) {
        this.clientOrders = clientOrders;
    }

    public Client(String name, String email, String phone) {

        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Client() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }


}