package interfaces;

import java.util.LinkedList;


public interface Searcher<T> {
	// the search method
	public LinkedList<T> search(Searchable<T> s);
}
