package exercicio1;

public class Perceptron {
	
	private double[] w;
	private double[][] matriz; // ultima posicao guarda o valor esperado
	private double limear;
	
	private int numEpocas;
	private int maxEpocas;
	
	public Perceptron(int numDimensions, double[][] matriz){
		this.w  = new double[numDimensions];
		this.matriz = matriz;
		limear = 0.5;
		numEpocas = 0;
		maxEpocas = 50;
	}
	
	public int run(double[] e){
		int saida = 0;
		double soma = 0;
		
		for(int i=0; i < w.length; i++){
			soma += e[i] * w[i];
		}
		
		if(soma > limear){
			saida = 1;
		}
		
		return saida;
	}
	
	public void treinar(){
		
		boolean treinado = true;
		
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
	
	private void updateW(double[] e, int saida, int esperado){
		
		for(int i=0; i < w.length; i++){
			w[i] += (esperado - saida) * e[i];
		}
		
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

	
}
