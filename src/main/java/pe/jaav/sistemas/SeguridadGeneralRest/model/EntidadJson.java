package pe.jaav.sistemas.SeguridadGeneralRest.model;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Consumer;

import pe.jaav.common.util.UtilesCommons;

public abstract class EntidadJson<J> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String PATTER_DATE_TIME_DEFAULT ="dd/MM/yyyy HH:mm:ss";

	private String uri;
	
	private Integer contadorTotal;
	private Integer inicio;
	private Integer numeroFilas;
	private Integer numeroColumnas;
	private boolean paginable = false;// INDICA SI LA CONSULTA SERA PAGINADA

	public EntidadJson() {
		super();
				
	}


	public String getUri() {
		return uri;
	}


	public void setUri(String uri) {
		this.uri = uri;
	}


	public Integer getContadorTotal() {
		return contadorTotal;
	}


	public void setContadorTotal(Integer contadorTotal) {
		this.contadorTotal = contadorTotal;
	}


	public Integer getInicio() {
		return inicio;
	}


	public void setInicio(Integer inicio) {
		this.inicio = inicio;
	}


	public Integer getNumeroFilas() {
		return numeroFilas;
	}


	public void setNumeroFilas(Integer numeroFilas) {
		this.numeroFilas = numeroFilas;
	}


	public boolean isPaginable() {
		return paginable;
	}


	public void setPaginable(boolean paginable) {
		this.paginable = paginable;
	}
	
	/** Permite corregir en un campo auxiliar de fecha-cadena (Si existiera) el vslor fecha
	 * @param json
	 * @param fecha
	 * @param fechaStr
	 * @param action
	 */
	protected void fixDateFormat(J json ,Object valor ,Consumer<? super J> action){
		if(valor== null){
			action.accept(json);	
		}		
	}
	
	protected Date getFecha(String fechaStr){
		if(fechaStr!=null){
			return UtilesCommons.getDateFormat(PATTER_DATE_TIME_DEFAULT, fechaStr);	
		}
		return null;
	}
	
	protected String getFechaFormat(Date fecha){
		return  UtilesCommons.printDate(PATTER_DATE_TIME_DEFAULT, fecha);
	}


	public Integer getNumeroColumnas() {
		return numeroColumnas;
	}

	public void setNumeroColumnas(Integer numeroColumnas) {
		this.numeroColumnas = numeroColumnas;
	}
}
