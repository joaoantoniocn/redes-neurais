package mlp;

import exercicio1.Perceptron;

public class MLP {

	private PerceptronMLP[][] perceptrons; // camada, neuronio
	private double[][] base; // exemplo, atributos
	private int[][] label; // exemplo, label
	private int[] neuroniosPorCamada;
	private double[][] entrada; // camada, atributo

	private double taxaAprendizagem;

	public MLP(double[][] base, int[][] label, int[] neuroniosPorCamada) {

		this.base = base;
		this.label = label;
		this.neuroniosPorCamada = neuroniosPorCamada;

		this.taxaAprendizagem = 1;
		iniciarPerceptrons();
		inciarEntrada();

	}

	private void iniciarPerceptrons() {

		// criando as camadas
		perceptrons = new PerceptronMLP[neuroniosPorCamada.length][];

		// rodando todas as camadas
		for (int camada = 0; camada < neuroniosPorCamada.length; camada++) {

			// criando os neuronios de cada camada
			perceptrons[camada] = new PerceptronMLP[neuroniosPorCamada[camada]];

			// rodando os todos os neuronios de cada camada
			for (int neuronio = 0; neuronio < neuroniosPorCamada[camada]; neuronio++) {

				// iniciando neuronios
				if (camada == 0) {
					// System.out.println("iniciado <" + camada + "," + neuronio
					// + ">");
					perceptrons[camada][neuronio] = new PerceptronMLP(
							base[0].length, taxaAprendizagem);
				} else {
					// System.out.println("iniciado <" + camada + "," + neuronio
					// + ">");
					perceptrons[camada][neuronio] = new PerceptronMLP(
							neuroniosPorCamada[camada - 1], taxaAprendizagem);
				}

			}
		}

	}

	private void inciarEntrada() {

		// numero de camadas + 1, ultima camada guarda a resposta da MLP
		entrada = new double[neuroniosPorCamada.length + 1][];

		// numero de atributos + 1, ultimo atributo é a bias
		entrada[0] = new double[base[0].length + 1];

		// colocando o valor da bias na ultima posicao da camada
		entrada[0][entrada[0].length - 1] = 1;

		for (int camada = 1; camada < entrada.length; camada++) {
			// dizendo quantos atributos vai ter em cada camada
			entrada[camada] = new double[neuroniosPorCamada[camada - 1] + 1];

			// colocando o valor da bias na ultima posicao de cada camada
			entrada[camada][entrada[camada].length - 1] = 1;
		}

	}

	public double[] run(double[] e) {

		for (int i = 0; i < entrada[0].length - 1; i++) {
			entrada[0][i] = e[i];
		}

		// camadas
		for (int camada = 0; camada < neuroniosPorCamada.length; camada++) {
			// neuronio
			// System.out.println("Camada " + camada + " tem " +
			// this.neuroniosPorCamada[camada] + " neuronios");
			for (int neuronio = 0; neuronio < this.neuroniosPorCamada[camada]; neuronio++) {
				// System.out.println("<" + camada + "," + neuronio + ">");
				entrada[camada + 1][neuronio] = perceptrons[camada][neuronio]
						.run(entrada[camada]);
			}

		}

		return entrada[neuroniosPorCamada.length];
	}
	
	public void treinar(int numeroEpocas){
		
		for(int i=0; i<numeroEpocas; i++){
			for(int exemplo=0; exemplo<base.length; exemplo++){
				run(base[exemplo]);
				calcularDelta(exemplo);
				atualizarPesos();
			}
		}
		
	}

	private void calcularDelta(int indiceExemplo) {

		for (int camada = (neuroniosPorCamada.length - 1); camada >= 0; camada--) {
			for (int neuronio = 0; neuronio < neuroniosPorCamada[camada]; neuronio++) {

				// camada de saida
				// o erro na camada de saida é o valor esperado - o valor obtido
				if (camada == (neuroniosPorCamada.length - 1)) {
					double saida = entrada[entrada.length-1][neuronio];
					
					double delta = sigmoideDerivada(saida)
							* (label[indiceExemplo][neuronio] - saida);

					perceptrons[camada][neuronio].setDelta(delta);
				} else {
					double error = 0;

					// calculando o erro do perceptron (camadas intermediarias)
					// o erro nessa camada é dado por o somatório de (erro do
					// perceptron da próxima camada (camada da frente)* peso que
					// liga o perceptron atual com o perceptron da próxima
					// camada)
					for (int neuronioFrente = 0; neuronioFrente < neuroniosPorCamada[camada+1]; neuronioFrente++) {
						// m+1 é usado no getW() pq o peso de indice 0 é o peso
						// do BIOS( entrada = 1)
						error += perceptrons[camada + 1][neuronioFrente].getW()[neuronio]
								* perceptrons[camada + 1][neuronioFrente].getDelta();
					}

					perceptrons[camada][neuronio]
							.setDelta(sigmoideDerivada(entrada[camada+1][neuronio])* error);
									
				}

			} // for neuronios
		} // for camadas
	} // calcularDelta()
	
	private void atualizarPesos(){
		
		for(int camada = 0; camada < neuroniosPorCamada.length; camada++){
			for(int neuronio = 0; neuronio < neuroniosPorCamada[camada]; neuronio++){
				perceptrons[camada][neuronio].atualizarPesos(entrada[camada]);
			}
		}
		
	} // atualizarPesos

	private double sigmoideDerivada(double x){
		return x * (1 - x);
	}
	
	public double calcularTaxaAcerto(){
		double taxaAcerto = 0;
		
		double[] temp;
		boolean acertou = true;
		
		for(int i=0; i < base.length; i++){
			
			temp = run(base[i]);
			acertou = true;
			
			for(int j=0; j < label[i].length; j++){
				
				if(temp[j] >= 0.5){
					if(!(label[i][j] == 1)){
						acertou = false;
					}
				}else{
					if(!(label[i][j] == 0)){
						acertou = false;
					}
				}
				
			}
			
			if(acertou){
				taxaAcerto += 1;
			}
			
		}
		
		taxaAcerto = taxaAcerto / base.length;
		
		return taxaAcerto;
	}
	
	public double calcularTaxaAcerto(double[][] baseTeste, int[][] labelTeste){
		double taxaAcerto = 0;
		
		double[] temp;
		boolean acertou = true;
		
		for(int i=0; i < baseTeste.length; i++){
			
			temp = run(baseTeste[i]);
			acertou = true;
			
			for(int j=0; j < labelTeste[i].length; j++){
				
				if(temp[j] >= 0.5){
					if(!(labelTeste[i][j] == 1)){
						acertou = false;
					}
				}else{
					if(!(labelTeste[i][j] == 0)){
						acertou = false;
					}
				}
				
			}
			
			if(acertou){
				taxaAcerto += 1;
			}
			
		}
		
		taxaAcerto = taxaAcerto / baseTeste.length;
		
		return taxaAcerto;
	}
	
}
