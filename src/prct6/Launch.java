package prct6;

import java.io.IOException;

public class Launch {

	public static void main(String[] args) {
		CorpusinsnorLoader.buildFiles();
		String [] corpusFilePaths = {"files/corpusnor.txt", "files/corpusins.txt"};
		String [] learningOutputFilePaths = {"files/aprendizajenor.txt","files/aprendizajeins.txt"};
		try {
			FileConcatenate.concatenate(corpusFilePaths,"files/corpustodo.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		CorpusinsnorLoader.buildVoc();
		CorpusinsnorLoader.learn(corpusFilePaths, learningOutputFilePaths);
		CorpusinsnorLoader.classify(learningOutputFilePaths, "files/testCorpus.txt", "files/clasificacion.txt");
	}

}
