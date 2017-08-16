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
import pe.jaav.sistemas.seguridadgeneral.model.domain.SysUsuario;


@RestController
@RequestMapping("/seguridad")
public class SeguridadController {

	 @Autowired
	 UsuarioService userService;
	 
	     
	 private JsonViewAssembler<SysUsuario, SysUsuarioJson> jsonAssemb = 
			 new JsonViewAssembler<SysUsuario, SysUsuarioJson>(SysUsuarioJson.class);
	 
	    //-------------------Retrieve All Elementos--------------------------------------------------------
	     
	    @RequestMapping(value = "/users/", method = RequestMethod.GET)	   
	    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)	    
	    public ResponseEntity<List<SysUsuarioJson>> listAllUsers() {
	        List<SysUsuario> users = userService.listar(new SysUsuario(),false);	        	        	       	        
	        
	        if(users.isEmpty()){
	            return new ResponseEntity<List<SysUsuarioJson>>(HttpStatus.NO_CONTENT);	            
	        }else{
		        List<SysUsuarioJson> usersJson = jsonAssemb.getJsonView(users);		    
		        
		        return new ResponseEntity<List<SysUsuarioJson>>(usersJson, HttpStatus.OK);	        	
	        }
	    }
	 
	 
	    //-------------------Retrieve Single User--------------------------------------------------------
	     
	    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
	    public ResponseEntity<SysUsuarioJson> getUser(@PathVariable("id") Integer id) {
	        System.out.println("Fetching User with id " + id);
	        SysUsuario user = userService.obtenerPorID(id);
	        if (user == null) {
	            System.out.println("User with id " + id + " not found");
	            return new ResponseEntity<SysUsuarioJson>(HttpStatus.NOT_FOUND);
	        }else{	        	
	        	return new ResponseEntity<SysUsuarioJson>(jsonAssemb.getJsonView(user), HttpStatus.OK);	
	        }
	    }
	 
	    @RequestMapping(value = "/user/{codigo}/{clave}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
	    public ResponseEntity<SysUsuarioJson> getUser(@PathVariable("codigo") String usuario,@PathVariable("clave") String clave ) {
	        //System.out.println("Fetching User with id " + usuario);
	        SysUsuario user = userService.obtenerLogin(usuario,clave);
	        if (user == null) {
	            System.out.println("User with id " + usuario+ " not found");
	            return new ResponseEntity<SysUsuarioJson>(HttpStatus.NOT_FOUND);
	        }else{	        	
	        	return new ResponseEntity<SysUsuarioJson>(jsonAssemb.getJsonView(user), HttpStatus.OK);	
	        }
	    }
	    
	    @RequestMapping(value = "/users/", method = RequestMethod.POST)    		
	    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
	    public ResponseEntity<List<SysUsuarioJson>> listUsers(@RequestBody SysUsuario usuario, UriComponentsBuilder ucBuilder) {
	        //System.out.println("Fetching User with id " + usuario);
	    	List<SysUsuario> users = userService.listar(usuario,false);	        	        	       	        	        
	        if(users.isEmpty()){
	            return new ResponseEntity<List<SysUsuarioJson>>(HttpStatus.NO_CONTENT);	            
	        }else{
		        List<SysUsuarioJson> usersJson = jsonAssemb.getJsonView(users);		    
		        
		        return new ResponseEntity<List<SysUsuarioJson>>(usersJson, HttpStatus.OK);	        	
	        }
	        
//	        SysUsuario user = userService.obtenerLogin(usuario.getUsuaUsuario(),usuario.getUsuaClave());
//	        if (user == null) {
//	            System.out.println("User with id " + usuario.getUsuaId()+ " not found");
//	            return new ResponseEntity<SysUsuarioJson>(HttpStatus.NOT_FOUND);
//	        }else{	        	
//	        	return new ResponseEntity<SysUsuarioJson>(jsonAssemb.getJsonView(user), HttpStatus.OK);	
//	        }
	    }
	    
	     
	    //-------------------Create a User--------------------------------------------------------
	     
	    @RequestMapping(value = "/user/", method = RequestMethod.POST)
	    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
	    public ResponseEntity<SysUsuarioJson> createUser(@RequestBody SysUsuario user,    UriComponentsBuilder ucBuilder) {
	        System.out.println("Creating User " + user.getUsuaNombre());
	         try{
	 	        /*if (userService.isUserExist(user)) {
		            System.out.println("A User with name " + user.getUsuaNombre() + " already exist");
		            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		        }*/
	        	 
	        	 int result = userService.guardar(user);
	        	 if(result > 0){
	        		 user.setUsuaId(result);
	        	 }	
			     return new ResponseEntity<SysUsuarioJson>(jsonAssemb.getJsonView(user), HttpStatus.CREATED);
		     }catch(Exception e){
		        return new ResponseEntity<SysUsuarioJson>(jsonAssemb.getJsonView(user), HttpStatus.INTERNAL_SERVER_ERROR);
		     }		         	         
	         /*
	        int result = userService.guardar(user);	 
	        HttpHeaders headers = new HttpHeaders();
	        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getUsuaId()).toUri());
	        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	        */
	    }
	 
	     
	    //------------------- Update a User --------------------------------------------------------
	     
	    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
	    public ResponseEntity<SysUsuarioJson> updateUser(@PathVariable("id") Integer id, @RequestBody SysUsuario user) {
	        System.out.println("Updating User " + id);
	         try{
	 	        SysUsuario currentUser = userService.obtenerPorID(id);
		         
		        if (currentUser==null) {
		            System.out.println("User with id " + id + " not found");
		            return new ResponseEntity<SysUsuarioJson>(HttpStatus.NOT_FOUND);
		        }		 
		        currentUser.setUsuaNombre(user.getUsuaNombre());
		        currentUser.setUsuaEstado(user.getUsuaEstado());
		        currentUser.setUsuariomodif(user.getUsuariomodif());
		         
		        userService.actualizar(currentUser);
		        return new ResponseEntity<SysUsuarioJson>(jsonAssemb.getJsonView(currentUser), HttpStatus.OK);
	         }catch(Exception e){
	        	 return new ResponseEntity<SysUsuarioJson>(jsonAssemb.getJsonView(user), HttpStatus.INTERNAL_SERVER_ERROR);
	         }	        		         
	    }
	 
	    //------------------- Delete a User --------------------------------------------------------
	     
	    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
	    public ResponseEntity<SysUsuarioJson> deleteUser(@PathVariable("id") Integer id) {
	        System.out.println("Fetching & Deleting User with id " + id);
	 
	        SysUsuario user = userService.obtenerPorID(id);
	        
	        if (user == null) {
	            System.out.println("Unable to delete. User with id " + id + " not found");
	            return new ResponseEntity<SysUsuarioJson>(HttpStatus.NOT_FOUND);
	        }	 	        
	        
	        userService.eliminar(user);
	        
	        return new ResponseEntity<SysUsuarioJson>(HttpStatus.NO_CONTENT);
	    }
	 
	     
	    //------------------- Delete All Users --------------------------------------------------------
	     
	    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	    @JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
	    public ResponseEntity<SysUsuarioJson> deleteAllUsers() {
	        System.out.println("Deleting All Users");
	        //ELIMINAR TODOS
	        //userService.deleteAllUsers();
	        return new ResponseEntity<SysUsuarioJson>(HttpStatus.NO_CONTENT);
	    }
	 
	
}
