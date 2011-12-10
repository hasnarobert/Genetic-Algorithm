package geneticalgorithm;

import java.util.ArrayList;
import java.util.Random;

public class Chromozom extends ArrayList<Boolean> implements
		Comparable<Chromozom>, Cloneable {
	private static final long serialVersionUID = 1L;

	private Parameters parametrii;
	private static Random random = new Random();

	public Chromozom(Parameters parametrii) {
		this.parametrii = parametrii;
		for (int i = 0; i < parametrii.L; ++i)
			add(random.nextBoolean());
	}

	public Chromozom mutatie() {
		int pozitie = random.nextInt(size());
		set(pozitie, Boolean.TRUE ^ get(pozitie));
		return this;
	}

	public void incrucisare(Chromozom cromozom) {
		int pozitie = random.nextInt(size());
		for (int i = pozitie; i < size(); ++i) {
			boolean temp = get(i);
			set(i, cromozom.get(i));
			cromozom.set(i, temp);
		}
	}

	private int bin2dec() {
		int temp = 0;
		for (int i = 0; i < size(); ++i)
			if (get(i))
				temp += 1 << (size() - 1 - i);
		return temp;
	}

	public double individ() {
		return parametrii.m * bin2dec() + parametrii.n;
	}

	private double individGrafic() {
		return ((int) ((individ() / 10) * Math.pow(10, parametrii.precizie)))
				/ Math.pow(10, parametrii.precizie);
	}

	public double performanta() {
		return parametrii.f(individ() / 10);
	}

	private double performantaGrafic() {
		return ((int) (parametrii.f(individGrafic()) * Math.pow(10,
				parametrii.precizie))) / Math.pow(10, parametrii.precizie);
	}

	@Override
	public int compareTo(Chromozom cromozom) {
		return performantaGrafic() < cromozom.performantaGrafic() ? -1 : 1;
	}

	@Override
	public String toString() {
		/*
		 * StringBuilder buff = new StringBuilder(); buff.append("["); for (int
		 * i = 0; i < size(); ++i) buff.append(get(i)?"1":"0");
		 * buff.append("]->("
		 * ).append(individ()).append(" ; ").append(performanta()).append(")");
		 * return buff.toString();
		 */

		StringBuilder buff = new StringBuilder();
		buff.append(individGrafic()).append(" -> ").append(performantaGrafic());
		return buff.toString();
	}

	@Override
	public Chromozom clone() {
		Chromozom clona = (Chromozom) super.clone();
		clona.parametrii = parametrii;
		return clona;
	}

	public String afisPeConsola() {
		StringBuilder buff = new StringBuilder();
		for (int i = 0; i < parametrii.L; ++i)
			buff.append(get(parametrii.L - i - 1) ? "1" : "0");
		buff.append(" ").append(this);
		return buff.toString();
	}
}
