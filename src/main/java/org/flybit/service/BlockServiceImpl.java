package org.flybit.service;

import org.flybit.domain.Block;
import org.flybit.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlockServiceImpl extends BaseEntityService<Block, String> implements BlockService {
    @Autowired
    private BlockRepository repository;

    @Override
    public CrudRepository<Block, String> getRepository() {
        return repository;
    }
    
    
}
