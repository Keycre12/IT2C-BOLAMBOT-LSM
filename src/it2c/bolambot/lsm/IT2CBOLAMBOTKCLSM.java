
package it2c.bolambot.lsm;

import java.util.Scanner;


public class IT2CBOLAMBOTKCLSM {

   
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = true;
       
        do{
            System.out.println("WELCOME TO LIBRARY INVENTORY");
            System.out.println("1. CATEGORY ");
            System.out.println("2. BOOKS ");
            System.out.println("3. INVENTORY ");
            System.out.println("4. REPORT ");
            System.out.println("5. EXIT ");

            
            System.out.print("Enter Action: ");
              while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number (1-5): ");
                sc.next();
            }
            
            int action = sc.nextInt();
            IT2CBOLAMBOTKCLSM book = new IT2CBOLAMBOTKCLSM(); 
            
              while (action < 1 || action > 5) {
                System.out.print("Invalid selection, Try Again: ");
                action = sc.nextInt();
             }
            switch (action){
                case 1:
                    Category cat = new Category();
                    cat.bCateg();
                    break;  
                case 2: 
                    Books bk = new Books();
                    bk.bDetails();
                    
                    break;
                case 3:
                    Inventory ss = new Inventory();
                    ss.cDetails();
                    
                    break;
                case 4:
                    Reports rp = new Reports ();
                    rp.genReport();
                    
                    break;
                case 5:
                    System.out.println("Exit Selected...type 'yes' to continue:" );
                    String resp = sc.next();
                    if(resp.equalsIgnoreCase("yes")){
                        exit = false;
                    }
                    break;
                    
                    
                    
            }
            
            

        }while(exit);

    
    }
    
}
