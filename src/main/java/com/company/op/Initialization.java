package com.company.op;

import com.company.constant.Message;

public class Initialization extends Operation {
    final private double totalCash;

    public double getTotalCash() {
        return totalCash;
    }

    public Initialization(String cmd) {
        try {
           double totalCash = Double.parseDouble(cmd);
           this.totalCash = totalCash;
        } catch (Exception e){
            throw new IllegalArgumentException("error when init ATM : Total cash value must be a number");
        }

    }

    @Override
    public String getErrorMessage() {
        return Message.INIT_FAILED;
    }
}
