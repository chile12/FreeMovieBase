/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.freemoviebase.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.ServletContextAware;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

/**
 *
 * @author Ramon Bernert
 */
public abstract class BaseService implements ServletContextAware {

	private ServletContext servletContext;
	
	protected String baseQueryUrl = "http://vmdbpedia.informatik.uni-leipzig.de:8890/sparql?format=json&query=";
	protected String relativeEntitiesPath = "/WEB-INF/resources/images/entities/";
	protected String outputPath = "/resources/images/entities/";
	
	protected List<String> getImageUrls(String query, int getImagesCount){
		
		List<String> urls = new ArrayList<String>();
		
		try {
		
			query = URLEncoder.encode(query, "UTF-8");
			
			query = baseQueryUrl + query;

			String resultString = getResponse(query);
			
			JSONObject result = new JSONObject(resultString);
			JSONArray arr = result.getJSONObject("results").getJSONArray("bindings");
			
			int count = getImagesCount >  arr.length() ? arr.length() : getImagesCount;
			
			for(int i = 0 ; i < count; i++){
				JSONObject imageJson = arr.getJSONObject(i);
				
				String url = imageJson.getJSONObject("imageurl").getString("value"); 
				
				System.out.println("image source url: " + url);
				
				url = cacheImage(url);
				
				System.out.println("image cached url: " + url);
				
				urls.add(url);
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return urls;
	}
	
	private String cacheImage(String url){
		
		String imageID = url.substring(url.indexOf("/image/m/") + 9, url.indexOf("?"));
		
    	String imagePath = getEnitiesPath() + "/" + imageID + ".jpg";
    	
    	File image = new File(imagePath);
    	
    	if(!image.exists()){
	    	try {
				URL imageUrl = new URL(url);
				
				ReadableByteChannel rbc = Channels.newChannel(imageUrl.openStream());
				
				FileOutputStream fos = new FileOutputStream(imagePath);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
				
			} catch (IOException e) {
				
				System.out.println(imageID + " not cached");
				
				return url;
			}
    	}
    	
    	return url = outputPath + imageID + ".jpg";
	}
	
	protected JSONArray getJSONArray(String query) throws IOException{
		
		query = URLEncoder.encode(query, "UTF-8");
		
		query = baseQueryUrl + query;
		
		String resultString = getResponse(query);
		
		JSONObject result = new JSONObject(resultString);
    	JSONArray arr = result.getJSONObject("results").getJSONArray("bindings");
    	
    	return arr;
	}
	
    protected String getResponse(String targetUrl) throws IOException{
    	URL url;
    	HttpURLConnection connection = null;
    	
    	String result = "";
    	
          //Create connection
          url = new URL(targetUrl);
          connection = (HttpURLConnection)url.openConnection();
          connection.setRequestMethod("GET");

          connection.setUseCaches (false);
          connection.setDoInput(true);
          connection.setDoOutput(true);

          //Get Response    
          InputStream is = connection.getInputStream();
          BufferedReader rd = new BufferedReader(new InputStreamReader(is));
          String line;
          StringBuffer response = new StringBuffer(); 
          while((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
          }
          
          rd.close();
          
          result = response.toString();

            if(connection != null) {
              connection.disconnect(); 
            }
        
        return result;
    }
    
    private String getEnitiesPath(){
    	return servletContext.getRealPath(relativeEntitiesPath);
    }
    
    public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}
}
