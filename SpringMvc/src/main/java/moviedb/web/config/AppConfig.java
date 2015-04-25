package moviedb.web.config;

import moviedb.service.IMovieService;
import moviedb.service.IPersonService;
import moviedb.service.IWidgetService;
import moviedb.service.MovieService;
import moviedb.service.PersonService;
import moviedb.service.WidgetService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

@Configuration
@ComponentScan("moviedb.web")
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/jsp/");
        resolver.setSuffix(".jsp");

        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tc = new TilesConfigurer();

        tc.setDefinitions("/WEB-INF/definitions.xml");

        return tc;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public IWidgetService widgetService() {
        WidgetService ws = new WidgetService();

        return ws;
    }

    @Bean
    public IPersonService personService() {
        PersonService ps = new PersonService();

        return ps;
    }

    @Bean
    public IMovieService movieService(){
        MovieService ms = new MovieService();

        return ms;
    }
}  