/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moviedb.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ramon Bernert
 */
public class Person extends Topic {
	
	public Person() {
		
	}
	
	private String name;
    private String description;
    private Date birthday;
    private String gender;
    private String placeOfBirth;
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
}
