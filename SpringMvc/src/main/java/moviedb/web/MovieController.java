package moviedb.web;
import javax.servlet.http.HttpServletRequest;

import moviedb.service.PersonService;

import org.json.JSONArray;
import org.json.JSONObject;

import moviedb.domain.Movie;
import moviedb.domain.Person;
import moviedb.service.BaseService;
import moviedb.service.IMovieService;
import moviedb.service.IPersonService;
import moviedb.service.IWidgetService;

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
        movieService.LoadAdditionalInformations(movie);
        model.addAttribute("item", movie);
        model.addAttribute("movie", movie);
        model.addAttribute("widgets", widgetService.getMovieWidgets());

        return "movie";
    }

    @RequestMapping("")
    public String getMovies(Model model) {

        model.addAttribute("movies", movieService.getMoviesByAward("m.0g_w", 2013));

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
        actor1Json.put("image", actor1.getImagePath());

        JSONObject actor2Json = new JSONObject();

        actor2Json.put("id", actor2.getmID());
        actor2Json.put("name", actor2.getName());
        actor2Json.put("type", "actor2");
        actor2Json.put("image", actor2.getImagePath());

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
            movieJson.put("image", movie.getImagePath());

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
    
    @RequestMapping("/getCountries")
    public ResponseEntity<String> getMovieCountries(@RequestParam(value="uri", required=true) String uri, Model model) {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
    	
        Movie movie = movieService.getMovie(uri);
        movieService.LoadAdditionalInformations(movie);
        
        JSONObject fillJson = new JSONObject();
        fillJson.put("fillKey", "MEDIUM");
        
        JSONObject countriesJson = new JSONObject();
        
        countriesJson.put("RUS", fillJson);
        
        for(String country : movie.getCountries()){
        	//countriesJson.put(country);
        }
    	
    	return new ResponseEntity<String>(countriesJson.toString(), headers, HttpStatus.OK);
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
    
    @RequestMapping(value = "/getImages")
    public ResponseEntity<String> getImages(@RequestParam(value="uris", required=true) String uris, @RequestParam(value="count", defaultValue="0") int count) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        JSONArray arr = new JSONArray(uris);
        
        JSONArray images = new JSONArray();
        
        for(int i = 0; i< arr.length();i++){
        	String id = arr.getString(i);
        	
        	Movie m = new Movie();
        	
        	m.setmID(id);
        	
        	List<String> lis = BaseService.getImageUrls(m, true);
        	
    		JSONObject obj = new JSONObject();
    		
    		obj.put("id", id);
    		
    		if(lis.size() == 0){
    			
        		obj.put("image", "/resources/images/gallery.gif");
        	}
    		else {
    			String image = lis.get(0);
    			obj.put("image", image);
    		}
    		
    		images.put(obj);
        }
        
        return new ResponseEntity<String>(images.toString(), headers, HttpStatus.OK);
    }
}
