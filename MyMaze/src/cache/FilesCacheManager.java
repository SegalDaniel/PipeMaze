package cache;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import interfaces.CacheManager;


public class FilesCacheManager implements CacheManager{
	
	private LinkedList<String> solutions = new LinkedList<String>();
	
	public FilesCacheManager() {
		try {
			loadSolutionsList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadSolutionsList() throws IOException {
		File solList = new File("solutions");
		String str = solList.load();
		if (str != null) {
			List<String> lines = Arrays.asList(str.split(System.lineSeparator()));
			for (String line : lines) {
				solutions.add(line);
			}
		}
	}
	
	private void saveSolutionList() throws IOException {
		File solList = new File("solutions");
		StringBuilder sb = new StringBuilder();

	    for (String line : solutions) {
	        sb.append(line);
	        sb.append(System.lineSeparator());
	    }
	    
	    solList.save(sb.toString());
	}

	@Override
	public void save(String problem, String solution) throws IOException {
		File newFile = new File(problem);
		newFile.save(solution);
		solutions.add(newFile.getName());
		saveSolutionList();
	}

	@Override
	public String load(String problem) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append(problem.hashCode());
		sb.append(".txt");
		String problemTxt = sb.toString();
		if (solutions.contains(problemTxt)) {
			// return the value (File) for the key 'problem' (the solution)
			File sol = new File(problem);
			String solution = sol.load();
			return solution;
		}
		return null;
	}
	
}
