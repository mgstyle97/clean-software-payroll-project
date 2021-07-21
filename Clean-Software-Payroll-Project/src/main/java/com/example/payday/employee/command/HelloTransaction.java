package com.example.payday.employee.command;

import com.example.payday.Transaction;

public class HelloTransaction implements Transaction {

    @Override
    public void execute() {
        System.out.println("Hello! I'm Hello Transaction");
    }

}
