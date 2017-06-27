/**
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@see <a href = "https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life" > Game Of Life Wiki </a>
@version 1.0
*/

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ControladorGameOfLife extends JApplet {

	private static final long serialVersionUID = 1L;
	private PanelControl panelControl = new PanelControl();
	private VistaGameOfLife vista;
	
	
	public ControladorGameOfLife () {
		initFrame ();
		initControles ();
		initVista();
		initListeners();
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame ("Game of life");
		ControladorGameOfLife applet= new ControladorGameOfLife();
		frame.add(applet, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);		
	}

	private void initControles () {
		add(getPanelControl(), BorderLayout.SOUTH);
	}
	
	private void initFrame () {
		setLayout(new BorderLayout());
	}
	
	private void initVista () {
		setVista(new VistaGameOfLife(getPanelControl().getBotones().getTamaño().getSelectedIndex(), getPanelControl().getBotones().getPatrones().getSelectedIndex()));
		add(getVista(), BorderLayout.CENTER);
	}
	
	public void eraseVista () {
		getVista().setVisible(false);
		getVista().removeAll();
	}
	
	private void initListeners () {
		getPanelControl().getBotones().getBoton(BotonEnum.START).addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getPanelControl().getBotones().getBoton(BotonEnum.START).getText().equals("Start")) {
					getVista().comienzaTimer();
					getPanelControl().getBotones().getBoton(BotonEnum.START).setText("Stop");
				}
				else {		
					getVista().getTemporizador().cancel();
					getPanelControl().getBotones().getBoton(BotonEnum.START).setText(BotonEnum.START.getTexto());
				}
				
			}
			
		});
		
		getPanelControl().getBotones().getBoton(BotonEnum.STEP).addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				getVista().getTemporizador().cancel();
				getVista().actualizaMatriz();
			}
			
		});
		
		getPanelControl().getBotones().getVelocidad().addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				getVista().getTemporizador().cancel();
				getVista().setDelay(getPanelControl().getBotones().getVelocidad().getValue());
				getPanelControl().getBotones().getBoton(BotonEnum.START).setText(BotonEnum.START.getTexto());
			}
		});

		
		getPanelControl().getBotones().getBoton(BotonEnum.CLEAR).addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				getVista().getTemporizador().cancel();
				getVista().clear();
			}
			
		});
		
		getPanelControl().getBotones().getTamaño().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eraseVista();
				initVista();

			}
		});
		
		getPanelControl().getBotones().getPatrones().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eraseVista();
				initVista();

			}
		});
		
	}
	

	
	/**
	 * @return the panelControl
	 */
	public PanelControl getPanelControl() {
		return panelControl;
	}

	/**
	 * @param panelControl the panelControl to set
	 */
	public void setPanelControl(PanelControl panelControl) {
		this.panelControl = panelControl;
	}

	/**
	 * @return the vista
	 */
	public VistaGameOfLife getVista() {
		return vista;
	}

	/**
	 * @param vista the vista to set
	 */
	public void setVista(VistaGameOfLife vista) {
		this.vista = vista;
	}
	
}
