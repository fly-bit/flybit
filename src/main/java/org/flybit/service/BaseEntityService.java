package org.flybit.service;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public abstract class BaseEntityService<T, ID extends Serializable> implements EnityService<T, ID>{

    protected abstract CrudRepository<T, ID> getRepository();

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     * 
     * @param entity must not be {@literal null}.
     * @return the saved entity will never be {@literal null}.
     */
    @Override
    public <S extends T> S save(S entity){
        return getRepository().save(entity);
    }

    /**
     * Saves all given entities.
     * 
     * @param entities must not be {@literal null}.
     * @return the saved entities will never be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities){
        return getRepository().saveAll(entities);
    }

    /**
     * Retrieves an entity by its id.
     * 
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    public Optional<T> findById(ID id){
        return getRepository().findById(id);
    }

    /**
     * Returns whether an entity with the given id exists.
     * 
     * @param id must not be {@literal null}.
     * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    public boolean existsById(ID id){
        return getRepository().existsById(id);
    }

    /**
     * Returns all instances of the type.
     * 
     * @return all entities
     */
    @Override
    public Iterable<T> findAll(){
        return getRepository().findAll();
    }

    /**
     * Returns all instances of the type with the given IDs.
     * 
     * @param ids
     * @return
     */
    public Iterable<T> findAllById(Iterable<ID> ids){
        return getRepository().findAllById(ids);
    }

    /**
     * Returns the number of entities available.
     * 
     * @return the number of entities
     */
    @Override
    public long count(){
        return getRepository().count();
    }

    /**
     * Deletes the entity with the given id.
     * 
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    public void deleteById(ID id){
         getRepository().deleteById(id);
    }

    /**
     * Deletes a given entity.
     * 
     * @param entity
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    @Override
    public void delete(T entity){
        getRepository().delete(entity);
    }

    /**
     * Deletes the given entities.
     * 
     * @param entities
     * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
     */
    public void deleteAll(Iterable<? extends T> entities){
        getRepository().deleteAll(entities);
    }

    /**
     * Deletes all entities managed by the repository.
     */
    @Override
    public void deleteAll(){
        getRepository().deleteAll();
    }
}

