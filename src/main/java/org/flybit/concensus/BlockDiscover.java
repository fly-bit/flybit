package org.flybit.concensus;

import org.flybit.domain.Block;

import java.util.Optional;

public interface BlockDiscover {

    Iterable<String> findBlockIds(String myCurrentBlockId);

    Iterable<Block> findAllById(Iterable<String> ids);

    Optional<Block> findById(String id);

}
