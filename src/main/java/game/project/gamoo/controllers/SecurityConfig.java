package game.project.gamoo.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;


@Component
public class SecurityConfig {
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests(authRequest ->{
			authRequest.requestMatchers("/","assets/**","/categories_all","/jeux","/about","/contact").permitAll();
			authRequest.requestMatchers("/categories_all/addCategory","/categories_all/delete/**","/categories_all/update/**","/jeux/**").authenticated();
		});
//		http.formLogin(formLogin->{
//			formLogin.loginPage("/login").permitAll();
//		}
//		);
		http.formLogin(Customizer.withDefaults());
		http.httpBasic(Customizer.withDefaults());
		return http.build();
	}

}
