package ru.msu.video_hosting.services;

import ru.msu.video_hosting.DAO.impl.CommonDAOImpl;
import ru.msu.video_hosting.model.CommonEntity;

import java.util.Collection;

public abstract class CommonService<T extends CommonEntity<Integer>, DAO extends CommonDAOImpl<T, Integer>> {
    protected DAO dao;

    public CommonService(DAO dao){
        this.dao = dao;
    }

    public T getById(Integer id){
        return dao.getById(id);
    }

    public Collection<T> getAll(){
        return dao.getAll();
    }

    public void save(T obj){
        dao.save(obj);
    }

    public void saveCollection(Collection<T> entities) {
        dao.saveCollection(entities);
    }

    public void update(T obj){
        dao.update(obj);
    }

    public void delete(T obj){
        dao.delete(obj);
    }
}

