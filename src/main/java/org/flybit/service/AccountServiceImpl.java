package org.flybit.service;

import org.flybit.domain.Account;
import org.flybit.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl extends BaseEntityService<Account, Long> implements AccountService {
    @Autowired
    private AccountRepository repository;

    @Override
    public CrudRepository<Account, Long> getRepository() {
        return repository;
    }
    
}
