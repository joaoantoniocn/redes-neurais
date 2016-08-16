package exercicio1;

import java.util.Random;

public class Perceptron {
	
	private double[] w;
	private double[][] matriz; // ultima posicao guarda o valor esperado
	private double limear;
	
	private int numEpocas;
	private int maxEpocas;
	private int error;
	
	public Perceptron(int numDimensions, double[][] matriz){
		this.w  = new double[numDimensions+1];
		this.matriz = matriz;
		limear = 1;
		numEpocas = 0;
		maxEpocas = 100;
	}
	
	public int run(double[] e){
		int saida = 0;
		double soma = w[0];
		
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
		//System.out.println("----------------------- epoca "+ numEpocas);
		
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
		
		boolean treinado = true;
		error = 0;
		//System.out.println("----------------------- epoca "+ numEpocas);
		
		// percorre todos os exemplos
		for(int i=0; i< matriz.length; i++){
			Random r = new Random();
			int indice = r.nextInt(matriz.length);
			int saida = run(matriz[indice]);
			
			// checa a saida como valor esperado
			if(saida != matriz[indice][matriz[1].length-1]){
				updateW(matriz[indice], saida, (int) matriz[indice][matriz[1].length-1]);
				treinado = false;
			}
			
		}
		
		numEpocas++;
		
		if((!treinado) && (numEpocas<maxEpocas)){
			treinar();
		}
		
	}
	
	public int calcularError(){
		
		error = 0;
		//System.out.println("----------------------- epoca "+ numEpocas);
		
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
				w[i] += (esperado - saida) * 1;
			}else{
				w[i] += (esperado - saida) * e[i-1];
			}
			
		}
		error++;
		//System.out.println("w0 = "+w[0]+" | w1 = "+w[1]+" | w2 = "+w[2]+" | w3 = "+w[3]);
		
	}
	
	public void printW(){
		for (int i = 0; i < w.length; i++) {
			System.out.print(w[i]);
			System.out.print(" | ");
		}
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

	public double getLimear() {
		return limear;
	}

	public void setLimear(double limear) {
		this.limear = limear;
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

	
	
}
