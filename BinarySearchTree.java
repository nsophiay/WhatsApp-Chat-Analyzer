

import java.util.ArrayList;

public class BinarySearchTree {

	
	private class Node{
	
		private int key;
		public Node(int k){
			setKey(k);
		}
		public int getKey() {
			return key;
		}
		public void setKey(int key) {
			this.key = key;
		}
		
		
	}

	ArrayList<Node> tree = new ArrayList<Node>();
	Node root;
	
	public BinarySearchTree(){
		root = null;
	}
	
	public BinarySearchTree(Node r){
		root = r;
		tree.add(r);
	}
	
	public void setRoot(Node r){
		root = r;
	}
	
	public int parent(int j){
		return (j-1)/2;
	}
	
	public int left(int j){
		return 2*j + 1;
	}
	
	public int right(int j){
		return 2*j+2;
	}
	public boolean hasLeft(int k){
		return left(k) < tree.size()-1;
	}
	public boolean hasRight(int k){
		return right(k) < tree.size()-1;
	}
	
	public boolean isEmpty(){
		return root == null;
	}
	
	public int getHeight(){
		return tree.size()-1;
	}
	
	public boolean isLeaf(int k){
		return (!isEmpty())?(!hasLeft(k) && !hasRight(k)):false;
	}
	
	public void insert(int root, int key){
		
		if(isEmpty()){
			Node r = new Node(key);
			setRoot(r);
			tree.add(r);
		}
		else if(tree.get(root) == null){
			tree.add(new Node(key));
		}
		else{
			
			if(key < tree.get(root).key)
				insert(left(root), key);
			else if(key > tree.get(root).key)
				insert(right(root), key);
			
		}
		
		
	}
	
	public int find(int key){
		
		int start = 0; // root
		
		while(tree.get(start) != null){
			if(key < tree.get(start).key){
				start = left(start);
			}
			else if(key > tree.get(start).key){
				start = right(start);
			}
		}
		
		return parent(start);
		
		
	}
	
	public void remove(int k){
		
		if(isEmpty()) return;
		
		int index = find(k);
		
		if(isLeaf(index))
			tree.remove(index);
		else if(isLeaf(left(index)) && !hasRight(index)){
			int temp = left(index);
			tree.add(k, tree.get(left(index)));
			tree.remove(temp);
		}
		else if(isLeaf(right(index)) && !hasLeft(index)){
			int temp = right(index);
			tree.add(k, tree.get(right(index)));
			tree.remove(temp);
		}
		else if(hasRight(index) && hasLeft(index)){
			
			tree.add(k, tree.get(inOrderSuccessor(right(index))));
			tree.remove(inOrderSuccessor(0));
			
		}
		
		
	}
	
	public int inOrderSuccessor(int k){
		
		int minv = tree.get(k).key; 
			while (hasLeft(k)){ 
	            minv = tree.get(k).key; 
	            k = left(k); 
	        } 
	    return minv; 

	}
	
	public void inOrderTraversal(int k){
		
		if(isLeaf(k)) return;
		while(!isLeaf(k)){
			
			inOrderTraversal(left(k));
			System.out.println(tree.get(k).key);
			inOrderTraversal(right(k));
			
		}
		
		
	}
	
	public void printTree(){
		
		for(int i = 0; i < tree.size(); i++){
			System.out.print(tree.get(i).key + " ");
		}
		
	}
	
	
	
}
