package cache;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class File{

	private String name;
	
	File(String name){
		StringBuffer sb = new StringBuffer();
		sb.append(name.hashCode());
		sb.append(".txt");
		this.name = sb.toString();
	}
	
	public String getName() {
		return this.name;
	}
	
	
	public void save(String solution) throws IOException {
		//open an writer to the file with the name problem
		PrintWriter writer = new PrintWriter(new FileWriter(name));
		List<String> lines = Arrays.asList(solution.split(System.lineSeparator()));
		for (String line : lines) {
			writer.println(line);
		}
		writer.close();
		//System.out.println(solution + ".txt file saved");
	}
	
	public String load() throws IOException {
		//load form file with the name 'problem' (this.name)
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.name));
			
			StringBuilder sb = new StringBuilder();
		    String line = reader.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = reader.readLine();
		    }
		    
		    reader.close();
		    return sb.toString();
			
		}catch(IOException e) {
			//System.out.println("couldnt open " + this.name + " file");
		}
		return null;
	}
	

}