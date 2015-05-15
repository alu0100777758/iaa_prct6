package prct6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class CorpusinsnorLoader {
	public static final String INPUT_FILE_PATH = "files/troll.csv";
	public static final String OUTPUT_NOR_PATH = "files/corpusnor.txt";
	public static final String OUTPUT_INS_PATH = "files/corpusins.txt";
	public static final String OUTPUT_VOC_PATH = "files/vocabulario.txt";
	public static final int INSULTO = 1;
	public static final int NO_INSULTO = 0;
	// private static final String ADDITIONAL_SPLIT_REGX =
	// "(\\.)+ (\\,)*(\\\\)*(\\\")*";
	public static final String DELETED_CHARACTERS = "[^\\w\\s]";
	private static final String INPUT_FILE_CORPUS_TODO_PATH = "files/corpustodo.txt";

	public static void buildFiles() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					INPUT_FILE_PATH)));
			PrintWriter pwins = new PrintWriter(new FileWriter(OUTPUT_INS_PATH));
			PrintWriter pwnor = new PrintWriter(new FileWriter(OUTPUT_NOR_PATH));
			br.readLine();
			String line;
			TreeMap<String, Integer> stringsCount = new TreeMap();
			while ((line = br.readLine()) != null) {
				switch (Integer.parseInt(line.split(",", 3)[0])) {
				case INSULTO:
					pwins.write("Texto:"
							+ line.split(",", 3)[2].substring(3,
									line.split(",", 3)[2].length() - 4)
							+ System.getProperty("line.separator"));
					break;

				case NO_INSULTO:
					pwnor.write("Texto:"
							+ line.split(",", 3)[2].substring(3,
									line.split(",", 3)[2].length() - 4)
							+ System.getProperty("line.separator"));
					break;
				}
			}
			br.close();
			pwins.close();
			pwnor.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void buildVoc() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					INPUT_FILE_CORPUS_TODO_PATH)));
			PrintWriter pwvoc = new PrintWriter(new FileWriter(OUTPUT_VOC_PATH));
			br.readLine();
			String line;
			int words = 0;
			TreeMap<String, Integer> stringsCount = new TreeMap();
			while ((line = br.readLine()) != null) {
				words++;
				// for (String unfiltered : line.split(ADDITIONAL_SPLIT_REGX)) {
				line = line.split(":")[1];
				StringTokenizer tokens = new StringTokenizer(line.replaceAll(
						DELETED_CHARACTERS, ""));
				while (tokens.hasMoreTokens()) {
					String thisToken = tokens.nextToken();
					Integer count = stringsCount.get(thisToken);
					if (count == null)
						stringsCount.put(thisToken, 1);
					else
						stringsCount.put(thisToken, count + 1);
					// System.out.println(tokens.nextToken());
					// }
				}

			}
			pwvoc.write("Numero de palabras:" + words);
			for (Entry<String, Integer> entry : stringsCount.entrySet()) {
				pwvoc.write("Palabra:" + entry.getKey()
						+ System.getProperty("line.separator"));
			}
			br.close();
			pwvoc.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void learn(String[] fileClassPath, String[] fileClassOutput) {
		try {
			BufferedReader brvoc = new BufferedReader(new FileReader(new File(
					OUTPUT_VOC_PATH)));
			int vocabSize = 0;
			String line;
			while ((line = brvoc.readLine()) != null) {
				vocabSize++;
			}
			brvoc.close();
			for (int i = 0; i < fileClassPath.length; i++) {
				BufferedReader br = new BufferedReader(new FileReader(new File(
						fileClassPath[i])));
				PrintWriter pwlearning = new PrintWriter(new FileWriter(
						fileClassOutput[i]));
				br.readLine();
				int words = 0;
				TreeMap<String, Integer> stringsCount = new TreeMap();

				while ((line = br.readLine()) != null) {
					words++;
					line = line.split(":")[1];
					StringTokenizer tokens = new StringTokenizer(
							line.replaceAll(DELETED_CHARACTERS, ""));
					while (tokens.hasMoreTokens()) {
						String thisToken = tokens.nextToken();
						Integer count = stringsCount.get(thisToken);
						if (count == null)
							stringsCount.put(thisToken, 1);
						else
							stringsCount.put(thisToken, count + 1);
					}

				}
				pwlearning.write("Numero de documentos del corpus :"
						+ fileClassPath.length
						+ System.getProperty("line.separator")
						+ "N�mero de palabras del corpus:" + words
						+ System.getProperty("line.separator"));
				for (Entry<String, Integer> entry : stringsCount.entrySet()) {
					pwlearning
							.write("Palabra:"
									+ entry.getKey()
									+ " Frec:"
									+ entry.getValue()
									+ " LogProb:"
									+ Math.log(((double) (entry.getValue() + 1) / (vocabSize + words)))
									+ System.getProperty("line.separator"));
				}
				br.close();
				pwlearning.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void classify(String[] filLearnedPath, String fileInput,
			String fileClassOutput) {
		try {
			PrintWriter pwlearning = new PrintWriter(new FileWriter(fileClassOutput));
			ArrayList<TreeMap<String, Double>> learnedData = new ArrayList<TreeMap<String, Double>>();
			String line;
			for (int i = 0; i < filLearnedPath.length; i++) {
				learnedData.add(new TreeMap<String, Double>());
				BufferedReader br = new BufferedReader(new FileReader(new File(
						filLearnedPath[i])));
				br.readLine();
				br.readLine();
				int words = 0;
				while ((line = br.readLine()) != null) {
					words++;
					StringTokenizer tokens = new StringTokenizer(
							line.replaceAll(DELETED_CHARACTERS, ""));
					String palabra = tokens.nextToken().replaceAll("Palabra", "");;
					tokens.nextToken();
					String doble = tokens.nextToken().replaceAll("LogProb", "-");
						learnedData.get(i).put(palabra, Double.parseDouble(doble));
//						System.out.println(palabra +" || " + doble);
					
				}
				br.close();
			}
			BufferedReader brinput = new BufferedReader(new FileReader(
					new File(fileInput)));
				while ((line = brinput.readLine()) != null) {
					line = line.split(":")[1];
					StringTokenizer tokens = new StringTokenizer(
							line.replaceAll(DELETED_CHARACTERS, ""));
					double [] logprob = new double[filLearnedPath.length];
					
					while (tokens.hasMoreTokens()) {
						String thisToken = tokens.nextToken();
						for (int i = 0; i < filLearnedPath.length; i++) {
							Double addin = learnedData.get(i).get(thisToken);
							if(addin != null)
								logprob[i]  += addin;
						}
						
					}
					
						int maxIndex = 0;
						double lastmax = Double.MIN_VALUE;
						for(int i = 0;i < filLearnedPath.length;i++){
							if(logprob[i]>lastmax){
								maxIndex = i;
								lastmax = logprob[i];
							}
						}
						pwlearning.write("Clase:" +  filLearnedPath[maxIndex].substring(filLearnedPath[maxIndex].length()-7,filLearnedPath[maxIndex].length()-4)+" Texto:" +line+System.getProperty("line.separator"));
				}
				pwlearning.close();
				brinput.close();
				

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
