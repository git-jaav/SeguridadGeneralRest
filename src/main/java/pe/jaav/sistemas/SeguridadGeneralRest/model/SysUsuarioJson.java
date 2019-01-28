package pe.jaav.sistemas.SeguridadGeneralRest.model;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import pe.jaav.sistemas.SeguridadGeneralRest.utiles.JsonViewCustom;

@JsonIgnoreProperties(value =  
	{ 
		"uri"	
	}
)
@JsonViewCustom(JsonViewInterfaces.ViewGeneral.class)
public  class SysUsuarioJson extends EntidadJson<SysUsuarioJson> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer usuaId;
	private Integer personaId;
	private String usuaClave;
	private String usuaEstado;
	private Date usuaFechamodif;
	private String usuaNombre;
	private String usuaUsuario;
	private String usuaUsuariomodif;
	
	private String usuaFlagExpirar;
	private Date usuaFechaExpiracion;
	private String usuaFlagIneditable;
	
	private String tokenSecurity;
	
	
	public Integer getUsuaId() {
		return usuaId;
	}
	public void setUsuaId(Integer usuaId) {
		this.usuaId = usuaId;
	}
	public Integer getPersonaId() {
		return personaId;
	}
	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	public String getUsuaClave() {
		return usuaClave;
	}
	public void setUsuaClave(String usuaClave) {
		this.usuaClave = usuaClave;
	}
	public String getUsuaEstado() {
		return usuaEstado;
	}
	public void setUsuaEstado(String usuaEstado) {
		this.usuaEstado = usuaEstado;
	}
	public Date getUsuaFechamodif() {
		return usuaFechamodif;
	}
	public void setUsuaFechamodif(Date usuaFechamodif) {
		this.usuaFechamodif = usuaFechamodif;
	}
	public String getUsuaNombre() {
		return usuaNombre;
	}
	public void setUsuaNombre(String usuaNombre) {
		this.usuaNombre = usuaNombre;
	}
	public String getUsuaUsuario() {
		return usuaUsuario;
	}
	public void setUsuaUsuario(String usuaUsuario) {
		this.usuaUsuario = usuaUsuario;
	}
	public String getUsuaUsuariomodif() {
		return usuaUsuariomodif;
	}
	public void setUsuaUsuariomodif(String usuaUsuariomodif) {
		this.usuaUsuariomodif = usuaUsuariomodif;
	}
	public String getUsuaFlagExpirar() {
		return usuaFlagExpirar;
	}
	public void setUsuaFlagExpirar(String usuaFlagExpirar) {
		this.usuaFlagExpirar = usuaFlagExpirar;
	}
	public Date getUsuaFechaExpiracion() {
		return usuaFechaExpiracion;
	}
	public void setUsuaFechaExpiracion(Date usuaFechaExpiracion) {
		this.usuaFechaExpiracion = usuaFechaExpiracion;
	}
	public String getUsuaFlagIneditable() {
		return usuaFlagIneditable;
	}
	public void setUsuaFlagIneditable(String usuaFlagIneditable) {
		this.usuaFlagIneditable = usuaFlagIneditable;
	}
	public String getTokenSecurity() {
		return tokenSecurity;
	}
	public void setTokenSecurity(String tokenSecurity) {
		this.tokenSecurity = tokenSecurity;
	}

	
	
}
