package pe.jaav.sistemas.SeguridadGeneralRest.controller.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import pe.jaav.sistemas.SeguridadGeneralRest.model.JsonViewInterfaces;
import pe.jaav.sistemas.SeguridadGeneralRest.model.SysUsuarioJson;
import pe.jaav.sistemas.SeguridadGeneralRest.security.UserCredentials;
import pe.jaav.sistemas.SeguridadGeneralRest.security.auth.jwt.AjaxAuthenticationProvider;
import pe.jaav.sistemas.SeguridadGeneralRest.security.config.WebSecurityConfig;
import pe.jaav.sistemas.SeguridadGeneralRest.utiles.JsonViewAssembler;
import pe.jaav.sistemas.SeguridadGeneralRest.utiles.JsonViewCustom;
import pe.jaav.sistemas.seguridadgeneral.model.domain.SysUsuario;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

	
	@Autowired private AjaxAuthenticationProvider ajaxAuthenticationProvider;
	
	 
	 private JsonViewAssembler<SysUsuario, SysUsuarioJson> jsonAssemb = 
			 new JsonViewAssembler<SysUsuario, SysUsuarioJson>(SysUsuarioJson.class);
	
    /**AUTH LOGIN, 
     * @param usuario
     * @param ucBuilder
     * @return
     */
    @RequestMapping(value = WebSecurityConfig.FORM_BASED_LOGIN_ENTRY_POINT_SECURITY_REQUEST, method = RequestMethod.POST)    		
    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
    public ResponseEntity<SysUsuarioJson> login(@RequestBody UserCredentials usuario, 
    		UriComponentsBuilder ucBuilder) {	       	    
    	try{
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            		usuario.getUsername(), 
            		usuario.getPassword());
            
            /*Autenticacion:
             * Validar el LOGIN con el servicio correspondiente
             * Obtener el Objeto USUARIO DB completo
             * Generar el TOKEN JWT
             * Set valores Seguridad: FECHAS EXPIRACION JWT, set el TOKEN (JWT)
             * Generar el Objeto SESION DB de acuerdo a los valores actual
             * Obtener la lista de ROLES DE del USUARIO atual
             * Crear el Objeto CREDENCIAL USER determinado (UsernamePasswordAuthenticationToken) 
             * */
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)
            		ajaxAuthenticationProvider.authenticate(authentication);
        	
            SysUsuario userAuth = null;
            if(token!=null){
    			if(token.getPrincipal() instanceof SysUsuario){				
    				userAuth = (SysUsuario) token.getPrincipal();
    				//jwt = ((SysUsuario)token.getPrincipal()).getTokenSecurity(); //No usado en esta seccion
    			}		
            }
    		
        	//SysUsuario user = userService.obtenerLogin(usuario.getUsuaUsuario(),usuario.getUsuaClave()); // Ya realizado	        	        	       	        	        
            if (userAuth == null) {	            
                return new ResponseEntity<SysUsuarioJson>(HttpStatus.NOT_FOUND);
            }else{	        	
            	return new ResponseEntity<SysUsuarioJson>(jsonAssemb.getJsonObject(userAuth), HttpStatus.OK);	
            }    		
    	}catch(Exception e){
    		e.printStackTrace();
    		return new ResponseEntity<SysUsuarioJson>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}

        	     
    }
}
