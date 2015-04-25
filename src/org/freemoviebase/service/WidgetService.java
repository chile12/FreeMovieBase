package org.freemoviebase.service;

import java.util.ArrayList;
import java.util.List;

import org.freemoviebase.domain.Widget;

public class WidgetService implements IWidgetService {
	
	public WidgetService(){
		
		Widget w1 = new Widget(1, "Details");
    	w1.setJspPath("/WEB-INF/jsp/widgets/personDetails.jsp");
    	
    	Widget w2 = new Widget(2, "Bilder");
    	w2.setJspPath("/WEB-INF/jsp/widgets/images.jsp");
    	
    	Widget w3 = new Widget(3, "Awards");
    	w3.setCssPath("/resources/css/widgets/personAwards.css");
    	w3.setScriptPath("/resources/js/widgets/personAwards.js");
    	
    	Widget w4 = new Widget(4, "Filme mit anderen Schauspielern");
    	w4.setCssPath("/resources/css/widgets/personMovie.css");
    	w4.setJspPath("/WEB-INF/jsp/widgets/personMovie.jsp");
    	w4.setScriptPath("/resources/js/widgets/personMovie.js");
    	w4.setAcceptedType("persons");
    	
    	personWidgets.add(w1);
    	personWidgets.add(w2);
    	personWidgets.add(w3);
    	personWidgets.add(w4);
    	
    	Widget w5 = new Widget(5, "Details");
    	w5.setJspPath("/WEB-INF/jsp/widgets/movieDetails.jsp");
    	
    	Widget w6 = new Widget(6, "Irgendwas");
    	w6.setCssPath("/resources/css/widgets/personMovie.css");
    	w6.setJspPath("/WEB-INF/jsp/widgets/moviePerson.jsp");
    	w6.setScriptPath("/resources/js/widgets/moviePerson.js");
    	w6.setAcceptedType("movies");
    	
    	movieWidgets.add(w5);
    	movieWidgets.add(w2);
    	movieWidgets.add(w6);
	} 
	
	private List<Widget> personWidgets = new ArrayList<Widget>();
	private List<Widget> movieWidgets = new ArrayList<Widget>();
	
	public List<Widget> getPersonWidgets(){
    	return personWidgets;
	}
	
	public List<Widget> getMovieWidgets(){
		return movieWidgets;
	}
}
