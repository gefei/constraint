import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author gefei
 */
public class Euler96 {

    private final int SIZE = 9;

    private CPModel model;

    private void solveOne(int[][] numbers) {
        model = new CPModel();

        IntegerVariable[][] rows = Choco.makeIntVarArray("rows", SIZE, SIZE, 1, SIZE);
        for (int i = 0; i < rows.length; i++) {
            model.addConstraint(Choco.allDifferent(rows[i]));

            IntegerVariable[] col = new IntegerVariable[SIZE];
            for (int j = 0; j < SIZE; j++) {
                col[j] = rows[j][i];
            }
            model.addConstraint(Choco.allDifferent(col));
        }

        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                IntegerVariable[] square = {
                    rows[i][j],  rows[i][j+1],  rows[i][j+2],
                    rows[i+1][j],rows[i+1][j+1],rows[i+1][j+2],
                    rows[i+2][j],rows[i+2][j+1],rows[i+2][j+2],
                };
                model.addConstraint(Choco.allDifferent(square));
            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
               if (numbers[i][j] != 0) model.addConstraint(Choco.eq(numbers[i][j], rows[i][j]));
            }
        }

        CPSolver solver = new CPSolver();
        solver.read(model);
        solver.solve();
        printGrid(rows, solver);

        int s = solver.getVar(rows[0][0]).getVal()*100 +
                solver.getVar(rows[0][1]).getVal()*10  +
                solver.getVar(rows[0][2]).getVal();
        sum += s;
    }

    private int sum = 0;

    private int[][] readFile(Scanner scanner) throws FileNotFoundException {
        scanner.nextLine();
        int[][] res = new int[SIZE][];
        for (int y = 0; y < SIZE; y++) {
            res[y] = new int[SIZE];
            String s = scanner.nextLine();
            for (int x = 0; x < SIZE; x++) {
                res[y][x] = (int)s.charAt(x) - (int)'0';
            }
        }
        return res;
    }

    public static void printGrid(IntegerVariable[][] rows, Solver s) {
        for (int i = 0; i < 9; i++) {
            StringBuffer st = new StringBuffer();
            for (int j = 0; j < 9; j++) st.append(s.getVar(rows[i][j]).getVal() + " ");
            System.out.println (st.toString());
        }
        System.out.println("\n");
    }

    public static void main(String[] args) throws FileNotFoundException {
        Euler96 sudoku = new Euler96();
        File file = new File("sudoku.txt");
        Scanner scanner = new Scanner(file);
        for (int i = 0; i < 50; i++) {
            int[][] numbers = sudoku.readFile(scanner);
            sudoku.solveOne(numbers);
        }
        System.out.println(sudoku.sum);
    }
}
