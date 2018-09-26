package algos;

import java.util.ArrayList;
import java.util.LinkedList;
//import java.util.PriorityQueue;
import java.util.Random;

import interfaces.Heuristic;
import interfaces.Searchable;
import utils.CommonSearcher;
import utils.State;

public class HillClimbing<T> extends CommonSearcher<T> {

	private Heuristic<T> heuristic;
	private int timeToRun;
	
	public HillClimbing(Heuristic<T> heu, int timeToRun){
		this.heuristic = heu;
		this.timeToRun = timeToRun;
		//openList = new PriorityQueue<State<T>>(new StateComperator<T>());
	}
	
	@Override
	public LinkedList<T> search(Searchable<T> s) {
		System.out.println("hill climbing");
		State<T> next = s.getInitialState();

		long time0 = System.currentTimeMillis();
		while (System.currentTimeMillis() - time0 < timeToRun) {
			ArrayList<State<T>> neighbors = s.getAllPossibleStates(next);

			if (s.isGoal(next)) {
				return backTrace(next, s.getInitialState());
			}

			if (neighbors.size() > 0) {
				if (Math.random() < 0.7) { 
					double grade = 0;
					for (State<T> step : neighbors) {
						double g = heuristic.calcHeuristicForState(step);
						if (g > grade) {
							grade = g;
							next = step;
						}
					}
				} else {
					next = neighbors.get(new Random().nextInt(neighbors.size()));
				}
			}
		}
		System.out.println("HillClimbing: TimeOut");
		return null;
	}

}
