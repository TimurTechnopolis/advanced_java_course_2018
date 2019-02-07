package edu.technopolis.advjava;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class CustomStringTest {

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

    @Test
    public void subSequence() {
        char[] charArray = {'H', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd', '!'};

        CustomString emptyString = new CustomString();
        CustomString customString = new CustomString(charArray);
        String defaultString = new String(charArray);

        String defSubString1 = defaultString.substring(1, 10);
        String defSubString2 = defSubString1.substring(3, 8);
        String defSubString3 = defSubString2.substring(1, 2);

        CharSequence subString1 = customString.subSequence(1, 10);
        CharSequence subString2 = subString1.subSequence(3, 8);
        CharSequence subString3 = subString2.subSequence(1, 2);

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
}