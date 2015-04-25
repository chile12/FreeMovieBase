package moviedb.service;

import moviedb.domain.Topic;
import org.json.JSONArray;
import org.json.JSONObject;
import moviedb.domain.Movie;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.*;

public class MovieService  extends BaseService implements IMovieService {

    private Map<String, Movie> movieCache = new HashMap<String, Movie>();
    public Movie getMovie(String mid){
    	List<Movie> movs = getMovies(Arrays.asList(mid));
        if(movs.size() > 0)
    	    return movs.get(0);
        return null;
    }
    
    public List<Movie> getMoviesByAward(String uri, int year){

        List<Movie> movies = new ArrayList<Movie>();
		String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
				"SELECT DISTINCT (?film as ?mid) " +
				"FROM <http://fmb.org> " +
				"WHERE { " +
				"?film ns:type.object.type ns:film.film. " +
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
    	query = String.format(query, uri, year);
        return getMovies(evalQueryResult(query));
    }
    
    public List<Movie> search(String term, int count){

        List<Movie> movies = new ArrayList<Movie>();
    	String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
    			"SELECT DISTINCT (?film as ?mid) " +
				"FROM <http://fmb.org> " +
				"WHERE { " +
				"?film ns:type.object.type ns:film.film. " +
				"FILTER (REGEX(STR(?filmTitle), \"%s\", \"i\"))}" +
				"ORDER BY (?filmTitle) " +
				"LIMIT " + count;
    	
    	query = String.format(query, term);
        return getMovies(evalQueryResult(query));
    }

    public List<Movie> getMoviesByActors(String uriActor1, String uriActor2){
        String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
                "SELECT DISTINCT (?film as ?mid) " +
                "FROM <http://fmb.org> "+
                "WHERE { "+
                "?film ns:type.object.type ns:film.film. " +
                "?film ns:film.film.starring ?perf1. " +
                "?film ns:film.film.starring ?perf2. " +
                "?perf1 ns:film.performance.actor ?actor1. " +
                "?perf2 ns:film.performance.actor ?actor2. " +
                "FILTER (?actor1 = ns:%s && ?actor2 = ns:%s)}";

        query = String.format(query, uriActor1, uriActor2);

        return getMovies(evalQueryResult(query));

    }
    
