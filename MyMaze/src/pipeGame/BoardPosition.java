package pipeGame;

public class BoardPosition {

	private int x;
	private int y;
	
	public BoardPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(BoardPosition bp) {
		if(x == bp.x && y == bp.y)
			return true;
		return false;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
}
