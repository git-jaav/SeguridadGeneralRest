package pe.jaav.sistemas.SeguridadGeneralRest.security.config;


import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import pe.jaav.sistemas.SeguridadGeneralRest.security.auth.jwt.AjaxAuthenticationProvider;
import pe.jaav.sistemas.SeguridadGeneralRest.security.auth.jwt.JwtAuthenticationFilter;
import pe.jaav.sistemas.SeguridadGeneralRest.security.auth.jwt.JwtAuthenticationProvider;
import pe.jaav.sistemas.SeguridadGeneralRest.security.auth.jwt.JwtLoginFilter;
import pe.jaav.sistemas.SeguridadGeneralRest.utiles.UtilesRest;


/**
 * @author araucoj
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
	/**CONSTANTES ENTRY POINT*/    
    //public static final String FORM_BASED_LOGIN_ENTRY_POINT = "/api/auth/login";
    public static final String FORM_BASED_LOGIN_ENTRY_POINT = "/api/auth/autorizar";    
    public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/**";
    public static final String TOKEN_REFRESH_ENTRY_POINT = "/api/auth/token";    
    public static final String DOCUM_ENTRY_POINT = "/swagger-ui.html";
    
    /**CONSTANTES JWT*/
    public static final String JWT_TOKEN_HEADER_PARAM = "Authorization";
    //static final long EXPIRATIONTIME = 864_000_000; // 10 days
    public static final long JWT_EXPIRATIONTIME = 7200000; // 120 min = 120*60*1000  ms
    public static final String JWT_SECRET = "clavePrivadaDef";
    public static final String JWT_TOKEN_PREFIX = "Bearer";    
    

    @SuppressWarnings("unused")
	@Autowired private JwtAuthenticationProvider jwtAuthenticationProvider;
        
    @Autowired private AjaxAuthenticationProvider ajaxAuthenticationProvider;
    
    
    
    /**CONFIG BASICA*/
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	boolean filtrarPorSeguridad = false;
    	if(UtilesRest.SI_db.equals(UtilesRest.getAppProperty("jaav.security.jwt.enabled_validacion"))){
    		filtrarPorSeguridad = true;
    	}
    	/**Verificamos si esta ACTIVA el filtrado de seguridad*/
    	if(filtrarPorSeguridad){
        	http.csrf().disable().authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers(DOCUM_ENTRY_POINT).permitAll()          
            .antMatchers(HttpMethod.POST, FORM_BASED_LOGIN_ENTRY_POINT).permitAll()
            .antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated() // Protected API End-points
            //.anyRequest().authenticated()
            .and()
            
            //Filtramos el LOGIN REQUEST
            .addFilterBefore(new JwtLoginFilter(FORM_BASED_LOGIN_ENTRY_POINT, authenticationManager()),
                    UsernamePasswordAuthenticationFilter.class)
            
            //Filtramos nuevamente para verificar el JWT en el HEADER
            .addFilterBefore(new JwtAuthenticationFilter(),
                    UsernamePasswordAuthenticationFilter.class);    		
    	}else{
    		http.csrf().disable().authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers(DOCUM_ENTRY_POINT).permitAll()
            .antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).permitAll()
            ;            
    	}
    	
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	boolean filtrarPorSeguridad = false;
    	if(UtilesRest.SI_db.equals(UtilesRest.getAppProperty("jaav.security.jwt.enabled_validacion"))){
    		filtrarPorSeguridad = true;
    	}
    	/**Verificamos si esta ACTIVA el filtrado de seguridad*/
    	if(filtrarPorSeguridad){
        	/**SOLUCION CON PROVIDER ... personalizado*/    	
        	auth.authenticationProvider(ajaxAuthenticationProvider);    	
        	//auth.authenticationProvider(jwtAuthenticationProvider); //otro PROVIDER...por implementar
        	
        	/**SOLUCION CON ESTRUCTURA LOGIN FIJA (en memoria)*/    	
            //auth.inMemoryAuthentication().withUser("admin").password("password").roles("ADMIN");    		
    	}
    }
    
}
