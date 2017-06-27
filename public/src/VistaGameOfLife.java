/**
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@see <a href = "https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life" > Game Of Life Wiki </a>
@version 1.0
*/

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class VistaGameOfLife extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int BLINKER [][] = new int[][] {{1,0}, {2,0}, {3,0}};
	private static final int GLIDER [][] = new int[][] {{1,0}, {2,1}, {2,2}, {1,2}, {0,2}};
	private static final int SPACESHIP [][] = new int[][] {{0,1}, {0,2}, {1,0}, {1,1}, {1,2}, {1,3}, {2,0}, {2,1}, {2,3}, {2,4}, {3,2}, {3,3}};
	private static final int PENTADEC [][] = new int[][] {{0,1}, {0,2}, {0,3}, {1,1}, {1,3}, {2,1}, {2,2}, {2,3}, {3,1}, {3,2}, {3,3}, {4,1}, {4,2}, {4,3}, {5,1}, {5,2}, {5,3}, {6,1}, {6,3}, {7,1}, {7,2}, {7,3}};
	private static final int PULSAR [][] = new int[][] {{0,2}, {0,3}, {0,4}, {0,8}, {0,9}, {0,10}, {2,0}, {2,5}, {2,7}, {2,12}, {3,0}, {3,5}, {3,7}, {3,12}, {4,0}, {4,5}, {4,7}, {4,12}, {5,2}, {5,3}, {5,4}, {5,8}, {5,9}, {5,10}, 
																		{7,2}, {7,3}, {7,4}, {7,8}, {7,9}, {7,10}, {8,0}, {8,5}, {8,7}, {8,12}, {9,0}, {9,5}, {9,7}, {9,12}, {10,0}, {10,5}, {10,7}, {10,12}, 
																		{12,2}, {12,3}, {12,4}, {12,8}, {12,9}, {12,10},};
	private static final int GLIDER_GUN [][] = new int[][] {{0,24}, {1,22}, {1,24}, {2,12}, {2,13}, {2,20}, {2,21}, {2,34}, {2,35}, {3,11}, {3,15}, {3,20}, {3,21}, {3,34}, {3,35},
																				{4,0}, {4,1}, {4,10}, {4,16}, {4,20}, {4,21},
																				{5,0}, {5,1}, {5,10}, {5,14}, {5,16}, {5,17}, {5,22}, {5,24},
																				{6,10}, {6,16}, {6,24}, {7,11}, {7,15}, {8,12}, {8,13}};
	private static final int SMALL = 40;
	private static final int MEDIUM = 70;
	private static final int LARGE = 100;
	private static final int ZONA_EXTERIOR = 20;
	private static final int ORIGEN_MATRIZ = getZonaExterior() / 2;
	private static final Color COLOR_CUADRICULA = Color.GRAY;
	private static final Color COLOR_CELULAS = Color.YELLOW;
	private ModeloCelula matriz [][];
	private int nFilas, nColumnas;
	private Timer temporizador = new Timer();
	private int delay = 250;
	
	public VistaGameOfLife (int tam, int patron) {
		initGrid (tam);
		cargarCelulas(patron);
	}
	
	public void cargarPatron (int patron [][]) {
		for(int i = 0; i < patron.length; i++)
			getMatriz()[patron[i][0] + getZonaExterior() - 6][patron[i][1] + getZonaExterior() - 6].setEstado(true);
	}
	
	private void cargarCelulas(int patron) {
		for(int i = 0; i < getnFilas() + getZonaExterior(); i++)
			for(int j = 0; j < getnColumnas() + getZonaExterior(); j++)
					getMatriz()[i][j] = new ModeloCelula(false);
		for(int i = getOrigenMatriz(); i < getnFilas() + getOrigenMatriz(); i++)
			for(int j = getOrigenMatriz(); j < getnColumnas() + getOrigenMatriz(); j++) {
				add(getMatriz()[i][j]);
			}
		switch (patron) {
		case 1: 	cargarPatron(getBlinker());
					break;
		case 2:	cargarPatron(getGlider());
					break;
		case 3:	cargarPatron(getSpaceship());
					break;
		case 4:	cargarPatron(getPentadec());
					break;
		case 5:	cargarPatron(getPulsar());
					break;
		case 6:	cargarPatron(getGliderGun());
					break;
		default: 
					break;
		}
	}
	
	private void initGrid (int tam) {
		switch (tam) {
		case 0: 	setnFilas(getSmall());
					setnColumnas(getSmall());
					break;
		case 1:	setnFilas(getMedium());
					setnColumnas(getMedium());
					break;
		case 2:	setnFilas(getLarge());
					setnColumnas(getLarge());
					break;
		default: setnFilas(getMedium());
					setnColumnas(getMedium());
					break;
		}
		setLayout(new GridLayout(getnFilas(), getnColumnas()));
		setMatriz(new ModeloCelula [getnFilas() + getZonaExterior()][getnColumnas() + getZonaExterior()]);
	}

	public void clear () {
		for(int i = 1; i < getnFilas() + getZonaExterior() - 1; i++)
			for(int j = 1; j < getnColumnas() + getZonaExterior() - 1; j++) {
				getMatriz()[i][j].setEstado(false);
				repaint();
			}
	}
	
	public void actualizaMatriz () {
		for(int i = 1; i < getnFilas() + getZonaExterior() - 1; i++)
			for(int j = 1; j < getnColumnas() + getZonaExterior() - 1; j++) {
				getMatriz()[i][j].actualizaCelula(this, i, j);
				repaint();
			}
	}
	
	/**
	 * Inicia el timer y ejecuta una tarea cada "delay" tiempo
	 */
	public void comienzaTimer () {
	    getTemporizador().cancel();
	    
	    TimerTask caminoTiempoTarea = new TimerTask() {

	      public void run() {
	      	actualizaMatriz ();
	      }
	    };

	    setTemporizador(new Timer());
	    getTemporizador().scheduleAtFixedRate(caminoTiempoTarea, 0, getDelay());
	  }
	
	/*protected void paintComponent (Graphics objetoGrafico) {
		super.paintComponent(objetoGrafico);
		drawCuadricula (objetoGrafico);
		drawCelulas(objetoGrafico);
	}*/
	
	/*protected void drawCuadricula (Graphics objetoGrafico) {
		// Con esta operación podemos obtener el valor de separacion entre filas y columnas para que queden equidistantes
		double espacioEntreFila = (double)getHeight() / (double)getnFilas();       
		double espacioEntreColumna = (double)getWidth() / (double)getnColumnas();     

		objetoGrafico.setColor(getColorCuadricula());

		// Aquí pintamos las filas
		for (int i = 0; i < getnFilas(); i++) {
			objetoGrafico.drawLine(0, (int)(i * espacioEntreFila), getWidth(), (int)(i * espacioEntreFila));
		}
 
		// Aquí pintamos las columnas
		for (int i = 0; i < getnColumnas(); i++) {
			objetoGrafico.drawLine((int)(i * espacioEntreColumna), 0, (int)(i * espacioEntreColumna), getHeight());
		}

	}*/
	
	/*protected void drawCelulas(Graphics objetoGrafico) {
		// Misma operacion que al dibujar la cuadrícula para pintar encima de ella
		double espacioEntreFila = (double)getHeight() / (double)getnFilas();       
		double espacioEntreColumna = (double)getWidth() / (double)getnColumnas();
		
		objetoGrafico.setColor(getColorCelulas());
		
		for(int i = 0; i < getnFilas(); i++)
			for(int j = 0; j < getnColumnas(); j++)
				if (getMatriz()[i][i].isEstado())
					objetoGrafico.fillRect((int)(i * espacioEntreColumna) + 1, (int)(j * espacioEntreFila) + 1, (int)espacioEntreColumna - 1, (int)espacioEntreFila - 1);
		
			// Bucle foreach de los caminos que hay para irlos pintando
			// Hacemos un cast de graphics a graphics2D para poder controlar la anchura de la línea que dibujamos usando basicStroke
			/*objetoGrafico.setColor(elemento.getColorCamino());
			Graphics2D objetoGrafico2D = (Graphics2D) objetoGrafico;
			objetoGrafico2D.setStroke(new BasicStroke (getAnchoLinea()));
			Polygon caminoDibujo = new Polygon ();
			
			// Guardamos los puntos en el polígono
			for (int i = 0; i < elemento.getPuntosVisitados().size(); i++)
				caminoDibujo.addPoint((int)(elemento.getPuntosVisitados().get(i).getX() * espacioEntreColumna), (int)(elemento.getPuntosVisitados().get(i).getY() * espacioEntreFila));
	
			// Dibujamos la polyline
			objetoGrafico2D.drawPolyline(caminoDibujo.xpoints, caminoDibujo.ypoints, caminoDibujo.npoints);

		
	}*/
	
	/**
	 * @return the matriz
	 */
	public ModeloCelula[][] getMatriz() {
		return matriz;
	}

	/**
	 * @param matriz the matriz to set
	 */
	public void setMatriz(ModeloCelula[][] matriz) {
		this.matriz = matriz;
	}

	/**
	 * @return the nFilas
	 */
	public int getnFilas() {
		return nFilas;
	}

	/**
	 * @param nFilas the nFilas to set
	 */
	public void setnFilas(int nFilas) {
		this.nFilas = nFilas;
	}

	/**
	 * @return the nColumnas
	 */
	public int getnColumnas() {
		return nColumnas;
	}

	/**
	 * @param nColumnas the nColumnas to set
	 */
	public void setnColumnas(int nColumnas) {
		this.nColumnas = nColumnas;
	}

	/**
	 * @return the small
	 */
	public static int getSmall() {
		return SMALL;
	}

	/**
	 * @return the medium
	 */
	public static int getMedium() {
		return MEDIUM;
	}

	/**
	 * @return the large
	 */
	public static int getLarge() {
		return LARGE;
	}

	/**
	 * @return the zonaExterior
	 */
	public static int getZonaExterior() {
		return ZONA_EXTERIOR;
	}

	/**
	 * @return the colorCuadricula
	 */
	public static Color getColorCuadricula() {
		return COLOR_CUADRICULA;
	}

	/**
	 * @return the colorCelulas
	 */
	public static Color getColorCelulas() {
		return COLOR_CELULAS;
	}

	/**
	 * @return the origenMatriz
	 */
	public static int getOrigenMatriz() {
		return ORIGEN_MATRIZ;
	}

	/**
	 * @return the temporizador
	 */
	public Timer getTemporizador() {
		return temporizador;
	}

	/**
	 * @param temporizador the temporizador to set
	 */
	public void setTemporizador(Timer temporizador) {
		this.temporizador = temporizador;
	}

	/**
	 * @return the delay
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * @param delay the delay to set
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}

	/**
	 * @return the glider
	 */
	public static int[][] getGlider() {
		return GLIDER;
	}

	/**
	 * @return the blinker
	 */
	public static int[][] getBlinker() {
		return BLINKER;
	}

	/**
	 * @return the spaceship
	 */
	public static int[][] getSpaceship() {
		return SPACESHIP;
	}

	/**
	 * @return the pentadec
	 */
	public static int[][] getPentadec() {
		return PENTADEC;
	}

	/**
	 * @return the pulsar
	 */
	public static int[][] getPulsar() {
		return PULSAR;
	}

	/**
	 * @return the gliderGun
	 */
	public static int[][] getGliderGun() {
		return GLIDER_GUN;
	}
	
}
