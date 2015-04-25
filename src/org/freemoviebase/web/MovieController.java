package org.freemoviebase.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.freemoviebase.domain.Movie;
import org.freemoviebase.domain.Person;
import org.freemoviebase.service.IMovieService;
import org.freemoviebase.service.IPersonService;
import org.freemoviebase.service.IWidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/movies")
public class MovieController {
	
	@Autowired
	private IMovieService movieService;
	
	@Autowired
	private IWidgetService widgetService;
	
	@Autowired
	private IPersonService personService;
	
	@RequestMapping("/get")
    public String getMovie(@RequestParam(value="uri", required=true) String uri, HttpServletRequest request, Model model) {
		
		Movie movie = movieService.getMovie(uri);
		
		model.addAttribute("item", movie);
		model.addAttribute("movie", movie);
		model.addAttribute("widgets", widgetService.getMovieWidgets());
		
		return "movie";
	}
	
	@RequestMapping("")
    public String getMovies(Model model) {
		
		model.addAttribute("movies", movieService.getMoviesByAward("0g_w", 2012));
		
		return "movies";
	}
	
	@RequestMapping("byActors")
	public ResponseEntity<String> getMovies(@RequestParam(value="actor1", required=true) String uriActor1, @RequestParam(value="actor2", required=true) String uriActor2, Model model) {
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        Person actor1 = personService.getPerson(uriActor1);
        
        Person actor2 = personService.getPerson(uriActor2);
        
		List<Movie> movies = movieService.getMoviesByActors(uriActor1, uriActor2);
		
		JSONObject actor1Json = new JSONObject();
		
		actor1Json.put("id", actor1.getmID());
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
		
		actor1Json.put("children", moviesJson);

        return new ResponseEntity<String>(actor1Json.toString(), headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/search")
	public ResponseEntity<String> getSearch(@RequestParam(value="term", required=true) String term, @RequestParam(value="count", defaultValue="0") int count) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        if(count <= 0){
        	count = 10;
        }

        JSONArray arr = new JSONArray();
                
        List<Movie> movies = movieService.search(term, count);
        
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
