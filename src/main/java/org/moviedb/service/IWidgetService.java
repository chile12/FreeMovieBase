package org.moviedb.service;

import java.util.List;

import org.moviedb.domain.Widget;

public interface IWidgetService {
	public List<Widget> getPersonWidgets();
	
	public List<Widget> getMovieWidgets();
}
