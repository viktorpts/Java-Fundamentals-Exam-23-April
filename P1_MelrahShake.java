import java.util.Scanner;

public class P1_MelrahShake {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        String input = console.nextLine();
        String pattern = console.nextLine();
        String afterFirst = "";
        String afterSecond = "";
        int len = 0;
        int plen = 0;
        boolean found = false;

        while (true) {
            len = input.length();
            plen = pattern.length();
            if (len == 0 || plen == 0 || len < plen * 2) break;
            afterFirst = "";
            afterSecond = "";

            StringBuilder result = new StringBuilder(input);
            int firstMatch = result.indexOf(pattern);
            if (firstMatch != -1) { // atleast one match
                int lastMatch = result.lastIndexOf(pattern);
                if (firstMatch != lastMatch) { // two matches
                    result.delete(firstMatch,firstMatch+plen);
                    result.delete(lastMatch-plen, lastMatch);
                    input = result.toString();
                    System.out.println("Shaked it.");

                    // trim pattern
                    int drop = plen/2;
                    StringBuilder newPattern = new StringBuilder(pattern);
                    newPattern.deleteCharAt(drop);
                    pattern = newPattern.toString();
                } else break;
            } else break;
        }
        System.out.println("No shake.");
        System.out.println(input);
    }
}
