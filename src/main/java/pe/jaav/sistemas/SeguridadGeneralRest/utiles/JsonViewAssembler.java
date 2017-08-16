package pe.jaav.sistemas.SeguridadGeneralRest.utiles;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JsonViewAssembler<T,K> {

	private T valueSource;
	private K valueTarget;
	private Class<K>  claseTarget;
	public JsonViewAssembler() {
	    
	}
	public JsonViewAssembler(Class<K> clsTarget) {
	    this.claseTarget  = clsTarget;
	}
	
	protected JsonViewAssembler(T valueSource,K valueTarget) {
		    this.valueSource = valueSource;
		    this.valueTarget = valueTarget;
	}
	
	/**Construye dinamicamente un nuevo objetode Una clase determinada a partir del objeto Original*/
    public  K getJsonView(T objetoOrigen){
		try{			
			K objNuevo=null;					
			if(objetoOrigen!=null && claseTarget !=null ){				
				//String type = claseNuevoObjeto.getName();
				
				objNuevo = claseTarget.newInstance();
				//objNuevo =  Class.forName(type).newInstance();
				////obtener los valores del objetoOrigen y set a objNuevo
				Field[] fields = objetoOrigen.getClass().getDeclaredFields();
				for(Field field : fields) {
	                try {
						if((field.getModifiers() & java.lang.reflect.Modifier.FINAL) == java.lang.reflect.Modifier.FINAL){
							//ES FINAL
						}else{
		                	String fieldName = field.getName();
		                	field.setAccessible(true);
		                	Object fieldValue = field.get(objetoOrigen);		                	
		                	//Field fieldNuevo = claseNuevoObjeto.getField(fieldName);
		                	
		                	//Field fieldNuevo = claseNuevoObjeto.getSuperclass().getField(fieldName);
		                	//Field fieldNuevo = objNuevo.getClass().getField(fieldName);
		                	
		                	field.set(objNuevo,fieldValue);
		                	/*
		                	if(fieldNuevo!=null){
		                		fieldNuevo.setAccessible(true);		                				                				                	
		                		//fieldNuevo.set(objNuevo.getClass().getSuperclass().newInstance(),fieldValue);	
		                		fieldNuevo.set(objNuevo,fieldValue);
		                	}*/
						}							                		               					
					} catch (IllegalArgumentException  e) {						
						e.printStackTrace();
					}	
				}									
			}
			return objNuevo;
		}catch(Exception ex){			
			ex.printStackTrace();
		}
		return null;
    }
    
    public  List<K> getJsonView(List<T> listaOrigen){
    	 List<K> listaTarget = new ArrayList<K>();
    	 for(T orign : listaOrigen){
    		 K target = getJsonView(orign) ;
    		 listaTarget.add(target);
    	 }    	 
    	 return  listaTarget;
    }
	
}
