package org.moviedb.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.moviedb.domain.Movie;
import org.moviedb.domain.Person;

public class MovieService  extends BaseService implements IMovieService {
	
    public Movie getMovie(String uri){
    	
    	Movie movie = null;
    	
    	try {
    		String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
    				"SELECT DISTINCT ?filmTitle ?film ?releaseDate " +
    				"FROM <http://fmb.org> " +
    				"WHERE { " +
    				"?film ns:type.object.type ns:film.film. " +
    				"?film ns:type.object.name ?filmTitle. " +
    				"?x ns:film.film_regional_release_date.release_date ?releaseDate. " +
    				"?x ns:film.film_regional_release_date.film_release_region ?region. " +
    				"FILTER(?film = ns:%s && ?region = ns:m.0345h)}";
        	query = String.format(query, "m." + uri);

    		JSONArray arr = getJSONArray(query);
    		
    		JSONObject movieJson = arr.getJSONObject(0);
    		
    		movie = createMovie(movieJson, true, true);
    		
    	} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return movie;
    }
    
    public List<Movie> getMoviesByAward(String uri, int year){
    	
		String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
				"SELECT DISTINCT ?filmTitle ?film " +
				"FROM <http://fmb.org> " +
				"WHERE { " +
				"?film ns:type.object.type ns:film.film. " +
				"?film ns:type.object.name ?filmTitle. " +
				"?film ns:common.topic.image ?image. " + 
				"?film ns:award.award_winning_work.awards_won ?awardHonor. " +
				"?awardHonor ns:award.award_honor.year ?year. " +
				"?awardHonor ns:award.award_honor.award ?awardCategory. " + 
				"?awardCategory ns:award.award_category.category_of ?award. " +
				"?award ns:type.object.name ?awardName. " +
				"FILTER(?award = ns:%s  && YEAR(?year) >= %s)} " +
				"GROUP BY ?film ?filmTitle ?year " +
				"ORDER BY DESC(?year) " +
				"LIMIT 100";
    	query = String.format(query, "m." + uri, year);
    	
    	return getMovies(query);
    }
    
    public List<Movie> search(String term, int count){
    	
    	String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
    			"SELECT DISTINCT ?filmTitle ?film " +
				"FROM <http://fmb.org> " +
				"WHERE { " +
				"?film ns:type.object.type ns:film.film. " +
				"?film ns:type.object.name ?filmTitle. " +
				"FILTER (REGEX(STR(?filmTitle), \"%s\", \"i\"))}" +
				"ORDER BY (?filmTitle) " +
				"LIMIT " + count;
    	
    	query = String.format(query, term);
    	
    	return getMovies(query);
    }
    
    private List<Movie> getMovies(String query){
    	
    	List<Movie> movies = new ArrayList<Movie>();
    	
    	try {
	    	JSONArray arr = getJSONArray(query);
	    	
			for(int i = 0 ; i < arr.length(); i++){
				
				JSONObject movieJson = arr.getJSONObject(i);
				
				Movie movie = createMovie(movieJson, 1, false);
				
				movies.add(movie);
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return movies;
    }
    
    private Movie createMovie(JSONObject actorJson, boolean getImages, boolean getCountries){
    	return createMovie(actorJson, getImages ? Integer.MAX_VALUE : 0, getCountries);
    }
    
    private Movie createMovie(JSONObject filmJson, int getImagesCount, boolean getCountries){
    	
    	Movie movie = new Movie();
		
    	String mID = filmJson.getJSONObject("film").getString("value");
		mID = mID.replace("http://rdf.freebase.com/ns/m.", "");
		movie.setmID(mID);
    	
    	if(filmJson.has("filmTitle")){
    		movie.setTitle(filmJson.getJSONObject("filmTitle").getString("value"));
    	}
    	
    	if(filmJson.has("releaseDate")){
    		
    		try {
    			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    			
    			String releaseString = filmJson.getJSONObject("releaseDate").getString("value");
    			
    			movie.setReleaseDateGermany(formatter.parse(releaseString));
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	if(getImagesCount > 0){
    		movie.getImagePaths().addAll(getImageUrls(movie, getImagesCount));
			
			if(movie.getImagePaths().size() > 0)
				movie.setImagePath(movie.getImagePaths().get(0));
    	}
		
    	
    	movie.getCountries().addAll(getCountries(movie));
    	
		return movie;
    }
    
    private List<String> getImageUrls(Movie movie, int imageCachingCount){
			
		String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
				"SELECT DISTINCT (CONCAT(CONCAT(\"https://usercontent.googleapis.com/freebase/v1/image/\", REPLACE(SUBSTR(str(?image), bif:strrchr(str(?image), '/')+2), '\\\\.', '\\\\/')), '?maxwidth=1000&maxheight=1000&mode=fit') as ?imageurl) " +
				"FROM <http://fmb.org> " +
				"WHERE {?film ns:type.object.type ns:film.film. " +
				"?film ns:common.topic.image ?image. " +
				"FILTER (?film = ns:%s)}";

		query = String.format(query, "m." + movie.getmID());
		
		return getImageUrls(query, imageCachingCount);
    }
    
    private List<String> getCountries(Movie movie){
    	
    	String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
    					"SELECT DISTINCT ?countryTitle " +
						"FROM <http://fmb.org> " +
    					"WHERE { " +
						"?film ns:type.object.type ns:film.film. " +
    					"?film ns:film.film.country ?country. " +
						"?country ns:type.object.name ?countryTitle. " +
						"FILTER (?film = ns:%s)}";

		query = String.format(query, "m." + movie.getmID());
		
		List<String> countries = new ArrayList<String>();
		
		try {
			JSONArray arr = getJSONArray(query);
	    	
			for(int i = 0 ; i < arr.length(); i++){
				
				JSONObject countryJson = arr.getJSONObject(i);
				
				countries.add(countryJson.getJSONObject("countryTitle").getString("value"));
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return countries;
    }
}
