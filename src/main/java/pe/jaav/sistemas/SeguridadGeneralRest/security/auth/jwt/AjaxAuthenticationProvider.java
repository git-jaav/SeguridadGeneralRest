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

import pe.jaav.common.util.UtilesCommons;
import pe.jaav.sistemas.SeguridadGeneralRest.security.UserContext;
import pe.jaav.sistemas.SeguridadGeneralRest.security.auth.TokenAuthenticationService;
import pe.jaav.sistemas.SeguridadGeneralRest.security.config.JwtSettings;
import pe.jaav.sistemas.SeguridadGeneralRest.utiles.UtilesRest;
import pe.jaav.sistemas.general.service.SysRolService;
import pe.jaav.sistemas.general.service.SysSesionService;
import pe.jaav.sistemas.general.service.UsuarioService;
import pe.jaav.sistemas.seguridadgeneral.model.domain.SysRol;
import pe.jaav.sistemas.seguridadgeneral.model.domain.SysSesion;
import pe.jaav.sistemas.seguridadgeneral.model.domain.SysUsuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


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
	 SysRolService sysRolService;

    @Autowired
    SysSesionService sysSesionService;

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
        Assert.notNull(authentication, UtilesRest.getMSJProperty("SCURITY.AUTH_DATOS_NOPROVEIDOS"));

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        /**Validar*/
        SysUsuario user = userService.obtenerLogin(username,password);

        if(user!=null){
            /**Generar TOKEN*/
            Date fechaExpiracionToken = TokenAuthenticationService.generarExpiracionJwtToken();
            user.setTokenSecurity(
                    TokenAuthenticationService.generarJwtToken(user, fechaExpiracionToken));

            /**Sesion*/
            generarSesionUsuario(user,fechaExpiracionToken);

        	/**Obtener SEGURIDAD ...PERFILES; PERMISOS...etc*/
        	List<SysRol> listaRoles =   sysRolService.listarSysRolUsuarioAsigandos(user.getUsuaId());
        	List<GrantedAuthority> authorities  = new  ArrayList<>();
        	if(UtilesCommons.noEsVacio(listaRoles)){
        		authorities = listaRoles.stream()
        		.map(f -> new SimpleGrantedAuthority(f.getRolCodigo()))
        		.collect(Collectors.toList());
        	}
        	else{
        		/**Agregar default*/
        		authorities.add( new SimpleGrantedAuthority("USUARIO"));	
        	}                                   
                    
            UserContext userContext = UserContext.create(user.getUsuaUsuario(), authorities);            
            return new UsernamePasswordAuthenticationToken(user, userContext, userContext.getAuthorities());        	
        }else{
        	throw new AuthenticationServiceException(UtilesRest.getMSJProperty("SCURITY.AUTH_ERROR"));
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    /**
     * Generar y guardar la SESION actual
     * */
    public SysSesion generarSesionUsuario(SysUsuario user, Date fechaExpiracionToken){
        SysSesion sesion = new SysSesion();
        sesion.setFkUsuaId(user.getUsuaId());
        sesion.setSesiHoraInicio(new Date());
        sesion.setSesiHoraFinal(fechaExpiracionToken);
        sesion.setSesiEstado(UtilesCommons.ACTIVO_db);
        sesion.setSesiToken(user.getTokenSecurity());
        sysSesionService.guardar(sesion);
        return sesion;
    }
}