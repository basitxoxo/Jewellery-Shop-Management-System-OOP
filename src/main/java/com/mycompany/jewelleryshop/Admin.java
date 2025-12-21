package com.mycompany.jewelleryshop;
import java.util.*;
import java.io.*;

class Admin extends Person
{
    Admin(String username, String password)
    {
        super(username, password);
    }
    @Override
    public void menu()
    {
        Scanner sc = new Scanner(System.in);
        int choice;
        do
        {
        System.out.println("\n=== ADMIN PANEL MENU ===");
        System.out.println("1. Product Management");
        System.out.println("2. Order Management");
        System.out.println("3. Customization Requests");
        System.out.println("4. Total revenue");
        System.out.println("5. Admin Settings");
        System.out.println("6. Logout");
        System.out.print("Choose option: ");
        choice = sc.nextInt();
        sc.nextLine();

        switch (choice)
        {
            case 1:
            {
                System.out.println("Product Management");
                productManagementMenu();
                break;
            }
            case 2:
            {
                System.out.println("Order Management");
                orderManagementMenu();
                break;
            }
            case 3:
            {
                System.out.println("Customization Requests");
                customizationRequestsMenu();
                break;
            }
            case 4:
            {
                System.out.println("Total revenue");
                totalSales();
                break;
            }
            case 5:
            {
                System.out.println("Admin Settings selected");
                //admin ki setting sab idhar sy hogi
                adminManagementMenu();
                break;
            }
            case 6:
            {
                System.out.println("Logging out from admin menu");
                return;
            }
            default:
            {
                System.out.println("Invalid choice!");
            }
        }
        } 
        while (choice != 8);
    }
    
