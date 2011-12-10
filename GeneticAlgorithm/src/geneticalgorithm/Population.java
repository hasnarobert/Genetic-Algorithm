package geneticalgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population extends ArrayList<Chromozom> implements Cloneable {
	private static final long serialVersionUID = 1L;

	private Parameters parametrii;
	private static Random random = new Random();
	private boolean prima_iteratie = true;

	public Population(Parameters parametrii) {
		this.parametrii = parametrii;
		for (int i = 0; i < parametrii.N; ++i)
			add(new Chromozom(parametrii));
	}

	public void createPopulatie() {
		clear();
		for (int i = 0; i < parametrii.N; ++i)
			add(new Chromozom(parametrii));
		prima_iteratie = true;
	}

	private void selectie() {
		double suma = 0, min_negativ = 0, temp;
		ArrayList<Double> probabilitate = new ArrayList<Double>();
		ArrayList<Double> sume_partiale = new ArrayList<Double>();

		for (int i = 0; i < size(); ++i)
			if ((temp = get(i).performanta()) < 0 && min_negativ > temp)
				min_negativ = temp;
		for (int i = 0; i < size(); ++i)
			suma += get(i).performanta() - min_negativ;
		for (int i = 0; i < size(); ++i) {
			probabilitate.add((get(i).performanta() - min_negativ) / suma);
			if (i == 0)
				sume_partiale.add(probabilitate.get(i));
			else
				sume_partiale.add(sume_partiale.get(i - 1)
						+ probabilitate.get(i));
		}
		if (prima_iteratie) {
			System.out
					.println("Probabilitatile de selectie sunt urmatoarele : ");
			for (int i = 0; i < parametrii.N; ++i)
				System.out.println(get(i).afisPeConsola() + " probabilitate : "
						+ probabilitate.get(i));

			System.out.println("Intervalele de selectie sunt urmatoarele : ");
			System.out.println("0 " + sume_partiale.get(0));
			for (int i = 0; i < sume_partiale.size() - 1; ++i)
				System.out.println(sume_partiale.get(i) + "  "
						+ sume_partiale.get(i + 1));
		}

		Population clona = clone();
		clear();

		for (int i = 0; i < parametrii.N; ++i) {
			double indicator = random.nextDouble();
			if (prima_iteratie)
				System.out.print("Aleator uniform : " + indicator);
			suma = 0;

			int st = 0, dr = sume_partiale.size() - 1;

			while (st < dr) {
				if (st == dr - 1)
					break;
				int mij = (st + dr) >> 1;
				if (sume_partiale.get(mij) >= indicator)
					dr = mij;
				else
					st = mij;
			}
			if (sume_partiale.get(st) >= indicator)
				dr = st;

			add(clona.get(dr).clone());
			if (prima_iteratie)
				System.out.println(" Si s-a ales cromozomul cu numarul " + dr);
		}
	}

	private void incrucisare() {
		ArrayList<Integer> indici = new ArrayList<Integer>();
		for (int i = 0; i < size(); ++i) {
			double indicator = random.nextDouble();
			if (indicator <= parametrii.PC)
				indici.add(i);
		}

		Collections.shuffle(indici);

		for (int i = 0; i + 1 < indici.size(); i += 2) {
			if (prima_iteratie)
				System.out.println("S-a incrucisat " + indici.get(i) + " cu "
						+ indici.get(i + 1));
			get(indici.get(i)).incrucisare(get(indici.get(i + 1)));
		}
	}

	private void mutatie() {
		for (int i = 0; i < size(); ++i) {
			double indicator = random.nextDouble();
			if (indicator <= parametrii.PM) {
				if (prima_iteratie)
					System.out.println("A fost supus mutatiei cromozomul " + i);
				get(i).mutatie();
			}
		}
		prima_iteratie = false;
	}

	public void evolutie() {
		selectie();
		incrucisare();
		mutatie();
	}

	@Override
	public String toString() {
		StringBuilder buff = new StringBuilder();
		buff.append("Populatie : { ");
		for (int i = 0; i < size(); ++i)
			buff.append(get(i).afisPeConsola()).append(" ");
		buff.append("}");
		return buff.toString();
	}

	@Override
	public Population clone() {
		Population clona = (Population) super.clone();
		clona.parametrii = parametrii;
		return clona;
	}
}
