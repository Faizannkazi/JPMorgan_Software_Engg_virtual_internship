package com.jpmc.midascore.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;

@Component
public class TransactionProcessor {
    @Autowired private UserRepository userRepository;
    @Autowired private TransactionRepository transactionRepository;

 @Autowired private RestTemplate restTemplate; // Inject the tool

public void process(Transaction transaction) {
    UserRecord sender = userRepository.findById(transaction.getSenderId());
    UserRecord recipient = userRepository.findById(transaction.getRecipientId());

    if (isValid(sender, recipient, transaction)) {
        // 1. Ask the API for the incentive
        String url = "http://localhost:8080/incentive";
        Incentive response = restTemplate.postForObject(url, transaction, Incentive.class);
        float incentiveAmount = (response != null) ? response.getAmount() : 0f;

        // 2. Adjust Balances
        sender.setBalance(sender.getBalance() - transaction.getAmount());
        // Recipient gets the transaction amount PLUS the incentive
        recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentiveAmount);

        // 3. Save to Database
        userRepository.save(sender);
        userRepository.save(recipient);

        TransactionRecord record = new TransactionRecord(sender, recipient, transaction.getAmount());
        record.setIncentive(incentiveAmount); // Save the incentive too
        transactionRepository.save(record);
        
        // Add this to see the answer for Task 4!
        if (sender.getName().equals("wilbur") || recipient.getName().equals("wilbur")) {
            System.out.println("WILBUR UPDATED BALANCE: " + userRepository.findByName("wilbur").getBalance());
        }
    }
}

    private boolean isValid(UserRecord sender, UserRecord recipient, Transaction t) {
        // Validation: Valid IDs and sufficient balance
        return sender != null && recipient != null && sender.getBalance() >= t.getAmount();
    }
}