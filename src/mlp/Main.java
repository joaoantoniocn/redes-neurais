package mlp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import exercicio1.Perceptron;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		double[][] base = getFile("./databases/iris.txt");
		int[][] label = getLabel("./databases/iris.txt");
		
		MLP mlp = new MLP(base, label, 3, 3);
		
		
		StringBuffer sb = new StringBuffer();
		for(int i=0; i< 1000; i++){
			mlp.treinar();
			
			if(i%10 == 0){
				sb.append('\n');
				sb.append(mlp.testarErro());
			}
		}
		
		new FileOutputStream("temp.txt").write(sb.toString().getBytes());
		
		
		
		

		double[] e = new double[4];
		e[0] = 5.1;
		e[1] = 3.5;
		e[2] = 1.4;
		e[3] = 0.2;
		
		mlp.run(e);
		
	}

	public static double[][] getFile(String path) {

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

			matriz = new double[lines.length][4];

			for (int i = 0; i < lines.length; i++) {

				String[] atributos = lines[i].split(",");

				matriz[i][0] = Double.parseDouble(atributos[0]);
				matriz[i][1] = Double.parseDouble(atributos[1]);
				matriz[i][2] = Double.parseDouble(atributos[2]);
				matriz[i][3] = Double.parseDouble(atributos[3]);

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

	public static int[][] getLabel(String path) {

		int[][] matriz = null;
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

			matriz = new int[lines.length][3];

			for (int i = 0; i < lines.length; i++) {

				String[] atributos = lines[i].split(",");
				
				if(atributos[4].contains("Iris-setosa")){
		    		matriz[i][0] = 1;
		    		matriz[i][1] = 0;
		    		matriz[i][2] = 0;
		    		
		    	}else if(atributos[4].contains("Iris-versicolor")){
		    		matriz[i][0] = 0;
		    		matriz[i][1] = 1;
		    		matriz[i][2] = 0;
		    	}else if(atributos[4].contains("Iris-virginica")){
		    		matriz[i][0] = 0;
		    		matriz[i][1] = 0;
		    		matriz[i][2] = 1;
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

	public static void printBase(double[][] base) {

		for (int i = 0; i < base.length; i++) {
			System.out.println();
			for (int j = 0; j < base[0].length; j++) {
				System.out.print(base[i][j]);
				System.out.print(" - ");
			}
		}

	}
	
	public static void printBase(int[][] base) {

		for (int i = 0; i < base.length; i++) {
			System.out.println();
			for (int j = 0; j < base[0].length; j++) {
				System.out.print(base[i][j]);
				System.out.print(" - ");
			}
		}

	}

}
