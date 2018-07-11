package org.flybit.concensus;

import org.flybit.domain.Block;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class BlockDiscoverImpl implements BlockDiscover {


    @Override
    public Iterable<String> findBlockIds(String myCurrentBlockId) {
        return Collections.<String>emptyList();
    }

    @Override
    public Iterable<Block> findAllById(Iterable<String> ids) {
        return Collections.<Block>emptyList();
    }

    @Override
    public Optional<Block> findById(String id) {
        return Optional.empty();
    }
}
