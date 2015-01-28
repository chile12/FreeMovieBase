package org.moviedb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="movies")
@NamedQueries({
	@NamedQuery(name = Movie.GET_BY_ID, query = "select m from Movie m where m.id = :id"),
}) 
public class Movie implements java.io.Serializable {
	
	public Movie(){
		
	}
	
	public Movie(Integer id){
		this.id = id;
	}
	
	public static final String GET_BY_ID = "Movie.getById";
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MovieID")
	private Integer id;
	@Column
	private String title;
	
	 public Integer getId() {
			return id;
		}
	    
	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }
}
