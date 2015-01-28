package org.moviedb.service;

import java.util.List;

import org.moviedb.domain.Person;

public interface IPersonService {
    public void addPerson(Person person);
    
    public Person getPerson(int id);
    
    public List<Person> getPersons(int start, int end);
    
    public long getPersonsSize();
    
    public void crawlePerson(String mID);
    
    public List<Person> crawlePersons(String search);
}
