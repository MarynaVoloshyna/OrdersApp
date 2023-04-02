package org.example;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils implements AutoCloseable{
   private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("OrdersJPA");;
   private static EntityManager entityManager = entityManagerFactory.createEntityManager();;


    public static void addClient(Scanner scanner) {
        System.out.print("Enter client name: ");
        String name = scanner.nextLine();
        System.out.print("Enter client mail: ");
        String email = scanner.nextLine();
        System.out.print("Enter client phone: ");
        String phone = scanner.nextLine();

        entityManager.getTransaction().begin();
        try {
            Client client = new Client(name, email, phone);
            entityManager.persist(client);
            entityManager.getTransaction().commit();
            System.out.println("Client's ID is: " + client.getId());

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println("Can't add client");
        }

    }

    public static void addGood(Scanner scanner) {
        System.out.print("Enter good name: ");
        String name = scanner.nextLine();
        System.out.print("Enter good's price: ");
        String price = scanner.nextLine();
        int gPrice = Integer.parseInt(price);
        entityManager.getTransaction().begin();

        Goods good = new Goods(name, gPrice);
        entityManager.persist(good);
        entityManager.getTransaction().commit();
        System.out.println("Number of your good in table is: " + good.getId());

    }
    public static List<Goods> chooseGoods(Scanner scanner) {
        List<Goods> selectedGoods = new ArrayList<>();
        while (true) {
            System.out.print("Enter good's ID (or press Enter to finish selecting goods): ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                break;
            }
            Long goodId = Long.parseLong(input);
            System.out.print("Enter the quantity of the selected good: ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());

            Goods goods = entityManager.getReference(Goods.class, goodId);
            if (goods != null) {
                goods.setQuantity(quantity);
                selectedGoods.add(goods);
            } else {
                System.out.println("Invalid good ID. Please try again.");
            }
        }
        return selectedGoods;
    }
    public static void createOrder(Scanner scanner) {
        System.out.print("Enter your client ID to create order: ");
        Long clientId = Long.parseLong(scanner.nextLine());
        Client client = entityManager.getReference(Client.class, clientId);
        List<Goods> selectedGoods = chooseGoods(scanner);
        int orderTotalSum = 0;
        for(Goods item: selectedGoods){
            orderTotalSum += item.getPrice();
        }

        entityManager.getTransaction().begin();
        try {
            Orders order = new Orders(client, selectedGoods, orderTotalSum);
            entityManager.persist(order);
            entityManager.getTransaction().commit();
            System.out.println("Thank you for order! Your total sum is: " + order.getTotalSum() + '\n'+
                    "You ordered:" +  order.getListOfGoods() + '\n'
                    + "Order ID: " + order.getId() + '\n'
                    + "Keep it in mind, if you decide to change it!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println("Your order is impossible :(");
        }
    }

    public static void changeOrder(Scanner scanner) {
        System.out.print("Enter your client ID to change order: ");
        Long clientId = Long.parseLong(scanner.nextLine());

        System.out.println("Enter order ID:");
        Long orderId = Long.parseLong(scanner.nextLine());

        System.out.println("Enter good's ID:");
        Long goodId = Long.parseLong(scanner.nextLine());

        System.out.println("Enter new quantity: ");
        int newQuantity = Integer.parseInt(scanner.nextLine());

        Query query = entityManager.createQuery("SELECT g FROM Orders g WHERE g.id =: orderID", Orders.class);
        query.setParameter("orderID", orderId);
        Orders order = null;
        try {
            order = (Orders) query.getSingleResult();
        }catch (NoResultException e) {
            System.out.println("Order with ID " + orderId + " does not exist.");
            return;
        }
        boolean check = false;
        List<Goods> goodsList = order.getListOfGoods();
        for (int i = 0; i < goodsList.size(); i++) {
            if(goodsList.get(i).getId() == goodId){
                goodsList.get(i).setQuantity(newQuantity);
                check = true;
                break;
            }

        }
        if(!check){
            System.out.println("Good with ID " + goodId + " not found in order " + orderId);
            return;
        }
        try {
            entityManager.getTransaction().begin();
            entityManager.getTransaction().commit();
            System.out.println("Thank you for order! Your total sum is: " + order.getTotalSum() + '\n'+
                    "You ordered:" +  goodsList + '\n'
                    + "Order ID: " + order.getId() + '\n'
                    + "Keep it in mind, if you decide to change it!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println("Uuuups! Something went wrong :(");
        }
    }

    public static void viewOrders(Scanner scanner) {
        int result = 0;
        System.out.println("Enter your ID");
        String clientId = scanner.nextLine();
        Query query = entityManager.createQuery("SELECT o FROM Orders o where o.client.id =:clientId ", Client.class);
        query.setParameter("clientId", clientId);

        List<Orders> ordersList = query.getResultList();
        for (int i = 0; i < ordersList.size(); i++) {
            result += ordersList.get(i).getTotalSum();
        }
        for(Orders item: ordersList){
            System.out.println(item);
        }
        System.out.println("Total sum of all your orders: " + result);
    }


    public static void deleteOrder(Scanner scanner){
        System.out.print("Enter order ID: ");
        Long orderId = Long.parseLong(scanner.nextLine());
        Orders order = entityManager.find(Orders.class, orderId);
        if (order == null) {
            System.out.println("Order with ID " + orderId + " does not exist.");
            return;
        }
        entityManager.getTransaction().begin();
        try{
            entityManager.remove(order);
            entityManager.getTransaction().commit();
            System.out.println("Your order was removed successfully");

        }catch (Exception ex){
            entityManager.getTransaction().rollback();
            System.out.println("Uuuups! Something went wrong :(");
        }
    }
    public static void showClients(){
        Query query = entityManager.createQuery("SELECT c FROM Client c", Client.class);
        List<Client>list = (List<Client>) query.getResultList();
        for (Client client:list) {
            System.out.println(client);
        }
    }
    public static void showGoods(){
        Query query = entityManager.createQuery("SELECT g FROM Goods g", Goods.class);
        List<Goods>list = (List<Goods>) query.getResultList();
        for (Goods goods:list) {
            System.out.println(goods);
        }
    }

    @Override
    public void close() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }
}
