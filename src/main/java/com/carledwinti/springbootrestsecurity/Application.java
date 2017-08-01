package com.carledwinti.springbootrestsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.carledwinti.springbootrestsecurity.model.User;
import com.carledwinti.springbootrestsecurity.repository.UserRepository;

//user:d6cda522-462f-4ba2-a4b5-e9fadd7e3c17
@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init(final UserRepository userRepository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				userRepository.save(new User("carl", "b@tata"));
				userRepository.save(new User("fulano", "&elancia"));
				userRepository.save(new User("beutrano", "#afe"));
				userRepository.save(new User("ciclano", "%va"));
				userRepository.save(new User("eubrano", "pEra"));
			}
		};
	}

	@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter{

		@Autowired
		UserRepository userRepository;
		
		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDatailsService());
		}

		@Bean
		UserDetailsService userDatailsService() {
			
			return new UserDetailsService(){

				@Override
				public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
					User user = userRepository.findByUsername(username);
					if(user != null){
						return new org.springframework.security.core.userdetails.User(user.getPassword(), user.getPassword(), true, true, true, true,AuthorityUtils.createAuthorityList("USER"));
					}else{
						throw new UsernameNotFoundException("could not find the user '" + username + "'");
					}
				}};
						
		}
	}
	
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
				.anyRequest()
				.fullyAuthenticated()
			.and()
				.httpBasic()
			.and()
				.csrf()
				.disable();
		}
	}
}
