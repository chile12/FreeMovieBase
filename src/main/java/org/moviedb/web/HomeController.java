package org.moviedb.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/home")
public class HomeController {
	@RequestMapping("")
    public String getHome(@RequestParam(value="id", required=false, defaultValue = "0") int id, Model model) {
		
		model.addAttribute("id", id);
		
		return "index";
	}
	
	@RequestMapping(value = "/startJob")
	public ResponseEntity<String> startJob(@RequestParam(value="id") int id) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/text");
        
        id++;
        
        return new ResponseEntity<String>(Integer.toString(id), headers, HttpStatus.OK);
	}
}
