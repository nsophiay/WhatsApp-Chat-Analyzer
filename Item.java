
public class Item {

	private String name;
	private int calories; // per serving
	private int protein; // per serving
	
	public Item(String n, int c, int p){
		name = n;
		calories = c;
		protein = p;
	}
	
	public String toString(){
		return name + ", " + calories + " calories for " + protein + " grams of protein";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public int getProtein() {
		return protein;
	}

	public void setProtein(int protein) {
		this.protein = protein;
	}
	
}
