package org.example;
import java.util.Scanner;
import static org.example.Utils.*;

public class Main {


    public static void main(String[] args) {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("1: add client");
                System.out.println("2: add good");
                System.out.println("3: press to see all products for make order");
                System.out.println("4: press to create order");
                System.out.println("5: press to change order");
                System.out.println("6: press to see all your orders");
                System.out.println("7: press for delete order");
                System.out.println("8: view client");
                System.out.print("-> ");

                String s = scanner.nextLine();
                switch (s) {
                    case "1":
                        addClient(scanner);
                        break;
                    case "2":
                        addGood(scanner);
                        break;
                    case "3":
                        showGoods();
                        break;
                    case "4":
                        createOrder(scanner);
                        break;
                    case "5":
                        changeOrder(scanner);
                        break;
                    case "6":
                        viewOrders(scanner);
                        break;
                    case "7":
                        deleteOrder(scanner);
                        break;
                    case "8":
                        showClients();
                        break;
                    default:
                        return;
                }
            } catch (Exception e){
                e.printStackTrace();
                }
            }
        }
    }