    private List<Movie> getMovies(List<String> mids){
    	List<Movie> movies = new ArrayList<Movie>();
        //extracts basic Informations about the movie
        String query = "PREFIX ns: <http://rdf.freebase.com/ns/> \n" +
                "PREFIX key: <http://rdf.freebase.com/key/> \n" +
                "                SELECT DISTINCT ?film ?wikiId ?filmTitle (group_concat(distinct ?website;separator=\",&\") as ?websites) (group_concat(distinct ?country;separator=\",&\") as ?countries) ?series ?tagline (group_concat(distinct ?description;separator=\",&\") as ?descriptions) \n" +
                "FROM <http://fmb.org>\n" +
                "                WHERE { \n" +
                "                OPTIONAL{?film ns:type.object.name ?filmTitle. }\n" +
                "                OPTIONAL{?film key:wikipedia.en_id ?wikiId. }\n" +
                "                OPTIONAL{?film ns:common.topic.official_website ?website.}\n" +
                "                OPTIONAL{?film ns:film.film.tagline ?tagline.  }\n" +
                "                OPTIONAL{?film ns:common.topic.description ?description.  }\n" +
                "                OPTIONAL{?film ns:film.film.country / ns:type.object.name ?country.  }\n" +
                "                OPTIONAL{?film ns:film.film.film_series / ns:type.object.name ?series.  }\n" +
                "FILTER ( ?film in ( ";
        for(String mid : mids)
        {
            query += "ns:" + mid + ",";
        }
        query = query.substring(0, query.length()-1) + "))} GROUP BY ?websites ?countries ?filmTitle ?wikiId ?film ?series ?tagline ?descriptions";

    	try {
	    	JSONArray arr = getJSONArray(query);
	    	
			for(int i = 0 ; i < arr.length(); i++){
				
				JSONObject movieJson = arr.getJSONObject(i);
				
				Movie movie = createMovie(movieJson);
				
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
    
    private Movie createMovie(JSONObject json){
    	String mID = json.getJSONObject("film").getString("value");
		mID = mID.replace("http://rdf.freebase.com/ns/", "");

        if(movieCache.keySet().contains(mID))
            return movieCache.get(mID);

        Movie movie = new Movie();
        movie.setmID(mID);
    	
    	if(json.has("filmTitle")) movie.setTitle(json.getJSONObject("filmTitle").getString("value"));
        if(json.has("websites"))
        {
            String zw = json.getJSONObject("websites").getString("value");
            if(zw.contains(",&"))
                zw = zw.substring(0, zw.indexOf(",&"));
            movie.setWebSite(zw);
        }
        if(json.has("description"))
        {
            String zw = json.getJSONObject("description").getString("value");
            String[] descs = zw.split(",\\&");
            zw = "";
            for(String st : descs)
                if(zw.length() < st.length())
                    zw = st;
            movie.setWebSite(zw);
        }
        if(json.has("wikiId")) movie.setWikiID(json.getJSONObject("wikiId").getString("value"));
        if(json.has("series")) movie.setSeries(json.getJSONObject("series").getString("value"));
        if(json.has("tagline")) movie.setTagline(json.getJSONObject("tagline").getString("value"));

        if(json.has("countries")){
            movie.getCountries().clear();
            for(String c : json.getJSONObject("countries").getString("value").split(",\\&"))
            {
                movie.getCountries().add(c);
            }
        }
        addMovieToCache(movie);
		return movie;
    }

    public Movie additionalInformations(Movie movie)
    {
        //extracts additional Informations about the movie
        String query = "PREFIX ns: <http://rdf.freebase.com/ns/> \n" +
                "PREFIX key: <http://rdf.freebase.com/key/> \n" +
                "SELECT DISTINCT (group_concat(distinct ?comp;separator=\",&\") as ?companies) (group_concat(distinct ?genre;separator=\",&\") as ?genres) (MIN(?run) as ?runtime) ?revenue ?budget (MIN(?rel) as ?release) ?starr ?actor ?character\n" +
                "FROM <http://fmb.org>\n" +
                "WHERE { ?film ns:film.film.starring ?starr.\n" +
                "                ?starr ns:film.performance.actor ?actor.\n" +
                "                OPTIONAL{?starr ns:film.performance.character / ns:type.object.name ?character.}\n" +
                "                OPTIONAL{?film ns:film.film.production_companies / ns:type.object.name ?comp. }\n" +
                "                OPTIONAL{?film ns:film.film.genre / ns:type.object.name ?genre.  }\n" +
                "                OPTIONAL{?film ns:film.film.runtime / ns:film.film_cut.runtime ?run. }\n" +
                "                OPTIONAL{?film ns:film.film.gross_revenue / ns:measurement_unit.dated_money_value.amount ?revenue. }\n" +
                "                OPTIONAL{?film ns:film.film.estimated_budget / ns:measurement_unit.dated_money_value.amount ?budget. }\n" +
                "                OPTIONAL{?film ns:film.film.release_date_s / ns:film.film_regional_release_date.release_date ?rel. }\n" +
                "FILTER ( ?film  = ns:%s)} \n" +
                "GROUP BY ?companies ?genres ?runtime ?revenue ?budget ?release ?actor ?character ?starr";
        query = String.format(query, movie.getmID());

        try {
            JSONArray arr = getJSONArray(query);

            for(int i =0; i < arr.length(); i++)
            {
                JSONObject filmJson = arr.getJSONObject(i);
                if( i == 0) {
                    if (filmJson.has("runtime")) movie.setRuntime(filmJson.getJSONObject("runtime").getDouble("value"));
                    if (filmJson.has("revenue")) movie.setRevenue(filmJson.getJSONObject("revenue").getDouble("value"));
                    if (filmJson.has("budget")) movie.setBudget(filmJson.getJSONObject("budget").getDouble("value"));
                    if (filmJson.has("release"))
                        movie.setReleaseDate(formatter.parse(filmJson.getJSONObject("release").getString("value")));

                    if (filmJson.has("companies")) {
                        movie.getCountries().clear();
                        for (String c : filmJson.getJSONObject("companies").getString("value").split(",\\&")) {
                            movie.getCompanies().add(c);
                        }
                    }
                    if (filmJson.has("genres")) {
                        movie.getCountries().clear();
                        for (String c : filmJson.getJSONObject("genres").getString("value").split(",\\&")) {
                            movie.getGenres().add(c);
                        }
                    }
                }
                movie.getActors().put(filmJson.getJSONObject("actor").getString("value"), filmJson.getJSONObject("character").getString("value"));
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return movie;
    }

    private void addMovieToCache(Movie mov)
    {
        if(!movieCache.keySet().contains(mov.getmID()))  //not!!
            movieCache.put(mov.getmID(), mov);
    }
}
