package com.company;

import com.company.constant.Message;
import com.company.log.Logger;
import com.company.op.Login;

import java.util.Optional;

public class User {
    private String accountNumber;
    private int pin;
    private double balance;
    private double overdraft;

    public double getBalance() {
        return balance;
    }

    public User(String accountNumber, int pin, double balance, double overdraft) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.overdraft = overdraft;
    }

    public boolean isValidToWithdraw(final double amount){
      return this.balance - amount + this.overdraft >= 0;
    }

    public void withdraw(final double amount){
        this.balance -= amount;
    }

    public static Optional<User> tryToLogin(String cmd){
        try {
            Login login = new Login(cmd);
            if(login.isSuccessful()){
                return Optional.of(new User(login.getAccountNumber(), login.getCorrectPin(), login.getBalance(), login.getOverdraft()));
            } else {
                Logger.debug(login.getErrorMessage());
                return Optional.empty();
            }
        }catch (IllegalArgumentException e){
            Logger.debug(Message.ACCOUNT_ERR);
            return Optional.empty();
        }
    }
}
