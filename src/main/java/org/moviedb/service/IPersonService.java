package org.moviedb.service;

import java.io.IOException;
import java.util.List;

import org.moviedb.domain.Person;

public interface IPersonService {

    public Person getPerson(String uri);
    
    public List<Person> getPersonsByAward(String uri, int year);
    
    public List<Person> search(String term, int count);
    
    public String getAvardsCountJson(String uri) throws IOException;
}
