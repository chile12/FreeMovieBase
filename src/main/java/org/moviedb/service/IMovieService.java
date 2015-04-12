package org.moviedb.service;

import java.util.List;

import org.moviedb.domain.Movie;
import org.moviedb.domain.Person;

public interface IMovieService {
	
    public Movie getMovie(String uri);
    
    public List<Movie> getMoviesByAward(String uri, int year);
    
    public List<Movie> search(String term, int count);
}
