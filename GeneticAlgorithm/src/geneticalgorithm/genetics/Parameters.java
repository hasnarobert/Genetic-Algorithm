package geneticalgorithm.genetics;

public class Parameters implements Function {
	public int N;// dimensiunea populatiei
	public double PM;// probabilitatea de mutatie
	public double PC;// probabilitatea de incrucisare/crossover
	public int precizie;// numarul de zecimale exacte dupa virgula
	public int L;// dimensiunea cromozomului
	public double A, B; // capetele intervalului pe care vrea usa aplic
						// algoritmul
	public double eps; // eroarea minima acceptata
	public double m, n;// functia f(x) = mx + n care translateaza liniar
						// cromozomul in intervalul [A,B]
	public int nr_iteratii;
	public int nr_fct;

	public Parameters(int N, int precizie, double PM, double PC, double A,
			double B) {
		this.N = N;
		this.precizie = precizie;
		this.PM = PM;
		this.PC = PC;
		this.A = A;
		this.B = B;
		compute();
	}

	final public void compute() {
		this.L = 0;
		double temp = (B - A) * Math.pow(10, precizie);
		while ((temp /= 2) >= 0.5)
			++this.L;
		this.eps = 1 / Math.pow(10, precizie);
		this.n = A;
		this.m = (B - A) / ((1 << L) - 1);
	}

	@Override
	public double f(double x) {
		switch (nr_fct) {
		case 0:
			return Math.sin(x - 25) * (x - 25) / 1.7;
		case 1:
			return Math.sin(x) * Math.sin(x) * Math.log(x * 10);
		case 2:
			return (Math.sin(x) + Math.sin(x * 1.6)) * 4;
		case 3:
			return x == 10 ? 15 : Math.sin(x - 10) / (x - 10) * 15;
		case 4:
			return -0.04 * (x - 18) * (x - 18) - 5;
		default:
			return x / 3;
		}
	}
}
