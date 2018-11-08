import edu.technopolis.CustomString;

public class Main {

    public static void main(String[] args) {
        String st = "How i think.";
        CustomString ar = new CustomString(st, 3);
        char a = ar.charAt(4);
        System.out.println(ar.length() + " " + a + " " + ar.charAt(9));

        String st1 = ar.toString();
        System.out.println(st1);

        CustomString test2 = ar.subSequence(2, 8);
        System.out.println(test2.length() + " " + test2.charAt(2) + " " + test2.charAt(4));
    }
}
