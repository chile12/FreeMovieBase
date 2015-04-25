package moviedb.service;

import java.util.List;

import moviedb.domain.Widget;

public interface IWidgetService {
    public List<Widget> getPersonWidgets();

    public List<Widget> getMovieWidgets();
}
