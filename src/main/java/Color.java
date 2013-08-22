import JaCoP.constraints.Max;
import JaCoP.constraints.XltC;
import JaCoP.constraints.XltY;
import JaCoP.constraints.XneqY;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import JaCoP.core.Switches;
import JaCoP.search.*;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author gefei
 */
public class Color {

    public static void main (String[] args) throws Exception {

        Store store = new Store();  // define FD store

        File file = new File(args[0]);
        Scanner scanner = new Scanner(file);
        int size = scanner.nextInt();
        int numE = scanner.nextInt();

        // define finite domain variables
        IntVar[] v = new IntVar[size];
        for (int i=0; i<size; i++) {
            v[i] = new IntVar(store, "v"+i, 0, size-1);
        }

        // define constraints
        IntVar maxColor = new IntVar(store, "max", 0, size-1);
        store.impose(new Max(v, maxColor));

        for (int i = 0; i < numE; i++) {
            int from = scanner.nextInt();
            int to   = scanner.nextInt();
            store.impose(new XneqY(v[from], v[to]));
        }

        // search for a solution and print results
        Search<IntVar> search = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select =
                new InputOrderSelect<IntVar>(store, v,
                        new IndomainMin<IntVar>());
        search.setTimeOut(30);
        search.setPrintInfo(false);
        boolean result = search.labeling(store, select, maxColor);


        if (result) {
            System.out.println(maxColor.value());
            for (int i = 0; i < size; i++) {
               System.out.print(v[i].value()+" ");
            }
            System.out.println();
        } else {
            System.out.println("*** No");
        }
    }
}
