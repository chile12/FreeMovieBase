/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviedb.web;

import java.io.IOException;
import java.util.List;

import moviedb.domain.Movie;
import moviedb.domain.Person;
import moviedb.service.IMovieService;
import moviedb.service.IPersonService;
import moviedb.service.IWidgetService;
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
    private IMovieService movieService;

    @Autowired
    private IWidgetService widgetService;

    @RequestMapping("")
    public String getPersons(Model model) {
        List<Person> persons = personService.getPersonsByAward("m.0g_w", 2013);

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
            int i =0;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new ResponseEntity<String>(json, headers, HttpStatus.OK);
    }

    @RequestMapping("byMovies")
    public ResponseEntity<String> getPersons(@RequestParam(value="movie1", required=true) String uriMovie1, @RequestParam(value="movie2", required=true) String uriMovie2, Model model) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        Movie movie1 = movieService.getMovie(uriMovie1);

        Movie movie2 = movieService.getMovie(uriMovie2);

        List<Person> persons = personService.getPersonByMovies(uriMovie1, uriMovie2);

        JSONObject movie1Json = new JSONObject();

        movie1Json.put("id", movie1.getmID());
        movie1Json.put("name", movie1.getTitle());
        movie1Json.put("type", "movie1");
        movie1Json.put("image", movie1.getImagePath());

        JSONObject movie2Json = new JSONObject();

        movie2Json.put("id", movie2.getmID());
        movie2Json.put("name", movie2.getTitle());
        movie2Json.put("type", "movie2");
        movie2Json.put("image", movie2.getImagePath());

        JSONObject dummy = new JSONObject();
        dummy.put("type", "dummy");

        JSONArray personsJson = new JSONArray();

        JSONArray movie2JsonArray = new JSONArray();
        movie2JsonArray.put(movie2Json);

        JSONArray dummyJsonArray = new JSONArray();
        dummyJsonArray.put(dummy);

        int i = 0;

        for(Person person : persons) {

            JSONObject movieJson = new JSONObject();

            movieJson.put("id", person.getmID());
            movieJson.put("name", person.getName());
            movieJson.put("image", person.getImagePath());

            if(i == 0)
                movieJson.put("children", movie2JsonArray);
            else
                movieJson.put("children", dummyJsonArray);

            personsJson.put(movieJson);

            i++;
        }

        movie1Json.put("children", personsJson);

        return new ResponseEntity<String>(movie1Json.toString(), headers, HttpStatus.OK);
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
