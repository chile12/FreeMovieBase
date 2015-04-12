/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moviedb.web;

import java.io.IOException;
import java.util.List;

import org.moviedb.domain.Person;
import org.moviedb.service.IPersonService;
import org.moviedb.service.IWidgetService;
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
}
