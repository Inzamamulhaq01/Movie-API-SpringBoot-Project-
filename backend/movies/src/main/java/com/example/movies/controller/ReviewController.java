package com.example.movies.controller;

import com.example.movies.model.Movies;
import com.example.movies.model.Review;
import com.example.movies.service.MovieService;
import com.example.movies.service.ReviewService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    MovieService movieService;
    @Autowired
    ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Map<String,String> request) {
        Review review = reviewService.createReview(request.get("imdbId"),request.get("reviewBody"));

        if(review == null)
            return new ResponseEntity<String>("unalbe to review movie not found",HttpStatus.NOT_FOUND);

        return new ResponseEntity<Review>(review ,HttpStatus.CREATED);
    }
}
