/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.freemoviebase.web;

import java.io.IOException;
import java.util.List;

import org.freemoviebase.domain.Movie;
import org.freemoviebase.domain.Person;
import org.freemoviebase.service.IPersonService;
import org.freemoviebase.service.IWidgetService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ramon Bernert
 */
@Controller
@RequestMapping("/persons")
public class PersonController {
	
	@Autowired
	private IPersonService personService;
	
	@Autowired
	private IWidgetService widgetService;
	
	@RequestMapping("")
    public String getPersons(Model model) {
		List<Person> persons = personService.getPersonsByAward("0g_w", 2012);
		
		model.addAttribute("persons", persons);
		return "persons";
	}
	
    @RequestMapping("/get")
    public String getPerson(@RequestParam(value="uri", required=true) String uri, Model model) {
    	
    	Person person = personService.getPerson(uri);
    	
    	model.addAttribute("item", person);
    	model.addAttribute("person", person);
    	
    	model.addAttribute("widgets", widgetService.getPersonWidgets());
    	
    	return "person";
    }
    
    @RequestMapping(value = "/getAvardsCount")
	public ResponseEntity<String> getAvardsCount(@RequestParam(value="uri", required=true) String uri) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        String json = "";
		try {
			json = personService.getAvardsCountJson(uri);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}
    
	@RequestMapping("byMovies")
	public ResponseEntity<String> getPersons(@RequestParam(value="actor1", required=true) String uriActor1, @RequestParam(value="actor2", required=true) String uriActor2, Model model) {
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        Person actor1 = personService.getPerson(uriActor1);
        
        Person actor2 = personService.getPerson(uriActor2);
        
		//List<Movie> movies = personService.getMoviesByActors(uriActor1, uriActor2);
		
		JSONObject actor1Json = new JSONObject();
		
		/*actor1Json.put("id", actor1.getmID());
		actor1Json.put("name", actor1.getName());
		actor1Json.put("type", "actor1");
		actor1Json.put("image", "/MovieDB" + actor1.getImagePath());
		
		JSONObject actor2Json = new JSONObject();
		
		actor2Json.put("id", actor2.getmID());
		actor2Json.put("name", actor2.getName());
		actor2Json.put("type", "actor2");
		actor2Json.put("image", "/MovieDB" + actor2.getImagePath());
		
		JSONObject dummy = new JSONObject();
		dummy.put("type", "dummy");
		
		JSONArray moviesJson = new JSONArray();
		
		JSONArray actor2JsonArray = new JSONArray();
		actor2JsonArray.put(actor2Json);
		
		JSONArray dummyJsonArray = new JSONArray();
		dummyJsonArray.put(dummy);
		
		int i = 0;
		
		for(Movie movie : movies) {
			
			JSONObject movieJson = new JSONObject();
			
			movieJson.put("id", movie.getmID());
			movieJson.put("name", movie.getTitle());
			movieJson.put("image", "/MovieDB" + movie.getImagePath());
			
			if(i == 0)
				movieJson.put("children", actor2JsonArray);
			else
				movieJson.put("children", dummyJsonArray);
			
			moviesJson.put(movieJson);
			
			i++;
		}
		
		actor1Json.put("children", moviesJson);*/

        return new ResponseEntity<String>(actor1Json.toString(), headers, HttpStatus.OK);
	}
    
    @RequestMapping(value = "/search")
	public ResponseEntity<String> getSearch(@RequestParam(value="term", required=true) String term, @RequestParam(value="count", defaultValue="0") int count) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        if(count <= 0){
        	count = 10;
        }
        
        List<Person> persons = personService.search(term, count);
        
        JSONArray arr = new JSONArray();
        
        for(Person person : persons){
        	JSONObject personJson = new JSONObject();
        	
        	personJson.put("uri", person.getmID());
        	personJson.put("label", person.getName());
        	personJson.put("type", "person");
        	
        	arr.put(personJson);
        }

        return new ResponseEntity<String>(arr.toString(), headers, HttpStatus.OK);
	}
}
