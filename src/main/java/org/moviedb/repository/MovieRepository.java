package org.moviedb.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.moviedb.domain.Movie;

public class MovieRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Movie> getMovies(){
		try {
			return entityManager.createQuery("select m from Movie m", Movie.class)
					.getResultList();
		} catch (PersistenceException e) {
			return new ArrayList<Movie>();
		}
	}
}
