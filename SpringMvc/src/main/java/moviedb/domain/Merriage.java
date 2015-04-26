package moviedb.domain;

import moviedb.service.PersonService;

import java.util.Date;

/**
 * Created by Chile on 4/26/2015.
 */
public class Merriage {
    private Person spouse1;
    private Person spouse2;
    private Date from;
    private Date to;

    public Merriage(Person spouse1)
    {
        this.spouse1 = spouse1;
    }

    public Person getSpouse1() {
        return spouse1;
    }

    public void setSpouse1(Person spouse1) {
        this.spouse1 = spouse1;
    }

    public Person getSpouse2() {
        return spouse2;
    }

    public void setSpouse2(Person spouse2) {
        this.spouse2 = spouse2;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
