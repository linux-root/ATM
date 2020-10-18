package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        ATM atm = ATM.getInstance();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        in.lines().forEach(atm::process);
    }
}
