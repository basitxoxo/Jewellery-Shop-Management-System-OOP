package com.mycompany.jewelleryshop;
import java.io.*;

class CustomProduct
{
    private String productName;
    private String description;
    private String customerName;
    private String status;

    public CustomProduct(String productName, String description, String customerName) {
        this.productName = productName;
        this.description = description;
        this.customerName = customerName;
        this.status = "Pending";
    }
    
    public String getCustomerName() {
        return customerName; 
    }
    public String getProductName() {
        return productName; 
    }
    public String getDescription() { 
        return description;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) { 
        this.status = status; 
    }

    public void saveRequestToFile() {
        try {
            FileWriter fw = new FileWriter("CustomRequest.txt", true);
            fw.write(
                "Customer: " + customerName + ", " +
                "Product: " + productName + ", " +
                "Description: " + description + ", " +
                "Status: " + status + "\n"
            );
            fw.close();
        } 
        catch(IOException e) {
            System.out.println("Error saving custom request!");
        }
    }
}