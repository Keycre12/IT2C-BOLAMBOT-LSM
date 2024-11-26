
package it2c.bolambot.lsm;

import java.util.Scanner;


public class Books {    
  public void bDetails(){
      Scanner sc = new Scanner(System.in);
      String response;
      
      do{
      
      System.out.println("\n--------------------------");
      System.out.println("BOOOKS PANEL               |");
      System.out.println("1. ADD BOOK                |");
      System.out.println("2. VIEW BOOK               |");
      System.out.println("3. UPDATE BOOK             |");
      System.out.println("4. DELETE BOOK             |");
      System.out.println("5. EXIT                    |");
      System.out.println("\n--------------------------");
      
      System.out.print("Enter Selection: ");
      int action= sc.nextInt();
      Books bk = new Books();
      
      while (action < 1 || action > 5) {
                System.out.print("Invalid selection, Try Again: ");
                action = sc.nextInt();
             }
      switch (action){
          
          case 1:
              bk.addBook();
              bk.viewBook();
              
              break;
          
          case 2:
              bk.viewBook();
              
              break;
          
          case 3:
              bk.viewBook();
              bk.updateBook();
              bk.viewBook();
              
              break;
              
          case 4:
              bk.viewBook();
              bk.DeleteBook();
              bk.viewBook();
              
              break;
          
          case 5:
              
              break;
              

      }
      System.out.print("Do you want to continue?(yes/no): ");
      response = sc.next();
      
  }   while(response.equalsIgnoreCase("yes"));
}
   public void addBook() {
    Scanner sc = new Scanner(System.in);
     config conf = new config();

    System.out.print("How many books do you want to add? ");
    while (!sc.hasNextInt()) {
        System.out.print("Please enter a valid number: ");
        sc.next();
    }

    int numberOfBooks = sc.nextInt();
    sc.nextLine();

    for (int i = 0; i < numberOfBooks; i++) {
        System.out.println("Enter details for Book " + (i + 1));
         

        String b_isbn;
        do {
            System.out.print("Book ISBN (10 digits): ");
            b_isbn = sc.next();
            if (b_isbn.isEmpty() || !b_isbn.matches("[0-9Xx]{10}")) {
                System.out.println("Invalid ISBN, Please enter(10 digits): ");
        }
            } while (b_isbn.isEmpty() || !b_isbn.matches("[0-9Xx]{10}"));
        
        sc.nextLine(); 
        String b_title;
        do {
            System.out.print("Book Title: ");
            b_title = sc.nextLine();

          
            if (b_title.isEmpty()) {
                System.out.println("Title cannot be empty.");
            } else if (b_title.matches(".*\\d.*")) {
                System.out.println("Invalid Title. Please try again.");
                b_title = "";
            }
        } while (b_title.isEmpty());
        

        String b_author;
        do {
            System.out.print("Book Author: ");
            b_author = sc.nextLine();

            
            if (b_author.isEmpty()) {
                System.out.println("Author name cannot be empty.");
            } else if (b_author.matches(".*\\d.*")) {
                System.out.println("Author name cannot contain numbers. Please try again.");
                b_author = "";
            }
        } while (b_author.isEmpty());
        
        System.out.println("Available Categories:");
        String qry = "SELECT c_id, c_name FROM Category";
        conf.viewRecords(qry, new String[]{"Category ID", "Category Name"}, new String[]{"c_id", "c_name"});

        System.out.print("Select category ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        while(conf.getSingleValue("SELECT c_id FROM Category WHERE c_id=?  ",id)==0){
              System.out.print("Selected ID doesn't exist");
              System.out.print("Select Book ID Again: ");
              id = sc.nextInt();
        }
        String b_publisher;
        do {
            System.out.print("Book Publisher: ");
            b_publisher = sc.nextLine();

          
            if (b_publisher.isEmpty()) {
                System.out.println("Publisher cannot be empty.");
            } else if (b_publisher.matches(".*\\d.*")) {
                System.out.println("Publisher cannot contain numbers. Please try again.");
                b_publisher = "";
            }
        } while (b_publisher.isEmpty());
    
        int b_publicationyr;
        while (true) {
            System.out.print("Book Year of Publish: ");
            if (sc.hasNextInt()) {
                b_publicationyr = sc.nextInt();
                if (b_publicationyr > 0 && b_publicationyr <= java.time.Year.now().getValue()) {
                    break;
                } else {
                    System.out.println("Please enter a valid year.");
            }
        } else {
            System.out.println("Invalid input. Please enter a numeric year.");
            sc.next(); 
        }
    }
         

        String sql = "INSERT INTO BookInfo (b_isbn, b_title, b_author, c_id, b_publisher, b_publicationyr) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addRecord(sql, b_isbn, b_title, b_author, id, b_publisher, b_publicationyr);

        System.out.println("Book " + (i + 1) + " added successfully!");
    }

    System.out.println("All books have been added.");
}
    public void viewBook(){
        String qry = "SELECT b_id, b_isbn, b_title, b_author, c_name, b_publisher, b_publicationyr FROM BookInfo "
                + "LEFT JOIN Category ON Category.c_id = BookInfo.c_id";
        String[] hrds = {"ID", "ISBN", "Title", "Author", "Category", "Publisher", "Publication Year"};
        String[] clmns = {"b_id", "b_isbn", "b_title", "b_author", "c_name", "b_publisher", "b_publicationyr"};


    config conf = new   config();
    conf.viewRecords(qry, hrds, clmns);
}

    
    public void updateBook(){
        
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.println("Enter Book ID: ");
        int id = sc.nextInt();
            
        while(conf.getSingleValue("SELECT b_id FROM BookInfo WHERE b_id=?  ",id)==0){
              System.out.print("Selected ID doesn't exist");
              System.out.print("Select Book ID Again: ");
              id=sc.nextInt();
        }
        sc.nextLine();
        String b_isbn;
        do {
            System.out.print("Book ISBN (10 digits): ");
            b_isbn = sc.nextLine();
            if (b_isbn.isEmpty() || !b_isbn.matches("[0-9Xx]{10}")) {
                System.out.println("Invalid ISBN, Please enter(10 digits): ");
        }
            } while (b_isbn.isEmpty() || !b_isbn.matches("[0-9Xx]{10}"));
        

        String b_title;
        do {
            System.out.print("Book Title: ");
            b_title = sc.nextLine();
            if (b_title.isEmpty()) {
                System.out.println("Title cannot be empty.");
            }
        } while (b_title.isEmpty());

        String b_author;
        do {
            System.out.print("Book Author: ");
            b_author = sc.nextLine();
            if (b_author.isEmpty()) {
                System.out.println("Author cannot be empty.");
            }
        } while (b_author.isEmpty());

   
        System.out.println("Available Categories:");
        String qry = "SELECT c_id, c_name FROM Category";
        conf.viewRecords(qry, new String[]{"Category ID", "Category Name"}, new String[]{"c_id", "c_name"});

        System.out.print("Select category ID: ");
        int categoryId = sc.nextInt(); 
        sc.nextLine();

        while(conf.getSingleValue("SELECT c_id FROM Category WHERE c_id=?  ", categoryId)==0){
              System.out.print("Selected ID doesn't exist");
              System.out.print("Select Book ID Again: ");
              categoryId = sc.nextInt();
        }
        String b_publisher;
        do {
            System.out.print("Book Publisher: ");
            b_publisher = sc.nextLine();
            if (b_publisher.isEmpty()) {
                System.out.println("Publisher cannot be empty.");
            }
        } while (b_publisher.isEmpty());

        int b_publicationyr;
        while (true) {
            System.out.print("Book Year of Publish: ");
            if (sc.hasNextInt()) {
                b_publicationyr = sc.nextInt();
                if (b_publicationyr > 0 && b_publicationyr <= java.time.Year.now().getValue()) {
                    break;
                } else {
                    System.out.println("Please enter a valid year.");
                    sc.next();
                }
                
            }
        }
        sc.nextLine();  
         
         String uqry = "UPDATE BookInfo SET b_isbn = ?, b_title = ?, b_author = ?, c_id = ?, b_publisher = ?, b_publicationyr = ? WHERE b_id = ?";
         conf.updateRecord(uqry, b_isbn, b_title, b_author,  categoryId, b_publisher, b_publicationyr, id);
             
     }
    
    public void DeleteBook(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Book ID to delete: ");
        int id = sc.nextInt();
        config conf = new config();
        
        while(conf.getSingleValue("SELECT b_id FROM BookInfo WHERE b_id=?  ",id)==0){
              System.out.println("Selected ID doesn't exist");
              System.out.print("Select Book ID Again: ");
              id=sc.nextInt();
        }
         
        
         String sqlDelete = "DELETE FROM BookInfo WHERE b_id = ?";
         conf.deleteRecord(sqlDelete, id);
       
      }
}


