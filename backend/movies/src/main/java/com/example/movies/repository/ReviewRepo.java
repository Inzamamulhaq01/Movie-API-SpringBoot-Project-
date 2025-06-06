package com.example.movies.repository;

import com.example.movies.model.Movies;
import com.example.movies.model.Review;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepo extends MongoRepository<Review, String> {
}
