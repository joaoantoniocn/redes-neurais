package mlp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import exercicio1.Perceptron;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		// double[][] base = {{0, 0}, {0, 1}, {1, 0}, {1, 1}}; // xor
		// int[][] label = {{0}, {1}, {1}, {0}}; // xor

		int folder = 10;

		double[][] baseTreino = getFileWine("./databases/wine.txt", folder,
				true);
		double[][] baseTeste = getFileWine("./databases/wine.txt", folder,
				false);

		int[][] labelTreino = getLabelWine("./databases/wine.txt", folder, true);
		int[][] labelTeste = getLabelWine("./databases/wine.txt", folder, false);

		// printBase(baseTeste);

		int[] neuroniosPorCamada = {  3, 3 };

		MLP mlp = new MLP(baseTreino, labelTreino, neuroniosPorCamada);

		//mlp.treinar(1000); // iris
		mlp.treinar(10000);

//		double[] e = { 13.2,1.78,2.14,11.2,100,2.65,2.76,.26,1.28,4.38,1.05,3.4,1050 };
//
//		printBase(mlp.run(e));

		System.out.println(mlp.calcularTaxaAcerto(baseTeste, labelTeste));

	}

	public static double[][] getFileWine(String path, int folder,
			boolean isTreino) {

		double[][] matriz = null;
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
			String[] lines = everything.split("\n");

			if (isTreino) {
				matriz = new double[lines.length - 12][13];
			} else {
				matriz = new double[12][13];
			}

			// System.out.println("total " + lines.length);

			int indice = 0;
			int classes;
			int classe1 = 0;
			int classe2 = 0;
			int classe3 = 0;

			for (int i = 0; i < lines.length; i++) {

				String[] atributos = lines[i].split(",");

				if (Double.parseDouble(atributos[0]) == 1) {
					classe1 += 1;
					classes = classe1 - 1;
				} else if (Double.parseDouble(atributos[0]) == 2) {
					classe2 += 1;
					classes = classe2 - 1;
				} else {
					classe3 += 1;
					classes = classe3 - 1;
				}

				if (!isTreino) {
					if ((classes >= ((folder - 1) * 4))
							&& (classes < ((folder) * 4))) {
						// System.out.println("classe: " + classes +
						// " - indice: " + indice);
						matriz[indice][0] = Double.parseDouble(atributos[1]);
						matriz[indice][1] = Double.parseDouble(atributos[2]);
						matriz[indice][2] = Double.parseDouble(atributos[3]);
						matriz[indice][3] = Double.parseDouble(atributos[4]);
						matriz[indice][4] = Double.parseDouble(atributos[5]);
						matriz[indice][5] = Double.parseDouble(atributos[6]);
						matriz[indice][6] = Double.parseDouble(atributos[7]);
						matriz[indice][7] = Double.parseDouble(atributos[8]);
						matriz[indice][8] = Double.parseDouble(atributos[9]);
						matriz[indice][9] = Double.parseDouble(atributos[10]);
						matriz[indice][10] = Double.parseDouble(atributos[11]);
						matriz[indice][11] = Double.parseDouble(atributos[12]);
						matriz[indice][12] = Double.parseDouble(atributos[13]);

						indice += 1;
					}
				} else {
					if (!((classes >= ((folder - 1) * 4)) && (classes < ((folder) * 4)))) {
						// System.out.println("classe: " + classes +
						// " - indice: " + indice);
						matriz[indice][0] = Double.parseDouble(atributos[1]);
						matriz[indice][1] = Double.parseDouble(atributos[2]);
						matriz[indice][2] = Double.parseDouble(atributos[3]);
						matriz[indice][3] = Double.parseDouble(atributos[4]);
						matriz[indice][4] = Double.parseDouble(atributos[5]);
						matriz[indice][5] = Double.parseDouble(atributos[6]);
						matriz[indice][6] = Double.parseDouble(atributos[7]);
						matriz[indice][7] = Double.parseDouble(atributos[8]);
						matriz[indice][8] = Double.parseDouble(atributos[9]);
						matriz[indice][9] = Double.parseDouble(atributos[10]);
						matriz[indice][10] = Double.parseDouble(atributos[11]);
						matriz[indice][11] = Double.parseDouble(atributos[12]);
						matriz[indice][12] = Double.parseDouble(atributos[13]);

						indice += 1;
					}
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return matriz;

	}

	public static int[][] getLabelWine(String path, int folder, boolean isTreino) {

		int[][] matriz = null;
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
			String[] lines = everything.split("\n");

			if (isTreino) {
				matriz = new int[lines.length - 12][3];
			} else {
				matriz = new int[12][3];
			}

			int indice = 0;
			int classes;
			int classe1 = 0;
			int classe2 = 0;
			int classe3 = 0;

			for (int i = 0; i < lines.length; i++) {

				String[] atributos = lines[i].split(",");

				if (Double.parseDouble(atributos[0]) == 1) {
					classe1 += 1;
					classes = classe1 - 1;
				} else if (Double.parseDouble(atributos[0]) == 2) {
					classe2 += 1;
					classes = classe2 - 1;
				} else {
					classe3 += 1;
					classes = classe3 - 1;
				}

				if (!isTreino) {
					if ((classes >= ((folder - 1) * 4))
							&& (classes < ((folder) * 4))) {

						if (atributos[0].contains("1")) {
							matriz[indice][0] = 1;
							matriz[indice][1] = 0;
							matriz[indice][2] = 0;

						} else if (atributos[0].contains("2")) {
							matriz[indice][0] = 0;
							matriz[indice][1] = 1;
							matriz[indice][2] = 0;
						} else if (atributos[0].contains("3")) {
							matriz[indice][0] = 0;
							matriz[indice][1] = 0;
							matriz[indice][2] = 1;
						}

						indice += 1;
					}
				} else {
					if (!((classes >= ((folder - 1) * 4)) && (classes < ((folder) * 4)))) {

						if (atributos[0].contains("1")) {
							matriz[indice][0] = 1;
							matriz[indice][1] = 0;
							matriz[indice][2] = 0;

						} else if (atributos[0].contains("2")) {
							matriz[indice][0] = 0;
							matriz[indice][1] = 1;
							matriz[indice][2] = 0;
						} else if (atributos[0].contains("3")) {
							matriz[indice][0] = 0;
							matriz[indice][1] = 0;
							matriz[indice][2] = 1;
						}

						indice += 1;
					}
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return matriz;

	}

	public static double[][] getFileIris(String path, int folder,
			boolean isTreino) {

		double[][] matriz = null;
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
			String[] lines = everything.split("\n");

			if (isTreino) {
				matriz = new double[lines.length - 15][4];
			} else {
				matriz = new double[15][4];
			}

			int indice = 0;
			int classes;

			for (int i = 0; i < lines.length; i++) {

				String[] atributos = lines[i].split(",");

				classes = i % 50;

				if (!isTreino) {
					if ((classes >= ((folder - 1) * 5))
							&& (classes < ((folder) * 5))) {
						matriz[indice][0] = Double.parseDouble(atributos[0]);
						matriz[indice][1] = Double.parseDouble(atributos[1]);
						matriz[indice][2] = Double.parseDouble(atributos[2]);
						matriz[indice][3] = Double.parseDouble(atributos[3]);

						indice += 1;
					}
				} else {
					if (!((classes >= ((folder - 1) * 5)) && (classes < ((folder) * 5)))) {
						matriz[indice][0] = Double.parseDouble(atributos[0]);
						matriz[indice][1] = Double.parseDouble(atributos[1]);
						matriz[indice][2] = Double.parseDouble(atributos[2]);
						matriz[indice][3] = Double.parseDouble(atributos[3]);

						indice += 1;
					}
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return matriz;

	}

	public static int[][] getLabelIris(String path, int folder, boolean isTreino) {

		int[][] matriz = null;
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
			String[] lines = everything.split("\n");

			if (isTreino) {
				matriz = new int[lines.length - 15][3];
			} else {
				matriz = new int[15][3];
			}

			int indice = 0;
			int classes;

			for (int i = 0; i < lines.length; i++) {

				String[] atributos = lines[i].split(",");

				classes = i % 50;

				if (!isTreino) {
					if ((classes >= ((folder - 1) * 5))
							&& (classes < ((folder) * 5))) {

						if (atributos[4].contains("Iris-setosa")) {
							matriz[indice][0] = 1;
							matriz[indice][1] = 0;
							matriz[indice][2] = 0;

						} else if (atributos[4].contains("Iris-versicolor")) {
							matriz[indice][0] = 0;
							matriz[indice][1] = 1;
							matriz[indice][2] = 0;
						} else if (atributos[4].contains("Iris-virginica")) {
							matriz[indice][0] = 0;
							matriz[indice][1] = 0;
							matriz[indice][2] = 1;
						}

						indice += 1;
					}
				} else {
					if (!((classes >= ((folder - 1) * 5)) && (classes < ((folder) * 5)))) {

						if (atributos[4].contains("Iris-setosa")) {
							matriz[indice][0] = 1;
							matriz[indice][1] = 0;
							matriz[indice][2] = 0;

						} else if (atributos[4].contains("Iris-versicolor")) {
							matriz[indice][0] = 0;
							matriz[indice][1] = 1;
							matriz[indice][2] = 0;
						} else if (atributos[4].contains("Iris-virginica")) {
							matriz[indice][0] = 0;
							matriz[indice][1] = 0;
							matriz[indice][2] = 1;
						}

						indice += 1;
					}
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return matriz;

	}

	public static double[][] getFile(String path) {

		double[][] matriz = null;
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
			String[] lines = everything.split("\n");

			matriz = new double[lines.length][4];

			for (int i = 0; i < lines.length; i++) {

				String[] atributos = lines[i].split(",");

				matriz[i][0] = Double.parseDouble(atributos[0]);
				matriz[i][1] = Double.parseDouble(atributos[1]);
				matriz[i][2] = Double.parseDouble(atributos[2]);
				matriz[i][3] = Double.parseDouble(atributos[3]);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return matriz;

	}

	public static int[][] getLabel(String path) {

		int[][] matriz = null;
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
			String[] lines = everything.split("\n");

			matriz = new int[lines.length][3];

			for (int i = 0; i < lines.length; i++) {

				String[] atributos = lines[i].split(",");

				if (atributos[4].contains("Iris-setosa")) {
					matriz[i][0] = 1;
					matriz[i][1] = 0;
					matriz[i][2] = 0;

				} else if (atributos[4].contains("Iris-versicolor")) {
					matriz[i][0] = 0;
					matriz[i][1] = 1;
					matriz[i][2] = 0;
				} else if (atributos[4].contains("Iris-virginica")) {
					matriz[i][0] = 0;
					matriz[i][1] = 0;
					matriz[i][2] = 1;
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return matriz;

	}

	public static void printBase(double[][] base) {

		for (int i = 0; i < base.length; i++) {
			System.out.println();
			for (int j = 0; j < base[0].length; j++) {
				System.out.print(base[i][j]);
				System.out.print(" - ");
			}
		}

	}

	public static void printBase(int[][] base) {

		for (int i = 0; i < base.length; i++) {
			System.out.println();
			for (int j = 0; j < base[0].length; j++) {
				System.out.print(base[i][j]);
				System.out.print(" - ");
			}
		}

	}

	public static void printBase(int[] base) {

		for (int j = 0; j < base.length; j++) {
			System.out.print(base[j]);
			System.out.print(" - ");
		}

	}
	public static void printBase(double[] base) {

		for (int j = 0; j < base.length; j++) {
			System.out.print(base[j]);
			System.out.print(" - ");
		}

	}

}
