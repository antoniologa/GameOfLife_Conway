

public enum BotonEnum {
	// Botones de la botonera
	START("Start"),
	STEP("Step"),
	CLEAR("Clear");
	
	private String valor;
	
	/**
	 * Constructor para el string de cada enumerado
	 * @param valor Define el valor del enumerado
	 */
	private BotonEnum (String valor) {
		this.valor = valor;
	}
	
	/**
	 * Getter
	 * @return Devuelve el nombre del boton
	 */
	public String getTexto ()	{
		return valor;
	}
}
