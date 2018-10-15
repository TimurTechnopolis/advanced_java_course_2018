package main.java.edu.technopolis.advjava.test;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import main.java.edu.technopolis.advjava.CustomString;

import static org.junit.Assert.*;

public class CustomStringTest {

    /**
     * Top secret!
     **/
    private static final String secretURL = "https://ziginsider.github.io/images/testing1.jpeg";

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @org.junit.Test
    public void length() {
        char[] firstChars = {'H', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd', '!'};
        char[] secondChars = new char[100];

        CustomString emptyString = new CustomString();
        CustomString firstString = new CustomString(firstChars);
        CustomString secondString = new CustomString(secondChars);

        assertEquals(0, emptyString.length());
        assertEquals(firstChars.length, firstString.length());
        assertEquals(secondChars.length, secondString.length());
    }

    @org.junit.Test
    public void toStringTest() {
        char[] charArray = {'H', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd', '!'};

        CustomString emptyString = new CustomString();
        CustomString customString = new CustomString(charArray);
        String defaultString = new String(charArray);

        assertEquals("", emptyString.toString());
        assertEquals(defaultString, customString.toString());
    }

    @org.junit.Test
    public void subSequence() {
        char[] charArray = {'H', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd', '!'};

        CustomString emptyString = new CustomString();
        CustomString customString = new CustomString(charArray);
        String defaultString = new String(charArray);

        String defSubString1 = defaultString.substring(1, 10);
        String defSubString2 = defSubString1.substring(3, 8);
        String defSubString3 = defSubString2.substring(1, 2);

        CustomString subString1 = customString.substring(1, 10);
        CustomString subString2 = subString1.substring(3, 8);
        CustomString subString3 = subString2.substring(1, 2);

        assertEquals(defSubString1, subString1.toString());
        assertEquals(defSubString2, subString2.toString());
        assertEquals(defSubString3, subString3.toString());

        exceptionRule.expect(IndexOutOfBoundsException.class);
        emptyString.subSequence(0, 10);

    }

    @org.junit.Test
    public void charAt() {
        char[] charArray = {'H', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd', '!'};

        CustomString emptyString = new CustomString();
        CustomString customString = new CustomString(charArray);

        assertEquals('H', customString.charAt(0));
        assertEquals(' ', customString.charAt(6));
        assertEquals('!', customString.charAt(12));

        exceptionRule.expect(IndexOutOfBoundsException.class);
        customString.charAt(-1000);
        customString.charAt(100);
        customString.charAt(Integer.MAX_VALUE);
        customString.charAt(Integer.MIN_VALUE);
        emptyString.charAt(10);

    }

    @org.junit.Test
    public void compareTo() {

        char[] longArray = {'H', 'e', 'l', 'l', 'o'};
        char[] longerArray = {'H', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o'};
        char[] longestArray = {'H', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd', '!'};
        char[] bigNumber = {'1', '1', '1'};
        char[] biggerNumber = {'1', '1', '2'};

        CustomString longStr = new CustomString(longArray);
        CustomString longerStr = new CustomString(longerArray);
        CustomString longestStr = new CustomString(longestArray);
        CustomString bigNumStr = new CustomString(bigNumber);
        CustomString biggerNumStr = new CustomString(biggerNumber);

        assertEquals(1, longerStr.compareTo(longStr));
        assertEquals(-1, longStr.compareTo(longerStr));
        assertEquals(1, longestStr.compareTo(longerStr));
        assertEquals(1, longestStr.compareTo(longStr));
        assertEquals(-1, longStr.compareTo(longestStr));
        assertEquals(0, longStr.compareTo(longStr));
        assertEquals(0, longerStr.compareTo(longerStr));
        assertEquals(0, longestStr.compareTo(longestStr));

        assertEquals(1, biggerNumStr.compareTo(bigNumStr));
        assertEquals(-1, bigNumStr.compareTo(biggerNumStr));
        assertEquals(0, bigNumStr.compareTo(bigNumStr));
        assertEquals(0, biggerNumStr.compareTo(biggerNumStr));

    }
}
