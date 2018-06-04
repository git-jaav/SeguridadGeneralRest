package pe.jaav.sistemas.SeguridadGeneralRest.security.auth.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import pe.jaav.sistemas.SeguridadGeneralRest.security.UserContext;
import pe.jaav.sistemas.SeguridadGeneralRest.security.config.JwtSettings;
import pe.jaav.sistemas.SeguridadGeneralRest.utiles.UtilesRest;
import pe.jaav.sistemas.general.service.UsuarioService;
import pe.jaav.sistemas.seguridadgeneral.model.domain.SysUsuario;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by araucoj.
 */
@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
	
    //private final BCryptPasswordEncoder encoder;
    
	private final JwtSettings jwtSettings;
	
	 @Autowired
	 UsuarioService userService;
    
    @Autowired
    public AjaxAuthenticationProvider(JwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
    }
    

//    @Value("${spring.security.ldap.active}")
//    private boolean ldapActive;

//    @Autowired
//    public AjaxAuthenticationProvider(final DatabaseUserService userService, final BCryptPasswordEncoder encoder) {
//        this.userService = userService;
//        this.encoder = encoder;
//    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, UtilesRest.getMSJProperty("SCURITY.AUTH__DATOS_NOPROVEIDOS"));

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        /**Validar*/
        SysUsuario user = userService.obtenerLogin(username,password);    
        if(user!=null){
        	/**Obtener SEGURIDAD ...PERFILES; PERMISOS...etc*/
            //String code = (String) authentication.getDetails();                   
            //Cliente userCliente = clienteService.obtenerPorID(code);
            List<GrantedAuthority> authorities  = new  ArrayList<>();
            authorities.add( new SimpleGrantedAuthority("ADMIN"));
            authorities.add( new SimpleGrantedAuthority("USUARIO"));
                    
            UserContext userContext = UserContext.create(user.getUsuaUsuario(), authorities);
            return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());        	
        }else{
        	throw new AuthenticationServiceException(UtilesRest.getMSJProperty("SCURITY.AUTH_ERROR"));
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}