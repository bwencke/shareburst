package solver;

public class Person {

	/*
	 * The person object represents a distinct user and their preferences for Starbursts.
	 * 
	 */
	
	//location in array of each flavor
	final int RED = 0;
	final int ORANGE = 1;
	final int YELLOW = 2;
	final int PINK = 3;
	
	int preferences[] = new int[4];		//	the desired distribution of starbursts
	int distribute[] = new int [4]; 	//  the final distribution
	
	
	/**
	 * Creates a new person with given preferences
	 * @param red		desired red
	 * @param orange	desired orange 
	 * @param pink		desired pink
	 * @param yellow	desired yellow
	 */
	public Person(int red, int orange, int pink, int yellow){
		preferences[RED] = red;
		preferences[ORANGE] = orange;
		preferences[YELLOW] = yellow;
		preferences[PINK] = pink;
	}
	
	/**
	 * Creates a new Person with the given preferences
	 * @param pref array of preferences in the order red, orange, pink, yellow
	 */
	public Person(int [] pref) {
		
		preferences[RED] = pref[0];
		preferences[ORANGE] = pref[1];
		preferences[PINK] = pref[2];
		preferences[YELLOW] = pref[3];
	}
}
