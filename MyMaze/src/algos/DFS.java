package algos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import interfaces.Searchable;
import utils.CommonSearcher;
import utils.State;

public class DFS<T> extends CommonSearcher<T> {

	Stack<State<T>> stack;
	int nodesCounter;
	
	@Override
	public LinkedList<T> search(Searchable<T> s) {
		this.stack = new Stack<State<T>>();
		State<T> currentState = s.getInitialState();
		stack.add(currentState);

		while (!stack.isEmpty()) {
			currentState = stack.pop();
			closedSet.add(currentState.toString());

			if (s.isGoal(currentState)) { 
				return backTrace(currentState, s.getInitialState());
			}
			ArrayList<State<T>> possibleStates = s.getAllPossibleStates(currentState);
			
			for (State<T> possibleState : possibleStates) {
				if ((!closedSet.contains(possibleState.toString())) && (!stack.contains(possibleState))) {
					possibleState.setCameFrom(currentState);
					possibleState.setCost(possibleState.getCost() + currentState.getCost());
					stack.add(possibleState);
				}
			}

		}
		return null;
	}
	

}
