package pe.jaav.sistemas.SeguridadGeneralRest.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import pe.jaav.sistemas.SeguridadGeneralRest.utiles.JsonViewCustom;
import pe.jaav.sistemas.seguridadgeneral.model.domain.SysUsuario;

@JsonIgnoreProperties(value =  
	{ "urlJson",
	//"numeroFilas",
	//"inicio", 	
	//"contadorTotal" ,	
	//"accionDB",
	"numeroColumnas" ,
	"valorBoolSup",
	"valorStringSup",
	"valorIntSup"
	
	}
)
@JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
public  class SysUsuarioJson extends SysUsuario {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	private String urlJson;

	public String getUrlJson() {		
		return urlJson;
	}

	public void setUrlJson(String urlJson) {
		this.urlJson = urlJson;
	}

}
