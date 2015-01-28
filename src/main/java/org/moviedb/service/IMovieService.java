package org.moviedb.service;

import java.util.List;

import org.moviedb.domain.Movie;

public interface IMovieService {
	public void addMovie(Movie person);
    
    public Movie getMovie(int id);
    
    public List<Movie> getMovies(int start, int end);
    
    public int getMoviesSize();
}
