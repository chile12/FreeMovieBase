package org.moviedb.web;

import javax.servlet.http.HttpServletRequest;

import org.moviedb.domain.Movie;
import org.moviedb.service.IMovieService;
import org.moviedb.service.IWidgetService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
