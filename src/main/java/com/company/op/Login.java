package com.company.op;

import com.company.constant.Message;

public class Login extends Operation {
    private String accountNumber;
    private int correctPin;
    private int enteredPin;
    private double balance;
    private double overdraft;

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getCorrectPin() {
        return correctPin;
    }

    public double getBalance() {
        return balance;
    }

    public double getOverdraft() {
        return overdraft;
    }

    public boolean isSuccessful() {
        return this.enteredPin == this.correctPin;
    }

    public Login(String cmd) {
        try {
            final String[] values = cmd.split("\\s");
            this.accountNumber = values[0];
            this.correctPin = Integer.parseInt(values[1]);
            this.enteredPin = Integer.parseInt(values[2]);
            this.balance = Integer.parseInt(values[3]);
            this.overdraft = Integer.parseInt(values[4]);
        } catch (Exception e){
            throw new IllegalArgumentException("Invalid login data");
        }
    }

    @Override
    public String getErrorMessage() {
        return Message.ACCOUNT_ERR;
    }
}
