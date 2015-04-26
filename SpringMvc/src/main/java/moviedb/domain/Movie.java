package moviedb.domain;

import moviedb.service.PersonService;

import java.util.*;

public class Movie extends Topic {

	private Date releaseDate;
    private String tagline;
    private String series;
    private Double runtime;
    private Double budget;
    private Double revenue;

    private Map<String, String> actorMap = new HashMap<String, String>();
    private List<String> genres = new ArrayList<String>();
    private List<String> companies = new ArrayList<String>();
	private List<String> countries = new ArrayList<String>();

    private List<String> writers = new ArrayList<String>();
    private List<String> producers = new ArrayList<String>();
    private List<String> directors = new ArrayList<String>();

    public List<String> getActorMids() {
        return new ArrayList<String>(actorMap.keySet());
    }

    public void addActor(String uri, String CharcterName)
    {
        actorMap.put(uri, CharcterName);
    }

    public List<Person> getActors() {
        return PersonService.resolveMidList(new ArrayList(actorMap.keySet()));
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Double getRuntime() {
        return runtime;
    }

    public void setRuntime(Double runtime) {
        this.runtime = runtime;
    }

    public String getBudget() {
        return String.format("%.2f", budget);
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getRevenue() {
        return String.format( "%.2f", revenue );
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getCompanies() {
        return companies;
    }

    public void setCompanies(List<String> companies) {
        this.companies = companies;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<Person> getWriters() {
        return PersonService.resolveMidList(writers);
    }

    public void addWriter(String mid)
    {
        writers.add(mid);
    }

    public List<Person> getProducers() {
        return PersonService.resolveMidList(producers);
    }

    public void addProducer(String mid)
    {
        producers.add(mid);
    }

    public List<Person> getDirectors() {
        return PersonService.resolveMidList(directors);
    }

    public void addDirector(String mid)
    {
        directors.add(mid);
    }
}
