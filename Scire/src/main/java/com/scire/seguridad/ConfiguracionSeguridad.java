package com.scire.seguridad;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.scire.servicios.UsuarioServicio;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter {
	

	@Autowired
	@Qualifier("usuarioServicio")
	public UsuarioServicio usuarioServicio;
    
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(usuarioServicio) 
		.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/css/*", "/js/*", "/img/*", "/**").permitAll().and().formLogin()
				.loginPage("/login").loginProcessingUrl("/logincheck") // va a ir a el th:action y procesar si los datos estan correctos lo vamos a usar el form login
				.usernameParameter("email") // lo vamos a enviar como name en el input es una validacion
				.passwordParameter("clave")
				.defaultSuccessUrl("/") //aca definimos a que url va a ingresar si el usuario se logueo correctamente
				.failureUrl("/login?error=error")
				.permitAll().and()
				
				.rememberMe()
				.key("myUniqueKey")		
				.tokenValiditySeconds(10000000)
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll().and().csrf()
				.disable();
	}

}



