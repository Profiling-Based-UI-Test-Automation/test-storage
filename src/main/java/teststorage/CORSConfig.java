package teststorage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*
 * springboot cors 설정 
 */

@Configuration
public class CORSConfig {
	@Bean 
	public WebMvcConfigurer corsConfigurer() { 
		return new WebMvcConfigurerAdapter() { 
			public void addCorsMappings(CorsRegistry registry) { 
				registry.addMapping("/**")
				.allowedOrigins("http://localhost:8090", "http://127.0.0.1:8090")
                .allowedMethods("GET", "DELETE", "PUT", "POST", "OPTIONS");
			} 
		}; 
	}
}
