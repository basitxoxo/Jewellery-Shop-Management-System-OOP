package com.mycompany.jewelleryshop;
import java.io.*;

class Payment {
    private String paymentId;
    private String orderId;
    private double amount;
    private String method;
    private String paymentStatus;

    public Payment(String paymentId, String orderId, double amount, String method)
    {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
        this.paymentStatus = "Will pay by " + method;
    }

    public void savePaymentToFile() {
        try {
            FileWriter fw = new FileWriter("Payment.txt", true);
            fw.write(
                "PaymentID: " + paymentId + ", " + "OrderID: " + orderId + ", " +
                "Amount: " + amount + "PKR, " + "Method: " + method + ", " + "Status: "
                        + paymentStatus + "\n");
            fw.close();
        } 
        catch(IOException e) {
            System.out.println("Error saving payment!");
        }
    }
}