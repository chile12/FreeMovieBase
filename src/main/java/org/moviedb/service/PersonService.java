/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moviedb.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.moviedb.domain.Person;
import org.moviedb.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Ramon Bernert
 */
public class PersonService implements IPersonService {
    
	/*public PersonService(PersonRepository personRepository){
		this.personRepository = personRepository;
	}*/
	
	@Autowired
	private PersonRepository personRepository;
	
    private HashMap<Integer, Person> persons = new  HashMap<Integer, Person>();

    public void addPerson(Person person){
    	persons.put(person.getId(), person);
    }
    
    public Person getPerson(int id){
    	//Person p = persons.get(id);
    	
    	Person person = personRepository.getById(id);
    	
    	person.setEvents("[{&quot;date&quot;: &quot;1991-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 1, &quot;producer&quot;: 0}, {&quot;date&quot;: &quot;1992-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 1, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;1993-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;1994-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;1995-01-01&quot;, &quot;movies&quot;: 5, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;1996-01-01&quot;, &quot;movies&quot;: 2, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;1997-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;1998-01-01&quot;, &quot;movies&quot;: 2, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;1999-01-01&quot;, &quot;movies&quot;: 0, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2000-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2001-01-01&quot;, &quot;movies&quot;: 0, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2002-01-01&quot;, &quot;movies&quot;: 2, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2003-01-01&quot;, &quot;movies&quot;: 0, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2004-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 0, &quot;producer&quot;: 2},{&quot;date&quot;: &quot;2005-01-01&quot;, &quot;movies&quot;: 0, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2006-01-01&quot;, &quot;movies&quot;: 2, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2007-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 0, &quot;producer&quot;: 2},{&quot;date&quot;: &quot;2008-01-01&quot;, &quot;movies&quot;: 2, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2009-01-01&quot;, &quot;movies&quot;: 0, &quot;tv&quot;: 0, &quot;producer&quot;: 1},{&quot;date&quot;: &quot;2010-01-01&quot;, &quot;movies&quot;: 2, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2011-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 0, &quot;producer&quot;: 2},{&quot;date&quot;: &quot;2012-01-01&quot;, &quot;movies&quot;: 1, &quot;tv&quot;: 0, &quot;producer&quot;: 0},{&quot;date&quot;: &quot;2013-01-01&quot;, &quot;movies&quot;: 2, &quot;tv&quot;: 0, &quot;producer&quot;: 3},{&quot;date&quot;: &quot;2014-01-01&quot;, &quot;movies&quot;: 0, &quot;tv&quot;: 0, &quot;producer&quot;: 0}]");
    	
    	return person;
    }
    
    public List<Person> getPersons(int start, int end){
    	
    	/*List<Person> tempPersons = new ArrayList<Person>();
    	
    	int i = 0;
    	for(Person p : persons.values().toArray(new Person[persons.size()])){
    		
    		if(i >= start && i <= end)
    			tempPersons.add(p);
    		
    		i++;
    	}*/
    	
    	List<Person> tempPersons = personRepository.getPersons();
    	
    	return tempPersons;
    }
    
    public long getPersonsSize(){
    	return personRepository.getSize();
    	//return persons.size();
    }
    
