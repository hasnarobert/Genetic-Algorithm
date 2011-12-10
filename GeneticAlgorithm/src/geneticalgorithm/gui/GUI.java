package geneticalgorithm.gui;

import geneticalgorithm.Parameters;
import geneticalgorithm.Population;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

public class GUI extends Frame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private final Parameters parametrii;
	private final Population populatie;
	private final FunctionGraphicalImage grFct;

	private final Button load;
	private final Button start;
	private final Button pauza;
	private final Button oiteratie;
	private final Button reset;

	private final TextField NTxt;
	private final TextField PMTxt;
	private final TextField PCTxt;
	private final TextField NrIterTxt;
	private final TextField ATxt;
	private final TextField BTxt;
	private final TextField PrecTxt;

	private final Choice functie;

	private final ListOfMaximumCandidates maxime;

	private boolean prima_iteratie;

	public GUI(Parameters parametrii, Population populatie) {
		// creez Frame-ul
		super("Genetic Algorithm - Maximum of a function");
		this.parametrii = parametrii;
		this.populatie = populatie;
		setSize(800, 500);
		setLayout(null);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent ev) {
				System.exit(0);
			}
		});

		// adaug graficul functiei
		grFct = new FunctionGraphicalImage(parametrii, populatie);
		grFct.setLocation(10, 37);
		add(grFct);

		// creez panourile cu controale
		Panel controaleDreapta = new Panel(new GridLayout(7, 2));
		Panel controaleJos = new Panel(new GridLayout(1, 5));
		add(controaleDreapta);
		controaleDreapta.setBounds(520, 37, 270, 200);
		add(controaleJos);
		controaleJos.setBounds(10, 447, 780, 40);

		// creez controalele de jos
		load = new Button("Load");
		start = new Button("Start");
		pauza = new Button("Pause");
		oiteratie = new Button("One iteration");
		reset = new Button("Reset");

		controaleJos.add(load);
		controaleJos.add(start);
		// controaleJos.add(pauza);
		controaleJos.add(oiteratie);
		controaleJos.add(reset);

		// creez controalele din dreapta
		NTxt = new TextField();
		PMTxt = new TextField();
		PCTxt = new TextField();
		NrIterTxt = new TextField();
		ATxt = new TextField(6);
		BTxt = new TextField(6);
		PrecTxt = new TextField();
		functie = new Choice();

		controaleDreapta.add(new Label("Population size :"));
		controaleDreapta.add(NTxt);
		controaleDreapta.add(new Label("Mutation prob :"));
		controaleDreapta.add(PMTxt);
		controaleDreapta.add(new Label("Crossing prob :"));
		controaleDreapta.add(PCTxt);
		controaleDreapta.add(new Label("Precision :"));
		controaleDreapta.add(PrecTxt);
		controaleDreapta.add(new Label("Range : "));
		Panel interval = new Panel(new GridLayout(1, 2));
		interval.add(ATxt);
		interval.add(BTxt);
		controaleDreapta.add(interval);
		controaleDreapta.add(new Label("No of iterations :"));
		controaleDreapta.add(NrIterTxt);
		controaleDreapta.add(new Label("Function :"));
		functie.addItem("sin(x-25)*(x-25)/1.7");
		functie.addItem("sin(x)^2 * ln(x*10)");
		functie.addItem("4sin(x)+4sin(1.6x)");
		functie.addItem("sin(x-10)/(x-10)*15");
		functie.addItem("-0.04(x-18)^2 - 5");
		controaleDreapta.add(functie);

		// creez lista unde se afiseaza maximele
		maxime = new ListOfMaximumCandidates(populatie, parametrii);
		maxime.setLocation(520, 257);
		add(maxime);

		// initializari
		start.setEnabled(false);
		pauza.setEnabled(false);
		oiteratie.setEnabled(false);
		load.addActionListener(this);
		start.addActionListener(this);
		pauza.addActionListener(this);
		oiteratie.addActionListener(this);
		reset.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Button buton = (Button) e.getSource();

		if (buton.equals(reset)) {
			doReset();
		} else if (buton.equals(load)) {
			doLoad();
		} else if (buton.equals(start)) {
			doStart();
		} else if (buton.equals(pauza)) {
			// doPauza();
			;
		} else if (buton.equals(oiteratie)) {
			doOIteratie();
		}
	}

	private void doLoad() {
		try {
			parametrii.N = Integer.parseInt(NTxt.getText());
			parametrii.PM = Double.parseDouble(PMTxt.getText());
			parametrii.PC = Double.parseDouble(PCTxt.getText());
			parametrii.A = Double.parseDouble(ATxt.getText()) * 10;
			parametrii.B = Double.parseDouble(BTxt.getText()) * 10;
			parametrii.nr_iteratii = Integer.parseInt(NrIterTxt.getText());
			parametrii.precizie = Integer.parseInt(PrecTxt.getText());
			parametrii.nr_fct = functie.getSelectedIndex();

			if (parametrii.A < 0 || parametrii.A > 420)
				throw new Exception();
			if (parametrii.B < 0 || parametrii.B > 450
					|| parametrii.A > parametrii.B)
				throw new Exception();
			if (parametrii.N < 2)
				throw new Exception();
			if (parametrii.PC < 0 || parametrii.PC > 1)
				throw new Exception();
			if (parametrii.PM < 0 || parametrii.PM > 1)
				throw new Exception();
			if (parametrii.precizie < 0 || parametrii.precizie > 6)
				throw new Exception();
			if (parametrii.nr_iteratii < 1)
				throw new Exception();

			parametrii.compute();
			populatie.createPopulatie();
			reloadInterfata();
			NTxt.setEditable(false);
			PMTxt.setEditable(false);
			PCTxt.setEditable(false);
			PCTxt.setEditable(false);
			PrecTxt.setEnabled(false);
			ATxt.setEnabled(false);
			BTxt.setEnabled(false);
			NrIterTxt.setEnabled(false);
			start.setEnabled(true);
			pauza.setEnabled(true);
			oiteratie.setEnabled(true);
			load.setEnabled(false);
			functie.setEnabled(false);
			this.prima_iteratie = true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"Esti tzaran? Baga bine valorile!");
		}

	}

	private void doReset() {
		parametrii.N = 0;
		parametrii.PM = 0;
		parametrii.PC = 0;
		parametrii.A = 0;
		parametrii.B = 0;
		parametrii.nr_iteratii = 0;
		parametrii.precizie = 0;
		parametrii.compute();
		populatie.createPopulatie();
		reloadInterfata();
		NTxt.setEditable(true);
		PMTxt.setEditable(true);
		PCTxt.setEditable(true);
		PCTxt.setEditable(true);
		PrecTxt.setEnabled(true);
		ATxt.setEnabled(true);
		BTxt.setEnabled(true);
		NrIterTxt.setEnabled(true);

		/*
		 * NTxt.setText(""); PMTxt.setText(""); PCTxt.setText("");
		 * PCTxt.setText(""); PrecTxt.setText(""); ATxt.setText("");
		 * BTxt.setText(""); NrIterTxt.setText("");
		 */

		start.setEnabled(false);
		pauza.setEnabled(false);
		oiteratie.setEnabled(false);
		load.setEnabled(true);
		functie.setEnabled(true);
	}

	private void doOIteratie() {
		if (prima_iteratie)
			System.out.println("Populatia initiala : " + populatie);
		prima_iteratie = false;
		if (NrIterTxt.getText().equals("0"))
			return;
		populatie.evolutie();
		reloadInterfata();
		NrIterTxt
				.setText(new Integer(Integer.parseInt(NrIterTxt.getText()) - 1)
						.toString());
		if (NrIterTxt.getText().equals("0")) {
			oiteratie.setEnabled(false);
			start.setEnabled(false);
			pauza.setEnabled(false);
		}
	}

	private void reloadInterfata() {
		grFct.repaint();
		maxime.repaint();
	}

	private void doStart() {
		for (int i = 0; i < parametrii.nr_iteratii; ++i)
			populatie.evolutie();
		reloadInterfata();
		start.setEnabled(false);
		pauza.setEnabled(false);
		NrIterTxt.setText("0");
		oiteratie.setEnabled(false);
	}
}
