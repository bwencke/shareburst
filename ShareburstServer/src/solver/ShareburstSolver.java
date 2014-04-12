package solver;

import java.util.ArrayList;

import org.apache.commons.math3.optim.*;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.*;

public class ShareburstSolver
{
	void solve()
	{
		LinearObjectiveFunction f = new LinearObjectiveFunction(new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, 0.0);
		
		ArrayList<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
		constraints.add(new LinearConstraint(new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, Relationship.LEQ, 6.0));
		constraints.add(new LinearConstraint(new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, Relationship.LEQ, 6.0));
		constraints.add(new LinearConstraint(new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, Relationship.LEQ, 6.0));
		constraints.add(new LinearConstraint(new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, Relationship.LEQ, 6.0));
		constraints.add(new LinearConstraint(new double[]{1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, Relationship.LEQ, 6.0));
		constraints.add(new LinearConstraint(new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, Relationship.LEQ, 6.0));
		
		
		SimplexSolver solver = new SimplexSolver();
		PointValuePair solution = solver.optimize(null, f, new LinearConstraintSet(constraints),
			GoalType.MAXIMIZE, new NonNegativeConstraint(false));
		
		System.out.println("x = " + solution.getPoint()[0]);
		System.out.println("y = " + solution.getPoint()[1]);
		System.out.println("value = " + solution.getValue());
	}
	
	public static void main(String[] args)
	{
		(new ShareburstSolver()).solve();
	}
}