  //------------total-sales-ka-method-hai------------------------
    private void totalSales() 
    {
        try 
        {
            File file = new File("Order.txt");
            
            if (!file.exists())
            {
            System.out.println("No orders found!");
            return;
            }

        Scanner sc = new Scanner(file);
        double totalSale = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(",");

            for (String part : parts) {
                if (part.trim().startsWith("Total:")) {
                    double amount = Double.parseDouble(
                            part.split(":")[1].trim()
                    );
                    totalSale += amount;
                }
            }
        }

        sc.close();

        System.out.println("\n=== SALES REPORT ===");
        System.out.println("Total Sales Amount: " + totalSale + " PKR");

    } catch (Exception e) 
    {
        System.out.println("Error generating sales report");
    }
}

  
//-------------------Ya sy product management shuru hai----------------------------------------------------------//    

    private void productManagementMenu() {
        
        Scanner sc = new Scanner(System.in);
        int choice=0;
        do{
        System.out.println("\n=== PRODUCT MANAGEMENT MENU ===");
        System.out.println("1. Add Product");
        System.out.println("2. Remove Product");
        System.out.println("3. Update Stock");
        System.out.println("4. Update Price");
        System.out.println("5. View All Products");
        System.out.println("6. Search Product");
        System.out.println("7. Back to Main Menu");

        System.out.print("Choose option: ");
        choice = sc.nextInt();

        switch (choice) {
            case 1: 
                addProduct();
                break;
            case 2:
                removeProduct(); 
                break;
            case 3: 
                updateStock();
                break;
            case 4: 
                updatePrice();
                break;
            case 5:  
                viewAllProducts(); 
                break;
            case 6: 
                searchProduct();
                break;
            case 7:  
                return;
            default: 
                System.out.println("Invalid choice!");
        }
    }
        while(choice!=7);
    }
    private void addProduct()
    {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter Product ID: ");
            String id = input.nextLine();
            System.out.print("Enter Product Name: ");
            String name = input.nextLine();
            System.out.print("Enter Category: ");
            String category = input.nextLine();
            System.out.print("Enter Price: ");
            double price = input.nextDouble();
            System.out.print("Enter Quantity: ");
            int quantity = input.nextInt();
            input.nextLine(); 

            Product p = new Product(id, name, category, price, quantity);
            p.saveProductToFile();

            System.out.println("Product added successfully!");
        } catch(Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }
    private void removeProduct() 
    {
     try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Product ID to remove: ");
            String removeId = sc.nextLine();

            File inputFile = new File("Product.txt");
            File tempFile = new File("Temp.txt");
            Scanner fileScanner = new Scanner(inputFile);
            FileWriter fw = new FileWriter(tempFile);

            boolean found = false;
            while(fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if(!line.startsWith("ID: " + removeId + ",")) {
                    fw.write(line + "\n");
                } else {
                    found = true;
                }
            }
            fw.close();
            fileScanner.close();

            if(found) {
                inputFile.delete();
                tempFile.renameTo(inputFile);
                System.out.println("Product removed successfully!");
            } else {
                tempFile.delete();
                System.out.println("Product ID not found!");
            }
        } catch(Exception e) {
            System.out.println("Error removing product: " + e.getMessage());
        }
    }
    private void updateStock() {
    try {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Product ID to update stock: ");
        String updateId = sc.nextLine();

        System.out.print("Enter quantity sold: ");
        int soldQty = sc.nextInt();
        sc.nextLine();

        File inputFile = new File("Product.txt");
        File tempFile = new File("Temp.txt");

        Scanner fileScanner = new Scanner(inputFile);
        FileWriter fw = new FileWriter(tempFile);

        boolean found = false;
        boolean enoughStock = true;

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();

            if (line.startsWith("ID: " + updateId + ",")) {
                found = true;

                // Line splitting
                String[] parts = line.split(",");
                int currentQty = Integer.parseInt(parts[4].trim().split(":")[1].trim());

                if (soldQty > currentQty) {
                    System.out.println("Not enough stock!");
                    enoughStock = false;

                    // Write original line back
                    fw.write(line + "\n");
                } else {
                    int newQty = currentQty - soldQty;
                    parts[4] = " Quantity: " + newQty;

                    // Join updated line
                    String newLine = String.join(",", parts);

                    fw.write(newLine + "\n");
                    System.out.println("Stock updated successfully!");
                }

            } else {
                fw.write(line + "\n");
            }
        }

        fileScanner.close();
        fw.close();

        if (found && enoughStock) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } else if (!found) {
            tempFile.delete();
            System.out.println("Product ID not found!");
        }

    } catch (Exception e) {
        System.out.println("Error updating stock: " + e.getMessage());
    }
}
    private void updatePrice() {
    try {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Product ID to update price: ");
        String updateId = sc.nextLine();

        System.out.print("Enter new price: ");
        double newPrice = sc.nextDouble();
        sc.nextLine();

        File inputFile = new File("Product.txt");
        File tempFile = new File("Temp.txt");

        Scanner fileScanner = new Scanner(inputFile);
        FileWriter fw = new FileWriter(tempFile);

        boolean found = false;

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();

            if (line.startsWith("ID: " + updateId + ",")) {
                found = true;

                // splitting
                String[] parts = line.split(",");

                // new price
                parts[3] = " Price: " + newPrice;

                // recreate line
                String updatedLine = parts[0] + "," + parts[1] + "," +
                parts[2] + "," + parts[3] + "," + parts[4];

                fw.write(updatedLine + "\n");

                System.out.println("Price updated successfully!");

            } else {
                fw.write(line + "\n");
            }
        }

        fw.close();
        fileScanner.close();

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } else {
            tempFile.delete();
            System.out.println("Product ID not found!");
        }

    } catch (Exception e) {
        System.out.println("Error updating price: " + e.getMessage());
    }
}
    @Override
    public void viewAllProducts() {
    try {
        File f = new File("Product.txt");
        Scanner sc = new Scanner(f);

        System.out.println("\n======= ALL PRODUCTS =======");

        if (!sc.hasNextLine()) {
            System.out.println("No products found!");
            return;
        }

        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }

        sc.close();
    } catch (Exception e) {
        System.out.println("Error reading file: " + e.getMessage());
    }
}
private void searchProduct() {
    try {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Product ID to search: ");
        String searchId = sc.nextLine();

        File f = new File("Product.txt");
        Scanner fileScanner = new Scanner(f);

        boolean found = false;

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();

            if (line.startsWith("ID: " + searchId + ",")) {
                System.out.println("\nProduct Found:");
                System.out.println(line);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Product not found!");
        }

        fileScanner.close();
    } catch (Exception e) {
        System.out.println("Error searching product: " + e.getMessage());
    }
}
//-------------------Ya tk product management khatmm hai----------------------------------------------------------//    

//-------------------Ya sy admin management shuru hai----------------------------------------------------------//    

private void adminManagementMenu()
{
    Scanner input = new Scanner(System.in);
    int choice;

    do
    {
        System.out.println("\n=== ADMIN MANAGEMENT ===");
        System.out.println("1. Add Admin");
        System.out.println("2. Remove Admin");
        System.out.println("3. View Admins");
        System.out.println("4. Back");
        System.out.print("Choose option: ");

        choice = input.nextInt();
        input.nextLine(); // buffe

        switch (choice)
        {
            case 1:
            {
                addAdmin();
                break;
            }

            case 2:
            {
                removeAdmin();
                break;
            }
            case 3:
            {
                viewAdmins();
                break;
            }
            case 4:
                System.out.println("Returning to main menu...");
                break;

            default:
                System.out.println("Invalid choice!");
        }

    } while (choice != 4);
}

