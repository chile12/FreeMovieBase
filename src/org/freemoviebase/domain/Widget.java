package org.freemoviebase.domain;

public class Widget {
	
	public Widget(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	private int id;
	private String name;
	private String jspPath;
	private String cssPath;
	private String scriptPath;
	private String acceptedType;

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public String getJspPath() {
		return jspPath;
	}

	public void setJspPath(String jspPath) {
		this.jspPath = jspPath;
	}

	public String getScriptPath() {
		return scriptPath;
	}

	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}

	public String getCssPath() {
		return cssPath;
	}

	public void setCssPath(String cssPath) {
		this.cssPath = cssPath;
	}

	public String getAcceptedType() {
		return acceptedType;
	}

	public void setAcceptedType(String acceptedType) {
		this.acceptedType = acceptedType;
	}
}
