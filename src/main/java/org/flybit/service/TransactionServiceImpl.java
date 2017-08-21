package org.flybit.service;

import org.flybit.domain.Transaction;
import org.flybit.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionServiceImpl extends BaseEntityService<Transaction, Long> implements TransactionService {
    @Autowired
    private TransactionRepository repository;

    @Override
    public CrudRepository<Transaction, Long> getRepository() {
        return repository;
    }
    
}
