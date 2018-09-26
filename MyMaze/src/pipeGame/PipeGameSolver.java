package pipeGame;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

//import algos.BFS;
import algos.BestFS;
//import algos.DFS;
//import algos.HillClimbing;
import exceptions.PipeGameBuilderException;
import exceptions.PipeGameTileException;
import interfaces.Searcher;
import interfaces.Solver;
import pipeGame.PipeGameTile.PipeDirection;

public class PipeGameSolver implements Solver<PipeGameLevel> {
	
	private Searcher<PipeGameLevel> searcher;
	
	public PipeGameSolver() {
		//this.searcher = new BFS<>();
		this.searcher = new BestFS<>(new PipeGameHeuristic<PipeGameLevel>());
		
		//System.out.println("new solver with BestFS");
		//this.searcher = new HillClimbing<>(new PipeGameHeuristic<PipeGameLevel>(), 10000); 
	}
	
	@Override
	public String solve(PipeGameLevel problem) {
		//System.out.println("start solving");
		PipeGameSearchable PGS = new PipeGameSearchable(problem);
		//System.out.println("new pipe game searchable");
		LinkedList<PipeGameLevel> backTrace = searcher.search(PGS);
		//System.out.println("got backTrace");
		return getMovesToSolution(backTrace);
	}
	
	private String getMovesToSolution(LinkedList<PipeGameLevel> backTrace) {
		
		if(backTrace == null)
			return "done";
		
		if(backTrace.size() == 1)
			return "done";
		
		LinkedList<PipeGameTile> path = backTrace.getFirst().onThePath();
		PipeGameLevel initial = backTrace.getLast();
		PipeGameLevel goal = backTrace.getFirst();
		int rows = goal.getNumRows();
		int cols = goal.getNumCol();
		int rotations = 0;
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				PipeGameTile initTile = initial.getTile(new BoardPosition(i, j));
				PipeGameTile goalTile = goal.getTile(new BoardPosition(i, j));
				if(!initTile.equals(goalTile) && path.contains(goalTile)) {
					ArrayList<PipeDirection> initRotations = initTile.getRotations();
					rotations = initRotations.indexOf(goalTile.getDirection()) + 1;
					sb.append(i+","+j+","+rotations);
					sb.append(System.lineSeparator());
				}
			}
		}
		return sb.toString();
	}
	
	
	public Searcher<PipeGameLevel> getSolver() {
		return searcher;
	}

	public void setSearcher(Searcher<PipeGameLevel> searcher) {
		this.searcher = searcher;
	}
	
	public static void main(String[] args) throws PipeGameBuilderException, PipeGameTileException {
		
		//Searcher<PipeGameLevel> BFSSearcher = new BFS<>();
		//Searcher<PipeGameLevel> DFSSearcher = new DFS<>();
		//Searcher<PipeGameLevel> BestFS = new BestFS<>(new PipeGameHeuristic<PipeGameLevel>());
		//Searcher<PipeGameLevel> HillClimbing = new HillClimbing<>(new PipeGameHeuristic<PipeGameLevel>(), 10000);
		
		PipeGameLevel p = new PipeGameLevel(" gF\n" + 
				"-JF\n" + 
				"7|s");
		System.out.println(p.toString());
		//System.out.println(p.getLengthFromStart());
		
		PipeGameSolver solver = new PipeGameSolver();
		/*
		 solver.setSearcher(BFSSearcher);
		//System.out.println("**************Starting PGS BFS*****************");
		Long start = new Date().getTime();
		//System.out.println("BFS: "+start);
		//String sol = solver.solve(p);
		Long end = new Date().getTime();
		//System.out.println("BFS: "+end);
		//System.out.println("BFS total: " + (end-start));
		//System.out.println(sol);
	   */
		//solver.setSearcher(BestFS);
		System.out.println("**************Starting PGS BestFS*****************");
		long start = new Date().getTime();
		//System.out.println("BFS: "+start);
		String sol = solver.solve(p);
		long end = new Date().getTime();
		//System.out.println("BFS: "+end);
		System.out.println("BestFS total: " + (end-start));
		System.out.println(sol);
		/*
		solver.setSearcher(DFSSearcher);
		System.out.println("**************Starting PGS DFS*****************");
		start = new Date().getTime();
		//System.out.println("BFS: "+start);
		sol = solver.solve(p);
		end = new Date().getTime();
		//System.out.println("BFS: "+end);
		System.out.println("DFS total: " + (end-start));
		System.out.println(sol);
		
		
		solver.setSearcher(HillClimbing);
		System.out.println("**************Starting PGS HillClimbing*****************");
		start = new Date().getTime();
		//System.out.println("BFS: "+start);
		sol = solver.solve(p);
		end = new Date().getTime();
		//System.out.println("BFS: "+end);
		System.out.println("HillClimbing total: " + (end-start));
		System.out.println(sol);
		*/
	}
	
}