//---------------Remove admin yahan sy shuru hai-----------------------------------------------------
private void removeAdmin() {
    try {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter admin username to remove: ");
        String usernameToRemove = sc.nextLine();

        System.out.print("Enter admin password: ");
        String passwordToRemove = sc.nextLine();

        File userFile = new File("Username.txt");
        File passFile = new File("Password.txt");
        
        File tempUser = new File("TempUsername.txt");
        File tempPass = new File("TempPassword.txt");

        Scanner userScanner = new Scanner(userFile);
        Scanner passScanner = new Scanner(passFile);

        boolean found = false;

        while (userScanner.hasNextLine() && passScanner.hasNextLine()) 
        {
            String u = userScanner.nextLine();
            String p = passScanner.nextLine();

            if (u.equals(usernameToRemove) && p.equals(passwordToRemove))
            {
                found = true; // skip this admin
            } else {
                FileWriter fwUser = new FileWriter(tempUser, true);
                fwUser.write(u + "\n");
                fwUser.close();

                FileWriter fwPass = new FileWriter(tempPass, true);
                fwPass.write(p + "\n");
                fwPass.close();
            }
        }

        userScanner.close();
        passScanner.close();

        if (found) {
            userFile.delete();
            tempUser.renameTo(userFile);

            passFile.delete();
            tempPass.renameTo(passFile);

            System.out.println("Admin removed successfully!");
        } else {
            tempUser.delete();
            tempPass.delete();
            System.out.println("Admin not found!");
        }

    } catch (Exception e) {
        System.out.println("Error removing admin: " + e.getMessage());
    }
}

//sary admins view hongy file sy
private void viewAdmins()
{
    try
    {
        File file = new File("Username.txt");
        Scanner usernamefile = new Scanner(file);

        System.out.println("=== List of Admins ===");

        boolean empty = true;
        
        while (usernamefile.hasNextLine())
        {
            String username = usernamefile.nextLine();
            System.out.println("Username: " + username);
            empty = false;
        }

        if (empty) 
        {
            System.out.println("No admins are found!");
        }

        usernamefile.close();
    }
    catch (IOException e)
    {
        System.out.println("Error " + e.getMessage());
    }
}
//new admin add hogaa
private void addAdmin() 
{
    try {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter new admin username: ");
        String newUsername = input.nextLine();

        Scanner fileScanner = new Scanner(new File("Username.txt"));
        while (fileScanner.hasNextLine())
        {
            if (fileScanner.nextLine().equals(newUsername))
            {
                System.out.println("Username is already in our system");
                fileScanner.close();
                return;
            }
        }
        fileScanner.close();

        System.out.print("Enter new admin password: ");
        String newPassword = input.nextLine();

        FileWriter usernamefile = new FileWriter("Username.txt", true);
        usernamefile.write("\n" + newUsername);
        usernamefile.close();
        
        FileWriter passwordfile = new FileWriter("Password.txt", true);
        passwordfile.write("\n" + newPassword);
        passwordfile.close();
        System.out.println("New Admin Added!");

    }
    catch (IOException e)
    {
        System.out.println("Error adding admin: " + e.getMessage());
    }
}


//-------------------Ya pr admin management khatmm hai----------------------------------------------------------//    

