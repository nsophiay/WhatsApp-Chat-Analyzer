

public interface PriorityQueue<T,T2>{
	int size();
	boolean isEmpty();
	Entry<T,T2> insert(T key, T2 value) throws IllegalArgumentException;
	Entry<T,T2> max();
	Entry<T,T2> removeMax();
}