    public void crawlePerson(String mID){
    	
    	try {
    		String query = "[{\"name\": null, \"mid\": \"%s\", " + 
    						"\"/people/person/date_of_birth\": null," +
    						"\"/people/person/gender\": null," +
							"\"type\": \"/film/actor\"}]";
    		query = String.format(query, mID);
    		query = URLEncoder.encode(query, "UTF-8");
    		query = "https://www.googleapis.com/freebase/v1/mqlread/?lang=%2Flang%2Fen&query=" + query;
    		
    		String resultString = getResponse(query);
    		
    		JSONObject obj = new JSONObject(resultString);
        	JSONArray arr = obj.getJSONArray("result");
        	
        	if(arr.length() > 0){
	        	Person person = createPerson(arr.getJSONObject(0));
	        	
	        	updateImage(person);
	        	
	        	personRepository.savePerson(person);
        	}
    		
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public List<Person> crawlePersons(String search){
    	
    	List<Person> persons = new ArrayList<Person>();
    	
    	try {
	    	String query = "[{\"name~=\": \"%s*\"," + 
	    					"\"name\": null," +
	    					"\"mid\": null," +
	    					"\"/people/person/date_of_birth\": null," +
	    					"\"/people/person/gender\": null," +
	    					"\"type\": \"/film/actor\"}]";
	    	
	    	query = String.format(query, search);
	    	query = URLEncoder.encode(query, "UTF-8");
			query = "https://www.googleapis.com/freebase/v1/mqlread/?lang=%2Flang%2Fen&query=" + query;
	    	
	    	JSONObject obj = new JSONObject(getResponse(query));
	    	JSONArray arr = obj.getJSONArray("result");
	    	
	    	for(int i = 0 ; i < arr.length(); i++){
	    		Person p = createPerson(arr.getJSONObject(i));
	    		
	    		personRepository.savePerson(p);
	    		persons.add(p);
			}
    	} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return persons;
    }
    
    private String getResponse(String targetUrl) throws IOException{
    	URL url;
    	HttpURLConnection connection = null;
    	
    	String result = "";
    	
          //Create connection
          url = new URL(targetUrl);
          connection = (HttpURLConnection)url.openConnection();
          connection.setRequestMethod("GET");

          connection.setUseCaches (false);
          connection.setDoInput(true);
          connection.setDoOutput(true);

          //Get Response    
          InputStream is = connection.getInputStream();
          BufferedReader rd = new BufferedReader(new InputStreamReader(is));
          String line;
          StringBuffer response = new StringBuffer(); 
          while((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
          }
          
          rd.close();
          
          result = response.toString();

            if(connection != null) {
              connection.disconnect(); 
            }
        
        return result;
    }
    
    private Person createPerson(JSONObject result){
    	Person p = new Person();
    	p.setName(result.getString("name"));
    	p.setmID(result.getString("mid"));
    	if(!result.isNull("/people/person/date_of_birth")){
    		
    		String birthday = result.getString("/people/person/date_of_birth");
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	 
    		try {
    			p.setBirthday(formatter.parse(birthday));
    	 
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    	}
    		
    	p.setGender(result.getString("/people/person/gender"));
    	
    	return p;
    }
    
    private void updateImage(Person person){
    	
    	try {
    		String query = "[{\"mid\": \"%s\", " + 
    						"\"/common/topic/image\": [{"+
    							"\"id\": null" +
    							"}],"+
							"\"type\": \"/film/actor\"}]";
    		query = String.format(query, person.getmID());
    		query = URLEncoder.encode(query, "UTF-8");
    		query = "https://www.googleapis.com/freebase/v1/mqlread/?lang=%2Flang%2Fen&query=" + query;
    		
    		JSONObject obj = new JSONObject(getResponse(query));
        	JSONArray arr = obj.getJSONArray("result");
        	
        	if(arr.length() > 0){
	        	JSONObject result = arr.getJSONObject(0);
	        	
	        	JSONArray imgArray = result.getJSONArray("/common/topic/image");
	        	
	        	for(int i = 0 ; i < imgArray.length(); i++){
	        		
	        		String imgId = imgArray.getJSONObject(i).getString("id");
	        		
	        		if(imgId.startsWith("/wikipedia/images/commons_id/")){
	        			String imgUrl = "https://www.googleapis.com/freebase/v1/image/wikipedia/images/commons_id/%s?key=AIzaSyCQVC9yA72POMg2VjiQhSJQQP1nf3ToZTs&maxwidth=1500";
	        			
	        			imgUrl = String.format(imgUrl, imgId.replace("/wikipedia/images/commons_id/", ""));
	        			
	        			person.setImagePath(imgUrl);
	        			
	        			break;
	        		}
	        	}
	        	
	        	personRepository.savePerson(person);
        	}
    		
    	} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
