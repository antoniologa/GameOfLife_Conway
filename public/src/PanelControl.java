


import java.awt.GridLayout;

import javax.swing.JPanel;

public class PanelControl extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private Controles botones;	// Array list con los botones, ademas contiene atributos con etiquetas y sliders
	
	public PanelControl () {
		setLayout(new GridLayout(2, 4));
		initBotonera();
	}

	/**
	 * Inicializa el panel de botones
	 */
	public void initBotonera () {
		setBotones(new Controles ());
		getBotones().forEach(boton -> add(boton) );
		add(getBotones().getVelocidad());
		add(getBotones().getPatronesTag());
		add(getBotones().getPatrones());
		add(getBotones().getTamañoTag());
		add(getBotones().getTamaño());
	}
	
	/**
	 * @return the botones
	 */
	public Controles getBotones() {
		return botones;
	}

	/**
	 * @param botones the botones to set
	 */
	public void setBotones(Controles botones) {
		this.botones = botones;
	}
	
	
}