//-------------------Ya sy order management shuru hai----------------------------------------------------------//    
private void orderManagementMenu() {
    Scanner sc = new Scanner(System.in);
    int choice;

    do {
        System.out.println("\n=== ORDER MANAGEMENT ===");
        System.out.println("1. View All Orders");
        System.out.println("2. Confirm Order");
        System.out.println("3. Cancel Order");
        System.out.println("4. Generate Invoice");
        System.out.println("5. Apply Discount");
        System.out.println("6. Back");

        System.out.print("Choose: ");
        choice = sc.nextInt();
        sc.nextLine();

        switch(choice) {
            case 1: viewAllOrders(); break;
            case 2: updateOrderStatus("Confirmed"); break;
            case 3: updateOrderStatus("Cancelled"); break;
            case 4: generateInvoice(); break;
            case 5: applyDiscount(); break;
            case 6: return;
            default: System.out.println("Invalid choice!");
        }
    } while(choice != 6);
}
private void viewAllOrders() {
    try {
        File f = new File("Order.txt");
        Scanner sc = new Scanner(f);

        while(sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
        sc.close();
    } catch(IOException e) {
        System.out.println("No orders found!");
    }
}
private void updateOrderStatus(String newStatus) {
    try {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Order ID: ");
        String id = sc.nextLine();

        File inputFile = new File("Order.txt");
        File tempFile = new File("Temp.txt");

        Scanner fileScanner = new Scanner(inputFile);
        FileWriter fw = new FileWriter(tempFile);

        boolean found = false;

        while(fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();

            if(line.startsWith("OrderID: " + id + ",")) {
                found = true;
                String[] parts = line.split(",");
                parts[4] = " Status: " + newStatus;
                fw.write(String.join(",", parts) + "\n");
            } else {
                fw.write(line + "\n");
            }
        }

        fw.close();
        fileScanner.close();

        if(found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
            System.out.println("Order " + newStatus);
        } else {
            tempFile.delete();
            System.out.println("Order not found!");
        }
    } catch(IOException e) {
        System.out.println("Error updating order!");
    }
}
private void applyDiscount() {
    try {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Order ID: ");
        String id = sc.nextLine();

        System.out.print("Enter discount amount: ");
        double discount = sc.nextDouble();

        File inputFile = new File("Order.txt");
        File tempFile = new File("Temp.txt");

        Scanner fs = new Scanner(inputFile);
        FileWriter fw = new FileWriter(tempFile);

        while(fs.hasNextLine()) {
            String line = fs.nextLine();

            if(line.startsWith("OrderID: " + id + ",")) {
                String[] parts = line.split(",");
                parts[3] = " Discount: " + discount;
                fw.write(String.join(",", parts) + "\n");
            } else {
                fw.write(line + "\n");
            }
        }

        fw.close();
        fs.close();
        inputFile.delete();
        tempFile.renameTo(inputFile);

        System.out.println("Discount applied!");
    } 
    catch(IOException e) {
        System.out.println("Error applying discount!");
    }
}
private void generateInvoice() {
    try {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Order ID: ");
        String id = sc.nextLine();

        File f = new File("Order.txt");
        Scanner fs = new Scanner(f);

        while(fs.hasNextLine()) {
            String line = fs.nextLine();
            if(line.startsWith("OrderID: " + id + ",")) {
                System.out.println("\n=== INVOICE ===");
                System.out.println(line);
                break;
            }
        }
        fs.close();
    } catch(IOException e) {
        System.out.println("Error generating invoice!");
    }
}
    void usernamefile()
    {
        try
        {
            File file = new File("Username.txt");
            if (file.createNewFile())
            {
                System.out.println("Username File is now created!");
            }
        }
        catch(IOException e)
        {
            System.out.println("Error in file: ");
            e.printStackTrace();
        }
    }
    
    void passwordfile()
    {
        try
        {
            File file = new File("Password.txt");
            if (file.createNewFile())
            {
                System.out.println("Password File is now created!");
            }
        }
      catch(IOException e)
        {
            System.out.println("Error in file: ");
            e.printStackTrace();
        }
    }
    
    boolean authentication()
    {
        File usernamefile = new File("Username.txt");
        File passwordfile = new File("Password.txt");
        boolean checkingusername = false;
        boolean checkingpassword = false;
        try
        {
            //username yahan hoga
            Scanner usernameread = new Scanner(usernamefile);
            while(usernameread.hasNextLine())
            {
                String line = usernameread.nextLine();
                if (line.equals(username))
                {
                    checkingusername = true;
                    break;
                }
            }
            usernameread.close();
            
            //password match hoga idhar
            Scanner passwordread = new Scanner(passwordfile);
            while(passwordread.hasNextLine())
            {
                String line = passwordread.nextLine();
                if (line.equals(password))
                {
                    checkingpassword = true;
                    break;
                }
            }
            passwordread.close();
            
            if (checkingusername && checkingpassword)
            {
                System.out.println("Login Successful");
                return true;
            }
            else
            {
                System.out.println("Incorrect Username or Password!");
                return false;
            }
        }
        catch(IOException e)
        {
            System.out.println("Error: ");
            e.printStackTrace();
            return false; // agar file error ho toh fail
        }
    }
//-------------------Ya sy customrequest management shuru hai----------------------------------------------------------//    
    private void customizationRequestsMenu() {
    Scanner sc = new Scanner(System.in);
    int choice;

    do {
        System.out.println("\n=== CUSTOMIZATION REQUESTS MENU ===");
        System.out.println("1. View All Requests");
        System.out.println("2. Approve / Reject Requests");
        System.out.println("3. Back to Main Menu");
        System.out.print("Choose: ");
        choice = sc.nextInt();
        sc.nextLine(); // consume newline

        switch(choice) {
            case 1:
                viewCustomizationRequests();
                break;
            case 2:
                processCustomizationRequests();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    } while(choice != 3);
}
private void viewCustomizationRequests() {
    try {
        File f = new File("CustomRequest.txt");
        Scanner sc = new Scanner(f);
        System.out.println("\n=== CUSTOM REQUESTS ===");
        while(sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
        sc.close();
    } catch(IOException e) {
        System.out.println("No requests found!");
    }
}

private void processCustomizationRequests() {
    try {
        File f = new File("CustomRequest.txt");
        Scanner sc = new Scanner(f);
        ArrayList<CustomProduct> requests = new ArrayList<>();

        // Read existing requests
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(",");
            String customer = parts[0].split(":")[1].trim();
            String product = parts[1].split(":")[1].trim();
            String desc = parts[2].split(":")[1].trim();
            String status = parts[3].split(":")[1].trim();

            CustomProduct cp = new CustomProduct(product, desc, customer);
            cp.setStatus(status);
            requests.add(cp);
        }
        sc.close();

        // Process pending requests
        Scanner input = new Scanner(System.in);
        for(CustomProduct cp : requests) {
            if(cp.getStatus().equals("Pending")) {
                System.out.println("\nCustomer: " + cp.getCustomerName());
                System.out.println("Product: " + cp.getProductName());
                System.out.println("Details: " + cp.getDescription());
                System.out.println("Status: " + cp.getStatus());
                System.out.print("Approve (A) / Reject (R) / Skip (S): ");
                char ch = input.next().charAt(0);
                if(ch == 'A' || ch == 'a') cp.setStatus("Approved");
                else if(ch == 'R' || ch == 'r') cp.setStatus("Rejected");
            }
        }

        // Update file
        FileWriter fw = new FileWriter("CustomRequest.txt", false);
        for(CustomProduct cp : requests) {
            fw.write(
                "Customer: " + cp.getCustomerName() + ", " +
                "Product: " + cp.getProductName() + ", " +
                "Description: " + cp.getDescription() + ", " +
                "Status: " + cp.getStatus() + "\n"
            );
        }
        fw.close();
        System.out.println("All updates saved!");
    } catch(IOException e) {
        System.out.println("Error processing requests!");
    }
}

//-------------------Ya sy customer management shuru hai----------------------------------------------------------//    
private void customerManagementMenu() {
    Scanner sc = new Scanner(System.in);
    int choice;

    do {
        System.out.println("\n=== CUSTOMER MANAGEMENT ===");
        System.out.println("1. View All Customers");
        System.out.println("2. Remove Customer");
        System.out.println("3. Back");
        System.out.print("Choose option: ");
        choice = sc.nextInt();
        sc.nextLine();

        switch(choice)
        {
            case 1:
                viewCustomers();
                break;
            case 2:
                removeCustomer();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    } while(choice != 3);
}
private void viewCustomers() {
    try {
        File f = new File("customers.txt");
        Scanner sc = new Scanner(f);

        System.out.println("\n=== CUSTOMER LIST ===");

        if(!sc.hasNextLine())
        {
            System.out.println("No customers found!");
        }

        while(sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
        sc.close();
    } 
    catch(IOException e) {
        System.out.println("Customer file not found!");
    }
}
private void removeCustomer() {
    try {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Customer Username to remove: ");
        String removeUser = sc.nextLine();

        File inputFile = new File("customers.txt");
        File tempFile = new File("tempCustomer.txt");

        Scanner fileScanner = new Scanner(inputFile);
        FileWriter fw = new FileWriter(tempFile);

        boolean found = false;

        while(fileScanner.hasNextLine())
        {
            String line = fileScanner.nextLine();
            if(!line.startsWith("Username: " + removeUser + ","))
            {
                fw.write(line + "\n");
            } else {
                found = true;
            }
        }

        fw.close();
        fileScanner.close();

        if(found)
        {
            inputFile.delete();
            tempFile.renameTo(inputFile);
            System.out.println("Customer removed successfully!");
        } 
        else
        {
            tempFile.delete();
            System.out.println("Customer not found!");
        }
    } 
    catch(IOException e) 
    {
        System.out.println("Error removing customer!");
    }
}
}