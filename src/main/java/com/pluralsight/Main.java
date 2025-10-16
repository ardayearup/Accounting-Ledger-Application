package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Ledger ledger = new Ledger();
        Scanner scanner = new Scanner(System.in);
        ledger.loadTransactions();

        boolean running = true;

        while (running) {
            System.out.println("\n------------------------------>PENNYPILOT<-------------------------------------");
            System.out.println("--------------->Welcome to your Personal Financial Advisor<--------------------");
            System.out.println("Please select an option: ");
            System.out.println("Press 'D' to Add Deposit");
            System.out.println("Press 'P' to Make Payment");
            System.out.println("Press 'L' to View Ledger");
            System.out.println("Press 'X' to Exit");
            String input = scanner.nextLine().trim().toUpperCase();

            switch (input) {
                case "D":
                    addTransaction(scanner, ledger, true);
                    break;
                case "P":
                    addTransaction(scanner, ledger, false);
                    break;
                case "L":
                    new SubMenu(ledger).show();
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select options displayed on the menu");
            }
        }

        System.out.println("Exiting PennyPilot. Thank you!");
        scanner.close();
    }

    private static void addTransaction(Scanner scanner, Ledger ledger, boolean isDeposit) {
        System.out.print("Please Enter a Description: ");
        String description = scanner.nextLine().trim();
        System.out.print("Please Enter a Vendor: ");
        String vendor = scanner.nextLine().trim();
        System.out.print("Please Enter an Amount: ");
        double amount = Double.parseDouble(scanner.nextLine().trim());

        if (!isDeposit) amount = -Math.abs(amount);

        Transaction t = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
        ledger.saveTransaction(t);
        ledger.loadTransactions();
        System.out.println("Transaction saved.");
    }
}