package algos;

import java.util.ArrayList;
import java.util.LinkedList;

import interfaces.Searchable;
import utils.CommonSearcher;
import utils.State;

public class BFS<T> extends CommonSearcher<T> {
	
	@Override
	public LinkedList<T> search(Searchable<T> s) {
		
		addToOpenList(s.getInitialState());
		
		while (openList.size() != 0) {
		
			State<T> n = popOpenList();
			closedSet.add(n.getState().toString());
			
			if (s.isGoal(n)) {
				return backTrace(n, s.getInitialState());
			}

			ArrayList<State<T>> successors = s.getAllPossibleStates(n);
			for (State<T> state : successors) {
				if (!openListContains(state) && !closedListContains(state)) {
					//System.out.println(state.getState().toString() + "not in open or closed, added to open");
					state.setCameFrom(n);
					state.setCost(1 + n.getCost());
					addToOpenList(state);
				}
				else if (state.getCost() > n.getCost() + 1) {
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

