package moviedb.domain;

import moviedb.service.BaseService;

import javax.jnlp.BasicService;
import java.util.ArrayList;
import java.util.List;

public abstract class Topic {
	private String mID;
    private String title;
    private String description;
    private String webSite;
    private String wikiID;
	private boolean queriedForImages = false;
	private List<String> imagePaths = new ArrayList<String>();

	
	public String getmID() {
		return mID;
	}

	public void setmID(String mID) {
		this.mID = mID;
	}
	
	public String getImagePath() {
        if(getImagePaths().size() > 0)
            return getImagePaths().get(0);
        return "";
    }
    
    public List<String> getImagePaths() {
		if(queriedForImages || this.getmID() == null)
            return imagePaths;

        imagePaths = BaseService.getImageUrls(this);
        queriedForImages = true;
        return imagePaths;
	}

    public String getWikiID() {
        return wikiID;
    }

    public void setWikiID(String wikiID) {
        this.wikiID = wikiID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
}
