package mlp;

public class PerceptronMLP {
	
	private double[] w;
	private double delta;
	private double taxaAprendizagem;
	
	
	public PerceptronMLP(int numeroAtributos, double taxaAprendizagem){
		this.w = new double[numeroAtributos+1];
		this.taxaAprendizagem = taxaAprendizagem;
		
		iniciarPesos();
	}

	private void iniciarPesos(){
		
		for(int i=0; i < w.length; i++){
			w[i] = Math.random()-0.5;
		}
		
	}
	
	public double run(double[] entrada){
		double result = 0;
		
		for(int i=0; i< entrada.length; i++){
			result += w[i] * entrada[i];
		}
		
		return sigmoide(result);
	}
	
	public void atualizarPesos(double[] entrada){
		
		for(int i=0; i<w.length; i++){
			this.w[i] += taxaAprendizagem * entrada[i] * delta;
		}
		
	}
	
	private double sigmoide(double x){
		return 1 / (1 + Math.exp(-x));
	}
	
	public double[] getW() {
		return w;
	}

	public void setW(double[] w) {
		this.w = w;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}
	
	

}
