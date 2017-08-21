package org.flybit.repository;

import org.flybit.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long>{
    
  
}
