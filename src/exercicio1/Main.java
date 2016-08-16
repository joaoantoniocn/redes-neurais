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
		
		Perceptron pSetosa = new Perceptron(4, matrizSetosa);
		Perceptron pVersicolor = new Perceptron(4, matrizVersicolor);
		Perceptron pVirginica = new Perceptron(4, matrizVirginica);
		
		pSetosa.treinar();
		pVersicolor.treinar();
		pVirginica.treinar();
//		
//		double[] e = {5.6,2.5,3.9,1.1};
//		
//		
//		
//		System.out.println(pSetosa.run(e));

		System.out.println("epocas = "+ pSetosa.getNumEpocas());
		System.out.println("error = " + pSetosa.calcularError());
		pSetosa.printW();
		
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
