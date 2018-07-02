package pe.jaav.sistemas.SeguridadGeneralRest.security.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pe.jaav.sistemas.SeguridadGeneralRest.security.config.WebSecurityConfig;
import pe.jaav.sistemas.seguridadgeneral.model.domain.SysUsuario;

import org.springframework.security
            .authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static java.util.Collections.emptyList;

import java.io.OutputStream;

/**
 * @author araucoj
 *
 */
public class TokenAuthenticationService {
	
  //@Autowired
  //private JwtSettings settings;
  
  /** Agregar al Autenticacion generada (El response HTTP) con el nombre de usuario y
   * la data para el Body
 * @param res
 * @param objPrincipal
 * @param objCredential
 */
public static void addAuthentication(HttpServletResponse res, Object objPrincipal,Object objCredential) {	 
	try{
		if(objPrincipal!=null){
			//String userName = null;
			//String userCode = null;
			//Integer useId = null;
			String JWT = null;
			if(objPrincipal instanceof SysUsuario){				
				//userName = ((SysUsuario)objPrincipal).getUsuaNombre();
				//userCode = ((SysUsuario)objPrincipal).getUsuaUsuario();
				//useId = ((SysUsuario)objPrincipal).getUsuaId();
				JWT = ((SysUsuario)objPrincipal).getTokenSecurity();
			}

			//lo generamos antes
			//generarJwtToken();
		        
	    	//HEADER
	        res.addHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM, WebSecurityConfig.JWT_TOKEN_PREFIX + " " + JWT);	
	        
		    //MAPPER OBJECT TO JASON (BODY)
		    if(objPrincipal!=null){
		    	ObjectMapper mapper = new ObjectMapper();
		    	byte[] jsonInBytes = mapper.writeValueAsBytes(objPrincipal);	    	
		        OutputStream os = res.getOutputStream();
		    	os.write(jsonInBytes);
		    	os.flush();	
		    }	    	    	
		}

	}catch(Exception e){
		e.printStackTrace();
	}
  }

  /**
 * @param request
 * @return
 */
public static Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM);
    if (token != null) {
      // parse the token.
      String user = Jwts.parser()
          .setSigningKey(WebSecurityConfig.JWT_SECRET)
          .parseClaimsJws(token.replace(WebSecurityConfig.JWT_TOKEN_PREFIX, ""))
          .getBody()
          .getSubject();

      return user != null ?
          new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
          null;
    }
    return null;
  }

  	/**
	 * Generar Token JWT
	 * */
	public static String generarJwtToken(SysUsuario usurioSescion, Date fechaExpiracion){
		String JWT = Jwts.builder()
				.setSubject(usurioSescion.getUsuaNombre()+"-"+usurioSescion.getUsuaUsuario())
				.setId(""+usurioSescion.getUsuaId())
				.setExpiration(fechaExpiracion)
				.signWith(SignatureAlgorithm.HS512, WebSecurityConfig.JWT_SECRET)
				.compact();
		return JWT;
	}

	/**
	 * Generar Token JWT
	 * */
	public static Date generarExpiracionJwtToken(){
		return new Date(System.currentTimeMillis() + WebSecurityConfig.JWT_EXPIRATIONTIME);
	}
}