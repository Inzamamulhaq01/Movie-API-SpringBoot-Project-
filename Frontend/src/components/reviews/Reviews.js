import { useEffect, useRef, useState } from 'react';
import api from '../../api/axiosConfig';
import { useParams } from 'react-router-dom';
import { Container, Row, Col } from 'react-bootstrap';
import ReviewForm from '../reviewForm/ReviewForm';
import React from 'react';

const Reviews = ({ getMovieData, setReviews, reviews: propReviews }) => {
    const [localMovie, setLocalMovie] = useState(null);
    const [localReviews, setLocalReviews] = useState(propReviews || []);
    const revText = useRef();
    const { movieId } = useParams();

    useEffect(() => {
        const fetchMovie = async () => {
            try {
                const response = await api.get(`/api/v1/movies/${movieId}`);
                const movieData = response.data;
                getMovieData(movieId);
                setLocalMovie(movieData);
                setLocalReviews(movieData.reviewIds || []);
                setReviews(movieData.reviewIds || []);
            } catch (err) {
                console.error(err);
            }
        };
        fetchMovie();
    }, [movieId, getMovieData, setReviews]);

    // Sync localReviews only when movieId changes (initial load or movie change)
    useEffect(() => {
        setLocalReviews(propReviews || []);
    }, [movieId]);  // <- sync only on movieId changes, NOT on every propReviews update

    const addReview = async (e) => {
        e.preventDefault();
        const rev = revText.current;

        if (!rev.value.trim()) return; // Prevent empty review

        try {
            const response = await api.post("/api/v1/reviews", {
                reviewBody: rev.value,
                imdbId: movieId
            });

            const updatedReviews = [...localReviews, response.data];
            rev.value = "";
            setLocalReviews(updatedReviews); // update local state immediately
            setReviews(updatedReviews);      // sync parent state
        } catch (err) {
            console.error(err);
        }
    };

    if (!localMovie) {
        return <div>Loading...</div>;
    }

    return (
        <Container>
            <Row>
                <Col><h3>Reviews</h3></Col>
            </Row>
            <Row className="mt-2">
                <Col md={4}>
                    <img
                        src={localMovie?.poster}
                        alt="Movie Poster"
                        style={{ width: '100%', borderRadius: '10px' }}
                    />
                </Col>
                <Col md={8}>
                    <Row>
                        <Col>
                            <ReviewForm
                                handleSubmit={addReview}
                                revText={revText}
                                labelText="Write a Review?"
                            />
                        </Col>
                    </Row>
                    <Row><Col><hr /></Col></Row>

                    {Array.isArray(localReviews) && localReviews.length > 0 ? (
                        localReviews.filter(r => r?.body).map((r) => (
                            <div key={r._id}>
                                <Row>
                                    <Col>
                                        <p>{r.body}</p>
                                    </Col>
                                </Row>
                                <Row><Col><hr /></Col></Row>
                            </div>
                        ))
                    ) : (
                        <Row>
                            <Col>
                                <p>No reviews yet. Be the first to write one!</p>
                            </Col>
                        </Row>
                    )}
                </Col>
            </Row>
            <Row>
                <Col><hr /></Col>
            </Row>
        </Container>
    );
};

export default Reviews;
