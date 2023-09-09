package com.binaklet.binaklet.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public abstract class BaseService<T,IdType> {
    private final JpaRepository<T,IdType> repository;

    @Autowired(required = false)
    public BaseService(JpaRepository<T,IdType> repository){
        this.repository=repository;
    }
    public List<T> getAll() {
        return repository.findAll();
    }

    public T getById(IdType id){
        return repository.findById(id).orElse(null);
    }
    public T create(T newData){
        return repository.save(newData);
    }
    public T update (IdType id,T freshData){
        Optional<T> searchedObject = repository.findById(id);
        if(searchedObject.isPresent()){
            T existingObject = searchedObject.get();
            BeanUtils.copyProperties(freshData,existingObject,"id","createdDate");
            return repository.save(existingObject);
        }
        else{
            return null;
        }

    }

    public void delete(IdType id ){
        repository.deleteById(id);
    }




}
