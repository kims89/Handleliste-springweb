package com.example;

        import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrukerRepository extends MongoRepository<Bruker, String> {

    public Bruker findByBrukernavn(String brukernavn);

}