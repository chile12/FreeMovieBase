package org.moviedb.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movie extends Topic {
	
	public Movie(){
		
	}
	
	private String title;
	private String description;
	private Date releaseDateGermany;
	
	private List<String> countries = new ArrayList<String>();
	
	public String getTitle() {
	    return title;
	}
	
	public void setTitle(String title) {
	    this.title = title;
	}
	
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public Date getReleaseDateGermany() {
		return releaseDateGermany;
	}

	public void setReleaseDateGermany(Date releaseDateGermany) {
		this.releaseDateGermany = releaseDateGermany;
	}

	public List<String> getCountries() {
		return countries;
	}
}
