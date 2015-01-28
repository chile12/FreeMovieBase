package org.moviedb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.moviedb.domain.Movie;
import org.moviedb.repository.MovieRepository;
import org.moviedb.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MovieService implements IMovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	private HashMap<Integer, Movie> movies = new  HashMap<Integer, Movie>();

    public void addMovie(Movie movie){
    	movies.put(movie.getId(), movie);
    }
    
    public Movie getMovie(int id){
    	Movie movie = movies.get(id);
    	
    	return movie;
    }
    
    public List<Movie> getMovies(int start, int end){
    	
    	/*List<Movie> tempMovies = new ArrayList<Movie>();
    	
    	int i = 0;
    	for(Movie movie : movies.values().toArray(new Movie[movies.size()])){
    		
    		if(i >= start && i <= end)
    			tempMovies.add(movie);
    		
    		i++;
    	}
    	
    	return tempMovies;*/
    	
    	return this.movieRepository.getMovies();
    }
    
    public int getMoviesSize(){
    	return movies.size();
    }
}
