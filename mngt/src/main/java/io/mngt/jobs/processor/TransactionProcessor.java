package io.mngt.jobs.processor;

import org.springframework.batch.item.ItemProcessor;

import io.mngt.entity.Transaction;
import io.mngt.jobs.domain.TransactionItem;

public class TransactionProcessor implements ItemProcessor<TransactionItem, Transaction> {

    @Override
    public Transaction process(TransactionItem item) throws Exception {
        
        Transaction transaction = new Transaction(item);
        return transaction;
    }

    
}