package com.example.dairymanagement;

import java.util.ArrayList;

public class Member {

    String name, phone, village;

    public static ArrayList<Member> memberList = new ArrayList<>();

    public Member(String name, String phone, String village) {
        this.name = name;
        this.phone = phone;
        this.village = village;
    }
}