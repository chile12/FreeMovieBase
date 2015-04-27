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
        if(movieCache.keySet().contains(mid))
            return movieCache.get(mid);
    	List<Movie> movs = getMovies(Arrays.asList(mid));
        if(movs.size() > 0)
    	    return movs.get(0);
        return null;
    }
    
    public List<Movie> getMoviesByAward(String uri, int year){

        List<Movie> movies = new ArrayList<Movie>();
		String query = "PREFIX ns: <http://rdf.freebase.com/ns/> \n" +
                "SELECT DISTINCT (?film as ?mid)  (COUNT(?film) as ?oscars)\n" +
                "FROM <http://fmb.org> \n" +
                "WHERE { \n" +
                "?film ns:type.object.type ns:film.film. \n" +
                "?film ns:award.award_winning_work.awards_won ?awardHonor. \n" +
                "?awardHonor ns:award.award_honor.year ?year. \n" +
                "?awardHonor ns:award.award_honor.award ?awardCategory. \n" +
                "?awardCategory ns:award.award_category.category_of ?award. \n" +
                "?award ns:type.object.name ?awardName. \n" +
                "FILTER(?award = ns:%s  && YEAR(?year) = %d)}\n" +
                "GROUP BY ?film ?year\n" +
                "ORDER BY DESC(?oscars)";
    	query = String.format(query, uri, year);
        return getMovies(evalQueryResult(query));
    }
    
    public List<Movie> search(String term, int count){

    	String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
    			"SELECT DISTINCT (?film as ?mid) " +
				"FROM <http://fmb.org> " +
				"WHERE { " +
				"?film ns:type.object.type ns:film.film. " +
				"?film ns:type.object.name ?filmTitle. " +
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
        List<String> rejects = new ArrayList<String>();

        for(String mid : mids) {
            if (movieCache.keySet().contains(mid))
                movies.add(movieCache.get(mid));
            else
                rejects.add(mid);
        }
        //extracts basic Informations about the movie
        String query = "PREFIX ns: <http://rdf.freebase.com/ns/> \n" +
                "PREFIX key: <http://rdf.freebase.com/key/> \n" +
                "SELECT (group_concat(distinct ?f;separator=\",&\") as ?film) ?wikiId ?filmTitle (group_concat(distinct ?website;separator=\",&\") as ?websites) (group_concat(distinct ?country;separator=\",&\") as ?countries) ?series (group_concat(distinct ?description;separator=\",&\") as ?descriptions) \n" +
                "FROM <http://fmb.org>\n" +
                "                WHERE { \n" +
                "                OPTIONAL{?f ns:type.object.name ?filmTitle. }\n" +
                "                OPTIONAL{?f  key:wikipedia.en_id ?wikiId. }\n" +
                "                OPTIONAL{?f  ns:common.topic.official_website ?website.}\n" +
                "                OPTIONAL{?f ns:common.topic.description ?description.  }\n" +
                "                OPTIONAL{?f ns:film.film.country / ns:type.object.name ?country.  }\n" +
                "                OPTIONAL{?f ns:film.film.film_series / ns:type.object.name ?series.  }\n" +
                "FILTER ( ?f in ( ";
        for(String mid : rejects)
        {
            query += "ns:" + mid + ",";
        }
        query = query.substring(0, query.length()-1) + "))} GROUP BY ?filmTitle ?wikiId ?series ?tagline";

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
            movie.setWebsite(zw);
        }
        if(json.has("descriptions"))
        {
            String zw = json.getJSONObject("descriptions").getString("value");
            String[] descs = zw.split(",\\&");
            zw = "";
            for(String st : descs)
                if(zw.length() < st.length())
                    zw = st;
            movie.setDescription(zw);
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

    @Override
    public void LoadAdditionalInformations(Movie movie)
    {
        if(movie == null || movie.getLoaded())
            return;
        //extracts additional Informations about the movie
        String query = "PREFIX ns: <http://rdf.freebase.com/ns/> \n" +
                "PREFIX key: <http://rdf.freebase.com/key/> \n" +
                "SELECT DISTINCT (group_concat(distinct ?comp;separator=\",&\") as ?companies) (group_concat(distinct ?genre;separator=\",&\") as ?genres) (MIN(?run) as ?runtime) ?revenue ?budget (MIN(?rel) as ?release)\n" +
                "(group_concat(distinct (bif:subseq(str(?actor), bif:strrchr(str(?actor), '/')+1));separator=\",&\") as ?actors)\n" +
                "(group_concat(distinct (bif:subseq(str(?director), bif:strrchr(str(?director), '/')+1));separator=\",&\") as ?directors)\n" +
                "(group_concat(distinct (bif:subseq(str(?producer), bif:strrchr(str(?producer), '/')+1));separator=\",&\") as ?producers)\n" +
                "(group_concat(distinct (bif:subseq(str(?writer), bif:strrchr(str(?writer), '/')+1));separator=\",&\") as ?writers)\n" +
                "FROM <http://fmb.org>\n" +
                "WHERE { ?film ns:film.film.starring ?starr.\n" +
                "                ?starr ns:film.performance.actor ?actor.\n" +
                "                  OPTIONAL{?film ns:film.film.directed_by ?director}                  \n" +
                "OPTIONAL{?film ns:film.film.produced_by ?producer}                  \n" +
                "OPTIONAL{?film ns:film.film.written_by ?writer}                  \n" +
                "OPTIONAL{?film ns:film.film.executive_produced_by ?producer}                \n" +
                "OPTIONAL{?starr ns:film.performance.character / ns:type.object.name ?character.}\n" +
                "                OPTIONAL{?film ns:film.film.production_companies / ns:type.object.name ?comp. }\n" +
                "                OPTIONAL{?film ns:film.film.genre / ns:type.object.name ?genre.  }\n" +
                "                OPTIONAL{?film ns:film.film.runtime / ns:film.film_cut.runtime ?run. }\n" +
                "                OPTIONAL{?film ns:film.film.gross_revenue / ns:measurement_unit.dated_money_value.amount ?revenue. }\n" +
                "                OPTIONAL{?film ns:film.film.estimated_budget / ns:measurement_unit.dated_money_value.amount ?budget. }\n" +
                "                OPTIONAL{?film ns:film.film.release_date_s / ns:film.film_regional_release_date.release_date ?rel. }\n" +
                "FILTER ( ?film  = ns:%s)} \n" +
                "GROUP BY ?companies ?genres ?runtime ?revenue ?budget ?release";
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
                        movie.setReleaseDateGermany(formatter.parse(filmJson.getJSONObject("release").getString("value")));

                    if (filmJson.has("companies")) {
                        movie.getCompanies().clear();
                        for (String c : filmJson.getJSONObject("companies").getString("value").split(",\\&")) {
                            movie.getCompanies().add(c);
                        }
                    }
                    if (filmJson.has("genres")) {
                        movie.getGenres().clear();
                        for (String c : filmJson.getJSONObject("genres").getString("value").split(",\\&")) {
                            movie.getGenres().add(c);
                        }
                    }
                    if (filmJson.has("actors")) {
                        movie.getActors().clear();
                        for (String c : filmJson.getJSONObject("actors").getString("value").split(",\\&")) {
                            movie.addActor(c, null);
                        }
                    }
                    if (filmJson.has("directors")) {
                        movie.getDirectors().clear();
                        for (String c : filmJson.getJSONObject("directors").getString("value").split(",\\&")) {
                            movie.addDirector(c);
                        }
                    }
                    if (filmJson.has("producers")) {
                        movie.getProducers().clear();
                        for (String c : filmJson.getJSONObject("producers").getString("value").split(",\\&")) {
                            movie.addProducer(c);
                        }
                    }
                    if (filmJson.has("writers")) {
                        movie.getWriters().clear();
                        for (String c : filmJson.getJSONObject("writers").getString("value").split(",\\&")) {
                            movie.addWriter(c);
                        }
                    }
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> GetBirthdayChildren()
    {
        String query = "PREFIX ns: <http://rdf.freebase.com/ns/> \n" +
                "PREFIX key: <http://rdf.freebase.com/key/> \n" +
                "SELECT DISTINCT (?film as ?mid)\n" +
                "FROM <http://fmb.org>\n" +
                "WHERE { ?film ns:type.object.type ns:film.film. \n" +
                "?film ns:film.film.release_date_s / ns:film.film_regional_release_date.release_date ?rd.\n" +
                "?film ns:award.award_winning_work.awards_won / ns:award.award_honor.award / ns:award.award_category.category_of ns:m.0g_w.\n" +
                "FILTER( !bif:isNull(xsd:date(?rd)) && ( ?rd >= \"1950-01-01\"^^xsd:dateTime ) && \n" +
                "bif:dayofmonth(?rd) = bif:dayofmonth(bif:curdate('')) && \n" +
                "bif:month(?rd) = bif:month(bif:curdate('')))} ";

        return getMovies(evalQueryResult(query));
    }

    private void addMovieToCache(Movie mov)
    {
        if(!movieCache.keySet().contains(mov.getmID()))  //not!!
            movieCache.put(mov.getmID(), mov);
    }
}
