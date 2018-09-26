package interfaces;

import java.util.ArrayList;
import utils.State;

public interface Searchable<T> {
	State<T> getInitialState();
	boolean isGoal(State<T> state);
	ArrayList<State<T>> getAllPossibleStates(State<T> s);
}
