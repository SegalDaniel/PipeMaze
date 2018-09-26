package algos;

import java.util.Comparator;

import utils.State;

public class StateComperator<T> implements Comparator<State<T>> {
	@Override
	public int compare(State<T> state1, State<T> state2) {
		// TODO Auto-generated method stub
		return (int) (state2.getCost()-state1.getCost());
	}
}