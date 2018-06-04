package pe.jaav.sistemas.SeguridadGeneralRest.controller.seguridad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import pe.jaav.sistemas.SeguridadGeneralRest.model.JsonViewInterfaces;
import pe.jaav.sistemas.SeguridadGeneralRest.model.SysUsuarioJson;
import pe.jaav.sistemas.SeguridadGeneralRest.utiles.JsonViewAssembler;
import pe.jaav.sistemas.SeguridadGeneralRest.utiles.JsonViewCustom;
import pe.jaav.sistemas.general.service.UsuarioService;
import pe.jaav.sistemas.general.service.utiles.Log;
import pe.jaav.sistemas.seguridadgeneral.model.domain.SysUsuario;


/**
 * @author JAAV
 * Rest Controller con CRUD y listados básicos ... basados en una entidad: SysUsuario
 */
@RestController
@RequestMapping("/api/seguridad")
public class SeguridadController {

	 @Autowired
	 UsuarioService userService;
	 
	     
	 private JsonViewAssembler<SysUsuario, SysUsuarioJson> jsonAssemb = 
			 new JsonViewAssembler<SysUsuario, SysUsuarioJson>(SysUsuarioJson.class);

	 private JsonViewAssembler<SysUsuarioJson, SysUsuario> jsonAssembInverso = 
			 new JsonViewAssembler<SysUsuarioJson, SysUsuario>(SysUsuario.class);


	     
	    /** listar todos los elementos ...
	     * @return
	     */
	    @RequestMapping(value = "/users/", method = RequestMethod.GET)	   
	    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)	    
	    public ResponseEntity<List<SysUsuarioJson>> listAllUsers() {
	        List<SysUsuario> users = userService.listar(new SysUsuario(),false);	        	        	       	        	        
	        if(users.isEmpty()){
	            return new ResponseEntity<List<SysUsuarioJson>>(HttpStatus.NO_CONTENT);	            
	        }else{
		        List<SysUsuarioJson> usersJson = jsonAssemb.getJsonListDozer(users);		    
		        
		        return new ResponseEntity<List<SysUsuarioJson>>(usersJson, HttpStatus.OK);	        	
	        }
	    }
	 
	 
	   
	     
	    /** Retornar un Objeto por Id
	     * @param id
	     * @return
	     */
	    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
	    public ResponseEntity<SysUsuarioJson> getUser(@PathVariable("id") Integer id) {	        
	        SysUsuario user = userService.obtenerPorID(id);
	        if (user == null) {	            
	            return new ResponseEntity<SysUsuarioJson>(HttpStatus.NOT_FOUND);
	        }else{	        	
	        	return new ResponseEntity<SysUsuarioJson>(jsonAssemb.getJsonObject(user), HttpStatus.OK);	
	        }
	    }
	 
	    /** Obtener un Usuario por el LOGIN
	     * @param usuario
	     * @param clave
	     * @return
	     */
	    @RequestMapping(value = "/user/{codigo}/{clave}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
	    public ResponseEntity<SysUsuarioJson> getUser(@PathVariable("codigo") String usuario,@PathVariable("clave") String clave ) {
	        //System.out.println("Fetching User with id " + usuario);
	        SysUsuario user = userService.obtenerLogin(usuario,clave);
	        if (user == null) {
	            System.out.println("User with id " + usuario+ " not found");
	            return new ResponseEntity<SysUsuarioJson>(HttpStatus.NOT_FOUND);
	        }else{	        	
	        	return new ResponseEntity<SysUsuarioJson>(jsonAssemb.getJsonObject(user), HttpStatus.OK);	
	        }
	    }
	    
	    /** GET LOGIN POST, 
	     * @param usuario
	     * @param ucBuilder
	     * @return
	     */
	    @RequestMapping(value = "/login", method = RequestMethod.POST)    		
	    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
	    public ResponseEntity<SysUsuarioJson> getLogin(@RequestBody SysUsuarioJson usuario, UriComponentsBuilder ucBuilder) {	       	    
	    	SysUsuario user = userService.obtenerLogin(usuario.getUsuaUsuario(),usuario.getUsuaClave());	        	        	       	        	        
	        if (user == null) {	            
	            return new ResponseEntity<SysUsuarioJson>(HttpStatus.NOT_FOUND);
	        }else{	        	
	        	return new ResponseEntity<SysUsuarioJson>(jsonAssemb.getJsonObject(user), HttpStatus.OK);	
	        }
	        	     
	    }
	    
	    /** Listado POST, con filtros variados, SIN PAGINAR
	     * @param usuario
	     * @param ucBuilder
	     * @return
	     */
	    @RequestMapping(value = "/users/", method = RequestMethod.POST)    		
	    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
	    public ResponseEntity<List<SysUsuarioJson>> listUsers(@RequestBody SysUsuarioJson usuario, UriComponentsBuilder ucBuilder) {
	         try{	 	 	        	 
	 	    	List<SysUsuario> users = userService.listar(jsonAssembInverso.getJsonObject(usuario),false);	        	        	       	        	        
		        if(users.isEmpty()){
		            return new ResponseEntity<List<SysUsuarioJson>>(HttpStatus.NO_CONTENT);	            
		        }else{
			        List<SysUsuarioJson> usersJson = jsonAssemb.getJsonListDozer(users);		    
			        
			        return new ResponseEntity<List<SysUsuarioJson>>(usersJson, HttpStatus.OK);	        	
		        }
		     }catch(Exception e){
		        e.printStackTrace();
		        Log.error(e, "listUsers");
		        return new ResponseEntity<List<SysUsuarioJson>>(HttpStatus.EXPECTATION_FAILED);
		     }	        
	        	     
	    }
	    
	    /** Listado POST, con filtros variados, PAGINADO
	     * @param usuario
	     * @param ucBuilder
	     * @return
	     */
	    @RequestMapping(value = "/users/pag/", method = RequestMethod.POST)    		
	    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
	    public ResponseEntity<List<SysUsuarioJson>> listUsersPaginado(@RequestBody SysUsuarioJson usuario, UriComponentsBuilder ucBuilder) {
	        try{
		    	SysUsuario filtro = jsonAssembInverso.getJsonObject(usuario);
		    	List<SysUsuario> users = userService.listar(filtro,true);	        	        	       	        	        
		        if(users.isEmpty()){
		            return new ResponseEntity<List<SysUsuarioJson>>(HttpStatus.NO_CONTENT);	            
		        }else{
		        	//Set valor de la cuenta para la paginacion...
		        	int cuenta = userService.contarListado(filtro);	        	
		        	for(SysUsuario user : users){
		        		user.setContadorTotal(cuenta);	
		        	}	        	        		        
			        List<SysUsuarioJson> usersJson = jsonAssemb.getJsonListDozer(users);		    			        
			        return new ResponseEntity<List<SysUsuarioJson>>(usersJson, HttpStatus.OK);	        	
		        }	
	        }catch(Exception e){
	        	e.printStackTrace();
	        	Log.error(e, "listUsersPaginado");
	        	return new ResponseEntity<List<SysUsuarioJson>>(HttpStatus.EXPECTATION_FAILED);
	        }	        	    
	    }
	    	     
	    
	    /** Guardar el usuario
	     * @param user
	     * @param ucBuilder
	     * @return
	     */
	    @RequestMapping(value = "/user/i/", method = RequestMethod.POST)
	    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
	    public ResponseEntity<SysUsuarioJson> guardar(@RequestBody SysUsuarioJson user,    UriComponentsBuilder ucBuilder) {	       
	         try{	 	 	        	 
	        	 int result = userService.guardar(jsonAssembInverso.getJsonObject(user));
	        	 if(result > 0){
	        		 user.setUsuaId(result);
	        	 }	
			     return new ResponseEntity<SysUsuarioJson>(user, HttpStatus.CREATED);
		     }catch(Exception e){
		        e.printStackTrace();
		        Log.error(e, "guardar");
		        return new ResponseEntity<SysUsuarioJson>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		     }		         	         
	    }
	    
	    /**Actualizar usuario
	     * @param user
	     * @param ucBuilder
	     * @return
	     */
	    @RequestMapping(value = "/user/u/", method = RequestMethod.POST)
	    //@RequestMapping(value = "/user/u/", method = RequestMethod.PUT)
	    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
	    public ResponseEntity<SysUsuarioJson> actualizar(@RequestBody SysUsuarioJson user,    UriComponentsBuilder ucBuilder) {	        
	         try{	 
	        	 int result = 0;
	        	 SysUsuario userUpdate =  userService.obtenerPorID(user.getUsuaId());
	        	 if(userUpdate!=null){
	        		 //Actualizar Objeto obtenido con los valores del Objeto parametro recibido...
	        		 userUpdate = jsonAssembInverso.getJsonObjectDestino(user, userUpdate);	        		
	        		 result =  userService.actualizar(userUpdate,
	        				 userService.detectarCambioClaveUsuario(userUpdate.getUsuaId(), userUpdate.getUsuaClave()));
	        		 		        	 
		        	 if(result > 0){
		        		 user.setUsuaId(result);
		        	 }		        		 
	        	 }
	        	 if(result > 0){
	        		 return new ResponseEntity<SysUsuarioJson>(user, HttpStatus.CREATED);	 
	        	 }else{
	        		 return new ResponseEntity<SysUsuarioJson>(user, HttpStatus.INTERNAL_SERVER_ERROR);
	        	 }			     
		     }catch(Exception e){
			    e.printStackTrace();
			    Log.error(e, "actualizar");		    	 
		        return new ResponseEntity<SysUsuarioJson>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		     }		         	         
	    }
	    
	    /**Eliminar usuario
	     * @param user
	     * @param ucBuilder
	     * @return
	     */
	    @RequestMapping(value = "/user/d/", method = RequestMethod.DELETE)
	    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
	    public ResponseEntity<SysUsuarioJson> eliminar(@RequestBody SysUsuarioJson user,    UriComponentsBuilder ucBuilder) {	        
	         try{	 
	        	 int result = 0;
	        	 SysUsuario userUpdate =  userService.obtenerPorID(user.getUsuaId());
	        	 if(userUpdate!=null){	        		 
		        	 result = userService.eliminar(userUpdate);
		        	 if(result > 0){
		        		 user.setUsuaId(result);
		        	 }		        		 
	        	 }
	        	 if(result > 0){
	        		 return new ResponseEntity<SysUsuarioJson>(user, HttpStatus.CREATED);	 
	        	 }else{
	        		 return new ResponseEntity<SysUsuarioJson>(user, HttpStatus.INTERNAL_SERVER_ERROR);
	        	 }			     
		     }catch(Exception e){
		    	e.printStackTrace();
				Log.error(e, "eliminar");
		        return new ResponseEntity<SysUsuarioJson>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		     }		         	         
	    }	 	  		 
	
}
