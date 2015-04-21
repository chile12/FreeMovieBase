package org.freemoviebase.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.freemoviebase.domain.Movie;
import org.freemoviebase.service.IMovieService;
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
        
		List<Movie> movies = movieService.getMoviesByActors(uriActor1, uriActor2);
		
		JSONArray arr = new JSONArray();
		
		for(Movie movie : movies) {
			
			JSONObject movieJson = new JSONObject();
			
			movieJson.append("uri", movie.getmID());
			movieJson.append("title", movie.getTitle());
			
			arr.put(movieJson);
		}

        return new ResponseEntity<String>(arr.toString(), headers, HttpStatus.OK);
	}
}
