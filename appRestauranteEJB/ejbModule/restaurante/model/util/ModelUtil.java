package restaurante.model.util;

public class ModelUtil {
	/**
	 * Verifica si una cadena es igual a null o tiene longitud igual a cero (0).
	 * @param cadena Cadena que va a verificarse. * @return
	 */
	public static boolean isEmpty(String cadena) {
		if (cadena == null || cadena.length() == 0)
			return true;
		return false;
	}
}