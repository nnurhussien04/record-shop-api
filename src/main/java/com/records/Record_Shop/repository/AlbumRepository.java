package com.records.Record_Shop.repository;


import com.records.Record_Shop.model.Album;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends CrudRepository<Album,Long> {

}
