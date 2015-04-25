/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviedb.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ramon Bernert
 */
public class Person extends Topic {

    public enum Profession{
        actor,
        director,
        writer,
        producer,
        musiccontributor,
        film_subject
    }

    public enum Sex{
        Male,
        Female
    }

	public Person() {
		
	}

    private Date birthday;
    private Date deathday;
    private Sex gender;
    private String placeOfDeath;
    private String placeOfBirth;
    private String causeOfDeath;
    private Integer awardCount;
    private Double wightKg;
    private Double height;
    private String spouse;
    private List<Profession> profession = new ArrayList<Profession>();
    private List<String> nationalities = new ArrayList<String>();
    private List<String> parents =  new ArrayList<String>();
    private List<String> children =  new ArrayList<String>();
    private List<String> siblings =  new ArrayList<String>();
    private List<String> merriages =  new ArrayList<String>();
    private List<String> languages =  new ArrayList<String>();
    
	public String getName() {
		return this.getTitle();
	}

	public void setName(String name) {
		this.setTitle(name);
	}

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

	public Sex getGender() {
		return gender;
	}

	public void setGender(Sex gender) {
		this.gender = gender;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

    public Date getDeathday() {
        return deathday;
    }

    public void setDeathday(Date deathday) {
        this.deathday = deathday;
    }

    public String getPlaceOfDeath() {
        return placeOfDeath;
    }

    public void setPlaceOfDeath(String placeOfDeath) {
        this.placeOfDeath = placeOfDeath;
    }

    public List<Profession> getProfession() {
        return profession;
    }

    public void addProfession(Profession prof)
    {
        profession.add(prof);
    }

    public void setProfession(List<Profession> profession) {
        this.profession = profession;
    }

    public List<String> getNationalities() {
        return nationalities;
    }

    public void setNationalities(List<String> nationalities) {
        this.nationalities = nationalities;
    }

    public Integer getAwardCount() {
        return awardCount;
    }

    public void setAwardCount(Integer awardCount) {
        this.awardCount = awardCount;
    }

    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    public void setCauseOfDeath(String causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }

    public Double getWightKg() {
        return wightKg;
    }

    public void setWightKg(Double wightKg) {
        this.wightKg = wightKg;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public List<String> getParents() {
        return parents;
    }

    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    public List<String> getMerriages() {
        return merriages;
    }

    public void setMerriages(List<String> merriages) {
        this.merriages = merriages;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public List<String> getSiblings() {
        return siblings;
    }

    public void setSiblings(List<String> siblings) {
        this.siblings = siblings;
    }
}
