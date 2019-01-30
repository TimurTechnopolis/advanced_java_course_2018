package main.java.edu.technopolis;

import static org.junit.Assert.assertEquals;

public class Tests {

    private char chars1[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k'};
    private char chars2[] = {'S', 'o', 'm', 'e', ' ', 'l', 'i', 't', 't', 'l', 'e', ' ', 's', 't', 'r', 'i', 'n', 'g', '.'};
    private char chars3[] = {};
    private CustomString string1 = new CustomString(chars1, 3);
    private CustomString string2 = new CustomString(chars2, 5);
    private CustomString string3 = new CustomString(chars3, 9);

    @org.junit.Test
    public void length() {
        assertEquals(11, string1.length());
        assertEquals(19, string2.length());
        assertEquals(0, string3.length());
    }

    @org.junit.Test
    public void charAt() {
        assertEquals('a', string1.charAt(0));
        assertEquals('l', string2.charAt(5));
        assertEquals(' ', string2.charAt(4));
    }

    @org.junit.Test
    public void subSequence() {
        char s1[] = {'d', 'e', 'f', 'g', 'h'};
        char s2[] = {'m', 'e', ' ', 'l', 'i'};
        char s3[] = {'l', 'i', 't', 't', 'l', 'e', ' ', 's'};
        CustomString expString1 = new CustomString(s1, 3);
        CustomString expString2 = new CustomString(s2, 5);
        CustomString expString3 = new CustomString(s3, 5);

        assertEquals(expString1.toString(), string1.subSequence(3, 7).toString());
        assertEquals(expString2.toString(), string2.subSequence(2, 6).toString());
        assertEquals(expString3.toString(), string2.subSequence(5, 12).toString());
    }
}
