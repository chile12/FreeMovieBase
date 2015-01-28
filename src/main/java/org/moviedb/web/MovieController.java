package org.moviedb.web;

import org.moviedb.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movies")
public class MovieController {
	
	@Autowired
	private IMovieService movieService;
	
	@RequestMapping("")
    public String getMovies(Model model) {
		
		model.addAttribute("movies", movieService.getMovies(0, 0));
		
		return "movies";
	}
}
