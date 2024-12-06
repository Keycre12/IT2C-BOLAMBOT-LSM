package it2c.bolambot.lsm;

import java.util.Scanner;

public class IT2CBOLAMBOTKCLSM {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("================================");
            System.out.println(" WELCOME TO LIBRARY INVENTORY  ║");
            System.out.println("       MANAGEMENT SYSTEM       ║");
            System.out.println("================================");
            System.out.println("                               ║");
            System.out.println("1. CATEGORY                    ║");
            System.out.println("2. BOOKS                       ║");
            System.out.println("3. INVENTORY                   ║");
            System.out.println("4. REPORT                      ║");
            System.out.println("5. EXIT                        ║");
            System.out.println("                               ║");
            System.out.println("================================");

            int action = 0;
            boolean validInput = false;

            while (!validInput) {
                System.out.print("Enter Action: ");
                String input = sc.nextLine();

                try {
                    action = Integer.parseInt(input);
                    if (action >= 1 && action <= 5) {
                        validInput = true;
                    } else {
                        System.out.println("Please enter a number: ");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number between 1-5.");
                }
            }

            switch (action) {
                case 1:
                    Category cat = new Category();
                    cat.bCateg();
                    break;
                case 2:
                    Books bk = new Books();
                    bk.bDetails();
                    break;
                case 3:
                    Inventory inv = new Inventory();
                    inv.cDetails();
                    break;
                case 4:
                    Reports rp = new Reports();
                    rp.genReport();
                    break;
                case 5:
                    System.out.print("Exit Selected...type 'yes' to confirm exit: ");
                    String resp = sc.nextLine();
                    if (resp.equalsIgnoreCase("yes")) {
                        exit = true;
                        System.out.println("Thank you for using the system, Goodbye:)");
                    }
                    break;
       
            }
        }

        sc.close();
    }
}
