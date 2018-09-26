package pipeGame;

import java.util.LinkedList;
import exceptions.PipeGameBuilderException;
import exceptions.PipeGameTileException;
import pipeGame.PipeGameTile.PipeDirection;

public class PipeGameLevel {
	
	private PipeGameTile[][] board;
	private int numRows;
	private int numCol;
	private PipeGameTile start;
	private PipeGameTile goal;
	private int lengthFS;

	
	public PipeGameLevel(String problem) throws PipeGameBuilderException, PipeGameTileException {
		String[] rows = problem.split(System.lineSeparator());
		for (int i = 0; i < rows.length-1; i++) {
			int diff = rows[i].length() - rows[i+1].length();
			if(diff < 0) {
				while(diff != 0) {
					rows[i] += ' ';
					diff++;
				}
			}else if (diff > 0) {
				while(diff != 0) {
					rows[i+1] += ' ';
					diff--;
				}
			}
				
		}
		this.numRows = rows.length;
		this.numCol = rows[0].length();
		this.lengthFS = -1;
		
		int countStart = 0;
		int countGoal = 0;
		
		board = new PipeGameTile[numRows][numCol];

		for (int i = 0; i < numRows; i++) {
			char[] row = rows[i].toCharArray();
			if (row.length != numCol)
				throw new PipeGameBuilderException("NOT ALL COLUMNS WITH THE SAME LENGTH");
			for (int j = 0; j < row.length; j++) {
				char c = row[j];
				board[i][j] = new PipeGameTile(c, new BoardPosition(i,j));
				if (c == 's') {
					this.start = board[i][j];
					countStart++;
				}
				else if (c == 'g') {
					this.goal = board[i][j];
					countGoal++;
				}
			}

		}
		if(countStart != 1 || countGoal != 1)
			throw new PipeGameBuilderException("s or g not equals to 1");
	}
	
	private PipeGameLevel(PipeGameLevel p, PipeGameTile[][] newBoard) throws PipeGameTileException {
		start = p.getStart();
		goal = p.getGoal();
		numRows = p.getNumRows();
		numCol = p.getNumCol();
		board = copyBoard(newBoard);
		lengthFS = -1;
	}
	
