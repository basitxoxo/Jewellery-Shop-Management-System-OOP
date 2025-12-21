package com.mycompany.jewelleryshop;
import java.util.*;
import java.io.*;

abstract class Person
{
    String username;
    String password;
    Person(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
    public abstract void menu();
    public abstract void viewAllProducts();
    /*Person class ko abstract banayienn or phir us mai menu ajayiee
    or customer ki class inheirt hogi person sy
    esyy admin or customer dono mai abstraction a skti hai
    */
}
