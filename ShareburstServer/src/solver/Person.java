package solver;

public class Person {

	/*
	 * The person object represents a distinct user and their preferences for Starbursts.
	 * 
	 */
	
	//location in array of each flavor
	final int ORANGE = 0;
	final int PINK = 1;
	final int RED = 2;
	final int YELLOW = 3;

	private int preferences[] = new int[4];		//	the desired distribution of starbursts
	private int distribute[] = new int[4]; 	//  the final distribution
	
	/**
	 * Creates a new person with given preferences
	 * @param orange	desired orange
	 * @param pink		desired pink 
	 * @param red		desired red
	 * @param yellow	desired yellow
	 */
	public Person(int orange, int pink, int red, int yellow){
		preferences[ORANGE] = orange;
		preferences[PINK] = pink;
		preferences[RED] = red;
		preferences[YELLOW] = yellow;
	}
	
	/**
	 * Creates a new Person with the given preferences
	 * @param pref array of preferences in the order orange, pink, red, yellow
	 */
	public Person(int [] pref) {
		preferences[ORANGE] = pref[ORANGE];
		preferences[PINK] = pref[PINK];
		preferences[RED] = pref[RED];
		preferences[YELLOW] = pref[YELLOW];
	}
	
	public int[] getPreferences() {
		return preferences;
	}

	public void setPreferences(int[] preferences) {
		this.preferences = preferences;
	}

	public int[] getDistribute() {
		return distribute;
	}

	public void setDistribute(int[] distribute) {
		this.distribute = distribute;
	}
}
