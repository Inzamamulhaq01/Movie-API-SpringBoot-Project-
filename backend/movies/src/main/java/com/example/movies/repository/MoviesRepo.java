package com.example.movies.repository;

import com.example.movies.model.Movies;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoviesRepo extends MongoRepository<Movies, String> {
    Optional<Movies> findByImdbId(String imdbId);
}
