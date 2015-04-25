/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviedb.service;

import moviedb.domain.Topic;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Ramon Bernert
 */
public abstract class BaseService implements ServletContextAware {

    static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private static ServletContext servletContext;
    private static String baseFreebaseQueryUrl = "http://vmdbpedia.informatik.uni-leipzig.de:8890/sparql?format=json&query=";
    private static String baseWikipediaQueryUrl = "http://dbpedia.org/sparql/default-graph-uri=http%3A%2F%2Fdbpedia.org?format=json&query=";
    private static String relativeEntitiesPath = "/resources/images/";

	public static List<String> getImageUrls(Topic top){
		
		List<String> urls = new ArrayList<String>();
        urls.addAll(getFreebaseImgUrl(top.getmID()));
        try {
            urls.add(getWikipediaImg(top.getWikiID()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        urls.removeAll(Collections.singleton(null));
		return urls;
	}

    private static  List<String> getFreebaseImgUrl(String id)
    {
        List<String> urls = new ArrayList<String>();
        try{
            String query = "PREFIX ns: <http://rdf.freebase.com/ns/> " +
                    "SELECT DISTINCT (CONCAT(CONCAT(\"https://usercontent.googleapis.com/freebase/v1/image/\", REPLACE(SUBSTR(str(?image), bif:strrchr(str(?image), '/')+2), '\\\\.', '\\\\/')), '?maxwidth=333&maxheight=333&mode=fit') as ?imageurl) " +
                    "FROM <http://fmb.org> " +
                    "WHERE {?film ns:common.topic.image ?image. " +
                    "FILTER (?film = ns:%s)}";

            query = String.format(query, id);
            query = baseFreebaseQueryUrl + URLEncoder.encode(query, "UTF-8");

            String resultString = getResponse(query);

            JSONObject result = new JSONObject(resultString);
            JSONArray arr = result.getJSONObject("results").getJSONArray("bindings");

            for(int i =0; i < arr.length(); i++) {
                String url = arr.getJSONObject(0).getJSONObject("imageurl").getString("value");
                urls.add(cacheImage(url));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return urls;
    }

    private static String getWikipediaImg(String pageId) throws UnsupportedEncodingException, JSONException {
        if(pageId == null)
            return null;
            String query = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                    "PREFIX dbpedia: <http://dbpedia.org/ontology/>\n" +
                    "Select ?page ?imgurl\n" +
                    "FROM <http://dbpedia.org>\n" +
                    "WHERE{\n" +
                    "?page dbpedia:wikiPageID %s.\n" +
                    "OPTIONAL{?page foaf:depiction ?imgurl}}";
        query = baseWikipediaQueryUrl + URLEncoder.encode(String.format(query, pageId), "UTF-8");

        String resultString = null;
        try {
            resultString = getResponse(query);

        } catch (IOException e) {
            e.printStackTrace();
        }

            JSONArray arr = null;

            if (resultString != null)
                arr = new JSONObject(resultString).getJSONObject("results").getJSONArray("bindings");

        if (resultString == null || arr == null || arr.length() == 0) {
                try {
                    resultString = getResponse(query.replace("commons", "en"));

                    if (resultString != null)
                        arr = new JSONObject(resultString).getJSONObject("results").getJSONArray("bindings");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (arr == null || arr.length() == 0)
                return null;

        if(arr.getJSONObject(0).has("imgurl")) {
            String url = arr.getJSONObject(0).getJSONObject("imgurl").getString("value");
            url = cacheImage(url);
            return url;
        }
        return null;
    }
	
	private static String cacheImage(String url){
		
		String imageID = null;
        if(url.contains("/image/m"))
            imageID = "freebase_" + url.substring(url.indexOf("/image/m/") + 9, url.indexOf("?")) + ".jpg";
        else
            imageID = "wikipedia_" + url.substring(url.lastIndexOf("/") + 1);

    	File image = new File(getEnitiesPath() + imageID);
    	if(!image.exists()){
	    	try {
				URL imageUrl = new URL(url);
                image.createNewFile();
				ReadableByteChannel rbc = Channels.newChannel(imageUrl.openStream());
				FileOutputStream fos = new FileOutputStream(image);
				long byteCount = fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();

                if(byteCount == 0)
                    return null;
				
			} catch (IOException e) {
				return null;
			}
    	}
    	
    	return relativeEntitiesPath + imageID;
	}
	
	protected JSONArray getJSONArray(String query) throws IOException{
		
		query = URLEncoder.encode(query, "UTF-8");
		
		query = baseFreebaseQueryUrl + query;
		
		String resultString = getResponse(query);
		
		JSONObject result = new JSONObject(resultString);
    	JSONArray arr = result.getJSONObject("results").getJSONArray("bindings");
    	
    	return arr;
	}
	
    protected static String getResponse(String targetUrl) throws IOException{
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
    
    private static  String getEnitiesPath(){
    	return servletContext.getRealPath(relativeEntitiesPath);
    }
    
    public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}
}