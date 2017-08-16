package pe.jaav.sistemas.SeguridadGeneralRest.utiles;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class JsonView<T> {


	  protected final T value;
	  protected final Map<Class<?>, Match> matches = new HashMap<Class<?>, Match>();

	  protected JsonView(T value) {
	    this.value = value;
	  }

	  T getValue() {
	    return value;
	  }

	  Match getMatch(Class<?> cls) {
	    return matches.get(cls);
	  }

	  public JsonView<T> onClass(Class<?> cls, Match match) {
	    matches.put(cls, match);
	    return this;
	  }

	  public static <E> JsonView<E> with(E value) {
	    return new JsonView<E>(value);
	  }

	
}
