package org.moviedb.service;

import java.util.ArrayList;
import java.util.List;

import org.moviedb.domain.Widget;

public class WidgetService implements IWidgetService {
	
	public WidgetService(){
		
		Widget w1 = new Widget(1, "Details");
    	w1.setJspPath("/jsp/widgets/personDetails.jsp");
    	
    	Widget w2 = new Widget(2, "Bilder");
    	w2.setJspPath("/jsp/widgets/images.jsp");
    	
    	Widget w3 = new Widget(3, "Awards");
    	w3.setCssPath("/resources/css/widgets/personAwards.css");
    	w3.setScriptPath("/resources/js/widgets/personAwards.js");
    	
    	personWidgets.add(w1);
    	personWidgets.add(w2);
    	personWidgets.add(w3);
    	
    	Widget w4 = new Widget(4, "Details");
    	w4.setJspPath("/jsp/widgets/movieDetails.jsp");
    	
    	movieWidgets.add(w4);
    	movieWidgets.add(w2);
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
