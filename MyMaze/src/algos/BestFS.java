package algos;

import java.util.ArrayList;
import java.util.LinkedList;
//import java.util.PriorityQueue;

import interfaces.Heuristic;
import interfaces.Searchable;
import utils.CommonSearcher;
import utils.State;

public class BestFS<T> extends CommonSearcher<T> {

	private Heuristic<T> heuristic;
	
	public BestFS(Heuristic<T> heu){
		this.heuristic = heu;
		//openList = new PriorityQueue<State<T>>(new StateComperator<T>());
	}
	
	@Override
	public LinkedList<T> search(Searchable<T> s) {
		System.out.println("bestFS");
		addToOpenList(s.getInitialState());

		while(openList.size()>0) {
			State<T> n = popOpenList();
			
			closedSet.add(n.getState().toString());

			if(s.isGoal(n))
				return backTrace(n,s.getInitialState());

			ArrayList<State<T>> successors=s.getAllPossibleStates(n);
			for(State<T> state: successors) {
				state.setCost(heuristic.calcHeuristicForState(state));
				if(!closedListContains(state) && !openListContains(state)){
					state.setCameFrom(n);
					state.setCost(n.getCost() + heuristic.calcHeuristicForState(n) + n.getCost());
					addToOpenList(state);
					//System.out.println(state.getState().toString() + " added to openList");
				}
				else if (!openList.isEmpty() && state.getCost() > n.getCost() + heuristic.calcHeuristicForState(state) ) {
					//System.out.println(state.getState().toString() + "In open or closed");
					openList.remove(state);
					state.setCost(n.getCost() + 1);
					state.setCameFrom(n);
					openList.add(state);
				}
			}
		}
		return null;
	}
}
