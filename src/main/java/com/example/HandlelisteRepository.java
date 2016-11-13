package com.example;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface HandlelisteRepository extends MongoRepository<Handleliste, String> {

    public List<Handleliste> findByid(String id);
    public Handleliste findByVare(String Vare);

}