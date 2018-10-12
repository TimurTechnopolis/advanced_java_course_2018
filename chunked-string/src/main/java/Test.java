import edu.technopolis.advjava.CustomString;


public class Test {
    public static void main(String[] args) {
        try {
            String sampleTextAboutOak = "At the edge of the road stood an oak. Probably ten times the age " +
                    "of the birches that formed the forest, it was ten times as thick and twice as " +
                    "tall as they. It was an enormous tree, its girth twice as great as a man could " +
                    "embrace, and evidently long ago some of its branches had been broken off and " +
                    "its bark scarred. With its huge ungainly limbs sprawling unsymmetrically, and " +
                    "its gnarled hands and fingers, it stood an aged, stern, and scornful monster " +
                    "among the smiling birch trees. Only the dead-looking evergreen firs dotted about " +
                    "in the forest, and this oak, refused to yield to the charm of spring or notice " +
                    "either the spring or the sunshine.";

            CustomString cs = new CustomString(sampleTextAboutOak, 4);
            System.out.println("Custom string consists of " + cs.length() + " characters and is");
            System.out.println(cs);
            CustomString ss = cs.subSequence(157, 180);
            System.out.println("Substring must be 'It was an enormous tree'");
            System.out.println(ss);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
