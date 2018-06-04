package pe.jaav.sistemas.SeguridadGeneralRest.security.auth.jwt;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import pe.jaav.sistemas.SeguridadGeneralRest.security.auth.TokenAuthenticationService;

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
  public void doFilter(ServletRequest request,
             ServletResponse response,
             FilterChain filterChain)
      throws IOException, ServletException {
    Authentication authentication = TokenAuthenticationService
        .getAuthentication((HttpServletRequest)request);

    SecurityContextHolder.getContext()
        .setAuthentication(authentication);
    filterChain.doFilter(request,response);
  }
}