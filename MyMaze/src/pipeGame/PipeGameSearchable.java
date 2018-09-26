package pipeGame;

import java.util.ArrayList;
import interfaces.Searchable;
import utils.State;

public class PipeGameSearchable implements Searchable<PipeGameLevel> {

	private State<PipeGameLevel> initialState;
	
	public PipeGameSearchable(PipeGameLevel initial) {
		this.initialState = new State<>(initial);
		this.initialState.setCost(0);
	}
	
	@Override
	public State<PipeGameLevel> getInitialState() {
		return initialState;
	}

	@Override
	public boolean isGoal(State<PipeGameLevel> state) {
		 return state.getState().isGoalLevel();
	}

	@Override
	public ArrayList<State<PipeGameLevel>> getAllPossibleStates(State<PipeGameLevel> s) {

		ArrayList<State<PipeGameLevel>> allPosStates = new ArrayList<State<PipeGameLevel>>();
		PipeGameLevel level = s.getState();
		int rows = level.getNumRows();
		int cols = level.getNumCol();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				PipeGameTile tile = level.getTile(new BoardPosition(i, j));
				if(tile.getCharacter() == 's' || tile.getCharacter() == 'g' || tile.getCharacter() == ' ')
					continue;
				if(tile.getCharacter() == '-' || tile.getCharacter() == '|') {
					State<PipeGameLevel> st = new State<>(level.rotate(new BoardPosition(i, j)));
					st.setCameFrom(s);
					st.setCost(s.getCost() + 1);
					if(st.getState().startIsConnected() && !st.getState().tileGoingOutOfBounds(new BoardPosition(i, j)) && st.getState().tileHasConnection(new BoardPosition(i, j)))
						allPosStates.add(st);
					continue;
				}
				else {
					for (int k = 1; k < 4; k++) {
						State<PipeGameLevel> st = new State<>(level);
						st.setState(level.rotate(new BoardPosition(i, j), k));
						st.setCameFrom(s);
						st.setCost(s.getCost() + 1);
						if(st.getState().startIsConnected() && !st.getState().tileGoingOutOfBounds(new BoardPosition(i, j)) && st.getState().tileHasConnection(new BoardPosition(i, j)))
							allPosStates.add(st);
					}
				}
				
				
				//System.out.print("{" + i + "," + j +"}");
			}
			//System.out.println("");
		}

		return allPosStates;
	}

}
