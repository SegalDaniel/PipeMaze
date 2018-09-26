package pipeGame;

import interfaces.Heuristic;
import utils.State;

public class PipeGameHeuristic<T> implements Heuristic<PipeGameLevel> {

	@Override
	public int calcHeuristicForState(State<PipeGameLevel> state) {
		return state.getState().getLengthFromStart();
	}

}
