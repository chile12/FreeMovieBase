/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviedb.domain;

import moviedb.service.PersonService;

import java.util.ArrayList;
import java.util.Comparator;
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
        music_contributor,
        film_subject
    }

    public enum Sex{
        Male,
        Female
    }

	public Person(PersonService service) {
		this.service = service;
	}

    private PersonService service;
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
    private List<Merriage> merrs;
    private List<String> languages =  new ArrayList<String>();
    private List<String> moviesActor = new ArrayList<String>();
    private List<String> moviesDirector =  new ArrayList<String>();
    private List<String> moviesWriter =  new ArrayList<String>();
    private List<String> moviesProducer =  new ArrayList<String>();
    private List<String> moviesMusicer =  new ArrayList<String>();
    
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

    public Double getWeight() {
        return wightKg;
    }

    public void setWeight(Double wightKg) {
        this.wightKg = wightKg;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public List<Person> getParents() {
        return service.resolveMidList(parents);
    }

    public void addParent(String mid){parents.add(mid);}

    public List<Person> getChildren() {
        return service.resolveMidList(children);
    }

    public void addChild(String mid){children.add(mid);}

    public List<Merriage> getMerriages() {
        if(merrs == null)
            merrs = service.createMerriages(this, merriages);
        return merrs;
    }

    public void addMerriage(String mid){merriages.add(mid);}

    public List<String> getLanguages() {
        return languages;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public List<Person> getSiblings() {
        return service.resolveMidList(siblings);
    }

    public void addSibling(String mid)
    {
        siblings.add(mid);
    }

    public List<String> getMoviesActor() {
        return moviesActor;
    }

    public List<String> getMoviesDirector() {
        return moviesDirector;
    }

    public List<String> getMoviesWriter() {
        return moviesWriter;
    }

    public List<String> getMoviesProducer() {
        return moviesProducer;
    }

    public List<String> getMoviesMusicer() {
        return moviesMusicer;
    }

    public static Comparator<Person> PersonComperator = new Comparator<Person>() {
        @Override
        public int compare(Person o1, Person o2) {
            return o2.getAwardCount() - o1.getAwardCount();
        }
    };
}
