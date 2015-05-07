package prct6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class CorpusinsnorLoader {
	public static final String INPUT_FILE_PATH = "files/troll.csv";
	public static final String OUTPUT_NOR_PATH = "files/corpusnor.txt";
	public static final String OUTPUT_INS_PATH = "files/corpusins.txt";

	public static final int INSULTO = 1;
	public static final int NO_INSULTO = 0;
	private static final String ADDITIONAL_SPLIT_REGX = "(\\.)+ (\\,)*(\\\\)*(\\\")*";

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
					pwins.write(line.split(",", 3)[2]);
					break;

				case NO_INSULTO:
					pwnor.write(line.split(",", 3)[2]);
					break;
				}
				for (String unfiltered : line.split(ADDITIONAL_SPLIT_REGX)) {
					StringTokenizer tokens = new StringTokenizer(unfiltered);
					while (tokens.hasMoreTokens()) {
						String thisToken = tokens.nextToken();
						Integer count = stringsCount.get(thisToken);
						if (count == null)
							stringsCount.put(thisToken, 1);
						else
							stringsCount.put(thisToken, count + 1);
						// System.out.println(tokens.nextToken());
					}
				}

			}
			for (Entry<String, Integer> entry : stringsCount.entrySet()) {
				System.out.println("Key: " + entry.getKey() + ". Value: "
						+ entry.getValue());
			}
			// System.out.println(stringsCount.get("fuck"));
			// System.out.println("fuckcounter " + fuckcounter);
			br.close();
			pwins.close();
			pwnor.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
