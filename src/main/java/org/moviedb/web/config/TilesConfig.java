package org.moviedb.web.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@Configuration 
public class TilesConfig {
	/*@Bean
	public ContentNegotiatingViewResolver contentNegotiatingResolver(){
		
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		
		resolver.setOrder(org.springframework.core.Ordered.HIGHEST_PRECEDENCE);
		
		Map<String, MediaType> m = new HashMap<String, MediaType>();
		
		m.put("html", MediaType.TEXT_HTML);
		m.put("xml", MediaType.APPLICATION_XML);
        m.put("json", MediaType.APPLICATION_JSON);
        m.put("atom", MediaType.APPLICATION_XML);
		
		PathExtensionContentNegotiationStrategy pecns = new PathExtensionContentNegotiationStrategy(m);
		
		ContentNegotiationManager cnm = new ContentNegotiationManager(pecns);
		
		resolver.setContentNegotiationManager(cnm);
		
		return resolver;
	}
	
	@Bean  
    public UrlBasedViewResolver tilesViewResolver() {  
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();  
        
        resolver.setViewClass(TilesView.class);
        resolver.setOrder(contentNegotiatingResolver().getOrder() + 1);
        
        return resolver;
    }
	
	@Bean
	public ResourceBundleViewResolver viewResolver(){
		ResourceBundleViewResolver resolver = new ResourceBundleViewResolver();
		resolver.setBasename("views");
		resolver.setOrder(tilesViewResolver().getOrder() + 1);
		
		return resolver;
	}*/
	
	
}
