📌 Project Overview

The Jewellery Shop Management System is a Java-based Object-Oriented Programming (OOP) project designed to manage jewellery shop operations efficiently.
It supports Admins and Customers, providing features such as product management, order processing, payments, customization requests, and sales reporting.

This project follows core OOP principles including Abstraction, Inheritance, Encapsulation, and Polymorphism.

🛠️ Technologies Used

• Java

• Object-Oriented Programming (OOP)

• File Handling (for data storage)

• Git & GitHub

🧩 OOP Concepts Used

• Abstraction → Person (abstract class)

• Inheritance → Admin and Customer extend Person

• Encapsulation → Private data members with getters/setters

• Polymorphism → Overridden methods like menu() and viewAllProducts()

📂 System Structure (Class Overview)

🔹 Abstract Class

• Person

Common attributes: username, password

🔹 Abstract methods:

• menu()

• viewAllProducts()

🔹 Main Class

Jewelleryshop

• Contains the main() method

• Entry point of the application

🔹 Admin Module

• Admin (extends Person)

Responsibilities:

• Authentication

• Product Management

• Admin Management

• Order Management

• Customization Requests

• Sales Reports

Key Features:

• Add / remove / update products

• Manage stock and prices

• View and manage orders

• Apply discounts and generate invoices

• Handle customization requests

• Calculate total sales

🔹 Customer Module

• Customer (extends Person)

Features:

• View products

• Place orders

• View personal orders

• Make customization requests

• Make payments

🔹 Product Management

• Product

Attributes: ID, name, category, price, quantity

Features:

• Display product details

• Update price & stock

• Save/load product data using files

🔹 Order Management

• Order

• Tracks customer orders

• Supports discounts

• Calculates final amount

• Saves order details to file

🔹 Payment System

• Payment

• Handles order payments

• Stores payment method and status

• Saves payment data to file

🔹 Customization Requests

• CustomProduct

• Allows customers to request custom jewellery

• Admin can review and process requests

• Status tracking with file storage

🔗 Class Relationships

Admin and Customer inherit from Person

Admin manages:

• Products

• Orders

• Custom Products

Customer:

• Views products

• Places orders

• Makes payments

• Requests customization

Order is associated with Payment

📁 Data Storage

Uses file handling for:

• Products

• Orders

• Payments

• Admin credentials

• Customization requests

🚀 To Run the Project

• Clone the repository:

  git clone https://github.com/basitxoxo/Jewellery-Shop-Management-System-OOP.git

• Open the project in any Java IDE (IntelliJ / Eclipse / NetBeans)

• Run the Jewelleryshop.java file

👤 Author

  | Abdul Basit |

📌 OOP Project – Jewellery Shop Management System
