package com.pluralsight;

import java.io.*;
import java.util.*;

public class Ledger {
    private List<Transaction> transactions = new ArrayList<>();
    private final String filePath = "src/main/resources/transactions.csv";

    public Ledger() {
        loadTransactions();
    }

    public void loadTransactions() {
        transactions.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Transaction t = Transaction.fromCSV(line);
                if (t != null) {
                    transactions.add(t);
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveTransaction(Transaction t) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(t.toCSV());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing file. Failed to save transaction: " + e.getMessage());
        }
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> copy = new ArrayList<>(transactions);
        copy.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        return copy;
    }

    public List<Transaction> getDeposits() {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) {
                result.add(t);
            }
        }
        return result;
    }

    public List<Transaction> getPayments() {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAmount() < 0) {
                result.add(t);
            }
        }
        return result;
    }
}