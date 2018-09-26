package utils;


public class State<T> implements Comparable<State<T>> {
	
	T state;
	State<T> cameFrom;
	private double cost;
	private boolean visited;
	
	public State(T state) {
		this.state = state;
		this.cost = 1;
		this.visited = false;
	}
	
	public State(State<T> s) {
		this.state = s.getState();
		this.cameFrom = s.getCameFrom();
		this.cost = s.getCost();
		this.visited = s.isVisited();
	}
	
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	
	@Override
	public String toString() {
		return this.state.toString();
		
	}
	
	public T getState() {
		return state;
	}

	public void setState(T state) {
		this.state = state;
	}

	public State<T> getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}
	
	
	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	@Override
	public int compareTo(State<T> arg0) {
		return (int) (this.cost - arg0.getCost());
	}
	
}
