package com.example.payday.employee.command;

import com.example.payday.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionFactory {

    public Transaction creationTransaction(String command) {
        Transaction transaction = null;
        switch (command) {
            case "hello":
                transaction = new HelloTransaction();
        }

        return transaction;
    }

}
