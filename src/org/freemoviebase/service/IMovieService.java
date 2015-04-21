package org.freemoviebase.service;

import java.util.List;

import org.freemoviebase.domain.Movie;

public interface IMovieService {
	
    public Movie getMovie(String uri);
    
    public List<Movie> getMoviesByAward(String uri, int year);
    
    public List<Movie> search(String term, int count);
    
    public List<Movie> getMoviesByActors(String uriActor1, String uriActor2);
}
