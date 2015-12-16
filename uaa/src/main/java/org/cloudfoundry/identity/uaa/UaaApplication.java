package org.cloudfoundry.identity.uaa;

import java.util.HashMap;
import java.util.Map;

import org.cloudfoundry.identity.uaa.config.YamlServletProfileInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@EnableAutoConfiguration(exclude={ThymeleafAutoConfiguration.class})
@ImportResource("file:./src/main/webapp/WEB-INF/spring-servlet.xml")
public class UaaApplication extends SpringBootServletInitializer {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return 
        		application
        			.initializers(new YamlServletProfileInitializer())
        			.sources(UaaApplication.class);
    }
	
    public static void main(String[] args) {
//    	System.setProperty("environmentConfigDefaults", "uaa.yml,login.yml");
//    	System.setProperty("environmentConfigLocations", "${LOGIN_CONFIG_URL},file:${LOGIN_CONFIG_PATH}/login.yml,file:${CLOUD_FOUNDRY_CONFIG_PATH}/login.yml,${UAA_CONFIG_URL},file:${UAA_CONFIG_PATH}/uaa.yml,file:${CLOUD_FOUNDRY_CONFIG_PATH}/uaa.yml");
        SpringApplication.run(UaaApplication.class, args);
    }
   
    @Bean
    public DefaultFiltersBeanFactoryPostProcessor filters()  {
    	return new DefaultFiltersBeanFactoryPostProcessor();
    }
    
    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }
    
    @Bean
    public ServletRegistrationBean dispatcherServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet());
        Map<String,String> params = new HashMap<String,String>();
        params.put("contextInitializerClasses","org.cloudfoundry.identity.uaa.config.YamlServletProfileInitializer");
        params.put("environmentConfigDefaults","uaa.yml,login.yml");
        params.put("environmentConfigLocations","${LOGIN_CONFIG_URL},file:${LOGIN_CONFIG_PATH}/login.yml,file:${CLOUD_FOUNDRY_CONFIG_PATH}/login.yml,${UAA_CONFIG_URL},file:${UAA_CONFIG_PATH}/uaa.yml,file:${CLOUD_FOUNDRY_CONFIG_PATH}/uaa.yml");
        registration.setInitParameters(params);
        return registration;
    }
    
    
    
//    @Configuration
//    public static class UaaConfig {

//    	@Bean
//    	public ServletRegistrationBean dispatcherServlet() {
//    		ServletRegistrationBean dispatcher = new ServletRegistrationBean();
//    		dispatcher.addInitParameter("context.initializer.classes", "org.cloudfoundry.identity.uaa.config.YamlServletProfileInitializer");
//    		dispatcher.addInitParameter("environmentConfigDefaults", "uaa.yml,login.yml");
//    		dispatcher.addInitParameter("environmentConfigLocations", "${LOGIN_CONFIG_URL},file:${LOGIN_CONFIG_PATH}/login.yml,file:${CLOUD_FOUNDRY_CONFIG_PATH}/login.yml,${UAA_CONFIG_URL},file:${UAA_CONFIG_PATH}/uaa.yml,file:${CLOUD_FOUNDRY_CONFIG_PATH}/uaa.yml");
//    		dispatcher.setLoadOnStartup(1);
//    		return dispatcher;
//    	}
    	
//    	@Bean
//    	public Filter backwardsCompatibleScopeParameter() {
//    		return new BackwardsCompatibleScopeParsingFilter();
//    	}
    	
//    	@Bean
//    	public EmbeddedServletContainerCustomizer containerCustomizer() {
//    	 
//    	   return (container -> {
//    	        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
//    	        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
//    	 
//    	        container.addErrorPages(error404Page, error500Page);
//    	   });
//    	}

//    }

    
}
