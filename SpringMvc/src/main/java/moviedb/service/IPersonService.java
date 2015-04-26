package moviedb.service;

import java.io.IOException;
import java.util.List;

import moviedb.domain.Movie;
import moviedb.domain.Person;

public interface IPersonService {

    public Person getPerson(String uri);

    public List<Person> getPersonsByAward(String uri, int year);

    public List<Person> search(String term, int count);

    public String getAvardsCountJson(String uri) throws IOException;

    public List<Person> getPersonByMovies(String uriMovie1, String uriMovie2);

    public void LoadAdditionalInformations(Person person);

    public List<Person> resolveMidList(List<String> mids);
}
