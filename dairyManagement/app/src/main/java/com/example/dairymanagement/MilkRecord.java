package com.example.dairymanagement;

import java.util.ArrayList;

public class MilkRecord {

    int id;
    String name;
    double litres;
    double fat;
    double amount;
    String date;
    String type;

    public static ArrayList<MilkRecord> recordList = new ArrayList<>();

    // No-arg constructor
    public MilkRecord() {}

    public MilkRecord(int id, String name, double litres, double fat,
                      double amount, String date, String type) {
        this.id = id;
        this.name = name;
        this.litres = litres;
        this.fat = fat;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }
}