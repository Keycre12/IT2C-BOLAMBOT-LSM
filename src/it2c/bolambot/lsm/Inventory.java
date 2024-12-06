
package it2c.bolambot.lsm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Inventory {
 
        public void cDetails() {
        Scanner sc = new Scanner(System.in);
        String response;
        
      while(true) {
            System.out.println("============================");
            System.out.println("      INVENTORY PANEL     ║");
            System.out.println("============================");
            System.out.println("                           ║");
            System.out.println("1. ADD INVENTORY           ║");
            System.out.println("2. VIEW INVENTORY          ║");
            System.out.println("3. UPDATE INVENTORY        ║");
            System.out.println("4. DELETE INVENTORY        ║");
            System.out.println("5. BACK                    ║");
            System.out.println("                           ║");
            System.out.println("============================");
            
            System.out.print("Enter Selection: ");
            int action = sc.nextInt();
            Inventory ss = new Inventory();
            
            
              while (action < 1 || action > 5) {
              System.out.print("Invalid selection, Try Again: ");
              action = sc.nextInt();
             }
      switch (action){
          
          case 1:
               ss.addInventory();
                ss.viewInventory();

              break;
          
          case 2:
               ss.viewInventory();
  
              break;
          
            case 3:
                ss.viewInventory();
                ss.updateInventory();
                ss.viewInventory();
                break;
         

            case 4:
                ss.viewInventory();
                ss.deleteInventory();
                ss.viewInventory();
              break;

            case 5:
                    System.out.println("Returning to Main Menu...");
                    return; 
                default:
                    System.out.println("Invalid selection. Try again.");
            }
        }
      }
    private void addInventory() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        Books bk = new Books();
        bk.viewBook();
        
        System.out.print("Enter the ID of the Book: ");
        int id = sc.nextInt();
        
        String sql = "SELECT b_id FROM BookInfo WHERE b_id=?";
        
        while (conf.getSingleValue(sql, id) == 0) {
            System.out.println("Selected ID doesn't exist.");
            System.out.print("\nSelect Book ID Again: ");
            id = sc.nextInt();
        }
        
         System.out.print("How many Transaction do you want to add? ");
        int numberOfTrans;
        while (true) {
            if (sc.hasNextInt()) {
                numberOfTrans = sc.nextInt();
                if (numberOfTrans > 0) break;
            }
            System.out.print("Invalid number, try again: ");
            sc.next();
        }

        sc.nextLine();

        for (int i = 0; i < numberOfTrans; i++) {
            System.out.println("Enter details for Transaction " + (i + 1));
        
        String t_type;
        while (true) {
            System.out.print("Enter Transaction type (restock, damage, borrowed): ");
            t_type = sc.next();
            if (t_type.equalsIgnoreCase("restock") || t_type.equalsIgnoreCase("damage") || t_type.equalsIgnoreCase("borrowed")) {
                break;
            } else {
                System.out.println("Invalid transaction type. Please try again.");
            }
        }
        
         int quantity = -1; 
        while (true) {
            System.out.print("Enter quantity: ");
            if (sc.hasNextInt()) {
                quantity = sc.nextInt();
                if (quantity > 0) {
                    break;
                } else {
                    System.out.println("Please enter a valid number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.next(); 
            }
        }
        
        LocalDate currdate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = currdate.format(format);

        String qry  = "INSERT INTO Inventory (b_id,  t_type, quantity, date) VALUES (?, ?, ?, ? )";
        conf.addRecord(qry, id, t_type, quantity,  date);
    
}  
    }
    
    public void viewInventory() {
        String qry = "SELECT i_id, b_title, t_type, quantity, date FROM Inventory " +
                     "LEFT JOIN BookInfo ON BookInfo.b_id = Inventory.b_id";
        String[] hrds = {"IID",  "Title", "Transaction Type", "Quantity",  "DATE"};
        String[] clmns = {"i_id", "b_title",  "t_type","quantity", "date"};
        
        config conf = new config();
        conf.viewRecords(qry, hrds, clmns);
    }
    
   
     private void updateInventory(){
         
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Enter IID TO UPDATE: ");
        int i_id = sc.nextInt();
        
        String sql = "SELECT i_id FROM Inventory WHERE i_id = ?";
        while (conf.getSingleValue(sql, i_id) == 0) {

              System.out.print("Selected ID doesn't exist");
              System.out.print("\nSelect Book ID Again: ");
              i_id =sc.nextInt();
        }
  
        int quantity = -1; 
        while (true) {
            System.out.print("Enter quantity: ");
            if (sc.hasNextInt()) {
                quantity = sc.nextInt();
                if (quantity > 0) {
                    break;
                } else {
                    System.out.println("Please enter a valid number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.next(); 
            }
        }

        LocalDate currdate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = currdate.format(format);
        
         String qry = "UPDATE Inventory SET quantity = ?, date = ? WHERE i_id = ?";
         conf.updateRecord(qry, quantity, date, i_id);        
     }
      public void deleteInventory(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("Enter Inventory ID to delete: ");
        int i_id = sc.nextInt();
        
        
        String sql = "SELECT i_id FROM Inventory WHERE i_id = ?";
        while (conf.getSingleValue(sql, i_id) == 0) {

              System.out.print("Selected ID doesn't exist");
              System.out.print("\nSelect Book ID Again: ");
              i_id =sc.nextInt();
        }
        
         String sqlDelete = "DELETE FROM Inventory WHERE i_id = ?";
         
         conf.deleteRecord(sqlDelete, i_id);
       
      }
}


