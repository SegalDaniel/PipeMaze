package pipeGame;


import java.util.ArrayList;

import exceptions.PipeGameTileException;

public class PipeGameTile implements Comparable<Object> {

	public static enum PipeDirection{
		TOP_RIGHT('L'),
		RIGHT_BOTTOM('F'),
		BOTTOM_LEFT('7'),
		LEFT_TOP('J'),
		TOP_BOTTOM('|'),
		LEFT_RIGHT('-'), 
		START('s'),
		GOAL('g');
		
	    public char asChar() {
	        return asChar;
	    }

	    private final char asChar;

	    private PipeDirection(char asChar) {
	        this.asChar = asChar;
	    }
	}
	
	BoardPosition position;
	char character;
	PipeDirection direction;
	ArrayList<PipeDirection> rotations;

	public PipeGameTile(char c, BoardPosition p) throws PipeGameTileException {
		this.character = c;
		this.position = new BoardPosition(p.getX(), p.getY());
		switch (c) {
			//empty tile
		case ' ':
			this.direction = null;
			this.rotations = null;
			break;
			
			//start
		case 's':
		//case 'S' :
			this.direction = PipeDirection.START;
			this.rotations = null;
			break;
			
			//goal
		case 'g':
		//case 'G':
			this.direction = PipeDirection.GOAL;
			this.rotations = null;
			break;
			
			//top_bottom
		case '|':
			this.direction = PipeDirection.TOP_BOTTOM;
			this.rotations = new ArrayList<PipeDirection>();
			this.rotations.add(PipeDirection.LEFT_RIGHT);
			this.rotations.add(PipeDirection.TOP_BOTTOM);
			break;
			
			//left_right
		case '-':
			this.direction = PipeDirection.LEFT_RIGHT;
			this.rotations = new ArrayList<PipeDirection>();
			this.rotations.add(PipeDirection.TOP_BOTTOM);
			this.rotations.add(PipeDirection.LEFT_RIGHT);
			break;
		
			//top_right	
		case 'L':
			this.direction = PipeDirection.TOP_RIGHT;
			this.rotations = new ArrayList<PipeDirection>();
			this.rotations.add(PipeDirection.RIGHT_BOTTOM);
			this.rotations.add(PipeDirection.BOTTOM_LEFT);
			this.rotations.add(PipeDirection.LEFT_TOP);
			this.rotations.add(PipeDirection.TOP_RIGHT);
			break;
			
			//right_bottom
		case 'F':
			this.direction = PipeDirection.RIGHT_BOTTOM;
			this.rotations = new ArrayList<PipeDirection>();
			this.rotations.add(PipeDirection.BOTTOM_LEFT);
			this.rotations.add(PipeDirection.LEFT_TOP);
			this.rotations.add(PipeDirection.TOP_RIGHT);
			this.rotations.add(PipeDirection.RIGHT_BOTTOM);
			break;
			
			//bottom_left
		case '7':
			this.direction = PipeDirection.BOTTOM_LEFT;
			this.rotations = new ArrayList<PipeDirection>();
			this.rotations.add(PipeDirection.LEFT_TOP);
			this.rotations.add(PipeDirection.TOP_RIGHT);
			this.rotations.add(PipeDirection.RIGHT_BOTTOM);
			this.rotations.add(PipeDirection.BOTTOM_LEFT);
			break;
			
			//left_top
		case 'J':
			this.direction = PipeDirection.LEFT_TOP;
			this.rotations = new ArrayList<PipeDirection>();
			this.rotations.add(PipeDirection.TOP_RIGHT);
			this.rotations.add(PipeDirection.RIGHT_BOTTOM);
			this.rotations.add(PipeDirection.BOTTOM_LEFT);
			this.rotations.add(PipeDirection.LEFT_TOP);
			break;

		default:
			
			//throw new PipeGameTileException("NO SUCH KIND OF TILE! UNSUPPORTED CHAR '" + c + "'!");
		}
				
	}

