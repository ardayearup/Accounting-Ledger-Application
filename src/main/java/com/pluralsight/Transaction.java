package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public static Transaction fromCSV(String line) {
        String[] parts = line.split("\\|");

        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid CSV format, expected 5 fields but got " + parts.length);
        }

        try {
            LocalDate date = LocalDate.parse(parts[0]);
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime time = LocalTime.parse(parts[1], timeFormatter);
            String description = parts[2];
            String vendor = parts[3];
            double amount = Double.parseDouble(parts[4]);

            return new Transaction(date, time, description, vendor, amount);
        } catch (Exception e) {
            System.out.println("Error parsing line: " + line);
            throw e;
        }
    }

    public String toCSV() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return date + "|" + time.format(timeFormatter) + "|" + description + "|" + vendor + "|" + amount;
    }

    @Override
    public String toString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return date + " " + time.format(timeFormatter) + " | " + description + " | " + vendor + " | " + amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getVendor() {
        return vendor;
    }
}