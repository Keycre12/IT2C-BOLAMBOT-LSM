
package it2c.bolambot.lsm;

import java.util.Scanner;


public class Reports {
    
    public void genReport() {
      Scanner sc = new Scanner(System.in);

      
      while(true){
      
            System.out.println("============================");
            System.out.println("        REPORTS PANEL      ║");
            System.out.println("============================");
            System.out.println("                           ║");
            System.out.println("1. VIEW ALL BOOKS          ║");
            System.out.println("2. INVENTORY SUMMARY       ║");
            System.out.println("3. VIEW BOOKS BY CATEGORY  ║");
            System.out.println("4. BACK                    ║");
            System.out.println("                           ║");
            System.out.println("============================");
            
      System.out.print("Enter Selection: ");
      int action= sc.nextInt();
      Reports rp = new Reports ();
      
      while (action < 1 || action > 4) {
                System.out.print("Invalid selection, Try Again: ");
                action = sc.nextInt();
             }
      switch (action){
          
          case 1:
               rp.viewAllBooks();
              
              break;
          
          case 2:
             rp.inventorySummary();

              
              break;
          
          case 3:
             rp.booksByCategory();
              
              break;
          
          case 4:
                    System.out.println("Returning to Main Menu...");
                    return; 
                default:
                    System.out.println("Invalid selection. Try again.");
            }
        }
      }
    private void viewAllBooks() {
        
        String qry = "SELECT b_id, b_isbn, b_title, b_author, c_name, b_publisher, b_publicationyr FROM BookInfo "
                + "LEFT JOIN Category ON Category.c_id = BookInfo.c_id";
        String[] hrds = {"ID", "ISBN", "Title", "Author", "Category", "Publisher", "Publication Year"};
        String[] clmns = {"b_id", "b_isbn", "b_title", "b_author", "c_name", "b_publisher", "b_publicationyr"};


        config conf = new   config();
        conf.viewRecords(qry, hrds, clmns);
    }

  private void inventorySummary() {
    String qry = "SELECT BookInfo.b_id,  b_title, SUM(Inventory.quantity) AS total_quantity " +
                 "FROM Inventory " +
                 "LEFT JOIN BookInfo ON BookInfo.b_id = Inventory.b_id " +
                 "WHERE Inventory.t_type = 'restock' " +
                 "GROUP BY BookInfo.b_id, BookInfo.b_isbn, BookInfo.b_title";
    String[] headers = {"ID", "Title", "Total Quantity"};
    String[] columns = {"b_id", "b_title", "total_quantity"};
    
    config conf = new config();
    conf.viewRecords(qry, headers, columns);
}



public void booksByCategory() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    System.out.println("Available Categories:");
    String qryCategories = "SELECT c_id, c_name FROM Category";
    conf.viewRecords(qryCategories, new String[]{"Category ID", "Category Name"}, new String[]{"c_id", "c_name"});

    System.out.print("Enter Category ID to view books: ");
    int categoryId = sc.nextInt();

    while (conf.getSingleValue("SELECT c_id FROM Category WHERE c_id = ?", categoryId) == 0) {
        System.out.println("Invalid Category ID. Please try again.");
        System.out.print("Enter Category ID to view books: ");
        categoryId = sc.nextInt();
    }
    
     String countSql = "SELECT COUNT(*) FROM BookInfo WHERE c_id = ?";
    int bookCount = (int) conf.getSingleValue(countSql, categoryId);

    if (bookCount == 0) {
        System.out.println("No books found under this category.");
    } else {

    String qryBooks = "SELECT b.b_id, b.b_isbn, b.b_title, b.b_author, b.b_publisher, b.b_publicationyr " +
                      "FROM BookInfo b " +
                      "WHERE b.c_id = ?";
    String[] headers = {"Book ID", "ISBN", "Title", "Author", "Publisher", "Publication Year"};
    String[] columns = {"b_id", "b_isbn", "b_title", "b_author", "b_publisher", "b_publicationyr"};

    System.out.println("Books under the selected category:");
    conf.viewRecords(qryBooks, headers, columns, categoryId);
    
    }
}

}