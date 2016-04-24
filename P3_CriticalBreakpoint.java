import java.math.BigInteger;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.Scanner;

public class P3_CriticalBreakpoint {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String input = "";

        ArrayList<Line> lines = new ArrayList<>();
        while (true) {
            input = console.nextLine();
            if (input.equals("Break it.")) break;

            String[] tokens = input.split(" ");
            long x1 = Long.parseLong(tokens[0]);
            long y1 = Long.parseLong(tokens[1]);
            long x2 = Long.parseLong(tokens[2]);
            long y2 = Long.parseLong(tokens[3]);

            lines.add(new Line(x1, y1, x2, y2));
        }

        // Check breakpoints
        if (!Line.converged) {
            System.out.println("Critical breakpoint does not exist.");
            return;
        }
        for (Line line : lines) {
            line.print();
        }
        System.out.printf("Critical Breakpoint: %s", Line.derive().toString());
    }

    static class Line {
        public static long counter = 0;
        long x1;
        long y1;
        long x2;
        long y2;
        long breakpoint;
        public static long common = 0;
        public static boolean converged = true;

        public Line(long x1, long y1, long x2, long y2) {
            counter++;
            this.y2 = y2;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            calcBreak();
        }

        private void calcBreak() {
            breakpoint = Math.abs((x2 + y2) - (x1 + y1));
            if (breakpoint != 0) {
                if (common == 0) { // found nonzero value
                    common = breakpoint;
                } else if (common != breakpoint) { // lost convergence
                    converged = false;
                    return;
                }
            }
        }

        public void print() {
            System.out.printf("Line: [%d, %d, %d, %d]%n", x1, y1, x2, y2);
        }

        public static BigInteger derive() {
            BigInteger result = BigInteger.valueOf(common);
            result = result.pow((int)counter);
            return result.remainder(BigInteger.valueOf(counter));
        }
    }

}