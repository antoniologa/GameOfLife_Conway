/**
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@see <a href = "https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life" > Game Of Life Wiki </a>
@version 1.0
*/

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;

public class Controles extends ArrayList<MiBoton> {

	private static final long serialVersionUID = 1L;
	private static final int ALINEACION_TAG = 0;     // 0 = Centrado; 2 = Izquierda; 4 = Derecha
	private static final int MAX_DELAY = 500;
	private static final int MIN_DELAY = 50;
	private String [] patronesBox = {"Empty", "Blinker", "Glider", "Lightweight spaceship", "Pentadecathlon", "Pulsar", "Gosper glider gun"};
	private String [] tamañoBox = {"Small", "Medium", "Large"};
	private JComboBox <String> patrones; 
	private JComboBox <String> tamaño; 
	private JSlider velocidad = new JSlider();
	private JLabel patronesTag = new JLabel ("Patterns:", getAlineacionTag());	// Etiqueta informativa para el número de nodos
	private JLabel tamañoTag = new JLabel ("Map size:", getAlineacionTag());	// Etiqueta informativa para la introducción de un punto
	
	/**
	 * Constructor de la botonera
	 * @param indice Define el número identificativo
	 */
	public Controles ()	{
		super();
		initBotonera();
	}

	/**
	 * Método para encontrar un boton
	 * @param tipoBoton Define el tipo de boton
	 * @return Devuelve el boton que coincide con el tipo de boton especificado por parámetros
	 */
	public MiBoton getBoton (BotonEnum tipoBoton) {
		for (MiBoton boton : this)
			if (boton.getTipoBoton() == tipoBoton)
				return boton;
		return null;
	}
	
	/**
	 * Aquí se inicializan e instancian los botones de la botonera
	 */
	public void initBotonera ()	{
		for (int i = 0; i < BotonEnum.values().length; i ++) {
			add(new MiBoton (BotonEnum.values()[i].getTexto(), BotonEnum.values()[i]));
		}
		setPatrones(new JComboBox<String>(getPatronesBox()));
		setTamaño(new JComboBox<String>(getTamañoBox()));
		setVelocidad(new JSlider(JSlider.HORIZONTAL, getMinDelay(), getMaxDelay(), getMaxDelay() / 2));
		getVelocidad().setMinorTickSpacing(10);
		getVelocidad().setMajorTickSpacing(50);
		getVelocidad().setPaintTicks(true);
		getVelocidad().setInverted(true);
	}

	/**
	 * Inicializa el boton de información
	 */

	/**
	 * @return the alineacionTag
	 */
	public static int getAlineacionTag() {
		return ALINEACION_TAG;
	}

	/**
	 * @return the patronesBox
	 */
	public String[] getPatronesBox() {
		return patronesBox;
	}

	/**
	 * @param patronesBox the patronesBox to set
	 */
	public void setPatronesBox(String[] patronesBox) {
		this.patronesBox = patronesBox;
	}

	/**
	 * @return the tamañoBox
	 */
	public String[] getTamañoBox() {
		return tamañoBox;
	}

	/**
	 * @param tamañoBox the tamañoBox to set
	 */
	public void setTamañoBox(String[] tamañoBox) {
		this.tamañoBox = tamañoBox;
	}

	/**
	 * @return the patrones
	 */
	public JComboBox<String> getPatrones() {
		return patrones;
	}

	/**
	 * @param patrones the patrones to set
	 */
	public void setPatrones(JComboBox<String> patrones) {
		this.patrones = patrones;
	}

	/**
	 * @return the tamaño
	 */
	public JComboBox<String> getTamaño() {
		return tamaño;
	}

	/**
	 * @param tamaño the tamaño to set
	 */
	public void setTamaño(JComboBox<String> tamaño) {
		this.tamaño = tamaño;
	}

	/**
	 * @return the velocidad
	 */
	public JSlider getVelocidad() {
		return velocidad;
	}

	/**
	 * @param velocidad the velocidad to set
	 */
	public void setVelocidad(JSlider velocidad) {
		this.velocidad = velocidad;
	}

	/**
	 * @return the patronesTag
	 */
	public JLabel getPatronesTag() {
		return patronesTag;
	}

	/**
	 * @param patronesTag the patronesTag to set
	 */
	public void setPatronesTag(JLabel patronesTag) {
		this.patronesTag = patronesTag;
	}

	/**
	 * @return the tamañoTag
	 */
	public JLabel getTamañoTag() {
		return tamañoTag;
	}

	/**
	 * @param tamañoTag the tamañoTag to set
	 */
	public void setTamañoTag(JLabel tamañoTag) {
		this.tamañoTag = tamañoTag;
	}

	/**
	 * @return the maxDelay
	 */
	public static int getMaxDelay() {
		return MAX_DELAY;
	}

	/**
	 * @return the minDelay
	 */
	public static int getMinDelay() {
		return MIN_DELAY;
	}

}
