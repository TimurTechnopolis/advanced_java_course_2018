package edu.technopolis.advjava;

import static org.junit.Assert.assertEquals;

public class SingleTest {
    @org.junit.Test
    public void subSequencesTest() {
        CustomString str1 = new CustomString("012345678901234567890123456789");
        CustomString str2 = str1.subSequence(0, 20);
        assertEquals("01234567890123456789", str2.toString());
        assertEquals("", str2.subSequence(5, 5).toString());
        CustomString str3 = str2.subSequence(9, 20);
        assertEquals("90123456789", str3.toString());
        assertEquals(11, str3.length());
        CustomString str4 = str3.subSequence(0,1);
        assertEquals("9", str4.toString());
        assertEquals('9', str4.charAt(0));
        CustomString str5 = str3.subSequence(5,6);
        assertEquals("4", str5.toString());
        CustomString str6 = str3.subSequence(9,10);
        assertEquals("8", str6.toString());
        assertEquals(1, str6.length());
        assertEquals("", str6.subSequence(0, 0).toString());
        assertEquals(0, str6.subSequence(0, 0).length());
    }
}