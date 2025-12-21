package com.mycompany.jewelleryshop;
import java.util.*;
import java.io.*;

class Customer extends Person 
{
    private final Scanner sc = new Scanner(System.in);
    Customer(String username, String password) {
        super(username, password);
    }

    @Override
    public void menu() {
        int choice;
        do {
            System.out.println("\n=== CUSTOMER MENU ===");
            System.out.println("1. View Products");
            System.out.println("2. Place Order");
            System.out.println("3. View My Orders");
            System.out.println("4. Customization Request");
            System.out.println("5. Logout");
            System.out.print("Choose: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: 
                {
                    viewAllProducts(); 
                break;
                }
                case 2:
                {
                    placeOrder();
                    break;
                }
                case 3: 
                {
                   viewMyOrders();
                    break;
                }
                case 4: 
                {
                    customizationRequest();
                    break;
                }
                case 5: 
                    System.out.println("Logging out...");
                break;
                default:
                {
                    System.out.println("Invalid choice!");
                }
            }
        } while (choice != 5);    
    }

    @Override
    public void viewAllProducts() {
    try {
        File f = new File("Product.txt");
        Scanner sc = new Scanner(f);

        System.out.println("\nALL PRODUCTS");

        if (!sc.hasNextLine()) {
            System.out.println("No products found!");
            return;
        }
        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
        sc.close();
    }
    catch (Exception e) {
        System.out.println("Error reading file: " + e.getMessage());
    }
}

private void placeOrder() {
    try {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String customername = sc.nextLine();
        System.out.print("Enter Product ID: ");
        String pid = sc.nextLine();

        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();
        sc.nextLine();
        
        System.out.print("Enter your payment method: ");
        String method = sc.nextLine();

        File inputFile = new File("Product.txt");
        File tempFile = new File("TempProduct.txt");

        Scanner fileScanner = new Scanner(inputFile);
        FileWriter fw = new FileWriter(tempFile);

        boolean found = false;
        double price = 0;
        String productName = "";

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();

            if (line.startsWith("ID: " + pid + ",")) {
                found = true;

                String[] parts = line.split(",");
                productName = parts[1].split(":")[1].trim();
                price = Double.parseDouble(parts[3].split(":")[1].trim());
                int stock = Integer.parseInt(parts[4].split(":")[1].trim());

                if (qty > stock) 
                {
                    System.out.println("Not enough stock!");
                    fw.write(line + "\n");
                } 
                else
                {
                    int newStock = stock - qty;
                    parts[4] = " Quantity: " + newStock;
                    fw.write(String.join(",", parts) + "\n");

                    Random r = new Random(); //1-100 tk random number dygaa
                    int randomNum = r.nextInt(100) + 1;
                    String orderId = "ORD-" + randomNum;
                    double total = price * qty;

                    Order order = new Order(orderId, customername, total);
                    order.saveOrderToFile();

                    Payment payment = new Payment("PAY-" + randomNum, orderId,total, method);
                    payment.savePaymentToFile();

                    System.out.println("\nOrder Placed Successfully!");
                    System.out.println("Order ID: " + orderId);
                    System.out.println("Payment Method: " + method);
                    System.out.println("Total Amount: " + total + " Rupees");
                }

            } 
            else {
                fw.write(line + "\n");
            }
        }
        fileScanner.close();
        fw.close();
        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } 
        else {
            tempFile.delete();
            System.out.println("Product not found!");
        }

    } 
    catch (IOException e) {
        System.out.println("Error placing order: " + e.getMessage());
    }
}

private void viewMyOrders() {
    try {
        System.out.print("Enter your name to view orders: ");
        String cname = sc.nextLine();
        File f = new File("Order.txt");
        Scanner fileScanner = new Scanner(f);
        boolean found = false;
        System.out.println("\n=== MY ORDERS ===");

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();

            if (line.contains("Customer: " + cname)) {
                System.out.println(line);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No orders found for this customer!");
        }
        fileScanner.close();
    }
    catch (IOException e) {
        System.out.println("Order file not found!");
    }
}

private void customizationRequest() {
    try {
        System.out.println("\n=== CUSTOMIZATION REQUEST ===");
        
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String customername = input.nextLine();

        System.out.print("Enter product name: ");
        String productName = input.nextLine();

        System.out.print("Describe your customization: ");
        String description = input.nextLine();
        
        CustomProduct cp = new CustomProduct(productName, description, customername);

        cp.saveRequestToFile();

        System.out.println("Customization request submitted successfully!");
        System.out.println("Status: Pending");

    } 
    catch (Exception e) {
        System.out.println("Error submitting customization request: " + e.getMessage());
    }
}
}