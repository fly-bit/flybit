package org.flybit.service;

import org.flybit.domain.PublicKey;
import org.flybit.repository.PublicKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PublicKeyServiceImpl extends BaseEntityService<PublicKey, String> implements PublicKeyService {
    @Autowired
    private PublicKeyRepository repository;

    @Override
    public CrudRepository<PublicKey, String> getRepository() {
        return repository;
    }
    
}
