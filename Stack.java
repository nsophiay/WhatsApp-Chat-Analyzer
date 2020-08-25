

public class Stack<T>{

	int size;
	T[] arr;
	
	// Default constructor
	public Stack(){
		size = 0;
		arr = (T[]) new Object[50];
	}
	
	// Checks if stack is empty
	public boolean isEmpty(){
		return size==0;
	}
	
	// Pops the first element from the stack
	public T pop(){
		
		if(isEmpty()) return null;
		T top = getTop();
		size--;
		return top;
		
	}
	
	// Empties the stack
	public void empty(){
		while(!isEmpty()){
			this.pop();
		}
	}
	
	// Pushes an element to the top of the stack
	public void push(T element){
		if(isEmpty())
			arr[0] = element;
		else if(size != arr.length-1){
			arr[size-1] = element;
		}
		else{
			T[] temp = (T[]) new Object[size*2];
			temp[0] = element;	// Insert new element
			for(int i = 0; i < size; i++){
				temp[i+1] = arr[i];	// Copy current array to larger one
			}
			arr = temp;
		}
		size++;
		
	}
	
	// Get top element
	public T getTop(){
		if(isEmpty()) return null;
		else return arr[size-1];
	}
	
	// Get size
	public int getSize(){
		return size;
	}
	
	public void printStack(){
		for(int i = 0; i < size; i++){
			System.out.println(arr[i]);
		}
	}
	
}
