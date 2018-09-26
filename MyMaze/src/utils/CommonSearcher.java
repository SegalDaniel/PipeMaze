package utils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import interfaces.Searchable;
import interfaces.Searcher;
import utils.State;

public abstract class CommonSearcher<T> implements Searcher<T> {

	protected Queue<State<T>> openList;
	protected HashSet<String> closedSet;
	private int evaluatedNodes;
	 
	public CommonSearcher() {
		openList = new PriorityQueue<State<T>>();
		closedSet = new HashSet<String>();
		evaluatedNodes = 0;
	}
	
	protected State<T> popOpenList() { 
		evaluatedNodes++;
		//System.out.println("evaluated nodes: " + evaluatedNodes);
		return openList.poll();
	}
	
	public void addToOpenList(State<T> s) {
		openList.add(s);
	}
	
	public boolean openListContains(State<T> s) {
		for (State<T> state : openList) {
			if(state.getState().equals(s.getState()))
				return true;
		}
		return false;
	}

	@Override
	public abstract LinkedList<T> search(Searchable<T> s);
	
	
	protected boolean closedListContains(State<T> s) {
		return closedSet.contains(s.getState().toString());
	}
	

	protected LinkedList<T> backTrace(State<T> endState, State<T> initialState) {
		
		LinkedList<T> stateArr = new LinkedList<T>();
		stateArr.add(endState.getState());
		
		if(endState.equals(initialState))
			return stateArr;

		State<T> tmpState = new State<T>(endState);
		while (!(tmpState.getCameFrom().equals(initialState))) {
			stateArr.add(tmpState.getState());
			tmpState = tmpState.getCameFrom();
		}
		stateArr.add(initialState.getState());

		return stateArr;
	}

}
