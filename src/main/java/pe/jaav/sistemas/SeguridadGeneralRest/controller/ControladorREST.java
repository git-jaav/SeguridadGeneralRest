package pe.jaav.sistemas.SeguridadGeneralRest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class ControladorREST {
	

	@GetMapping(value = "/home" /*, method = RequestMethod.GET */)
	public String hom() {
		return "Bienvenido Seguridad general."	;
	}

}
