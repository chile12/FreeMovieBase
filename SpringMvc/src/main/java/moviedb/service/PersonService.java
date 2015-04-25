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
import java.text.ParseException;
import java.util.*;

/**
 *
 * @author Ramon Bernert
 */
public class PersonService extends BaseService implements IPersonService {

    private Map<String, Person> personCache = new HashMap<String, Person>();

    public Person getPerson(String mid){
        List<Person> movs = getPersons(Arrays.asList(mid));
        if(movs.size() > 0)
            return movs.get(0);
        return null;
    }
    
    public List<Person> getPersonsByAward(String mid, int year){
    	
		String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
				"PREFIX ns: <http://rdf.freebase.com/ns/> \n" +
                "SELECT DISTINCT (?actor as ?mid) \n" +
                "FROM <http://fmb.org> \n" +
                "WHERE { ?actor ns:type.object.type ns:film.actor. \n" +
                "?actor ns:award.award_winner.awards_won ?awardHonor. \n" +
                "?awardHonor ns:award.award_honor.year ?year. \n" +
                "?awardHonor ns:award.award_honor.award ?awardCategory. \n" +
                "?awardCategory ns:award.award_category.category_of ?award. \n" +
                "?award ns:type.object.name ?awardName.\n" +
                "FILTER(?award = ns:%s  && YEAR(?year) = %d)} \n" +
                "GROUP BY ?actor ?actorName ?year ORDER BY DESC(?year) ";
    	query = String.format(query, mid, year);
    	return getPersons(evalQueryResult(query));
    }
    
    public List<Person> search(String term, int count){
    	
    	String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
				"SELECT DISTINCT (?actor as ?mid) " +
				"FROM <http://fmb.org> " +
				"WHERE { " +
				"?actor ns:type.object.type ns:film.actor. " +
				"?actor ns:type.object.name ?actorName. " +
				"FILTER (REGEX(STR(?actorName), \"%s\", \"i\"))}" +
				"ORDER BY (?actorName) " +
				"LIMIT " + count;
    	query = String.format(query, term);
        return getPersons(evalQueryResult(query));
    }
    
