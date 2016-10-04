package exercicio1;

import java.util.Random;

public class Perceptron {
	
	private double[] w;
	private double[][] matriz; // ultima posicao guarda o valor esperado
	private double taxaAprendizagem;
	
	private int numEpocas;
	private int maxEpocas;
	private int error;
	
	// --- MLP ---
	private double lambda; // 
	private double delta; // erro usado para atualizar os pesos da MLP
	private double[] ultimaEntrada; // guarda o valor da ultima entrada para ser usado na atualização dos pesos
	
	public Perceptron(double[][] matriz){
		
		this.w  = new double[matriz[1].length];
		this.matriz = matriz;
		taxaAprendizagem = 1;
		numEpocas = 0;
		maxEpocas = 100;
	}
	
	// MLP
	public Perceptron(){
		taxaAprendizagem = 1;
	}
	
	public double runMLP(double[] e){
		double saida = 0;
		double soma = 0;
		this.ultimaEntrada = e;
		
		for(int i=0; i < w.length; i++){
			
			if(i==0){
				soma += 1 * w[i];
			}else{
				soma += e[i-1] * w[i];
			}
			
			
		}
		
		saida = sigmoide(soma);
		
		return saida;
	}
	
	private double sigmoide(double x){
		double result = 1/(1 + Math.pow(Math.E, lambda*x*-1));
		
		return result;
	}
	
	private double sigmoideDerivada(double x){
		double result = (1-sigmoide(x)) * sigmoide(x);
		
		return result;
	}
	
	public int run(double[] e){
		int saida = 0;
		double soma = 0;
		
		for(int i=0; i < w.length; i++){
			
			if(i==0){
				soma += 1 * w[i];
			}else{
				soma += e[i-1] * w[i];
			}
			
			
		}
		
		if(soma > 0){
			saida = 1;
		}
		
		return saida;
	}
	
	public void treinar(){
		
		boolean treinado = true;
		error = 0;
		
		// percorre todos os exemplos
		for(int i=0; i< matriz.length; i++){
			
			int saida = run(matriz[i]);
			
			// checa a saida como valor esperado
			if(saida != matriz[i][matriz[1].length-1]){
				updateW(matriz[i], saida, (int) matriz[i][matriz[1].length-1]);
				treinado = false;
			}
			
		}
		
		numEpocas++;
		
		if((!treinado) && (numEpocas<maxEpocas)){
			treinar();
		}
		
	}
	
	public void treinarAleatorio(){
		double[] temp;
		
		for(int i=0; i< matriz.length; i++){
			
			if((i%3) == 0){
				temp = matriz[i];
				
				matriz[i] = matriz[matriz.length-i-1];
				matriz[matriz.length-1-i] = temp;
			}
			
		}
		
		treinar();
	}
	
	public int calcularError(){
		
		error = 0;

		// percorre todos os exemplos
		for(int i=0; i< matriz.length; i++){
			
			int saida = run(matriz[i]);
			
			// checa a saida como valor esperado
			if(saida != matriz[i][matriz[1].length-1]){
				error++;			
			}
			
		}
		
		return error;
	}
	
	private void updateW(double[] e, int saida, int esperado){
		
		for(int i=0; i < w.length; i++){
			
			if(i==0){
				w[i] += taxaAprendizagem * (esperado - saida) * 1;
			}else{
				w[i] += taxaAprendizagem * (esperado - saida) * e[i-1];
			}
			
		}
		error++;
		//System.out.println("w0 = "+w[0]+" | w1 = "+w[1]+" | w2 = "+w[2]+" | w3 = "+w[3]);
		
	}
	
	public void updateWMLP(){
		
		for(int i=0; i < w.length; i++){
			
			if(i==0){
				w[i] += taxaAprendizagem * 1 * delta;
			}else{
				w[i] += taxaAprendizagem * ultimaEntrada[i-1] * delta;
			}
			
		}
		
	} // updateWMLP()
	
	public void printBase(){
		for (int i = 0; i < matriz.length; i++) {
			System.out.println();
			for (int j = 0; j < w.length; j++) {
				//System.out.print(w.length);
				
				if(j==0){
					System.out.print(i + ": ");
				}
				System.out.print(matriz[i][j]);
				if(j<(w.length-2)){
					System.out.print(",");
				}else if(j == (w.length-2)){
					System.out.print(" - ");
				} 
			}

		}
	}
	
	public void printEntrada(){
		print(ultimaEntrada);
	}
	
	private void print(double[] e){
		for(int i=0; i < e.length; i++){
			System.out.print(e[i] + " - ");
		}
		System.out.println();
	}
	
	private void print(int[] e){
		for(int i=0; i < e.length; i++){
			System.out.print(e[i] + " - ");
		}
		System.out.println();
	}
	
	public void printW(){
		for (int i = 0; i < w.length; i++) {
			System.out.print(w[i]);
			System.out.print(" | ");
		}
	}
	
	// seta o numero de pesos
	public void setWMLP(int tamanho){
		this.w = new double[tamanho];
	}
	
	public double[] getW() {
		return w;
	}

	public void setW(double[] w) {
		this.w = w;
	}

	public double[][] getMatriz() {
		return matriz;
	}

	public void setMatriz(double[][] matriz) {
		this.matriz = matriz;
	}

	public double getTaxaAprendizagem() {
		return taxaAprendizagem;
	}

	public void setTaxaAprendizagem(double taxaAprendizagem) {
		this.taxaAprendizagem = taxaAprendizagem;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public int getNumEpocas() {
		return numEpocas;
	}

	public void setNumEpocas(int numEpocas) {
		this.numEpocas = numEpocas;
	}

	public double getLambda() {
		return lambda;
	}

	public void setLambda(double lambda) {
		this.lambda = lambda;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	
}
