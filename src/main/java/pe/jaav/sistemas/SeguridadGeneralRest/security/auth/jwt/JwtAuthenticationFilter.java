package pe.jaav.sistemas.SeguridadGeneralRest.security.auth.jwt;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import pe.jaav.sistemas.SeguridadGeneralRest.security.auth.TokenAuthenticationService;
import pe.jaav.sistemas.SeguridadGeneralRest.security.config.WebSecurityConfig;
import org.springframework.security.core.Authentication;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * @author araucoj
 *
 */
public class JwtAuthenticationFilter extends GenericFilterBean {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, 
		  FilterChain filterChain) throws IOException, ServletException {
	  
	HttpServletRequest  requestHttp =  (HttpServletRequest)request;
	String uri = requestHttp.getRequestURI();
	/**Filtrar solo si no es la URI del LOGIN REQUEST*/
	if(WebSecurityConfig.FORM_BASED_LOGIN_ENTRY_POINT.equals(uri)){
		filterChain.doFilter(request,response);
	}else{
		/**Autenticar: Obtener la AUTENTICACION del REQUESS (token del HEADER)*/	  
	    Authentication authentication = TokenAuthenticationService.getAuthentication(requestHttp);

	    /**Set la autenticacion obtenida en el Contexto  */
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    
	    /**Terminar el filtro*/
	    filterChain.doFilter(request,response);		
	}	
  }
}