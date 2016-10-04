package exercicio1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] matrizSetosa = getFile("./databases/iris.txt", "Iris-setosa");
		double[][] matrizVersicolor = getFile("./databases/iris.txt", "Iris-versicolor");
		double[][] matrizVirginica = getFile("./databases/iris.txt", "Iris-virginica");
		
		Perceptron pSetosa = new Perceptron(matrizSetosa);
		Perceptron pVersicolor = new Perceptron(matrizVersicolor);
		Perceptron pVirginica = new Perceptron(matrizVirginica);
		
		pSetosa.treinar();
		pVersicolor.treinar();
		pVirginica.treinar();
		
		Perceptron mVersicolor = new Perceptron(getSaida(matrizVersicolor, pSetosa, pVirginica));
		mVersicolor.treinar();
		
		double[] e = {6.3,3.3,6.0,2.5};
		double[] linha = new double[3];
		linha[0] = pSetosa.run(e);
		linha[1] = pVirginica.run(e);
		linha[2] = 0;
//		
//		
//		
//		System.out.println(pSetosa.run(e));

		//System.out.println("epocas = "+ pVirginica.getNumEpocas());
		System.out.println("antigo error = " + pVersicolor.calcularError());
		System.out.println("novo error = " + mVersicolor.calcularError());
		System.out.println("resultado  = " + mVersicolor.run(linha));
		//pVirginica.printBase();
		
//		System.out.println("Setosa = " + pSetosa.run(e));
//		System.out.println("Versicolor = " + pVersicolor.run(e));
//		System.out.println("Virginica = " + pVirginica.run(e));
//		System.out.println(classificar(e, pSetosa, pVersicolor, pVirginica));
		
	}
	
	
	/*
	 * Recebe a matriz usada para treinar o Perceptron da versicolor e retorna a saida dos dois outros
	 * Perceptrons para essa msm matriz, junto com a resposta esperada para cada linha.
	 * 
	 */
	public static double[][] getSaida(double[][] matrizVersicolor, Perceptron pSetosa, Perceptron pVirginica){
		double[][] matriz = new double[matrizVersicolor.length][3];
		double[] linha; 
		
		for (int i = 0; i < matrizVersicolor.length; i++) {
			linha = new double[3];
			
			linha[0] = pSetosa.run(matrizVersicolor[i]);
			linha[1] = pVirginica.run(matrizVersicolor[i]);
			linha[2] = matrizVersicolor[i][matrizVersicolor[1].length-1];
			
			matriz[i] = linha;
		}
		
		return matriz;
	}
	
	public static String classificar(double[] e, Perceptron pSetosa, Perceptron pVersicolor, Perceptron pVirginica ){
		String classeName = "";
		
		double resultSetosa = pSetosa.run(e);
		double resultVersicolo = pVersicolor.run(e);
		double resultVirginica = pVirginica.run(e);
		
		double pesoSetosa = -1 * (((double)pSetosa.calcularError() / (double)pSetosa.getMatriz().length) -1);
		double pesoVersicolo = -1 * (((double)pVersicolor.calcularError() / (double)pVersicolor.getMatriz().length) -1);
		double pesoVirginica = -1 * (((double)pVirginica.calcularError() / (double)pVirginica.getMatriz().length) -1);
		
		double chanceSetosa = resultSetosa * pesoSetosa;
		double chanceVersicolo = resultVersicolo * pesoVersicolo;
		double chanceVirginica = resultVirginica * pesoVirginica;
		
		// Quando nenhum dos Perceptrons classificam como positivo
		// a classe Versicolo é escolhida para resposta.
		// Isso é feito pq a taxa de acerto dela é mto baixa
		if((chanceVersicolo >= chanceSetosa) && (chanceVersicolo >= chanceVirginica)){
			classeName = "Versicolo";
		}else if((chanceSetosa >= chanceVersicolo) && (chanceSetosa >= chanceVirginica)){
			classeName = "Setosa";
		}else if((chanceVirginica >= chanceSetosa) && (chanceVirginica >= chanceVersicolo)){
			classeName = "Virginica";
		}
		
		return classeName;
	}
	
	
	public static double[][] getFile(String path, String classe){
		
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
		    
		    for(int i=0; i< lines.length; i++){
		    	
		    	String[] atributos = lines[i].split(",");
		    	
		    	matriz[i][0] = Double.parseDouble(atributos[0]);
		    	matriz[i][1] = Double.parseDouble(atributos[1]);
		    	matriz[i][2] = Double.parseDouble(atributos[2]);
		    	matriz[i][3] = Double.parseDouble(atributos[3]);
		    	
		    	
		    	
		    	if(atributos[4].contains(classe)){
		    		matriz[i][4] = 1;
		    	}else{
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

}
