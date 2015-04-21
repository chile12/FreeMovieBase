/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviedb.service;

import org.json.JSONArray;
import org.json.JSONObject;
import moviedb.domain.Person;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ramon Bernert
 */
public class PersonService extends BaseService implements IPersonService {


    private static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public Person getPerson(String uri){
    	
    	Person person = null;
    	
    	try {
    		String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
    				"SELECT DISTINCT ?actorName ?actorDes ?actor ?dateOfBirth ?placeOfBirth ?dateOfDeath ?placeOfDeath ?gender " +
    				"FROM <http://fmb.org> " +
    				"WHERE { " +
    				"?actor ns:type.object.name ?actorName. " +
    				"OPTIONAL{?actor ns:common.topic.description ?actorDes.} " +
    				"OPTIONAL{?actor ns:people.person.date_of_birth ?dateOfBirth.} " +
                    "OPTIONAL{?actor ns:people.person.date_of_death ?dateOfDeath.} " +
                    "OPTIONAL{?actor ns:people.person.place_of_birth/ns:type.object.name ?placeOfBirth.} " +
                    "OPTIONAL{?actor ns:people.person.place_of_death/ns:type.object.name ?placeOfDeath.} " +
    				"OPTIONAL{?actor ns:people.person.gender/ns:type.object.name ?gender.} " +
    				"FILTER(?actor = ns:%s)}";
        	query = String.format(query, "m." + uri);
			query = URLEncoder.encode(query, "UTF-8");
			
			query = baseQueryUrl + query;
			
			System.out.println(query);
			
			String resultString = getResponse(query);
    		
			System.out.println(resultString);
			
    		JSONObject result = new JSONObject(resultString);
    		JSONArray arr = result.getJSONObject("results").getJSONArray("bindings");

            if(arr.length() > 0) {
                JSONObject actorJson = arr.getJSONObject(0);
                person = createPerson(actorJson, true);
            }
    		
    	} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//person.setEvents("[{&quot;date&quot;: &quot;1991-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 1, &quot;producer&quot;: 0}, {&quot;date&quot;: &quot;1992-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 1, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;1993-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;1994-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;1995-01-01&quot;, &quot;movies&quot;: 5, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;1996-01-01&quot;, &quot;movies&quot;: 2, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;1997-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;1998-01-01&quot;, &quot;movies&quot;: 2, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;1999-01-01&quot;, &quot;movies&quot;: 0, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2000-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2001-01-01&quot;, &quot;movies&quot;: 0, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2002-01-01&quot;, &quot;movies&quot;: 2, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2003-01-01&quot;, &quot;movies&quot;: 0, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2004-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 0, &quot;producer&quot;: 2},{&quot;date&quot;: &quot;2005-01-01&quot;, &quot;movies&quot;: 0, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2006-01-01&quot;, &quot;movies&quot;: 2, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2007-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 0, &quot;producer&quot;: 2},{&quot;date&quot;: &quot;2008-01-01&quot;, &quot;movies&quot;: 2, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2009-01-01&quot;, &quot;movies&quot;: 0, &quot;tv&quot;: 0, &quot;producer&quot;: 1},{&quot;date&quot;: &quot;2010-01-01&quot;, &quot;movies&quot;: 2, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2011-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 0, &quot;producer&quot;: 2},{&quot;date&quot;: &quot;2012-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2013-01-01&quot;, &quot;movies&quot;: 2, &quot;tv&quot;: 0, &quot;producer&quot;: 3},{&quot;date&quot;: &quot;2014-01-01&quot;, &quot;movies&quot;: 0, &quot;tv&quot;: 0, &quot;producer&quot;: 0}]");
    	
    	return person;
    }
    
    public List<Person> getPersonsByAward(String uri, int year){
    	
		String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
				"SELECT DISTINCT ?actorName ?actor " +
				"FROM <http://fmb.org> " +
				"WHERE { " +
				"?actor ns:type.object.type ns:film.actor. " +
				"?actor ns:type.object.name ?actorName. " +
				"?actor ns:common.topic.image ?image. " + 
				"?actor ns:award.award_winner.awards_won ?awardHonor. " +
				"?awardHonor ns:award.award_honor.year ?year. " +
				"?awardHonor ns:award.award_honor.award ?awardCategory. " + 
				"?awardCategory ns:award.award_category.category_of ?award. " +
				"?award ns:type.object.name ?awardName. " +
				"FILTER(?award = ns:%s  && YEAR(?year) >= %s)} " +
				"GROUP BY ?actor ?actorName ?year " +
				"ORDER BY DESC(?year) " +
				"LIMIT 100";
    	query = String.format(query, "m." + uri, year);
	
    	return getPersons(query);
    }
    
