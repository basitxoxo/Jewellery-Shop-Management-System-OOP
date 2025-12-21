package com.mycompany.jewelleryshop;
import java.io.*;

class Order {
    private String orderId;
    private String customerName;
    private double totalAmount;
    private String status;
    private double discount;

    public Order(String orderId, String customerName, double totalAmount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.status = "Pending";
        this.discount = 0;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getFinalAmount() {
        return totalAmount - discount;
    }

    public void saveOrderToFile() {
        try {
            FileWriter fw = new FileWriter("Order.txt", true);
            fw.write(
                "OrderID: " + orderId + ", " + "Customer: " + customerName + ", " +
                "Total: " + totalAmount + ", " + "Discount: " + discount + ", " + 
                        "Status: " + status + "\n"
            );
            fw.close();
        }
        catch(IOException e) {
            System.out.println("Error saving order!");
        }
    }
}