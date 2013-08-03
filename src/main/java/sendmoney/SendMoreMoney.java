package sendmoney;

import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerExpressionVariable;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;

/**
 *
 * @author gefei
 */
public class SendMoreMoney {
    public static void main(String[] args) {
        Model m = new CPModel();

        IntegerVariable d = Choco.makeIntVar("D", 1, 9);
        IntegerVariable o = Choco.makeIntVar("O", 0, 9);
        IntegerVariable n = Choco.makeIntVar("N", 0, 9);
        IntegerVariable a = Choco.makeIntVar("A", 0, 9);
        IntegerVariable l = Choco.makeIntVar("L", 0, 9);
        IntegerVariable g = Choco.makeIntVar("G", 1, 9);
        IntegerVariable e = Choco.makeIntVar("E", 0, 9);
        IntegerVariable r = Choco.makeIntVar("R", 1, 9);
        IntegerVariable b = Choco.makeIntVar("B", 0, 9);
        IntegerVariable t = Choco.makeIntVar("T", 0, 9);


        IntegerExpressionVariable donald = Choco.sum(Choco.mult(d, 100000), Choco.mult(o, 10000),
                Choco.mult(n, 1000), Choco.mult(a, 100), Choco.mult(l, 10), d);
        IntegerExpressionVariable gerald = Choco.sum(Choco.mult(g, 100000), Choco.mult(e, 10000),
                Choco.mult(r, 1000), Choco.mult(a, 100), Choco.mult(l, 10), d);
        IntegerExpressionVariable robert = Choco.sum(Choco.mult(r, 100000), Choco.mult(o, 10000),
                Choco.mult(b, 1000), Choco.mult(o, 100), Choco.mult(r, 10), t);

        Constraint constraint = Choco.eq(Choco.sum(donald, gerald), robert);
        Constraint allDifferent = Choco.allDifferent(d,o,n,a,l,g,e,r,b,t);

        m.addConstraint(constraint);
        m.addConstraint(allDifferent);

        //m.addConstraint(Choco.neq(1, d));
        //m.addConstraint(Choco.neq(5, d));

        Solver s = new CPSolver();

        s.read(m);
        s.maximize(s.getVar(d), false);

        System.out.println("d="+s.getVar(d).getVal());
        System.out.println("o="+s.getVar(o).getVal());
        System.out.println("n="+s.getVar(n).getVal());
        System.out.println("a="+s.getVar(a).getVal());
        System.out.println("l="+s.getVar(l).getVal());

        System.out.println(s.getVar(d).getVal()+""+s.getVar(o).getVal()+""+s.getVar(n).getVal()+""+s.getVar(a).getVal()+""+s.getVar(l).getVal()+""+s.getVar(d).getVal());
        System.out.println(s.getVar(g).getVal()+""+s.getVar(e).getVal()+""+s.getVar(r).getVal()+""+s.getVar(a).getVal()+""+s.getVar(l).getVal()+""+s.getVar(d).getVal());
        System.out.println(s.getVar(r).getVal()+""+s.getVar(o).getVal()+""+s.getVar(b).getVal()+""+s.getVar(o).getVal()+""+s.getVar(r).getVal()+""+s.getVar(t).getVal());


    }
}
