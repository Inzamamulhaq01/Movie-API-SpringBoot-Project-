package com.example.movies.service;

import com.example.movies.model.Movies;
import com.example.movies.model.Review;
import com.example.movies.repository.MoviesRepo;
import com.example.movies.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

        @Autowired
        ReviewRepo reviewRepo;

        @Autowired
        MoviesRepo moviesRepo;

        public Review createReview(String imdbid,String reviewBody)
        {
            Review review = new Review(reviewBody);
            reviewRepo.insert(review);

            Movies movies = moviesRepo.findByImdbId(imdbid).orElse(null);
            if(movies == null)
                return null;
            else
            {
                List<Review> existingReviews = movies.getReviewIds();
                if(existingReviews == null)
                {
                    existingReviews = new ArrayList<>();
                }

                existingReviews.add(review);
                movies.setReviewIds(existingReviews);
                moviesRepo.save(movies);
            }



            return review;
        }

}
