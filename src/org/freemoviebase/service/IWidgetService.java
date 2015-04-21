package org.freemoviebase.service;

import java.util.List;

import org.freemoviebase.domain.Widget;

public interface IWidgetService {
	public List<Widget> getPersonWidgets();
	
	public List<Widget> getMovieWidgets();
}
