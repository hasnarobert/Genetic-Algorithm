package geneticalgorithm.gui;

import geneticalgorithm.Parameters;
import geneticalgorithm.Population;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

public class FunctionGraphicalImage extends Canvas {
	private static final long serialVersionUID = 1L;

	private final Parameters parametrii;
	private final Population populatie;

	public FunctionGraphicalImage(Parameters parametrii, Population populatie) {
		this.parametrii = parametrii;
		this.populatie = populatie;
		setBackground(Color.WHITE);
		setSize(500, 400);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D plansa = (Graphics2D) g;
		plansa.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		int height = getHeight();
		int width = getWidth();

		// desenez axele de coordonate
		plansa.drawLine(10, height / 2, width - 10, height / 2);
		plansa.drawLine(width - 10, height / 2, width - 20, height / 2 - 5);
		plansa.drawLine(width - 10, height / 2, width - 20, height / 2 + 5);
		plansa.drawString("x", width - 10, height / 2 + 15);

		plansa.drawLine(30, 10, 30, height - 10);
		plansa.drawLine(30, 10, 25, 20);
		plansa.drawLine(30, 10, 35, 20);
		plansa.drawString("f(x)", 2, 20);

		// desenez gradatiile pe axe
		for (int i = 3; i < width / 10 - 5; i += 3) {
			plansa.drawLine(30 + i * 10, height / 2 - 2, 30 + i * 10,
					height / 2 + 2);
			plansa.drawString((new Integer(i)).toString(), 23 + i * 10,
					height / 2 + 20);
		}
		for (int i = 3; i < height / 20 - 2; i += 3) {
			plansa.drawLine(28, height / 2 - i * 10, 32, height / 2 - i * 10);
			plansa.drawString((new Integer(i)).toString(), 5, height / 2 - i
					* 10 + 5);
		}
		for (int i = -3; i > -height / 20 + 2; i -= 3) {
			plansa.drawLine(28, height / 2 - i * 10, 32, height / 2 - i * 10);
			plansa.drawString((new Integer(i)).toString(), 5, height / 2 - i
					* 10 + 5);
		}

		// desenez graficul functiei
		plansa.setColor(Color.BLUE);
		ArrayList<Integer> fx = new ArrayList<Integer>();
		for (int i = (int) parametrii.A; i <= parametrii.B; ++i)
			fx.add((int) parametrii.f(i));

		for (int i = (int) parametrii.A; i < parametrii.B; ++i) {
			double x1, y1;
			x1 = (double) i / 10;
			y1 = parametrii.f(x1);
			x1 = x1 * 10;
			y1 = y1 * 10;
			double x2, y2;
			x2 = (double) (i + 1) / 10;
			y2 = parametrii.f(x2);
			x2 = x2 * 10;
			y2 = y2 * 10;
			plansa.drawLine(30 + (int) x1, height / 2 - (int) y1,
					30 + (int) x2, height / 2 - (int) y2);
		}

		// desenez populatia pe functie
		plansa.setColor(Color.RED);
		for (int i = 0; i < populatie.size(); ++i) {
			double x = populatie.get(i).individ();
			double y = parametrii.f(x / 10) * 10;
			plansa.fillOval(27 + (int) x, height / 2 - (int) y - 3, 6, 6);
		}
	}
}
