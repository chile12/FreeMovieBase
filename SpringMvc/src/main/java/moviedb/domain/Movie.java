package moviedb.domain;

import java.util.*;

public class Movie extends Topic {
	
	public Movie(){
		
	}
	private Date releaseDate;
    private String tagline;
    private String series;
    private Double runtime;
    private Double budget;
    private Double revenue;

    private Map<String, String> actors = new HashMap<String, String>();
    private List<String> genres = new ArrayList<String>();
    private List<String> companies = new ArrayList<String>();
	private List<String> countries = new ArrayList<String>();

    public Map<String, String> getActors() {
        return actors;
    }

    public void setActors(Map<String, String> actors) {
        this.actors = actors;
    }

    public Date getReleaseDateGermany() {
        return releaseDate;
    }

    public void setReleaseDateGermany(Date releaseDate) {
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

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Double getRevenue() {
        return revenue;
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
}
