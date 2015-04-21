package moviedb.service;

import moviedb.domain.Widget;

import java.util.List;

public interface IWidgetService {
	public List<Widget> getPersonWidgets();
	
	public List<Widget> getMovieWidgets();
}