	public PipeGameLevel rotate(BoardPosition p){
		PipeGameTile[][] newBoard;
		try {
			newBoard = copyBoard(board);
			newBoard[p.getX()][p.getY()].rotate();
			return new PipeGameLevel(this, newBoard);
		} catch (PipeGameTileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public PipeGameLevel rotate(BoardPosition p, int times) {
		PipeGameTile[][] newBoard;
		try {
			newBoard = copyBoard(board);
			for (int i = 0; i < times; i++) {
				newBoard[p.getX()][p.getY()].rotate();
			}
			return new PipeGameLevel(this, newBoard);
		} catch (PipeGameTileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		for (PipeGameTile[] row : board) {
			for (PipeGameTile tile : row) {
				sb.append(tile.getCharacter());
			}
			sb.append('\n');
		}
		
		return sb.toString();
	}

	public PipeGameTile getTile(BoardPosition pos) {
		return board[pos.getX()][pos.getY()];
	}
	
	private LinkedList<PipeGameTile> getNeighbors(PipeGameTile pgt) {
		LinkedList<PipeGameTile> neighbors = new LinkedList<PipeGameTile>();
		int posX = pgt.getPosition().getX();
		int posY = pgt.getPosition().getY();
		
		if(posX > 0)
			neighbors.add(getTile(new BoardPosition(posX - 1, posY)));
		if(posX < numRows - 1)
			neighbors.add(getTile(new BoardPosition(posX + 1, posY)));
		if(posY > 0)
			neighbors.add(getTile(new BoardPosition(posX, posY - 1)));
		if(posY < numCol - 1)
			neighbors.add(getTile(new BoardPosition(posX, posY + 1)));
		
		return neighbors;
	}

	public boolean isGoalLevel() {
		LinkedList<PipeGameTile> neighbors = getNeighbors(start);
		for (PipeGameTile tile : neighbors) {
			if(start.isConnected(tile) && pathToGoal(tile, start, 1))
				return true;
		}
		return false;
	}
	
	public boolean pathToGoal(PipeGameTile tile, PipeGameTile cameFrom, int sum) {
		LinkedList<PipeGameTile> neighbors = getNeighbors(tile);
		while(!neighbors.isEmpty()) {
			PipeGameTile neighbor = neighbors.removeFirst();
			if(tile.isConnected(neighbor) && !neighbor.equals(cameFrom)) {
				if(this.lengthFS == -1 || sum > this.lengthFS) 
					this.lengthFS = sum + 1;
				if (neighbor.equals(goal)) 
					return true;
				return pathToGoal(neighbor, tile, sum+1);
			}
		}
		if(this.lengthFS == -1 || sum > this.lengthFS)
			this.lengthFS = sum;
		return false;
	}
	
	
	public LinkedList<PipeGameTile> onThePath(){
		LinkedList<PipeGameTile> neighbors = getNeighbors(start);
		for (PipeGameTile tile : neighbors) {
			if(start.isConnected(tile) && pathToGoal(tile, start, 1)) {
				LinkedList<PipeGameTile> list = new LinkedList<PipeGameTile>();
				pathToGoalTiles(tile, start, list);
				return list;
			}
		}
		return null;
	}
	
	public LinkedList<PipeGameTile> pathToGoalTiles(PipeGameTile tile, PipeGameTile cameFrom, LinkedList<PipeGameTile> list) {
		LinkedList<PipeGameTile> neighbors = getNeighbors(tile);
		while(!neighbors.isEmpty()) {
			PipeGameTile neighbor = neighbors.removeFirst();
			if(tile.isConnected(neighbor) && !neighbor.equals(cameFrom)) {
				if (neighbor.equals(goal)) { 
					list.add(tile);
					return list;
				}
				list.add(tile);
				return pathToGoalTiles(neighbor, tile, list);
			}
		}
		return null;
	}
	
	//=-=-=-=-=-=-=-==-=-==-=-==-=-=-=-=searchable helpers-=-=-=-=-=-==-=-==-=-==-=-=-=-=-=-=-=-=-=-=-=-=-
	
	public int getLengthFromStart() {
		if(lengthFS != -1)
			return lengthFS;
		isGoalLevel();
		return lengthFS;
	}
	
	public boolean tileHasConnection(BoardPosition pos) {
		PipeGameTile  tile = this.getTile(pos);
		LinkedList<PipeGameTile> neighbors = getNeighbors(tile);
		for (PipeGameTile neighbor : neighbors) {
			if(tile.isConnected(neighbor) && !neighbor.equals(tile)) 
				return true;
			else if(neighbor.equals(start))
				return true;
		}
		return false;
	}
	
	public boolean tileGoingOutOfBounds(BoardPosition pos) {
		if (pos.getX() > 0 && pos.getX() < numRows - 1 && pos.getY() > 0 && pos.getY() < numCol - 1)
			return false;
		PipeGameTile  tile = this.getTile(pos);
		PipeDirection tileDir = tile.getDirection();
		boolean flag = false;
		
		if(pos.getX() == 0) {
			if(tileDir == PipeDirection.TOP_BOTTOM || tileDir == PipeDirection.TOP_RIGHT || tileDir == PipeDirection.LEFT_TOP) 
				flag = true;
		}
		else if(pos.getX() == numRows - 1) {
			if(tileDir == PipeDirection.TOP_BOTTOM || tileDir == PipeDirection.BOTTOM_LEFT || tileDir == PipeDirection.RIGHT_BOTTOM) 
				flag = true;
		}
		if(pos.getY() == 0) {
			if(tileDir == PipeDirection.LEFT_RIGHT || tileDir == PipeDirection.BOTTOM_LEFT || tileDir == PipeDirection.LEFT_TOP) 
				flag = true;
		}
		else if(pos.getY() == numCol - 1) {
			if(tileDir == PipeDirection.LEFT_RIGHT || tileDir == PipeDirection.RIGHT_BOTTOM || tileDir == PipeDirection.TOP_RIGHT) 
				flag = true;
		}
		return flag;
	}
	
	private PipeGameTile[][] copyBoard(PipeGameTile[][] board) throws PipeGameTileException{
		PipeGameTile[][] newBoard = new PipeGameTile[numRows][numCol];
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCol; j++) {
				newBoard[i][j] = new PipeGameTile(board[i][j].character, new BoardPosition(i, j));
			}
		}
		return newBoard;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() == this.getClass()) {
			String thisBoard = this.toString();
			String otherBoard = ((PipeGameLevel) obj).toString();
			return thisBoard.equals(otherBoard);
		}
		return super.equals(obj);
	}
	
	public boolean goalIsConnected() {
		LinkedList<PipeGameTile> neighbors = getNeighbors(goal);
		for (PipeGameTile tile : neighbors) {
			if(tile.isConnected(goal))
				return true;
		}
		return false;
	}
	
	public boolean startIsConnected() {
		LinkedList<PipeGameTile> neighbors = getNeighbors(start);
		for (PipeGameTile tile : neighbors) {
			if(start.isConnected(tile))
				return true;
		}
		return false;
	}

	
	
	//-----------Getters/Setters------------------------

	public PipeGameTile[][] getBoard() {
		return board;
	}

	public void setBoard(PipeGameTile[][] board) {
		this.board = board;
	}
	
	public PipeGameTile getStart() {
		return start;
	}

	public void setStart(PipeGameTile start) {
		this.start = start;
	}

	public PipeGameTile getGoal() {
		return goal;
	}

	public void setGoal(PipeGameTile goal) {
		this.goal = goal;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumCol() {
		return numCol;
	}

	public void setNumCol(int numCol) {
		this.numCol = numCol;
	}

	public int getLengthFS() {
		return lengthFS;
	}

	public void setLengthFS(int lengthFS) {
		this.lengthFS = lengthFS;
	}
}