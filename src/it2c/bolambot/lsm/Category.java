
package it2c.bolambot.lsm;

import java.util.Scanner;


public class Category {  
  public void bCateg(){
      Scanner sc = new Scanner(System.in);
      
     while (true) {
        System.out.println("============================");
        System.out.println("       CATEGORY PANEL      ║");
        System.out.println("============================");
        System.out.println("                           ║");
        System.out.println("1. ADD CATEGORY            ║");
        System.out.println("2. VIEW CATEGORIES         ║");
        System.out.println("3. UPDATE CATEGORY         ║");
        System.out.println("4. DELETE CATEGORY         ║");
        System.out.println("5. BACK                    ║");
        System.out.println("                           ║");
        System.out.println("============================");
      
      System.out.print("Enter Selection: ");
      int action= sc.nextInt();
      Category cat = new Category();
      
      while (action < 1 || action > 5) {
                System.out.print("Invalid selection, Try Again: ");
                action = sc.nextInt();
             }
      switch (action){
          
          case 1:
              cat.addCategory();
              cat.viewCategory();
              
              break;
          
          case 2:
              cat.viewCategory();
              
              break;
          
          case 3:
              cat.viewCategory();
              cat.updateCategory();
              cat.viewCategory();
              
              break;
              
          case 4:
              cat.viewCategory();
              cat.DeleteCategory();
              cat.viewCategory();
              
              break;
          
          case 5:
                    System.out.println("Returning to Main Menu...");
                    return; 
                default:
                    System.out.println("Invalid selection. Try again.");
            }
        }
      }
   public void addCategory() {
    Scanner sc = new Scanner(System.in);

    System.out.print("How many category do you want to add?: ");
    while (!sc.hasNextInt()) {
        System.out.print("Please enter a valid number: ");
        sc.next();
    }

    int numberOfcateg = sc.nextInt();
    sc.nextLine();

    for (int i = 0; i < numberOfcateg; i++) {
        System.out.println("Enter details for Category " + (i + 1));
         
        String c_name;
        do {
            System.out.print("Enter Category Name: ");
            c_name = sc.nextLine();

            
            if (c_name.isEmpty()) {
                System.out.println("Category can't be empty.");
            } else if (c_name.matches(".*\\d.*")) {
                System.out.println("Category can't contain numbers, Please try again: ");
                c_name= "";
            }
        } while (c_name.isEmpty());

         

        String sql = "INSERT INTO Category (c_name) VALUES (?)";
        config conf = new config();
        conf.addRecord(sql, c_name);
    }
}
    public void viewCategory(){
  
       String qry = "SELECT c_id, c_name FROM Category";
       String[] hrds = {"ID", "CATEGORY"};
       String[] clmns = {"C_id", "c_name"};

       config conf = new config();
       conf.viewRecords(qry, hrds, clmns);
    }
    
    public void updateCategory(){
        
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("Select category ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        while(conf.getSingleValue("SELECT c_id FROM Category WHERE c_id=?  ",id)==0){
              System.out.print("Selected ID doesn't exist");
              System.out.print("\nSelect Category ID Again: ");
              id = sc.nextInt();
        }
        
         String c_name;
        do {
            System.out.print("Enter Category Name: ");
            c_name = sc.nextLine();

            
            if (c_name.isEmpty()) {
                System.out.println("Category cannot be empty.");
            } else if (c_name.matches(".*\\d.*")) {
                System.out.println("Category cannot contain numbers. Please try again.");
                c_name= "";
            }
        } while (c_name.isEmpty());

         String qry = "UPDATE Category SET c_name = ? WHERE c_id = ? ";
         
         conf.updateRecord(qry, c_name, id);
             
     }
    
    public void DeleteCategory(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Category ID to delete: ");
        int id = sc.nextInt();
        config conf = new config();
        
        while(conf.getSingleValue("SELECT c_id FROM Category WHERE c_id=?  ",id)==0){
              System.out.println("Selected ID doesn't exist");
              System.out.print("\nSelect Category ID Again: ");
              id=sc.nextInt();
        }
         
        
         String sqlDelete = "DELETE FROM Category WHERE c_id = ?";
         conf.deleteRecord(sqlDelete, id);
       
      }
}




