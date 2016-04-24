import java.util.ArrayList;
import java.util.Scanner;

public class P2_Crossfire {

    static ArrayList<ArrayList<Integer>> matrix;

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String input = "";

        String tokens[] = console.nextLine().split(" ");
        int rows = Integer.parseInt(tokens[0]);
        int cols = Integer.parseInt(tokens[1]);

        int counter = 1;

        // Initialize array
        matrix = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            matrix.add(new ArrayList<Integer>(cols));
            for (int j = 0; j < cols; j++) {
                matrix.get(i).add(counter);
                counter++;
            }
        }

        // Start firing
        while (true) {
            input = console.nextLine();
            if (input.equals("Nuke it from orbit")) break;

            tokens = input.split(" ");
            long targetRow = Long.parseLong(tokens[0]);
            long targetCol = Long.parseLong(tokens[1]);
            long radius = Long.parseLong(tokens[2]);

            if ((targetRow > rows - 1 && targetCol > cols - 1) ||
                    (targetRow < 0 && targetCol < 0)) {
                // fired well outside the target area
                continue;
            }
            ArrayList<Integer[]> hits = new ArrayList<>();
            // Add target cell
            // Verify starting rows
            long startingRow = targetRow - radius;
            if (startingRow > matrix.size() - 1) continue; // if we start outside the matrix, ignore this cycle
            if (startingRow < 0) startingRow = 0;
            long endingRow = targetRow + radius;
            if (endingRow < 0) continue; // if we end before the matrix, ignore this cycle
            if (endingRow > matrix.size() - 1) endingRow = matrix.size() - 1;
            // Verify starting cols
            long startingCol = targetCol - radius;
            if (startingCol > cols) continue; // if we start outside the matrix, ignore this cycle
            if (startingCol < 0) startingCol = 0;
            long endingCol = targetCol + radius;
            if (endingCol < 0) continue; // if we end before the matrix, ignore this cycle
            if (endingCol > cols) endingCol = cols - 1;

            addHit(hits, targetRow, targetCol);
            for (long i = startingRow; i <= endingRow; i++) {
                if (i == targetRow) continue; // don't add target cell more than once
                addHit(hits, i, targetCol);
            }
            for (long i = startingCol; i <= endingCol; i++) {
                if (i == targetCol) continue; // don't add target cell more than once
                addHit(hits, targetRow, i);
            }

            // Set hit block to zero
            for (Integer[] hit : hits) {
                matrix.get(hit[0]).set(hit[1], 0);
            }

            // Remove hit elements
            matrix.stream().forEach(row -> {
                row.removeIf(cell -> cell == 0);
            });
            // Purge empty rows
            matrix.removeIf(row -> row.size() == 0);
        }

        // Output
        //System.out.println("Start");
        output(matrix);
        //System.out.println("End");
    }

    static void output(ArrayList<ArrayList<Integer>> matrix) {
        for (ArrayList<Integer> row : matrix) {
            for (Integer cell : row) {
                System.out.printf("%d ", cell);
            }
            System.out.printf("%n");
        }
    }

    static void addHit(ArrayList<Integer[]> hits, long row, long col) {
        if (row < 0 || row > matrix.size() - 1) return;
        if (col < 0 || col > matrix.get((int)row).size() - 1) return;
        hits.add(new Integer[]{(int) row, (int) col});
    }

}
