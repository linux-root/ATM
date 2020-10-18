package com.company;

import com.company.constant.ActionKeys;
import com.company.constant.Message;
import com.company.log.Logger;
import com.company.op.Initialization;

import java.util.Optional;

public class ATM {

    private double totalCash;
    private Optional<User> operatingUser = Optional.empty();

    public double getTotalCash() {
        return totalCash;
    }

    private static ATM instance = new ATM(-1);

    public static ATM getInstance() {
        return instance;
    }

    public boolean isInUse(){
      return this.operatingUser.isPresent();
    }

    public Double getOperatingUserBalance() {
        if (this.operatingUser.isPresent()){
           return this.operatingUser.get().getBalance();
        } else{
           return null;
        }
    }

    public static void reset(){
        instance = new ATM(-1);
    }

    private static void init(final Initialization initialization){
        instance.totalCash = initialization.getTotalCash();
    }

    private ATM(final double totalCash){
        this.totalCash = totalCash;
    }

    private boolean isNotReady(){
      return this.totalCash < 0;
    }

    private void terminateUserSession(){
        this.operatingUser = Optional.empty();
    }

    public void process(String cmd){
        if(isNotReady()){
          Initialization i = new Initialization(cmd);
          init(i);
        } else {
            if (operatingUser.isPresent()) {
               handleUserOperation(cmd);
            } else {
               operatingUser = User.tryToLogin(cmd);
            }
        }
    }

    private void handleUserOperation(String cmd){
        if (!operatingUser.isPresent()) return;

        User user = operatingUser.get();
        String[] values = cmd.split("\\s");
        String action = values[0];

        if (action.equals(ActionKeys.TERMINATE_SESSION)){
            terminateUserSession();
        } else {
            if (action.equals(ActionKeys.BALANCE)){
                Logger.debug(user.getBalance());
            } else if (action.equals(ActionKeys.WITHDRAW)){
                final double amount = Double.parseDouble(values[1]);
                this.withdraw(amount, user);
            }
        }
    }

    private void withdraw(double amount, User user){
        if (amount > this.totalCash){
            Logger.debug(Message.ATM_ERR);
        } else {
            if (user.isValidToWithdraw(amount)){
                user.withdraw(amount);
                this.totalCash -= amount;
                Logger.debug(user.getBalance());
            } else {
                Logger.debug(Message.FUND_ERR);
            }
        }
    }

}
