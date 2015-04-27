package moviedb.web;
import javax.servlet.http.HttpServletRequest;

import moviedb.service.PersonService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import moviedb.domain.Movie;
import moviedb.domain.Person;
import moviedb.service.BaseService;
import moviedb.service.IMovieService;
import moviedb.service.IPersonService;
import moviedb.service.IWidgetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private IMovieService movieService;

    @Autowired
    private IWidgetService widgetService;

    @Autowired
    private IPersonService personService;

    @RequestMapping("/get")
    public String getMovie(@RequestParam(value="uri", required=true) String uri, HttpServletRequest request, Model model) {

        Movie movie = movieService.getMovie(uri);
        movieService.LoadAdditionalInformations(movie);
        model.addAttribute("item", movie);
        model.addAttribute("movie", movie);
        model.addAttribute("widgets", widgetService.getMovieWidgets());

        return "movie";
    }

    @RequestMapping("")
    public String getMovies(Model model) {

        model.addAttribute("movies", movieService.getMoviesByAward("m.0g_w", 2013));

        return "movies";
    }

    @RequestMapping("byActors")
    public ResponseEntity<String> getMovies(@RequestParam(value="actor1", required=true) String uriActor1, @RequestParam(value="actor2", required=true) String uriActor2, Model model) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        Person actor1 = personService.getPerson(uriActor1);

        Person actor2 = personService.getPerson(uriActor2);

        List<Movie> movies = movieService.getMoviesByActors(uriActor1, uriActor2);

        JSONObject actor1Json = new JSONObject();

        actor1Json.put("id", actor1.getmID());
        actor1Json.put("name", actor1.getName());
        actor1Json.put("type", "actor1");
        actor1Json.put("image", actor1.getImagePath());

        JSONObject actor2Json = new JSONObject();

        actor2Json.put("id", actor2.getmID());
        actor2Json.put("name", actor2.getName());
        actor2Json.put("type", "actor2");
        actor2Json.put("image", actor2.getImagePath());

        JSONObject dummy = new JSONObject();
        dummy.put("type", "dummy");

        JSONArray moviesJson = new JSONArray();

        JSONArray actor2JsonArray = new JSONArray();
        actor2JsonArray.put(actor2Json);

        JSONArray dummyJsonArray = new JSONArray();
        dummyJsonArray.put(dummy);

        int i = 0;

        for(Movie movie : movies) {

            JSONObject movieJson = new JSONObject();

            movieJson.put("id", movie.getmID());
            movieJson.put("name", movie.getTitle());
            movieJson.put("image", movie.getImagePath());

            if(i == 0)
                movieJson.put("children", actor2JsonArray);
            else
                movieJson.put("children", dummyJsonArray);

            moviesJson.put(movieJson);

            i++;
        }

        actor1Json.put("children", moviesJson);

        return new ResponseEntity<String>(actor1Json.toString(), headers, HttpStatus.OK);
    }
    
    @RequestMapping("/getCountries")
    public ResponseEntity<String> getMovieCountries(@RequestParam(value="uri", required=true) String uri, Model model) {
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
    	
        Movie movie = movieService.getMovie(uri);
        movieService.LoadAdditionalInformations(movie);
        
        JSONObject fillJson = new JSONObject();
        fillJson.put("fillKey", "MEDIUM");
        
        JSONObject countriesJson = new JSONObject();

        for(String country : movie.getCountries()){
            try {
            	countriesJson.put(getCountryCode(country), fillJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<String>(countriesJson.toString(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/search")
    public ResponseEntity<String> getSearch(@RequestParam(value="term", required=true) String term, @RequestParam(value="count", defaultValue="0") int count) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        if(count <= 0){
            count = 10;
        }

        JSONArray arr = new JSONArray();

        List<Movie> movies = movieService.search(term, count);

        for(Movie movie : movies){
            JSONObject movieJson = new JSONObject();

            movieJson.put("uri", movie.getmID());
            movieJson.put("label", movie.getTitle());
            movieJson.put("type", "movie");

            arr.put(movieJson);
        }

        return new ResponseEntity<String>(arr.toString(), headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/getImages")
    public ResponseEntity<String> getImages(@RequestParam(value="uris", required=true) String uris, @RequestParam(value="count", defaultValue="0") int count) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        JSONArray arr = new JSONArray(uris);
        
        JSONArray images = new JSONArray();
        
        for(int i = 0; i< arr.length();i++){
        	String id = arr.getString(i);
        	
        	Movie m = new Movie();
        	
        	m.setmID(id);
        	
        	List<String> lis = BaseService.getImageUrls(m, true);
        	
    		JSONObject obj = new JSONObject();
    		
    		obj.put("id", id);
    		
    		if(lis.size() == 0){
    			
        		obj.put("image", "/resources/images/gallery.gif");
        	}
    		else {
    			String image = lis.get(0);
    			obj.put("image", image);
    		}
    		
    		images.put(obj);
        }
        
        return new ResponseEntity<String>(images.toString(), headers, HttpStatus.OK);
    }

    private Map<String, String> countryMap = new HashMap<String, String>();

    public String getCountryCode(String country)
    {
        if(countryMap.size() == 0)
        {
            countryMap.put("Aruba", "ABW");
            countryMap.put("Afghanistan", "AFG");
            countryMap.put("Angola", "AGO");
            countryMap.put("Anguilla", "AIA");
            countryMap.put("Åland Islands", "ALA");
            countryMap.put("Albania", "ALB");
            countryMap.put("Andorra", "AND");
            countryMap.put("United Arab Emirates", "ARE");
            countryMap.put("Argentina", "ARG");
            countryMap.put("Armenia", "ARM");
            countryMap.put("American Samoa", "ASM");
            countryMap.put("Antarctica", "ATA");
            countryMap.put("French Southern Territories", "ATF");
            countryMap.put("Antigua and Barbuda", "ATG");
            countryMap.put("Australia", "AUS");
            countryMap.put("Austria", "AUT");
            countryMap.put("Azerbaijan", "AZE");
            countryMap.put("Burundi", "BDI");
            countryMap.put("Belgium", "BEL");
            countryMap.put("Benin", "BEN");
            countryMap.put("Bonaire, Sint Eustatius and Saba", "BES");
            countryMap.put("Burkina Faso", "BFA");
            countryMap.put("Bangladesh", "BGD");
            countryMap.put("Bulgaria", "BGR");
            countryMap.put("Bahrain", "BHR");
            countryMap.put("Bahamas", "BHS");
            countryMap.put("Bosnia and Herzegovina", "BIH");
            countryMap.put("Saint Barthélemy", "BLM");
            countryMap.put("Belarus", "BLR");
            countryMap.put("Belize", "BLZ");
            countryMap.put("Bermuda", "BMU");
            countryMap.put("Bolivia, Plurinational State of", "BOL");
            countryMap.put("Brazil", "BRA");
            countryMap.put("Barbados", "BRB");
            countryMap.put("Brunei Darussalam", "BRN");
            countryMap.put("Bhutan", "BTN");
            countryMap.put("Bouvet Island", "BVT");
            countryMap.put("Botswana", "BWA");
            countryMap.put("Central African Republic", "CAF");
            countryMap.put("Canada", "CAN");
            countryMap.put("Cocos (Keeling) Islands", "CCK");
            countryMap.put("Switzerland", "CHE");
            countryMap.put("Chile", "CHL");
            countryMap.put("China", "CHN");
            countryMap.put("Côte d'Ivoire", "CIV");
            countryMap.put("Cameroon", "CMR");
            countryMap.put("Congo, the Democratic Republic of the", "COD");
            countryMap.put("Congo", "COG");
            countryMap.put("Cook Islands", "COK");
            countryMap.put("Colombia", "COL");
            countryMap.put("Comoros", "COM");
            countryMap.put("Cabo Verde", "CPV");
            countryMap.put("Costa Rica", "CRI");
            countryMap.put("Cuba", "CUB");
            countryMap.put("Curaçao", "CUW");
            countryMap.put("Christmas Island", "CXR");
            countryMap.put("Cayman Islands", "CYM");
            countryMap.put("Cyprus", "CYP");
            countryMap.put("Czech Republic", "CZE");
            countryMap.put("Germany", "DEU");
            countryMap.put("Djibouti", "DJI");
            countryMap.put("Dominica", "DMA");
            countryMap.put("Denmark", "DNK");
            countryMap.put("Dominican Republic", "DOM");
            countryMap.put("Algeria", "DZA");
            countryMap.put("Ecuador", "ECU");
            countryMap.put("Egypt", "EGY");
            countryMap.put("Eritrea", "ERI");
            countryMap.put("Western Sahara", "ESH");
            countryMap.put("Spain", "ESP");
            countryMap.put("Estonia", "EST");
            countryMap.put("Ethiopia", "ETH");
            countryMap.put("Finland", "FIN");
            countryMap.put("Fiji", "FJI");
            countryMap.put("Falkland Islands (Malvinas)", "FLK");
            countryMap.put("France", "FRA");
            countryMap.put("Faroe Islands", "FRO");
            countryMap.put("Micronesia, Federated States of", "FSM");
            countryMap.put("Gabon", "GAB");
            countryMap.put("United Kingdom", "GBR");
            countryMap.put("Georgia", "GEO");
            countryMap.put("Guernsey", "GGY");
            countryMap.put("Ghana", "GHA");
            countryMap.put("Gibraltar", "GIB");
            countryMap.put("Guinea", "GIN");
            countryMap.put("Guadeloupe", "GLP");
            countryMap.put("Gambia", "GMB");
            countryMap.put("Guinea-Bissau", "GNB");
            countryMap.put("Equatorial Guinea", "GNQ");
            countryMap.put("Greece", "GRC");
            countryMap.put("Grenada", "GRD");
            countryMap.put("Greenland", "GRL");
            countryMap.put("Guatemala", "GTM");
            countryMap.put("French Guiana", "GUF");
            countryMap.put("Guam", "GUM");
            countryMap.put("Guyana", "GUY");
            countryMap.put("Hong Kong", "HKG");
            countryMap.put("Heard Island and McDonald Islands", "HMD");
            countryMap.put("Honduras", "HND");
            countryMap.put("Croatia", "HRV");
            countryMap.put("Haiti", "HTI");
            countryMap.put("Hungary", "HUN");
            countryMap.put("Indonesia", "IDN");
            countryMap.put("Isle of Man", "IMN");
            countryMap.put("India", "IND");
            countryMap.put("British Indian Ocean Territory", "IOT");
            countryMap.put("Ireland", "IRL");
            countryMap.put("Iran, Islamic Republic of", "IRN");
            countryMap.put("Iraq", "IRQ");
            countryMap.put("Iceland", "ISL");
            countryMap.put("Israel", "ISR");
            countryMap.put("Italy", "ITA");
            countryMap.put("Jamaica", "JAM");
            countryMap.put("Jersey", "JEY");
            countryMap.put("Jordan", "JOR");
            countryMap.put("Japan", "JPN");
            countryMap.put("Kazakhstan", "KAZ");
            countryMap.put("Kenya", "KEN");
            countryMap.put("Kyrgyzstan", "KGZ");
            countryMap.put("Cambodia", "KHM");
            countryMap.put("Kiribati", "KIR");
            countryMap.put("Saint Kitts and Nevis", "KNA");
            countryMap.put("Korea, Republic of", "KOR");
            countryMap.put("Kuwait", "KWT");
            countryMap.put("Lao People's Democratic Republic", "LAO");
            countryMap.put("Lebanon", "LBN");
            countryMap.put("Liberia", "LBR");
            countryMap.put("Libya", "LBY");
            countryMap.put("Saint Lucia", "LCA");
            countryMap.put("Liechtenstein", "LIE");
            countryMap.put("Sri Lanka", "LKA");
            countryMap.put("Lesotho", "LSO");
            countryMap.put("Lithuania", "LTU");
            countryMap.put("Luxembourg", "LUX");
            countryMap.put("Latvia", "LVA");
            countryMap.put("Macao", "MAC");
            countryMap.put("Saint Martin (French part)", "MAF");
            countryMap.put("Morocco", "MAR");
            countryMap.put("Monaco", "MCO");
            countryMap.put("Moldova, Republic of", "MDA");
            countryMap.put("Madagascar", "MDG");
            countryMap.put("Maldives", "MDV");
            countryMap.put("Mexico", "MEX");
            countryMap.put("Marshall Islands", "MHL");
            countryMap.put("Macedonia, the former Yugoslav Republic of", "MKD");
            countryMap.put("Mali", "MLI");
            countryMap.put("Malta", "MLT");
            countryMap.put("Myanmar", "MMR");
            countryMap.put("Montenegro", "MNE");
            countryMap.put("Mongolia", "MNG");
            countryMap.put("Northern Mariana Islands", "MNP");
            countryMap.put("Mozambique", "MOZ");
            countryMap.put("Mauritania", "MRT");
            countryMap.put("Montserrat", "MSR");
            countryMap.put("Martinique", "MTQ");
            countryMap.put("Mauritius", "MUS");
            countryMap.put("Malawi", "MWI");
            countryMap.put("Malaysia", "MYS");
            countryMap.put("Mayotte", "MYT");
            countryMap.put("Namibia", "NAM");
            countryMap.put("New Caledonia", "NCL");
            countryMap.put("Niger", "NER");
            countryMap.put("Norfolk Island", "NFK");
            countryMap.put("Nigeria", "NGA");
            countryMap.put("Nicaragua", "NIC");
            countryMap.put("Niue", "NIU");
            countryMap.put("Netherlands", "NLD");
            countryMap.put("Norway", "NOR");
            countryMap.put("Nepal", "NPL");
            countryMap.put("Nauru", "NRU");
            countryMap.put("New Zealand", "NZL");
            countryMap.put("Oman", "OMN");
            countryMap.put("Pakistan", "PAK");
            countryMap.put("Panama", "PAN");
            countryMap.put("Pitcairn", "PCN");
            countryMap.put("Peru", "PER");
            countryMap.put("Philippines", "PHL");
            countryMap.put("Palau", "PLW");
            countryMap.put("Papua New Guinea", "PNG");
            countryMap.put("Poland", "POL");
            countryMap.put("Puerto Rico", "PRI");
            countryMap.put("Korea, Democratic People's Republic of", "PRK");
            countryMap.put("Portugal", "PRT");
            countryMap.put("Paraguay", "PRY");
            countryMap.put("Palestine, State of", "PSE");
            countryMap.put("French Polynesia", "PYF");
            countryMap.put("Qatar", "QAT");
            countryMap.put("Réunion", "REU");
            countryMap.put("Romania", "ROU");
            countryMap.put("Russian Federation", "RUS");
            countryMap.put("Rwanda", "RWA");
            countryMap.put("Saudi Arabia", "SAU");
            countryMap.put("Sudan", "SDN");
            countryMap.put("Senegal", "SEN");
            countryMap.put("Singapore", "SGP");
            countryMap.put("South Georgia and the South Sandwich Islands", "SGS");
            countryMap.put("Saint Helena, Ascension and Tristan da Cunha", "SHN");
            countryMap.put("Svalbard and Jan Mayen", "SJM");
            countryMap.put("Solomon Islands", "SLB");
            countryMap.put("Sierra Leone", "SLE");
            countryMap.put("El Salvador", "SLV");
            countryMap.put("San Marino", "SMR");
            countryMap.put("Somalia", "SOM");
            countryMap.put("Saint Pierre and Miquelon", "SPM");
            countryMap.put("Serbia", "SRB");
            countryMap.put("South Sudan", "SSD");
            countryMap.put("Sao Tome and Principe", "STP");
            countryMap.put("Suriname", "SUR");
            countryMap.put("Slovakia", "SVK");
            countryMap.put("Slovenia", "SVN");
            countryMap.put("Sweden", "SWE");
            countryMap.put("Swaziland", "SWZ");
            countryMap.put("Sint Maarten (Dutch part)", "SXM");
            countryMap.put("Seychelles", "SYC");
            countryMap.put("Syrian Arab Republic", "SYR");
            countryMap.put("Turks and Caicos Islands", "TCA");
            countryMap.put("Chad", "TCD");
            countryMap.put("Togo", "TGO");
            countryMap.put("Thailand", "THA");
            countryMap.put("Tajikistan", "TJK");
            countryMap.put("Tokelau", "TKL");
            countryMap.put("Turkmenistan", "TKM");
            countryMap.put("Timor-Leste", "TLS");
            countryMap.put("Tonga", "TON");
            countryMap.put("Trinidad and Tobago", "TTO");
            countryMap.put("Tunisia", "TUN");
            countryMap.put("Turkey", "TUR");
            countryMap.put("Tuvalu", "TUV");
            countryMap.put("Taiwan, Province of China", "TWN");
            countryMap.put("Tanzania, United Republic of", "TZA");
            countryMap.put("Uganda", "UGA");
            countryMap.put("Ukraine", "UKR");
            countryMap.put("United States Minor Outlying Islands", "UMI");
            countryMap.put("Uruguay", "URY");
            countryMap.put("United States of America", "USA");
            countryMap.put("Uzbekistan", "UZB");
            countryMap.put("Holy See (Vatican City State)", "VAT");
            countryMap.put("Saint Vincent and the Grenadines", "VCT");
            countryMap.put("Venezuela, Bolivarian Republic of", "VEN");
            countryMap.put("Virgin Islands, British", "VGB");
            countryMap.put("Virgin Islands, U.S.", "VIR");
            countryMap.put("Viet Nam", "VNM");
            countryMap.put("Vanuatu", "VUT");
            countryMap.put("Wallis and Futuna", "WLF");
            countryMap.put("Samoa", "WSM");
            countryMap.put("Yemen", "YEM");
            countryMap.put("South Africa", "ZAF");
            countryMap.put("Zambia", "ZMB");
            countryMap.put("Zimbabwe", "ZWE");
        }

        return countryMap.get(country);
    }
}
