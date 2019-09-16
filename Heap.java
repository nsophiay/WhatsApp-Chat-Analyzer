

import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T,T2> extends AbstractPriorityQueue<T,T2>{
	
	protected ArrayList<Entry<T,T2>> heap = new ArrayList<Entry<T,T2>>();
	
	public Heap(){
		super();
	}
	
	public Heap(ArrayList<Entry<T, T2>> h){
		heap = h;
	}
	
	public Heap(Comparator<T> c){
		super(c);
	}
	
	public ArrayList<Entry<T,T2>> getHeap(){
		return heap;
	}
	
	protected int parent(int j){
		return (j-1)/2;
	}
	
	protected int left(int j){
		return 2*j+1;
	}
	
	protected int right(int j){
		return 2*j+2;
	}
	
	protected boolean hasLeft(int j){
		return left(j) < heap.size();
	}
	
	protected boolean hasRight(int j){
		return right(j) < heap.size();
	}
	
	protected void swap(int i, int j){
		Entry<T,T2> temp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, temp);
	}
	
	protected void upheap(int j){
		while(j>0){
			int p = parent(j);
			if(compare(heap.get(j),heap.get(p)) <= 0) break;
			swap(j,p);
			j = p;
		}
	}
	
	protected void downheap(int j){
		while(hasLeft(j)){
			
			int leftIndex = left(j);
			int smallChildIndex = leftIndex;
			if(hasRight(j)){
				int rightIndex = right(j);
				if(compare(heap.get(leftIndex),heap.get(rightIndex)) < 0)
					smallChildIndex = rightIndex;
			}
			if(compare(heap.get(smallChildIndex), heap.get(j)) <= 0)
				break;
			swap(j, smallChildIndex);
			j = smallChildIndex;
			
		}
	}
	
	public int size(){
		return heap.size();
	}
	
	public Entry<T,T2> max(){
		if(heap.isEmpty()) return null;
		return heap.get(0);
	}
	
	public Entry<T,T2> insert(T key, T2 value){
		
		Entry<T,T2> newest = new Entry<>(key, value);
		heap.add(newest);
		upheap(heap.size()-1);
		return newest;
		
	}
	
	public int findDuplicate(T2 value){
		
		for(int i = 0; i < heap.size(); i++){
			if(heap.get(i).getValue().equals(value))
				return i;
		}
		return -1;
	
		
	}
	
	public Entry<T,T2> removeMax(){
		
		if(heap.isEmpty()) return null;
		Entry<T,T2> answer = heap.get(0);
		swap(0, heap.size()-1);
		heap.remove(heap.size()-1);
		downheap(0);
		return answer;
		
	}

	
}
