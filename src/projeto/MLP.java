package projeto;

// MLP feita por Prof. Tiago Buarque
public class MLP {

	int[] neuroniosPorCamada;
	double taxaAprendizagem;
	double[][][] w; // camadaNeuronioPeso
	double[][] entrada; // camadaPeso
	double[][] delta; // camadaNeuronio

	public MLP(int dimensaoDaEntrada, int[] neuroniosPorCamada_, double taxaDeAprendizagem) {
		taxaAprendizagem = taxaDeAprendizagem;
		this.neuroniosPorCamada = neuroniosPorCamada_;
		// aloca w
		w = new double[neuroniosPorCamada.length][][];
		w[0] = new double[neuroniosPorCamada[0]][dimensaoDaEntrada + 1];
		for (int i = 1; i < w.length; i++) {
			w[i] = new double[neuroniosPorCamada[i]][neuroniosPorCamada[i - 1] + 1];
		}
		// inicializa pesos
		for (int i = 0; i < w.length; i++) {
			for (int j = 0; j < w[i].length; j++) {
				for (int k = 0; k < w[i][j].length; k++) {
					w[i][j][k] = Math.random()-0.5;
				}
			}
		}
		// aloca entrada
		entrada = new double[neuroniosPorCamada.length + 1][];
		entrada[0] = new double[dimensaoDaEntrada + 1];
		for (int i = 1; i < entrada.length; i++) {
			entrada[i] = new double[neuroniosPorCamada[i - 1] + 1];
		}
		// preenchendo as entradas fixas para o bias
		for (int i = 0; i < entrada.length; i++) {
			entrada[i][entrada[i].length - 1] = 1;
		}
		// aloca delta
		delta = new double[neuroniosPorCamada.length][];
		for (int i = 0; i < neuroniosPorCamada.length; i++) {
			delta[i] = new double[neuroniosPorCamada[i]];
		}
	}

	public double[] classifique(double[] exemplo) {
		for (int i = 0; i < entrada[0].length - 1; i++) {
			entrada[0][i] = exemplo[i];
		}

		for (int camada = 0; camada < neuroniosPorCamada.length; camada++) {
			for (int neuronio = 0; neuronio < neuroniosPorCamada[camada]; neuronio++) {
				double soma = 0;
				for (int peso = 0; peso < w[camada][neuronio].length; peso++) {
					soma += w[camada][neuronio][peso] * entrada[camada][peso];
				}
				entrada[camada + 1][neuronio] = ativacao(soma);
			}
		}
		return entrada[neuroniosPorCamada.length];
	}

	private double ativacao(double soma) {
		return Math.max(0, soma);
	}

	private double derivaAtivacao(double saida) {
		
		double result = 0;
		
		if(saida<=0){
			result = 0.001;
		}else{
			result = 1;
		}
		
		return result;
	}

	private void calcularDeltas(double[] classes) {
		// na camada de saída
		for (int neuronio = 0; neuronio < delta[delta.length - 1].length; neuronio++) {
			double saida = entrada[delta.length][neuronio];
			delta[delta.length - 1][neuronio] = (-saida + classes[neuronio]) * derivaAtivacao(saida);
		}
		// na camada escondia
		for (int camada = delta.length - 2; camada >= 0; camada--) {
			for (int neuronio = 0; neuronio < delta[camada].length; neuronio++) {
				double temp = 0;
				for (int neuronioFrente = 0; neuronioFrente < neuroniosPorCamada[camada + 1]; neuronioFrente++) {
					temp += w[camada + 1][neuronioFrente][neuronio] * delta[camada + 1][neuronioFrente];
				}
				delta[camada][neuronio] = temp * derivaAtivacao(entrada[camada + 1][neuronio]);
			}
		}
	}

	private void ajustarPesos() {
		for (int camada = 0; camada < neuroniosPorCamada.length; camada++) {
			for (int neuronio = 0; neuronio < neuroniosPorCamada[camada]; neuronio++) {
				for (int peso = 0; peso < w[camada][neuronio].length; peso++) {
					w[camada][neuronio][peso] += taxaAprendizagem*entrada[camada][peso]*delta[camada][neuronio];
				}
			}
		}
	}

	public void treinar(Exemplo[] base, int numerEpocas) {
		for (int epoca = 1; epoca <= numerEpocas; epoca++) {
			for (int exemplo = 0; exemplo < base.length; exemplo++) {
				classifique(base[exemplo].atributos);
				calcularDeltas(base[exemplo].classe);
				ajustarPesos();
			}
		}
	}
	
	public void treinarComTeste(Exemplo[] base, Exemplo[] baseTeste, int numerEpocas) {
		for (int epoca = 1; epoca <= numerEpocas; epoca++) {
			for (int exemplo = 0; exemplo < base.length; exemplo++) {
				classifique(base[exemplo].atributos);
				calcularDeltas(base[exemplo].classe);
				ajustarPesos();
			}
			if((epoca%50) == 0){
				System.out.println("Taxa de acerto na epoca "+ epoca + " = "+calcularTaxaAcerto(baseTeste));
			}
		}
	}
	
	public double calcularTaxaAcerto(Exemplo[] baseTeste){
		
		double result =0;
		
		double[] resultadoExemplo;
		int classe = 0;
		for(int i=0; i < baseTeste.length; i++){
			resultadoExemplo = classifique(baseTeste[i].atributos);
			classe = maiorValor(resultadoExemplo);
			
			if(baseTeste[i].classe[classe] == 1){
				result++;
			}
		
		}
		
		result = result/baseTeste.length;
		
		return result;
	}
	
	// usado para verificar em qual classe a MLP classificou o exemplo
	private int maiorValor(double[] resultadoExemplo){
		int result = 0;
		
		double maior = resultadoExemplo[0];
		// resultadoExemplo.length-1, usei o -1 pq o ultimo valor é o valor da bias que sempre será =1. Então devemos desconsiderar ele
		for(int i=0; i < resultadoExemplo.length-1; i++){
			
			if(maior<resultadoExemplo[i]){
				maior = resultadoExemplo[i];
				result = i;
			}
			
		}
	
		return result;
	}
	

}


