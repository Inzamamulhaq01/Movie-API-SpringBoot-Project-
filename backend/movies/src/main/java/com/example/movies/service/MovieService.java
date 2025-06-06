package com.example.movies.service;

import com.example.movies.model.Movies;
import com.example.movies.repository.MoviesRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    MoviesRepo moviesRepo;

    public List<Movies> getMovies() {

        return moviesRepo.findAll();
    }

    public Movies getSingleMovie(String imdbid) {
        return moviesRepo.findByImdbId(imdbid).orElse(null);
    }
}
