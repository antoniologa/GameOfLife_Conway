/**
@author Antonio Jesús López Garnier - Correo: alu0100454437@ull.edu.es
@see <a href = "https://github.com/AntonioGarnier" > Mi Github </a>
@see <a href = "https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life" > Game Of Life Wiki </a>
@version 1.0
*/


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ModeloCelula extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private static final int RADIO = 1;	//Debe tener el mismo valor que MARGEN en Universo. Indica el radio de alcance para calcular las vecinas
	private static final int SEPARADOR = (int) ((Math.pow((2 * RADIO + 1), 2) - 1) / 2); //Indica cuando debe comparar con el estado anterior de la célula y cuando con el actual
	private static final Color VIVA = Color.YELLOW;
	private static final Color MUERTA = Color.GRAY;
	private boolean estadoActual, estadoAnterior; //Estado actual y anterior de la célula, para poder hacer la evolución simultáneamente

	/**
	 * Sobrecarga del constructor
	 * @param estado Define el estado inicial de la célula (True = viva; False = Muerta)
	 */
	public ModeloCelula (boolean estado)	{
		setEstadoActual(estado);
		setEstadoAnterior(estado);
		setBorder(new LineBorder(Color.BLACK));
		addMouseListener(this);
		if (!estado) 
			setBackground(getMuerta());
		else
			setBackground(getViva());

	}
	
	/**
	 * Constructor por defecto que crea una célula muerta
	 */
	public ModeloCelula ()	{
		this (false);
	}
	
	/**
	 * @return the radio
	 */
	public static int getRadio() {
		return RADIO;
	}

	/**
	 * @return the separador
	 */
	public static int getSeparador() {
		return SEPARADOR;
	}
	
	/**
	 * Getter para saber si la célula ESTÁ viva
	 * @return the estadoActual
	 */
	public boolean isEstadoActual() {
		return estadoActual;
	}

	/**
	 * Setter para establecer el estado actual de la célual
	 * @param estadoActual the estadoActual to set
	 */
	public void setEstadoActual(boolean estadoActual) {
		this.estadoActual = estadoActual;
	}

	/**
	 * Getter para saber si la célula ESTABA viva
	 * @return the estadoAnterior
	 */
	public boolean isEstadoAnterior() {
		return estadoAnterior;
	}

	/**
	 * Setter para establecer el estado anterior de la célula
	 * @param estadoAnterior the estadoAnterior to set
	 */
	public void setEstadoAnterior(boolean estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	/**
	 * Setter para actualizar el estado. Cambia el valor del estado anterior por el estado actual
	 * y el estado actual lo actualiza al nuevo estado
	 * @param estadoActualizado
	 */
	public void setEstado (boolean estadoActualizado)	{
		setEstadoAnterior (isEstadoActual());
		setEstadoActual (estadoActualizado);
		repaint();
	}
	
	/**
	 * Método que calcula el número de células vecinas vivas que tiene la célula que se esta actualizando
	 * @param tablero Define el tablero de células
	 * @param fila Define la fila que ocupa la célula que se está actualizando
	 * @param columna Define la columna que ocupa la célula que se está actualizando
	 * @return
	 */
	public int vecindad (VistaGameOfLife tablero, int fila, int columna)	{
		int vecinas = 0;
		int cuentaSeparacion = 0;
		/*
		 * Este bucle recorre solo las células alrededor de la célula que se esta analizando
		 * la constante RADIO se utiliza para aumentar o disminuir el alcance a la hora de calcular
		 * el númer de vecinas que tiene una célula
		 */
		for (int i = fila - getRadio(); i <= fila + getRadio(); i++)
			for (int j = columna - getRadio(); j <= columna + getRadio(); j++)	{
				if (cuentaSeparacion < getSeparador())	{
					if (tablero.getMatriz()[i] [j].isEstadoAnterior())
						vecinas++;
				}
				else
					if (cuentaSeparacion > getSeparador())
						if(tablero.getMatriz()[i] [j].isEstadoActual())
							vecinas++;	
				cuentaSeparacion++;
		}
		return vecinas;
	}
	
	/*
	 *	Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.
		Any live cell with two or three live neighbours lives on to the next generation.
		Any live cell with more than three live neighbours dies, as if by overpopulation.
		Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction. 
	 */
	/**
	 * Método que actualiza el estado de la celula que se está actualizando segun las siguiente reglas:
	 * - Cualquier célula viva con menos de 2 vecinas vivas, muere por despoblación.
	 * - Cualquier célula viva con 2 o mas vecinas vivas, vive en la siguiente generación.
	 * - Cualquier célula viva con mas de 3 vecinas vivas, muere por sobrepoblación.
	 * - Cualquier célula muerta con, exactamente, 3 vecinas vivas, nace por reproducción.
	 * @param tablero Define el tablero (universo) de células
	 * @param fila Define la fila que ocupa la célula que se está actualizando
	 * @param columna Define la columna que ocupa la célula que se está actualizando
	 */
	public void actualizaCelula (VistaGameOfLife tablero, int fila, int columna)	{
		int vecinas = vecindad (tablero, fila, columna); 
		if (isEstadoActual())
			if (vecinas == 2 || vecinas == 3)
				tablero.getMatriz()[fila] [columna].setEstado(true);
			else
				tablero.getMatriz()[fila] [columna].setEstado(false);
		else
			if (vecinas == 3)
				tablero.getMatriz()[fila] [columna].setEstado(true);
			else
				tablero.getMatriz()[fila] [columna].setEstado(false);
	}
	
	/**
	 * Método toString para mostrar un objeto Célula (* = Muerta; 'o' = Viva)
	 */
	public String toString ()	{
		if (estadoActual)
			return "o";
		return "*";
	}

	/**
	 * @return the viva
	 */
	public static Color getViva() {
		return VIVA;
	}

	/**
	 * @return the muerta
	 */
	public static Color getMuerta() {
		return MUERTA;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!isEstadoActual()) 
			setBackground(getMuerta());
		else
			setBackground(getViva());

	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (isEstadoActual())
			setEstado(false);
		else 
			setEstado(true);
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
