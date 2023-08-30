package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService extends BaseService<Tag,Long>{
    public TagService(JpaRepository<Tag, Long> repository) {
        super(repository);
    }
}
