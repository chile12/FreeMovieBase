package moviedb.service;

import moviedb.domain.Movie;

import java.util.List;

public interface IMovieService {
	
    public Movie getMovie(String uri);
    
    public List<Movie> getMoviesByAward(String uri, int year);
    
    public List<Movie> search(String term, int count);
}
