package com.sv;

import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

public class ServletInitializer extends SpringBootServletInitializer implements WebApplicationInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TrackerApplication.class);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		/*servletContext.setInitParameter("spring.profiles.active", "dev");
    	servletContext.setInitParameter("spring.profiles.default", "dev");
    	servletContext.setInitParameter("spring.liveBeansView.mbeanDomain", "dev");*/
		
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    	
		// servletContext.addFilter("LogFilter", LogFilter.class).addMappingForUrlPatterns(null, false, "/*");
		
		/* AnnotationConfigWebApplicationContext config = new AnnotationConfigWebApplicationContext();
		config.register(AppConfiguration.class);
		
				
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("SpringDispatcher", new DispatcherServlet(config));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/rest/*");
		*/
	
	}
}
