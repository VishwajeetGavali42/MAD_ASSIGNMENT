package com.example.dairymanagement;

public class MemberTotalModel {

    public String name;

    // AMOUNT
    public double today;
    public double monthly;
    public double yearly;
    public double total;

    // LITRES
    public double todayLitres;
    public double monthlyLitres;
    public double yearlyLitres;
    public double totalLitres;

    public MemberTotalModel(String name,
                            double today, double monthly, double yearly, double total,
                            double todayLitres, double monthlyLitres,
                            double yearlyLitres, double totalLitres) {
        this.name = name;
        this.today = today;
        this.monthly = monthly;
        this.yearly = yearly;
        this.total = total;
        this.todayLitres = todayLitres;
        this.monthlyLitres = monthlyLitres;
        this.yearlyLitres = yearlyLitres;
        this.totalLitres = totalLitres;
    }
}