	public void rotate() {
		if(rotations != null) {
			int i = rotations.indexOf(direction);
			i = (i + 1) % rotations.size();
			direction = rotations.get(i);
			character = direction.asChar;
		}
	}
	
	public boolean isConnected(PipeGameTile pgt) {
		int xDiff = this.position.getX() - pgt.getPosition().getX();
		int yDiff = this.position.getY() - pgt.getPosition().getY();

		// check if the tiles beside each other and also not at an angle
		if ((xDiff > 1 || xDiff < -1) && (yDiff > 1 || yDiff < -1))
			return false;

		PipeDirection pgtDir = pgt.getDirection();

		// tiles on the same column
		if (yDiff == 0) {
			switch (xDiff) {

			// pgs at top
			case 1:
				if ((direction == PipeDirection.TOP_BOTTOM || direction == PipeDirection.TOP_RIGHT
						|| direction == PipeDirection.LEFT_TOP || direction == PipeDirection.START)
						&& (pgtDir == PipeDirection.BOTTOM_LEFT || pgtDir == PipeDirection.RIGHT_BOTTOM
								|| pgtDir == PipeDirection.TOP_BOTTOM || pgtDir == PipeDirection.GOAL))
					return true;
				break;
			// pgs at the bottom
			case -1:
				if ((direction == PipeDirection.BOTTOM_LEFT || direction == PipeDirection.RIGHT_BOTTOM
						|| direction == PipeDirection.TOP_BOTTOM || direction == PipeDirection.START)
						&& (pgtDir == PipeDirection.TOP_BOTTOM || pgtDir == PipeDirection.TOP_RIGHT
								|| pgtDir == PipeDirection.LEFT_TOP || pgtDir == PipeDirection.GOAL))
					return true;
				break;
			default:
				break;
			}
		} else {
			// tiles at the same row
			switch (yDiff) {

			// pgs at the left
			case 1:
				if ((direction == PipeDirection.BOTTOM_LEFT || direction == PipeDirection.LEFT_TOP
						|| direction == PipeDirection.LEFT_RIGHT || direction == PipeDirection.START)
						&& (pgtDir == PipeDirection.RIGHT_BOTTOM || pgtDir == PipeDirection.TOP_RIGHT
								|| pgtDir == PipeDirection.LEFT_RIGHT || pgtDir == PipeDirection.GOAL))
					return true;
				break;

			// pgs at the right
			case -1:
				if ((direction == PipeDirection.RIGHT_BOTTOM || direction == PipeDirection.TOP_RIGHT
						|| direction == PipeDirection.LEFT_RIGHT || direction == PipeDirection.START)
						&& (pgtDir == PipeDirection.LEFT_TOP || pgtDir == PipeDirection.BOTTOM_LEFT
								|| pgtDir == PipeDirection.LEFT_RIGHT || pgtDir == PipeDirection.GOAL))
					return true;
				break;
			default:
				break;
			}
		}
		return false;
	}
	
	public boolean equals(PipeGameTile pgt) {
		if (direction == pgt.direction && position.equals(pgt.position))
			return true;
		return false;
	}
	
	//-----------Getters/Setters----------
	
	public BoardPosition getPosition() {
		return position;
	}

	public void setPosition(BoardPosition position) {
		this.position = position;
	}

	public char getCharacter() {
		return character;
	}

	public void setCharacter(char character) {
		this.character = character;
	}

	public PipeDirection getDirection() {
		return direction;
	}

	public void setDirection(PipeDirection direction) {
		this.direction = direction;
	}

	public ArrayList<PipeDirection> getRotations() {
		return rotations;
	}

	public void setRotations(ArrayList<PipeDirection> rotations) {
		this.rotations = rotations;
	}

	@Override
	public int compareTo(Object arg0) {
		
		return 0;
	}

	
}