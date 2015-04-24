package moviedb.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Topic {
	
	private String mID;
    //TODO
    private String pageID = "37410";
	private String imagePath;
	
	private List<String> imagePaths = new ArrayList<String>();
	
	public String getmID() {
		return mID;
	}

	public void setmID(String mID) {
		this.mID = mID;
	}
	
	public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public List<String> getImagePaths() {
		return imagePaths;
	}

    public String getPageID() {
        return pageID;
    }

    public void setPageID(String pageID) {
        this.pageID = pageID;
    }
}
