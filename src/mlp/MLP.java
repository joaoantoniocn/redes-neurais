package mlp;

import exercicio1.Perceptron;

public class MLP {

	private Perceptron[][] perceptrons; // linha, coluna
	private double[][] base; // base
	private int[][] label;
	private int neuronios;
	private int camadas;
	private double lambda;
	private int erros; // numero de erros no ciclo
	private int ciclos;
	private double taxaAprendizagem;

	public MLP(double[][] base, int[][] label, int camadas, int neuronios) {
		perceptrons = new Perceptron[camadas][neuronios];
		this.base = base;
		this.label = label;
		this.neuronios = neuronios;
		this.camadas = camadas;
		this.lambda = 7;
		this.ciclos = 0;
		this.taxaAprendizagem = 1;
		iniciarPerceptrons(base[1].length);

	}

	private void iniciarPerceptrons(int tamanho) {

		for (int i = 0; i < this.camadas; i++) {
			for (int j = 0; j < neuronios; j++) {

				perceptrons[i][j] = new Perceptron();
				perceptrons[i][j].setWMLP(tamanho);
				perceptrons[i][j].setLambda(lambda);
				perceptrons[i][j].setTaxaAprendizagem(taxaAprendizagem);
			}
		}

	}

	public double[] run(double[] e) {
		double[] result = new double[neuronios];
		double[][] resultLayer = new double[camadas][neuronios]; // [camada][neuronio]

		// camadas
		for (int i = 0; i < this.camadas; i++) {
			// perceptrons
			for (int j = 0; j < this.neuronios; j++) {

				if (i == 0) {
					resultLayer[i][j] = sigmoide(perceptrons[i][j].runMLP(e));

				} else {
					resultLayer[i][j] = sigmoide(perceptrons[i][j]
							.runMLP(resultLayer[i - 1]));
				}
			}

		}

		result = resultLayer[this.camadas - 1];

		return result;
	}

	public void treinar() {

		erros = 0;

		// resultado da ultima camada para um exemplo de input k
		double[] resultLastLayer = new double[neuronios]; // [camada][neuronio]

		// base
		for (int k = 0; k < base.length; k++) {
			resultLastLayer = run(base[k]);

			//if (checkErro(resultLastLayer, k)) {
				// --- calculando o Delta (erro) de cada perceptron em back
				// propagation
				calcularDelta(resultLastLayer);

				// --- atualizando os pesos
				atualizarPesos();

				erros += 1;
			//}

		} // base

		ciclos += 1;

	}

	private void print(double[] e) {
		System.out.println();
		for (int i = 0; i < e.length; i++) {
			System.out.print(e[i] + " - ");
		}
		System.out.println();
	}

	private void print(int[] e) {
		System.out.println();
		for (int i = 0; i < e.length; i++) {
			System.out.print(e[i] + " - ");
		}
		System.out.println();
	}

	public double testarErro() {
		double result = 0;
		double[] saida = new double[neuronios];
		for (int i = 0; i < base.length; i++) {
			saida = run(base[i]);

			result += checkDiferenca(saida, i);
		}

		return result;
	}

	private double checkDiferenca(double[] saida, int exemplo) {
		double result = 0;

		for (int i = 0; i < saida.length; i++) {
			if (saida[i] != label[exemplo][i]) {
				result += (label[exemplo][i] - saida[i]) * (label[exemplo][i] - saida[i]);
			}
		}

		return result;
	}

	// exemplo é o indice do exemplo que passou pela rede e gerou esses
	// resultados
	private boolean checkErro(double[] saida, int exemplo) {
		boolean result = false;

		for (int i = 0; i < saida.length; i++) {
			if (sigmoide(saida[i]) != label[exemplo][i]) {
				result = true;
			}
		}

		return result;
	}

	private void atualizarPesos() {

		for (int i = 0; i < camadas; i++) {
			for (int j = 0; j < neuronios; j++) {
				perceptrons[i][j].updateWMLP();
			}
		}

	}

	private void calcularDelta(double[] resultLastLayer) {

		for (int i = (camadas - 1); i >= 0; i--) {
			for (int j = 0; j < neuronios; j++) {

				// camada de saida
				// o erro na camada de saida é o valor esperado - o valor obtido
				if (i == (camadas - 1)) {

					double delta = sigmoideDerivada(resultLastLayer[j])
							* (label[i][j] - resultLastLayer[j]);
					
					perceptrons[i][j].setDelta(delta);
				} else {
					double error = 0;

					// calculando o erro do perceptron (camadas intermediarias)
					// o erro nessa camada é dado por o somatório de (erro do
					// perceptron da próxima camada (camada da frente)* peso que
					// liga o perceptron atual com o perceptron da próxima
					// camada)
					for (int m = 0; m < neuronios; m++) {
						// m+1 é usado no getW() pq o peso de indice 0 é o peso
						// do BIOS( entrada = 1)
						error += perceptrons[i + 1][m].getW()[j + 1]
								* perceptrons[i + 1][m].getDelta();
					}

					perceptrons[i][j]
							.setDelta(sigmoideDerivada(resultLastLayer[j])
									* error);
				}

			} // for neuronios
		} // for camadas
	} // calcularDelta()

	private double sigmoide(double x) {
		double result = 1 / (1 + Math.pow(Math.E, lambda * x * -1));

		return result;
	}
	
	// x já é o valor passado pela sigmoide
	private double sigmoideDerivada(double x) {
		//double result = (1-sigmoide(x)) * sigmoide(x);
		double result = (1-x) * x;

		return result;
	}
}
