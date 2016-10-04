package exercicio1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] matrizSetosaTreino = getFile("./databases/iris.txt",
				"Iris-setosa", true);
		double[][] matrizSetosaTeste = getFile("./databases/iris.txt",
				"Iris-setosa", false);

		double[][] matrizVersicolorTreino = getFile("./databases/iris.txt",
				"Iris-versicolor", true);
		double[][] matrizVersicolorTeste = getFile("./databases/iris.txt",
				"Iris-versicolor", false);

		double[][] matrizVirginicaTreino = getFile("./databases/iris.txt",
				"Iris-virginica", true);
		double[][] matrizVirginicaTeste = getFile("./databases/iris.txt",
				"Iris-virginica", false);

		Perceptron pSetosa = new Perceptron(matrizSetosaTreino);
		Perceptron pVersicolor = new Perceptron(matrizVersicolorTreino);
		Perceptron pVirginica = new Perceptron(matrizVirginicaTreino);

		pSetosa.treinarAleatorio();
		pVersicolor.treinarAleatorio();
		pVirginica.treinarAleatorio();

		//quantidadeErros(matrizSetosaTeste, pSetosa);

		// calcularMatrizConfusao(matrizSetosaTeste, "Setosa", pSetosa,
		// pVersicolor, pVirginica);

		// System.out.println("erro setosa = " + pSetosa.calcularError());
		// System.out.println("erro versicolor = " +
		// pVersicolor.calcularError());
		// System.out.println("erro virginica  = " +
		// pVirginica.calcularError());

		 Perceptron mVersicolor = new Perceptron(getSaida(matrizVersicolorTreino, pSetosa, pVirginica));
		 quantidadeErros(matrizVersicolorTeste, mVersicolor, pSetosa, pVirginica);
		// mVersicolor.treinar();

		// double[] e = { 6.3, 3.3, 6.0, 2.5 };
		// double[] linha = new double[3];
		// linha[0] = pSetosa.run(e);
		// linha[1] = pVirginica.run(e);
		// linha[2] = 0;
		//
		//
		//
		// System.out.println(pSetosa.run(e));

		// System.out.println("epocas = "+ pVirginica.getNumEpocas());
		// System.out.println("antigo error = " + pVersicolor.calcularError());
		// System.out.println("novo error = " + mVersicolor.calcularError());
		// System.out.println("resultado  = " + mVersicolor.run(linha));
		// //pVirginica.printBase();

		// System.out.println("Setosa = " + pSetosa.run(e));
		// System.out.println("Versicolor = " + pVersicolor.run(e));
		// System.out.println("Virginica = " + pVirginica.run(e));
		// System.out.println(classificar(e, pSetosa, pVersicolor, pVirginica));

	}

	// Calcula quantidade de exemplos errados
	public static void quantidadeErros(double[][] teste, Perceptron treino) {

		int erros = 0;

		for (int i = 0; i < teste.length; i++) {

			if (treino.run(teste[i]) != teste[i][4]) {
				erros += 1;
			}

		}

		System.out.println("erros = " + erros);

	}

	// Calcula quantidade de exemplos errados (no perceptron treinado a partir da saida dos outros dois)
	public static void quantidadeErros(double[][] teste, Perceptron treino, Perceptron p1, Perceptron p2) {

		int erros = 0;
		double[] e;
		for (int i = 0; i < teste.length; i++) {
			e = new double[3];
			e[0] = p1.run(teste[i]);
			e[1] = p2.run(teste[i]);
			e[2] = 0;
			
			if (treino.run(e) != teste[i][4]) {
				erros += 1;
			}

		}

		System.out.println("erros = " + erros);

	}

	public static void calcularMatrizConfusao(double[][] base,
			String classeLabel, Perceptron pSetosa, Perceptron pVersicolor,
			Perceptron pVirginica) {

		System.out.println(classeLabel);
		int setosa = 0;
		int versicolo = 0;
		int virginica = 0;

		String result = "";
		for (int i = 0; i < base.length; i++) {
			if (base[i][4] == 1) {
				result = classificar(base[i], pSetosa, pVersicolor, pVirginica);

				if (result.equals("Versicolo")) {
					versicolo += 1;
				} else if (result.equals("Setosa")) {
					setosa += 1;
				} else if (result.equals("Virginica")) {
					virginica += 1;
				}

			}
		} // for

		System.out.println("Setosa = " + setosa);
		System.out.println("Versicolo = " + versicolo);
		System.out.println("Virginica = " + virginica);

	}

	/*
	 * Recebe a matriz usada para treinar o Perceptron da versicolor e retorna a
	 * saida dos dois outros Perceptrons para essa msm matriz, junto com a
	 * resposta esperada para cada linha.
	 */
	public static double[][] getSaida(double[][] matrizVersicolor,
			Perceptron pSetosa, Perceptron pVirginica) {
		double[][] matriz = new double[matrizVersicolor.length][3];
		double[] linha;

		for (int i = 0; i < matrizVersicolor.length; i++) {
			linha = new double[3];

			linha[0] = pSetosa.run(matrizVersicolor[i]);
			linha[1] = pVirginica.run(matrizVersicolor[i]);
			linha[2] = matrizVersicolor[i][matrizVersicolor[1].length - 1];

			matriz[i] = linha;
		}

		return matriz;
	}

	public static String classificar(double[] e, Perceptron pSetosa,
			Perceptron pVersicolor, Perceptron pVirginica) {
		String classeName = "";

		double resultSetosa = pSetosa.run(e);
		double resultVersicolo = pVersicolor.run(e);
		double resultVirginica = pVirginica.run(e);

		double pesoSetosa = -1
				* (((double) pSetosa.calcularError() / (double) pSetosa
						.getMatriz().length) - 1);
		double pesoVersicolo = -1
				* (((double) pVersicolor.calcularError() / (double) pVersicolor
						.getMatriz().length) - 1);
		double pesoVirginica = -1
				* (((double) pVirginica.calcularError() / (double) pVirginica
						.getMatriz().length) - 1);

		double chanceSetosa = resultSetosa * pesoSetosa;
		double chanceVersicolo = resultVersicolo * pesoVersicolo;
		double chanceVirginica = resultVirginica * pesoVirginica;

		// Quando nenhum dos Perceptrons classificam como positivo
		// a classe Versicolo é escolhida para resposta.
		// Isso é feito pq a taxa de acerto dela é mto baixa
		if ((chanceVersicolo >= chanceSetosa)
				&& (chanceVersicolo >= chanceVirginica)) {
			classeName = "Versicolo";
		} else if ((chanceSetosa >= chanceVersicolo)
				&& (chanceSetosa >= chanceVirginica)) {
			classeName = "Setosa";
		} else if ((chanceVirginica >= chanceSetosa)
				&& (chanceVirginica >= chanceVersicolo)) {
			classeName = "Virginica";
		}

		return classeName;
	}

	public static double[][] getFile(String path, String classe) {

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

			matriz = new double[lines.length][5];

			for (int i = 0; i < lines.length; i++) {

				String[] atributos = lines[i].split(",");

				matriz[i][0] = Double.parseDouble(atributos[0]);
				matriz[i][1] = Double.parseDouble(atributos[1]);
				matriz[i][2] = Double.parseDouble(atributos[2]);
				matriz[i][3] = Double.parseDouble(atributos[3]);

				if (atributos[4].contains(classe)) {
					matriz[i][4] = 1;
				} else {
					matriz[i][4] = 0;
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

	// separa treino e teste
	public static double[][] getFile(String path, String classe,
			boolean isTreino) {

		double[][] matriz = null;
		BufferedReader br = null;
		int setosa = 0;
		int versicolo = 0;
		int virginica = 0;

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

			matriz = new double[lines.length / 2][5];

			int indice = 0;
			for (int i = 0; i < lines.length; i++) {

				String[] atributos = lines[i].split(",");

				if (isTreino) {
					if ((i % 50) < 25) {

						matriz[indice][0] = Double.parseDouble(atributos[0]);
						matriz[indice][1] = Double.parseDouble(atributos[1]);
						matriz[indice][2] = Double.parseDouble(atributos[2]);
						matriz[indice][3] = Double.parseDouble(atributos[3]);

						if (atributos[4].contains(classe)) {
							matriz[indice][4] = 1;
						} else {
							matriz[indice][4] = 0;
						}
						indice += 1;
					}
				} else {
					if ((i % 50) >= 25) {

						matriz[indice][0] = Double.parseDouble(atributos[0]);
						matriz[indice][1] = Double.parseDouble(atributos[1]);
						matriz[indice][2] = Double.parseDouble(atributos[2]);
						matriz[indice][3] = Double.parseDouble(atributos[3]);

						if (atributos[4].contains(classe)) {
							matriz[indice][4] = 1;
						} else {
							matriz[indice][4] = 0;
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

}
