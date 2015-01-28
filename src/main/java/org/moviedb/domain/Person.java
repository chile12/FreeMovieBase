/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moviedb.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Ramon Bernert
 */
@SuppressWarnings("serial")
@Entity
@Table(name="persons")
@NamedQueries({
	@NamedQuery(name = Person.GET_BY_ID, query = "select p from Person p where p.id = :id"),
	@NamedQuery(name = Person.FIND_BY_NAME, query = "select p from Person p where p.name = :name"),
	@NamedQuery(name = Person.FIND_BY_MID, query = "select p from Person p where p.mID = :mID")
}) 
public class Person implements java.io.Serializable {
	
	public Person() {
		
	}
	
	public Person(Integer id){
		this.id = id;
	}

	public static final String GET_BY_ID = "Person.getById";
	public static final String FIND_BY_NAME = "Person.findByName";
	public static final String FIND_BY_MID = "Person.findByMID";
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PersonID")
	private Integer id;
	@Column
	private String name;
	@Column
    private String imagePath;
	@Column
    private String description;
	@Transient
    private String events;
    @Column
    private Date birthday;
    @Column
    private String mID;
    @Column
    private String gender;
    
    public Integer getId() {
		return id;
	}
    
    public void setId(int id){
    	this.id = id;
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    /**
     * @return the _imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param _imagePath the _imagePath to set
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * @return the events
     */
    public String getEvents() {
        return events.replace("&quot;", "\"");
    }

    /**
     * @param events the events to set
     */
    public void setEvents(String events) {
        
        events = events.replace("&quot;", "\"");
        
        this.events = events;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

	public String getmID() {
		return mID;
	}

	public void setmID(String mID) {
		this.mID = mID;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
