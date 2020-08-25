
public class LinkedList {
	
	private class Node{
		private String name;
		private Node link;
		
		public Node(){
			link = null;
			name = null;
		}
		
		public Node(String name, Node link){
			this.name = name;
			this.link= link;
		}
	}
	
	private Node head;
	private Node position;
	
	public LinkedList(){
		head = null;
	}
	
	public void addToStart(String name){
		this.head = new Node(name, head);
	}
	
	
	
}
