package com.mycompany.jewelleryshop;
import java.util.*;
import java.io.*;

class Product
{
    private String productid;
    private String name;
    private String category;
    private double price;
    private int quantity;
    public Product(String productid,String name,String category,double price,int quantity)
    {
       this.productid=productid;
       this.name=name;
       this.category=category;
       this.price=price;
       this.quantity=quantity;
    }
    public String getProductName()
    {
       return this.name;
    }
    public String getProductId()
    {
       return this.productid;
    }
    public String getProductCategory()
    {
       return this.category;
    }
    public double getProductPrice()
    {
       return this.price;
    }
    public int getProductQuantity()
    {
       return this.quantity;
    }
    public void setProductQuantity(int q)
    {
      this.quantity=q;
    }
    public void setProductPrice(double p)
    {
      this.price=p;
    }
    public void displayProduct()
    {
     System.out.println("ID:"+getProductId());
     System.out.println("Name:"+getProductName());
     System.out.println("Category:"+getProductCategory());
     System.out.println("Price:"+getProductPrice());
     System.out.println("Quantity:"+getProductQuantity());
    }
    public void saveProductToFile()
    {
    try{
             FileWriter fw = new FileWriter("Product.txt",true);
            fw.write( "ID: " + getProductId() + ", " + "Name: " + getProductName() + ", " +
    "Category: " + getProductCategory() + ", " + "Price: " + getProductPrice() + ", " +
    "Quantity: " + getProductQuantity() + "\n" );
            
fw.close();
    }
        catch (IOException e)
        {
            System.out.println("Error saving product: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void LoadDataFromFile()
    {
        try
        {
          File f = new File("Product.txt");
          Scanner sc=new Scanner(f);
          while(sc.hasNextLine())
            System.out.println(sc.nextLine());
        }
        catch(IOException e)
        {
          e.printStackTrace();
        }
    }
}