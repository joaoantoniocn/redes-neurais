package projeto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//rodarCifar();
		//rodarIris();
		rodarWine();

		
		// printar resultado de cada exemplo
//		for (int iBase = 0; iBase < base.length; iBase++) {
//			double[] resp = mlp.classifique(base[iBase].atributos);
//			for (int i = 0; i < resp.length; i++) {
//				System.out.print("<" + resp[i] + ">, ");
//			}
//			System.out.println();
//		}
		
//		// printar base
//		for(int i=0; i<base.length; i++){
//			printBase(base[i].atributos);
//			System.out.println();
//		}
		

		
	}
	
	public static void rodarCifar(){
		Exemplo[] base = getFileCifar("./databases/data_1.txt", "./databases/labels_1.txt");
		Exemplo[] baseTeste = getFileCifar("./databases/data_teste.txt", "./databases/labels_teste.txt");

		MLP mlp = new MLP(3072, new int[] { 1000, 1000, 1000, 10 }, 0.001);
		mlp.treinarComTeste(base, baseTeste, 10000);
		System.out.println("Taxa de acerto = " + mlp.calcularTaxaAcerto(baseTeste));
	}
	
	public static void rodarIris(){
		Exemplo[] base = getFileIris("./databases/iris.txt");
		Exemplo[] baseTeste = getFileIris("./databases/iris_teste.txt");
		MLP mlp = new MLP(4, new int[] { 3, 5, 3 }, 0.001);
		mlp.treinarComTeste(base, baseTeste, 100000);
		System.out.println("Taxa de acerto = " + mlp.calcularTaxaAcerto(baseTeste));
	}
	
	public static void rodarWine(){
		Exemplo[] base = getFileWine("./databases/wine_treino.txt");
		Exemplo[] baseTeste = getFileWine("./databases/wine_teste.txt");
		MLP mlp = new MLP(13, new int[] { 18, 15, 10, 3 }, 0.00001);
		mlp.treinarComTeste(base, baseTeste, 1000000);
		System.out.println("Taxa de acerto = " + mlp.calcularTaxaAcerto(baseTeste));
	}
	
	public static Exemplo[] getFileWine(String path) {

		Exemplo[] base = null;
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

			base = new Exemplo[lines.length];

			for (int exemplo = 0; exemplo < lines.length; exemplo++) {

				String[] atributos = lines[exemplo].split(",");
				double[] vetorAtributos = new double[atributos.length-1];
				
				// Atributos
				for(int atributo=1; atributo<atributos.length; atributo++){
					vetorAtributos[atributo-1] = Double.parseDouble(atributos[atributo]);
				}
				
				// Label
				double[] vetorLabel = new double[3];
				
				if (atributos[0].contains("1")) {
					vetorLabel[0] = 1;
					vetorLabel[1] = 0;
					vetorLabel[2] = 0;
				} else if (atributos[0].contains("2")) {
					vetorLabel[0] = 0;
					vetorLabel[1] = 1;
					vetorLabel[2] = 0;
				} else if (atributos[0].contains("3")) {
					vetorLabel[0] = 0;
					vetorLabel[1] = 0;
					vetorLabel[2] = 1;
				}
				
				base[exemplo] = new Exemplo(vetorAtributos, vetorLabel);
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

		return base;

	}
	
	public static Exemplo[] getFileIris(String path) {

		Exemplo[] base = null;
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

			base = new Exemplo[lines.length];

			for (int exemplo = 0; exemplo < lines.length; exemplo++) {

				String[] atributos = lines[exemplo].split(",");
				double[] vetorAtributos = new double[atributos.length-1];
				
				// Atributos
				for(int atributo=0; atributo<(atributos.length-1); atributo++){
					vetorAtributos[atributo] = Double.parseDouble(atributos[atributo]);
				}
				
				// Label
				double[] vetorLabel = new double[3];
				if (atributos[4].contains("Iris-setosa")) {
					vetorLabel[0] = 1;
					vetorLabel[1] = 0;
					vetorLabel[2] = 0;

				} else if (atributos[4].contains("Iris-versicolor")) {
					vetorLabel[0] = 0;
					vetorLabel[1] = 1;
					vetorLabel[2] = 0;
				} else if (atributos[4].contains("Iris-virginica")) {
					vetorLabel[0] = 0;
					vetorLabel[1] = 0;
					vetorLabel[2] = 1;
				}
				
				base[exemplo] = new Exemplo(vetorAtributos, vetorLabel);
				
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

		return base;

	}
	
	public static Exemplo[] getFileCifar(String pathData, String pathLabel) {

		Exemplo[] base = null;
		BufferedReader br = null;
		BufferedReader brLabel = null;

		try {
			br = new BufferedReader(new FileReader(pathData));
			brLabel = new BufferedReader(new FileReader(pathLabel));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			StringBuilder sb = new StringBuilder();
			StringBuilder sbLabel = new StringBuilder();
			
			String line = br.readLine();
			String lineLabel = brLabel.readLine();
			
			// data
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
			String[] lines = everything.split("\n");
			
			// label
			while (lineLabel != null) {
				sbLabel.append(lineLabel);
				sbLabel.append(System.lineSeparator());
				lineLabel = brLabel.readLine();
			}
			String everythingLabel = sbLabel.toString();
			String[] linesLabel = everythingLabel.split("\n");

	
			base = new Exemplo[lines.length];
			
			for (int exemplo = 0; exemplo < lines.length; exemplo++) {

				String[] atributos = lines[exemplo].split(",");
				String[] atributosLabel = linesLabel[exemplo].split(",");
				
				// DATA
				double[] vetorAtributos = new double[atributos.length];
				for(int atributo=0; atributo < atributos.length; atributo++){
					vetorAtributos[atributo] = Double.parseDouble(atributos[atributo]);
				}
				
				// Label
				double[] vetorLabel = new double[atributosLabel.length];
				for(int atributo=0; atributo < atributosLabel.length; atributo++){
					vetorLabel[atributo] = Double.parseDouble(atributosLabel[atributo]);
				}
				
				base[exemplo] = new Exemplo(vetorAtributos, vetorLabel);
				

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

		return base;

	}// getFileCifar()
	
	public static void printBase(double[] base) {

		for (int j = 0; j < base.length; j++) {
			System.out.print(base[j]);
			System.out.print(" - ");
		}

	}

}
