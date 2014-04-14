package solver;

import java.util.ArrayList;

import org.apache.commons.math3.optim.*;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.*;

/**
 * Solves the Starburst distribution problem.
 * 
 * @author Bryan Rainey
 */

public class ShareburstSolver
{
	/**
	 * Solves the Starburst distribution problem.
	 * @param people array of Person objects
	 * @param nPackets number of Starburst packets
	 */
	public void solve(Person[] people, int nPackets)
	{
		// Specify objective function: the sum of the errors
		double[] coefficients = new double[8*people.length];
		for (int i = coefficients.length / 2; i < coefficients.length; i++)
		{
			coefficients[i] = 1.0;
		}
		LinearObjectiveFunction f = new LinearObjectiveFunction(coefficients, 0.0);
		
		// Specify constraints
		ArrayList<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
		// Restrict to nonnegative numbers
		for (int i = 0; i < 4*people.length; i++)
		{
			double[] coeffs = new double[8*people.length];
			coeffs[i] = 1.0;
			constraints.add(new LinearConstraint(coeffs, Relationship.GEQ, 0.0));
		}
		// Restrict number of available colors
		for (int i = 0; i < 4; i++)
		{
			double[] coeffs = new double[8*people.length];
			for (int j = 0; j < people.length; j++)
			{
				coeffs[i + 4*j] = 1.0;
			}
			constraints.add(new LinearConstraint(coeffs, Relationship.LEQ, 3.0*nPackets));
		}
		// Restrict number of Starbursts per person
		double nPerPerson = (double) (12 * nPackets / people.length);
		for (int i = 0; i < people.length; i++)
		{
			double[] coeffs = new double[8*people.length];
			for (int j = 0; j < 4; j++)
			{
				coeffs[4*i + j] = 1.0;
			}
			constraints.add(new LinearConstraint(coeffs, Relationship.EQ, 
					nPerPerson));
		}
		// Specify preferences
		for (int i = 0; i < people.length; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				double[] coeffs = new double[8*people.length];
				coeffs[4*i + j] = 1.0;
				coeffs[4*i + j + 4*people.length] = -1.0;
				constraints.add(new LinearConstraint(coeffs, Relationship.LEQ, 
						((double) people[i].getPreferences()[j]) * nPerPerson / 12.0));
				coeffs = new double[8*people.length];
				coeffs[4*i + j] = 1.0;
				coeffs[4*i + j + 4*people.length] = 1.0;
				constraints.add(new LinearConstraint(coeffs, Relationship.GEQ, 
						((double) people[i].getPreferences()[j]) * nPerPerson / 12.0));
			}
		}
		
		// Solve the linear programming problem
		SimplexSolver solver = new SimplexSolver();
		PointValuePair solution = solver.optimize(null, f, new LinearConstraintSet(constraints),
			GoalType.MINIMIZE, new NonNegativeConstraint(false));
		
		// Restrict answer to integers
		for (int i = 0; i < 4*people.length; i++)
		{
			double[] coeffs = new double[8*people.length];
			coeffs[i] = 1.0;
			constraints.add(new LinearConstraint(coeffs, Relationship.EQ, 
					(double) Math.round(solution.getPoint()[i])));
			solution = solver.optimize(null, f, new LinearConstraintSet(constraints),
					GoalType.MINIMIZE, new NonNegativeConstraint(false));
		}
		
		// Assign distribution to each person
		for (int i = 0; i < people.length; i++)
		{
			int[] assignment = new int[4];
			for (int j = 0; j < 4; j++)
			{
				assignment[j] = (int) Math.round(solution.getPoint()[4*i + j]);
			}
			people[i].setDistribute(assignment);
		}
	}
	
	public static void main(String[] args)
	{
		// Example problem
		// Warning: This is not deterministic because the problem may have multiple solutions.
		
		Person[] people = new Person[6];
		people[0] = new Person(new int[]{5, 3, 1, 3});
		people[1] = new Person(new int[]{3, 4, 1, 4});
		people[2] = new Person(new int[]{5, 3, 2, 2});
		people[3] = new Person(new int[]{4, 2, 3, 3});
		people[4] = new Person(new int[]{3, 1, 6, 1});
		people[5] = new Person(new int[]{4, 2, 2, 4});
		
		(new ShareburstSolver()).solve(people, 4);
		
		for (int i = 0; i < 6; i++)
		{
			System.out.println("Person " + (i + 1) + ":");
			for (int j = 0; j < 4; j++)
			{
				System.out.println(people[i].getDistribute()[j]);
			}
			System.out.println();
		}
	}
}