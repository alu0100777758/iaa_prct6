package prct6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Rendimiento {
	public static final String ERROR_PATH = "files/vocabulario.txt";
	public static void print(String fileClassPath, String file_ok) {
			try {
				int v_nor = 0;
				int v_nonor = 0;
				int f_nor = 0;
				int f_nonor = 0;
				BufferedReader brok = new BufferedReader(new FileReader(new File(
						file_ok)));
				BufferedReader brclass = new BufferedReader(new FileReader(new File(
						fileClassPath)));
				String lineOK;
				String lineClass;
					PrintWriter pwerror = new PrintWriter(new FileWriter(
							ERROR_PATH));
					while ((lineOK = brok.readLine()) != null){
						lineClass =  brclass.readLine();
						if(lineOK.split("(:)|(\\s)")[1].equals(lineClass.split("(:)|(\\s)")[1]))
							if(lineOK.split("(:)|(\\s)")[1].equals("ins"))
								v_nonor++;
							else
								v_nor++;
						else if(lineOK.split("(:)|(\\s)")[1].equals("ins"))
							f_nor++;
						else
							f_nonor++;
					}
//					Accuracy=<valor numérico>
//					Matriz de confusión
//					V_NOR=<valor numérico> F_NOR=<valor numérico>
//					V_NONOR=<valor numérico> F_NONOR=<valor numérico>
//					Clase
//					NOR:
//					Precision=<valor
//					numérico>
//					numérico>
//					Clase NO_NOR: Precision=<valor numérico>
//					numérico>
//					Recall=<Valor
//					Recall=<Valor
					pwerror.write("Accuracy="+((v_nor+v_nonor)/(v_nonor+v_nor+f_nonor+f_nor))+"\n"+"Matriz de confusión\n"+"V_NOR="+v_nor);
					pwerror.close();
					brok.close();
					brclass.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
