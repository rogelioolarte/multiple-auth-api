package com.example.service.Impl;

import com.example.exception.BadRequestException;
import com.example.exception.NotFoundException;
import com.example.persistence.IGenericDAO;
import com.example.qualifier.FindEntities;
import com.example.qualifier.FindEntitiesSet;
import com.example.qualifier.FindEntity;
import com.example.service.IGenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public abstract class GenericServiceImpl<T,ID> implements
        IGenericService<T,ID> {

    protected abstract IGenericDAO<T, ID> getDAO();

    protected abstract String getEntityName();

    @FindEntity
    @Transactional
    @Override
    public T findById(ID id) {
        if (id == null) throw new BadRequestException(
                String.format("%s cannot be found", getEntityName()));
        return getDAO().findById(id).orElseThrow(() -> new NotFoundException(
                String.format("%s with ID %s not found",
                        getEntityName(), id.toString())));
    }

    @FindEntities
    @Transactional
    @Override
    public List<T> findAllByIds(Collection<ID> ids) {
        if(ids == null || ids.isEmpty()) throw new BadRequestException(
                String.format("%s(s) cannot be found", getEntityName()));
        if(!existsAllByIds(ids)) throw new NotFoundException(
                String.format("Some %s(s) cannot be found", getEntityName()));
        return getDAO().findAllByIds(ids);
    }

    @FindEntitiesSet
    public Set<T> findAllByIdstoSet(Collection<ID> ids) {
        return new HashSet<>(findAllByIds(ids));
    }

    @Transactional
    @Override
    public T save(T t) {
        if(t == null) throw new BadRequestException(
                String.format("%s cannot be created", getEntityName()));
        if(existsByUniqueProperties(t)) throw new BadRequestException(
                String.format("%s already exists", getEntityName()));
        return getDAO().save(t);
    }

    @Transactional
    @Override
    public List<T> saveAll(List<T> t) {
        return getDAO().saveAll(t);
    }

    @Transactional
    @Override
    public T updateById(T t, ID id) {
        if(id == null || t == null) throw new BadRequestException(
                String.format("%s cannot be updated", getEntityName()));
        return Optional.ofNullable(getDAO().updateById(t, id)).orElseThrow(() ->
                new NotFoundException(String.format("%s with ID %s not found",
                        getEntityName(), id.toString())));
    }

    @Transactional
    @Override
    public void deleteById(ID id) {
        if(id == null) throw new BadRequestException(
                String.format("%s cannot be deleted", getEntityName()));
        getDAO().deleteById(id);
    }

    @Transactional
    @Override
    public List<T> findAll() {
        return getDAO().findAll();
    }

    @Transactional
    @Override
    public Page<T> findAllPage(Pageable pageable) {
        return getDAO().findAllPage(pageable);
    }

    @Transactional
    @Override
    public boolean existsById(ID id) {
        return getDAO().existsById(id);
    }

    @Transactional
    @Override
    public boolean existsAllByIds(Collection<ID> ids) {
        return getDAO().existsAllById(ids);
    }

    @Transactional
    @Override
    public boolean existsByUniqueProperties(T t) {
        return getDAO().existsByUniqueProperties(t);
    }

}
