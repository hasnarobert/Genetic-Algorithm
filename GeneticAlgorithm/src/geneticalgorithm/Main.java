package geneticalgorithm;

import geneticalgorithm.genetics.Parameters;
import geneticalgorithm.genetics.Population;
import geneticalgorithm.gui.GUI;

public class Main {
	public static void main(String[] args) {
		Parameters parametrii = new Parameters(0, 0, 0, 0, 0, 0);
		Population populatie = new Population(parametrii);

		GUI GUI = new GUI(parametrii, populatie);

		GUI.setVisible(true);
	}
}
