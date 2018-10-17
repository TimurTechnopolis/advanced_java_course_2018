package edu.technopolis.advjava;

import static org.junit.Assert.assertEquals;

public class SingleTest {
    @org.junit.Test
    public void subSequencesTest() {
        CustomString str1 = new CustomString("012345678901234567890123456789");
        CustomString str2 = str1.subSequence(0, 20);
        assertEquals(str2.toString(), "01234567890123456789");
        assertEquals(str2.subSequence(5,5).toString(), "");
        CustomString str3 = str2.subSequence(10, 20);
        assertEquals(str3.toString(), "0123456789");
        CustomString str4 = str3.subSequence(0,1);
        assertEquals(str4.toString(), "0");
        CustomString str5 = str3.subSequence(5,6);
        assertEquals(str5.toString(), "5");
        CustomString str6 = str3.subSequence(9,10);
        assertEquals(str6.toString(), "9");
        assertEquals(str6.subSequence(0,0).toString(), "");
    }
}