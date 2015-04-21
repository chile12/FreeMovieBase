package moviedb.web;

import org.json.JSONArray;
import org.json.JSONObject;
import moviedb.domain.Movie;
import moviedb.domain.Person;
import moviedb.service.IMovieService;
import moviedb.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private IPersonService personService;
	
	@Autowired
	private IMovieService movieService;
	
	@RequestMapping("")
    public String getHome(@RequestParam(value="id", required=false, defaultValue = "0") int id, Model model) {
		
		List<Person> persons = personService.getPersonsByAward("0g_w", 2012);
		List<Movie> movies = movieService.getMoviesByAward("0g_w", 2012);
		
		model.addAttribute("persons", persons.subList(0, 4));
		model.addAttribute("movies", movies.subList(0, 4));
		
		return "index";
	}
	
	@RequestMapping(value = "/search")
	public ResponseEntity<String> getSearch(@RequestParam(value="term", required=true) String term) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        List<Person> persons = personService.search(term, 10);
        
        JSONArray arr = new JSONArray();
        
        for(Person person : persons){
        	JSONObject personJson = new JSONObject();
        	
        	personJson.put("uri", person.getmID());
        	personJson.put("label", person.getName());
        	personJson.put("type", "person");
        	
        	arr.put(personJson);
        }
        
        List<Movie> movies = movieService.search(term, 10);
        
        for(Movie movie : movies){
        	JSONObject movieJson = new JSONObject();
        	
        	movieJson.put("uri", movie.getmID());
        	movieJson.put("label", movie.getTitle());
        	movieJson.put("type", "movie");
        	
        	arr.put(movieJson);
        }

        return new ResponseEntity<String>(arr.toString(), headers, HttpStatus.OK);
	}
}
