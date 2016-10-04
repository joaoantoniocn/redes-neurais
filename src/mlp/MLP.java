package mlp;

import exercicio1.Perceptron;

public class MLP {

	private Perceptron[][] perceptrons; // linha, coluna
	private double[][] base; // base
	private int[][] label;
	private int neuronios;
	private int camadas;

	public MLP(double[][] base, int[][] label, int neuronios, int camadas) {
		perceptrons = new Perceptron[camadas][neuronios];
		this.base = base;
		this.label = label;
		this.neuronios = neuronios;
		this.camadas = camadas;
		iniciarPerceptrons(base[1].length);
	}

	private void iniciarPerceptrons(int tamanho) {

		for (int i = 0; i < this.camadas; i++) {
			for (int j = 0; j < neuronios; j++) {

				perceptrons[i][j] = new Perceptron();
				perceptrons[i][j].setWMLP(tamanho);
			}
		}

	}

	public double[] run(double[] e) {
		double[] result = new double[base.length];
		double[][] resultLayer = new double[base[1].length][base.length]; // [camada][neuronio]

		// camadas
		for (int i = 0; i < this.camadas; i++) {
			// perceptrons
			for (int j = 0; j < this.neuronios; j++) {

				if (i == 0) {
					resultLayer[i][j] = perceptrons[i][j].runMLP(e);
				} else {
					resultLayer[i][j] = perceptrons[i][j].runMLP(resultLayer[i - 1]);
				}
			}
		}
		
		
		result = resultLayer[this.camadas-1];

		return result;
	}
	
	public void treinar(){
		
		// resultado da ultima camada para um exemplo de input k
		double[] resultLayer = new double[neuronios]; // [camada][neuronio]
		
		// base
		for(int k=0; k< base.length; k++){
			resultLayer = run(base[k]);
			
			for(int i = (camadas-1); i >= 0; i--){
				for(int j = (neuronios-1); j >= 0; j--){
					
					// camada de saida
					// o erro na camada de saida é o valor esperado - o valor obtido
					if (i == (camadas-1)){
						perceptrons[i][j].setErrorMLP(label[i][j] - resultLayer[j]);
					}else{
						double error = 0;
						
						// calculando o erro do perceptron (camadas intermediarias)
						// o erro nessa camada é dado por o somatório de (erro do perceptron da próxima camada (camada da frente)* peso que liga o perceptron atual com o perceptron da próxima camada)
						for(int m=0; m < neuronios; m++){
							// m+1 é usado no getW() pq o peso de indice 0 é o peso do BIOS( entrada = 1)
							error += perceptrons[i+1][m].getW()[m+1] * perceptrons[i+1][m].getErrorMLP();
						}
						
						perceptrons[i][j].setErrorMLP(error);
					}
					
				}
			}
		}
		
		
	}
}
