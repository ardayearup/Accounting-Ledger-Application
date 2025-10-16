package com.pluralsight;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ReportMenu {
    private List<Transaction> transactions;
    private Scanner scanner;

    public ReportMenu(List<Transaction> transactions) {
        this.transactions = transactions;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        String choice = "";

        while (!choice.equals("0")) {
            System.out.println("\n===REPORTS MENU===");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back");
            System.out.print("Enter choice: ");
            choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    filterByDate(LocalDate.now().withDayOfMonth(1), LocalDate.now());
                    break;
                case "2":
                    LocalDate startLastMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
                    LocalDate endLastMonth = startLastMonth.withDayOfMonth(startLastMonth.lengthOfMonth());
                    filterByDate(startLastMonth, endLastMonth);
                    break;
                case "3":
                    filterByDate(LocalDate.now().withDayOfYear(1), LocalDate.now());
                    break;
                case "4":
                    LocalDate lastYearStart = LocalDate.now().minusYears(1).withDayOfYear(1);
                    LocalDate lastYearEnd = lastYearStart.withDayOfYear(lastYearStart.lengthOfYear());
                    filterByDate(lastYearStart, lastYearEnd);
                    break;
                case "5":
                    searchByVendor();
                    break;
                case "6":
                    customSearch();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Invalid choice. Please select options displayed on the menu");
            }
        }
    }

    private void filterByDate(LocalDate start, LocalDate end) {
        System.out.println("\n----->Transactions from " + start + " to " + end);
        for (Transaction t : transactions) {
            if (!t.getDate().isBefore(start) && !t.getDate().isAfter(end)) {
                System.out.println(t);
            }
        }
    }

    private void searchByVendor() {
        System.out.print("----->Enter vendor name to search: ");
        String vendor = scanner.nextLine().toLowerCase();
        System.out.println("\n----->Transactions for vendor: " + vendor);
        for (Transaction t : transactions) {
            if (t.getVendor().toLowerCase().contains(vendor)) {
                System.out.println(t);
            }
        }
    }

    private void customSearch() {
        System.out.println("\n===DETAILED SEARCH===");
        System.out.println("Please enter at least one input to search: ");
        System.out.print("\"Please enter a Start date (YYYY-MM-DD): ");
        String startInput = scanner.nextLine().trim();
        System.out.print("\"Please enter an End date (YYYY-MM-DD): ");
        String endInput = scanner.nextLine().trim();
        System.out.print("\"Please enter a Description (optional): ");
        String description = scanner.nextLine().trim().toLowerCase();
        System.out.print("\"Please enter a Vendor (optional): ");
        String vendor = scanner.nextLine().trim().toLowerCase();
        System.out.print("\"Please enter an Amount (optional): ");
        String amountInput = scanner.nextLine().trim();

        LocalDate startDate = startInput.isEmpty() ? LocalDate.MIN : LocalDate.parse(startInput);
        LocalDate endDate = endInput.isEmpty() ? LocalDate.MAX : LocalDate.parse(endInput);
        Double amount = amountInput.isEmpty() ? null : Double.parseDouble(amountInput);

        System.out.println("\n===DETAILED SEARCH RESULTS===");

        for (Transaction t : transactions) {
            boolean matches = true;

            if (t.getDate().isBefore(startDate) || t.getDate().isAfter(endDate)) matches = false;
            if (!description.isEmpty() && !t.toString().toLowerCase().contains(description)) matches = false;
            if (!vendor.isEmpty() && !t.getVendor().toLowerCase().contains(vendor)) matches = false;
            if (amount != null && t.getAmount() != amount) matches = false;

            if (matches) {
                System.out.println(t);
            }
        }
    }
}