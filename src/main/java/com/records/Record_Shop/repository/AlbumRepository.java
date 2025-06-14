package com.records.Record_Shop.repository;


import com.records.Record_Shop.model.Album;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//#spring.datasource.driver-class-name=org.h2.Driver

@Repository
public interface AlbumRepository extends CrudRepository<Album,Long> {

    @Override
    @CacheEvict(cacheNames = "albums",key = "#result.id")
    <S extends Album> S save(S entity);

    @Override
    @Cacheable("albums")
    Optional<Album> findById(Long aLong);

}


