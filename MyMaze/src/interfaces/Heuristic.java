package interfaces;

import utils.State;

public interface Heuristic<T> {
	public int calcHeuristicForState(State<T> state);
}
