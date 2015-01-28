package org.moviedb.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.moviedb.domain.Person;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PersonRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Person getById(int id){
		try {
			return entityManager.createNamedQuery(Person.GET_BY_ID, Person.class)
					.setParameter("id", id)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	public Person findByName(String name) {
		try {
			return entityManager.createNamedQuery(Person.FIND_BY_NAME, Person.class)
					.setParameter("name", name)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	public Person findByMID(String mid) {
		try {
			return entityManager.createNamedQuery(Person.FIND_BY_MID, Person.class)
					.setParameter("mID", mid)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	public List<Person> getPersons(){
		try {
			return entityManager.createQuery("select p from Person p order by p.name", Person.class)
					//.setParameter("lastname", lastname)
					.getResultList();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	public long getSize(){
		TypedQuery<Long> query = entityManager.createQuery(
			      "SELECT COUNT(p) FROM Person p", Long.class);
		return query.getSingleResult();
	}
	
	@Transactional
	public void savePerson(Person person){
		
		Person temp = this.findByMID(person.getmID());
		
		if(temp == null){
			entityManager.persist(person);
		}
		else {
			person.setId(temp.getId());
			
			entityManager.merge(person);
		}
	}
}
