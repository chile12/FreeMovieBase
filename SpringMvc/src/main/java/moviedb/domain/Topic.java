package moviedb.domain;

import moviedb.service.BaseService;

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
        if(getImagePaths(false).size() > 0)
            return getImagePaths(false).get(0);
        //TODO default
        return "/resources/images/gallery.gif";
    }

    public List<String> getImagePaths() {
        return getImagePaths(true);
    }
    
    private List<String> getImagePaths(boolean all) {
		if(queriedForImages || this.getmID() == null)
            return imagePaths;

        imagePaths = BaseService.getImageUrls(this, all);
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

    public boolean isQueriedForImages() {
        return queriedForImages;
    }

    public void setQueriedForImages(boolean queriedForImages) {
        this.queriedForImages = queriedForImages;
    }
}