    private List<Person> getPersons(List<String> mids){
    	List<Person> persons = new ArrayList<Person>();
        //extracts basic Informations about the person
        String query = "PREFIX ns: <http://rdf.freebase.com/ns/> \n" +
                "PREFIX key: <http://rdf.freebase.com/key/> \n" +
                "SELECT DISTINCT (?p as ?person) ?gender (group_concat(distinct (bif:subseq(str(?prof), bif:strrchr(str(?prof), '.')+1));separator=\",&\") as ?profession) (group_concat(distinct ?website;separator=\",&\") as ?websites) ?wikiId " +
                "   (COUNT(?nomi) as ?awards) ?name (group_concat(distinct ?country;separator=\",&\") as ?nationalities) (group_concat(distinct ?description;separator=\",&\") as ?descriptions) \n" +
                "                FROM <http://fmb.org> \n" +
                "                WHERE { \n" +
                "                ?p ns:type.object.type ?prof.\n" +
                "                ?p ns:type.object.name ?name. \n" +
                "                ?p key:wikipedia.en_id ?wikiId. \n" +
                "                OPTIONAL{?p ns:award.award_winner.awards_won ?nomi. }\n" +
                "                OPTIONAL{?p ns:common.topic.official_website ?website. }\n" +
                "                OPTIONAL{?p ns:common.topic.description ?description.  }\n" +
                "                OPTIONAL{?p ns:people.person.nationality / ns:type.object.name ?country .  }\n" +
                "                OPTIONAL{?p ns:people.person.gender / ns:type.object.name ?gender.  }\n" +
                "                FILTER ( ?p in ( ";
        for(String mid : mids)
        {
            query += "ns:" + mid + ",";
        }
        query = query.substring(0, query.length()-1) + "))} GROUP BY ?p ?name ?wikiId ?websites ?descriptions ?nationalities ?gender";
        try {
            JSONArray arr = getJSONArray(query);

            for(int i = 0 ; i < arr.length(); i++){
                JSONObject movieJson = arr.getJSONObject(i);
                persons.add(createPerson(movieJson));
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
    
    private Person createPerson(JSONObject json){
        String mID = json.getJSONObject("person").getString("value");
        mID = mID.replace("http://rdf.freebase.com/ns/", "");

        if(personCache.keySet().contains(mID))
            return personCache.get(mID);

        Person person = new Person();
        person.setmID(mID);

        if(json.has("name")) person.setTitle(json.getJSONObject("name").getString("value"));
        if(json.has("websites"))
        {
            String zw = json.getJSONObject("websites").getString("value");
            if(zw.contains(",&"))
                zw = zw.substring(0, zw.indexOf(",&"));
            person.setWebSite(zw);
        }
        if(json.has("description"))
        {
            String zw = json.getJSONObject("description").getString("value");
            String[] descs = zw.split(",\\&");
            zw = "";
            for(String st : descs)
                if(zw.length() < st.length())
                    zw = st;
            person.setWebSite(zw);
        }
        if(json.has("awards")) person.setAwardCount(json.getJSONObject("awards").getInt("value"));
        if(json.has("wikiId")) person.setWikiID(json.getJSONObject("wikiId").getString("value"));
        if (json.has("gender")) person.setGender(Person.Sex.valueOf(json.getJSONObject("gender").getString("value")));

        if(json.has("nationalities")){
            person.getNationalities().clear();
            for(String c : json.getJSONObject("nationalities").getString("value").split(",\\&"))
            {
                person.getNationalities().add(c);
            }
        }
        if(json.has("profession")){
            person.getNationalities().clear();
            for(String c : json.getJSONObject("profession").getString("value").split(",\\&"))
            {
                person.addProfession(Person.Profession.valueOf(c));
            }
        }
        addPersonToCache(person);
        return person;
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
    	query = String.format(query, uri);
    	
    	query = URLEncoder.encode(query, "UTF-8");
		return getResponse(BaseService.baseFreebaseQueryUrl + query);
	}

    public Person additionalInformations(Person movie)
    {
        //extracts additional Informations about the movie
        String query = "PREFIX ns: <http://rdf.freebase.com/ns/> \n" +
                "PREFIX key: <http://rdf.freebase.com/key/> \n" +
                "SELECT DISTINCT ?height ?weight ?birthdate ?birthplace ?deathdate ?deathplace ?causedeath (group_concat(distinct ?language;separator=\",&\") as ?languages)\n" +
                "(group_concat(distinct (bif:subseq(str(?parent), bif:strrchr(str(?parent), '.')+1));separator=\",&\") as ?parents) \n" +
                "(group_concat(distinct (bif:subseq(str(?child), bif:strrchr(str(?child), '.')+1));separator=\",&\") as ?children)\n" +
                "(group_concat(distinct (bif:subseq(str(?merriage), bif:strrchr(str(?merriage), '.')+1));separator=\",&\") as ?merriages) \n" +
                "(group_concat(distinct (bif:subseq(str(?sibling), bif:strrchr(str(?sibling), '.')+1));separator=\",&\") as ?siblings)\n" +
                "                FROM <http://fmb.org> \n" +
                "                WHERE { \n" +
                "                OPTIONAL{?p ns:people.deceased_person.place_of_death / ns:type.object.name ?deathplace.  }\n" +
                "                OPTIONAL{?p ns:people.person.place_of_birth / ns:type.object.name ?birthplace.  }\n" +
                "                OPTIONAL{?p ns:people.person.height_meters ?height.  }\n" +
                "                OPTIONAL{?p ns:people.person.weight_kg ?weight.  }\n" +
                "                OPTIONAL{?p ns:people.deceased_person.cause_of_death / ns:type.object.name ?causedeath.  }\n" +
                "                OPTIONAL{?p ns:people.deceased_person.date_of_death ?deathdate.  }\n" +
                "                OPTIONAL{?p ns:people.person.date_of_birth ?birthdate.  }\n" +
                "                OPTIONAL{?p ns:people.person.languages / ns:type.object.name ?language.  }\n" +
                "                OPTIONAL{?p ns:people.person.parents ?parent. }\n" +
                "                OPTIONAL{?p ns:people.person.children ?child. }\n" +
                "                OPTIONAL{?p ^ns:people.marriage.spouse ?merriage. }\n" +
                "                OPTIONAL{?p ^ns:people.sibling_relationship.sibling/ns:people.sibling_relationship.sibling ?sibling. }\n" +
                "                FILTER ( ?p = ns:%s)\n" +
                "}";
        query = String.format(query, movie.getmID());

        try {
            JSONArray arr = getJSONArray(query);

            for(int i =0; i < arr.length(); i++)
            {
                JSONObject json = arr.getJSONObject(i);
                if( i == 0) {
                    if (json.has("height")) movie.setHeight(json.getJSONObject("height").getDouble("value"));
                    if (json.has("weight")) movie.setWightKg(json.getJSONObject("weight").getDouble("value"));
                    if (json.has("deathplace")) movie.setPlaceOfDeath(json.getJSONObject("deathplace").getString("value"));
                    if (json.has("birthplace")) movie.setPlaceOfBirth(json.getJSONObject("birthplace").getString("value"));
                    if (json.has("causedeath")) movie.setCauseOfDeath(json.getJSONObject("causedeath").getString("value"));
                    if (json.has("deathdate")) movie.setDeathday(formatter.parse(json.getJSONObject("deathdate").getString("value")));
                    if (json.has("birthdate")) movie.setBirthday(formatter.parse(json.getJSONObject("birthday").getString("value")));

                    if (json.has("languages")) {
                        movie.getLanguages().clear();
                        for (String c : json.getJSONObject("languages").getString("value").split(",\\&")) {
                            movie.getLanguages().add(c);
                        }
                    }
                    if (json.has("parents")) {
                        movie.getParents().clear();
                        for (String c : json.getJSONObject("parents").getString("value").split(",\\&")) {
                            movie.getParents().add(c);
                        }
                    }
                    if (json.has("children")) {
                        movie.getChildren().clear();
                        for (String c : json.getJSONObject("children").getString("value").split(",\\&")) {
                            movie.getChildren().add(c);
                        }
                    }
                    if (json.has("merriages")) {
                        movie.getMerriages().clear();
                        for (String c : json.getJSONObject("merriages").getString("value").split(",\\&")) {
                            movie.getMerriages().add(c);
                        }
                    }
                    if (json.has("siblings")) {
                        movie.getSiblings().clear();
                        for (String c : json.getJSONObject("siblings").getString("value").split(",\\&")) {
                            movie.getSiblings().add(c);
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
        return movie;
    }

    public List<Person> getPersonByMovies(String uriMovie1, String uriMovie2){

        String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
                "SELECT DISTINCT (?actor as ?mid) " +
                "FROM <http://fmb.org> " +
                "WHERE { " +
                "?actor ns:type.object.type ns:film.actor. " +
                "?perf1 ns:film.performance.actor ?actor. " +
                "?perf2 ns:film.performance.actor ?actor. " +
                "?perf1 ns:film.performance.film ?film1. " +
                "?perf2 ns:film.performance.film ?film2. " +
                "FILTER (?film1 = ns:%s && ?film2 = ns:%s)}";


        return getPersons(evalQueryResult(query));

    }

    private void addPersonToCache(Person mov)
    {
        if(!personCache.keySet().contains(mov.getmID()))  //not!!
            personCache.put(mov.getmID(), mov);
    }
}
