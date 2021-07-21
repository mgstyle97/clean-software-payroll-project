package com.example.payday.employee.command;

import com.example.payday.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HelloTransactionTest {

    @Autowired
    private TransactionFactory transactionFactory;

    @Test
    @DisplayName("커맨드 패턴 테스트")
    void test_command_pattern() {
        Transaction transaction = transactionFactory.creationTransaction("hello");
        transaction.execute();
    }

}