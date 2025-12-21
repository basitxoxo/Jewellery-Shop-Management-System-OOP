package com.mycompany.jewelleryshop;
import java.util.*;
import java.io.*;

public class Jewelleryshop 
{
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in); 
        System.out.println("Welcome to Jewellery Shop");
        while(true)
        {
        System.out.println("| \"O\" for Owner |");
        System.out.println("| \"C\" for Customer |");
        System.out.println("| \"E\" for Exit |");
        System.out.print("Enter: ");
        char choice = input.next().charAt(0);
        input.nextLine();
        if (choice == 'O' || choice == 'o')
        {
            //Owner module ayiega
            System.out.println("|| Verification! ||");
            for(int i=1; i<4; i++)
            {
                System.out.print("Enter your Username: ");
                String username = input.nextLine();
                System.out.print("Enter password: ");
                String password = input.nextLine();
                Admin admin = new Admin(username, password);
                admin.usernamefile();
                admin.passwordfile();
                if(admin.authentication())
                {
                    admin.menu();
                    //idhar admin ka menu ayiegaa
                    break;
                }
                if (i==3)
                {
                    System.out.println("Maximum attempts reached!");
                }
            }
        }
        else if (choice == 'C' || choice == 'c')
        {
            //Customer module ayiegaa
            Customer customer = new Customer("", "");
            customer.menu();
        }
        else if (choice == 'E' || choice == 'e')
        {
            System.out.println("Thanks for visiting!");
            return;
        }
        else
        {
            System.out.println("Invalid Choice, Try again");
            //agr ghalat input diya toh
        }
        }
    }
}