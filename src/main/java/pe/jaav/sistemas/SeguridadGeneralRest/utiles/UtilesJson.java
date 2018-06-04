package pe.jaav.sistemas.SeguridadGeneralRest.utiles;

import java.lang.reflect.Field;

public class UtilesJson {
	
	/**Construye dinamicamente un nuevo objetode Una clase determinada a partir del objeto Original*/
    public static Object getNuevoObjetoGenerico(Object objetoOrigen, Class<?> claseNuevoObjeto){
		try{			
			Object objNuevo=null;
			if(objetoOrigen!=null && claseNuevoObjeto!=null){				
				String type = claseNuevoObjeto.getName();
				objNuevo = claseNuevoObjeto.newInstance();
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
}
