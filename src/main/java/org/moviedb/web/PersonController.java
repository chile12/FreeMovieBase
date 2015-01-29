/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moviedb.web;

import java.util.ArrayList;
import java.util.List;

import org.moviedb.domain.Person;
import org.moviedb.repository.PersonRepository;
import org.moviedb.service.IPersonService;
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
    
	private int stepWidth = 20;
	
	@RequestMapping("")
    public String getPersons(@RequestParam(value="step", required=false, defaultValue = "0") int step, Model model) {
		
		int start = step * stepWidth;
		int end = start + stepWidth;
		
		List<Person> persons = personService.getPersons(start, end);
		
		model.addAttribute("persons", persons);
		model.addAttribute("step", step);
		model.addAttribute("morePersons", personService.getPersonsSize() > end);
		return "persons";
	}
	
    @RequestMapping("/get")
    public String getPerson(@RequestParam(value="id", required=true) int id, Model model) {
    	Person person = personService.getPerson(id);
    	
    	model.addAttribute("person", person);
    	
    	List<String> widgetScripts = new ArrayList<String>();
    	
    	widgetScripts.add("/resources/js/widgets/widget1.js");
    	widgetScripts.add("/resources/js/widgets/widget2.js");
    	widgetScripts.add("/resources/js/widgets/widget3.js");
    	
    	List<String> widgets = new ArrayList<String>();
    	
    	widgets.add("/jsp/widgets/widget1.jsp");
    	widgets.add("/jsp/widgets/widget2.jsp");
    	widgets.add("/jsp/widgets/widget3.jsp");
    	
    	model.addAttribute("scripts", widgetScripts);
    	model.addAttribute("widgets", widgets);
    	
    	return "person";
    }
    
    @RequestMapping(value = "/getEvents")
	public ResponseEntity<String> getEvents(@RequestParam(value="id", required=true) int id) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        Person person = personService.getPerson(id);

        return new ResponseEntity<String>(person.getEvents(), headers, HttpStatus.OK);
	}
    
    @RequestMapping("/update")
    public String updatePerson(@RequestParam(value="id", required=true) int id, Model model) {
    	
    	Person person = personService.getPerson(id);
    	
    	personService.crawlePerson(person.getmID());
    	
    	person = personService.getPerson(id);
    	
    	model.addAttribute("person", person);
    	return "person";
    }
    
    @RequestMapping(value = "/crawlePerson")
    public String crawlePerson(@RequestParam(value="search", required=true) String search, Model model){ 
    	List<Person> persons = this.personService.crawlePersons(search);
    	
    	model.addAttribute("persons", persons);
		model.addAttribute("step", 1);
		model.addAttribute("morePersons", false);
    	return "persons";
    }
}
