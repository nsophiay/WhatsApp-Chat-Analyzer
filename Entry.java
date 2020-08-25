

public class Entry<T1, T2> {
	private T1 key;
	private T2 value;
	
	public Entry(T1 key, T2 value){
		this.key = key;
		this.value = value;
	}
	
	public T1 getKey(){
		return key;
	}
	public T2 getValue(){
		return value;
	}
	public void setKey(T1 k){
		key = k;
	}
	public void setValue(T2 v){
		value = v;
	}
	
}
