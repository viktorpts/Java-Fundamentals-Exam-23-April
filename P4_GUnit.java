import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class P4_GUnit {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String input = "";

        String regex = ("^([A-Z][\\w]*)\\s\\|\\s([A-Z][\\w]*)\\s\\|\\s([A-Z][\\w]*)$");
        Pattern p = Pattern.compile(regex);

        ArrayList<TClass> classes = new ArrayList<>();

        while (true) {
            input = console.nextLine();
            if (input.equals("It's testing time!")) break;

            Matcher matcher = p.matcher(input);

            while (matcher.find()) {
                String className = matcher.group(1);
                String methodName = matcher.group(2);
                String unitName = matcher.group(3);

                boolean found = false;
                for (TClass aClass : classes) {
                    if (aClass.name.equals(className)) {
                        aClass.addTest(methodName, unitName);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    classes.add(new TClass(className, methodName, unitName));
                }
            }
        }
        Collator collator = Collator.getInstance();
        classes.sort((c1, c2) -> collator.compare(c1.name, c2.name));
        classes.sort((c1, c2) -> Integer.compare(c1.nOfMethods, c2.nOfMethods));
        classes.sort((c1, c2) -> Integer.compare(c2.nOfUnits, c1.nOfUnits));
        System.out.println();
        classes.stream().forEach(TClass::print);
    }

    static class TClass {
        public String name;
        public int nOfUnits = 0;
        public int nOfMethods = 0;

        public ArrayList<TMethod> methods;

        public TClass(String name, String methodName, String unitName) {
            this.name = name;
            methods = new ArrayList<>();
            methods.add(new TMethod(methodName, unitName));
            nOfMethods++;
            nOfUnits++;
        }

        public void addTest(String methodName, String unitName) {
            for (TMethod method : methods) {
                if (method.name.equals(methodName)) {
                    if (method.addTest(unitName)) nOfUnits++;
                    return;
                }
            }
            methods.add(new TMethod(methodName, unitName));
            nOfMethods++;
            nOfUnits++;
        }

        public void print() {
            System.out.printf("%s:%n", name);
            Collator collator = Collator.getInstance();
            methods.sort((m1, m2) -> collator.compare(m1.name, m2.name));
            methods.sort((m1, m2) -> Integer.compare(m2.units.size(), m1.units.size()));
            methods.stream().forEach(TMethod::print);
        }
    }

    static class TMethod {
        public String name;
        public int nOfUnits = 0;

        public ArrayList<TUnit> units;

        public TMethod(String name, String unitName) {
            this.name = name;
            units = new ArrayList<>();
            units.add(new TUnit(unitName));
            nOfUnits++;
        }

        public void print() {
            System.out.printf("##%s%n", name);
            Collator collator = Collator.getInstance();
            units.sort((u1, u2) -> collator.compare(u1.name, u2.name));
            units.sort((u1, u2) -> Integer.compare(u1.name.length(), u2.name.length()));
            units.stream().forEach(TUnit::print);
        }

        public boolean addTest(String unitName) {
            for (TUnit unit : units) {
                if (unit.name.equals(unitName)) return false;
            }
            units.add(new TUnit(unitName));
            nOfUnits++;
            return true;
        }
    }

    static class TUnit {
        public String name;

        public TUnit(String name) {
            this.name = name;
        }

        public void print() {
            System.out.printf("####%s%n", name);
        }
    }

}
