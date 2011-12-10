package geneticalgorithm.gui;

import geneticalgorithm.Parameters;
import geneticalgorithm.Population;

import java.awt.List;
import java.util.Arrays;

public class ListOfMaximumCandidates extends List {
	private static final long serialVersionUID = 1L;

	private final Population populatie;
	private final Parameters parametrii;

	public ListOfMaximumCandidates(Population populatie, Parameters parametrii) {
		super(9, false);
		this.populatie = populatie;
		this.parametrii = parametrii;
		setSize(270, 180);
		repaint();
	}

	@Override
	public void repaint() {
		// Populatie temp = populatie.clone();
		Object[] arr = populatie.toArray();
		Arrays.sort(arr);
		removeAll();

		for (int i = 0; i < 10 && i < parametrii.N; ++i)
			add(arr[arr.length - 1 - i].toString());
	}
}