    public List<Person> search(String term, int count){
    	
    	String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
				"SELECT DISTINCT ?actorName ?actor " +
				"FROM <http://fmb.org> " +
				"WHERE { " +
				"?actor ns:type.object.type ns:film.actor. " +
				"?actor ns:type.object.name ?actorName. " +
				"FILTER (REGEX(STR(?actorName), \"%s\", \"i\"))}" +
				"ORDER BY (?actorName) " +
				"LIMIT " + count;
    	query = String.format(query, term);
	
    	return getPersons(query);
    }
    
    private List<Person> getPersons(String query){
    	List<Person> persons = new ArrayList<Person>();

    	try {
			query = URLEncoder.encode(query, "UTF-8");
			
			query = baseQueryUrl + query;
			
			System.out.println(query);
			
			String resultString = getResponse(query);
    		
			System.out.println(resultString);
			
    		JSONObject result = new JSONObject(resultString);
        	JSONArray arr = result.getJSONObject("results").getJSONArray("bindings");
        	
    		for(int i = 0 ; i < arr.length(); i++){
    			
    			JSONObject actorJson = arr.getJSONObject(i);
    			
    			Person person = createPerson(actorJson, 1);
    			
    			persons.add(person);
    		}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return persons;
    }
    
    private Person createPerson(JSONObject actorJson, boolean getImages){
    	return createPerson(actorJson, getImages ? Integer.MAX_VALUE : 0);
    }
    
    private Person createPerson(JSONObject actorJson, int getImagesCount){
    	
    	Person person = new Person();
    	
    	String mID = actorJson.getJSONObject("actor").getString("value");
		mID = mID.replace("http://rdf.freebase.com/ns/m.", "");
		person.setmID(mID);
    	
    	if(actorJson.has("actorName")){
    		person.setName(actorJson.getJSONObject("actorName").getString("value"));
    	}
		
    	if(actorJson.has("actorDes")){
    		person.setDescription(actorJson.getJSONObject("actorDes").getString("value"));
    	}
		
		//person.setGender(actorJson.getJSONObject("gender").getString("value"));
		
    	if(actorJson.has("dateOfBirth")){
			try {
				
				String birthString = actorJson.getJSONObject("dateOfBirth").getString("value");
				
				person.setBirthday(dateFormatter.parse(birthString));
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}

        if(actorJson.has("dateOfDeath")){
            try {

                String birthString = actorJson.getJSONObject("dateOfDeath").getString("value");

                person.setDeathday(dateFormatter.parse(birthString));

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		
    	if(actorJson.has("placeOfBirth")){
    		person.setPlaceOfBirth(actorJson.getJSONObject("placeOfBirth").getString("value"));
    	}


        if(actorJson.has("placeOfDeath")){
            person.setPlaceOfBirth(actorJson.getJSONObject("placeOfDeath").getString("value"));
        }
    	
    	if(actorJson.has("gender")){
    		person.setGender(actorJson.getJSONObject("gender").getString("value"));
    	}
		
    	if(getImagesCount > 0){
			person.getImagePaths().addAll(getImageUrls(person, getImagesCount));
			
			if(person.getImagePaths().size() > 0)
				person.setImagePath(person.getImagePaths().get(0));
    	}
		
		return person;
    }

	private List<String> getImageUrls(Person person, int imageCachingCount){
		
		String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
				"SELECT DISTINCT (CONCAT(CONCAT(\"https://usercontent.googleapis.com/freebase/v1/image/\", REPLACE(SUBSTR(str(?image), bif:strrchr(str(?image), '/')+2), '\\\\.', '\\\\/')), '?maxwidth=1000&maxheight=1000&mode=fit') as ?imageurl) " +
				"FROM <http://fmb.org> " +
				"WHERE {?actor ns:type.object.type ns:film.actor. " +
				"?actor ns:common.topic.image ?image. " +
				"FILTER (?actor = ns:%s)}";

		query = String.format(query, "m." + person.getmID());
		
		return getImageUrls(query, imageCachingCount);
	}
	
	public String getAvardsCountJson(String uri) throws IOException{
		
		String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
				"SELECT DISTINCT ?year (COUNT(*) AS ?count) " +
				"FROM <http://fmb.org> " +
				"WHERE { " +
				"?actor ns:type.object.type ns:film.actor. " +
				"?actor ns:type.object.name ?actorName. " +
				"?actor ns:common.topic.image ?image. " + 
				"?actor ns:award.award_winner.awards_won ?awardHonor. " +
				"?awardHonor ns:award.award_honor.year ?year. " +
				"?awardHonor ns:award.award_honor.award ?awardCategory. " + 
				"?awardCategory ns:award.award_category.category_of ?award. " +
				"?award ns:type.object.name ?awardName. " +
				"FILTER (?actor = ns:%s)} " +
				"GROUP BY ?year " +
				"ORDER BY DESC(?year) " +
				"LIMIT 100";
    	query = String.format(query, "m." + uri);
    	
    	query = URLEncoder.encode(query, "UTF-8");
		
		query = baseQueryUrl + query;
		
		return getResponse(query);
	}
}
