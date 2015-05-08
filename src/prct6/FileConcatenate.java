package prct6;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileConcatenate {
	public static int N = 4096;

	public static void concatenate(String [] paths, String outFile) throws IOException{
		   OutputStream out = new FileOutputStream(outFile);
		    byte[] buf = new byte[N];
		    for (String file : paths) {
		        InputStream in = new FileInputStream(file);
		        int b = 0;
		        while ( (b = in.read(buf)) >= 0) {
		            out.write(buf, 0, b);
		            out.flush();
		        }
		        in.close();
		    }
		    out.close();
	}
}
