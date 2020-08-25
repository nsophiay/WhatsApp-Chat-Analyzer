import java.util.Comparator;


public abstract class AbstractPriorityQueue<T,T2> implements PriorityQueue<T,T2>{

	private Comparator<T> comp;
	
	protected AbstractPriorityQueue(Comparator<T> c){
		comp = c;
	}
	
	protected AbstractPriorityQueue(){
		this(new DefaultComparator<T>());
	}
	
	protected int compare(Entry<T,T2> a, Entry<T,T2> b) {
		return comp.compare(a.getKey(), b.getKey());
	}
	
	protected boolean checkKey(T key) throws IllegalArgumentException {
		try {
			return (comp.compare(key,key) == 0); // see if key can be compared to itself
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Incompatible key");
		}
	}
	
	public boolean isEmpty() { return size() == 0; }
	
}
