package edu.technopolis.advjava;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomStringTest {

    @Test
    public void simpleTest() {
        String string = "Just a String.";
        CustomString cs = new CustomString(string);
        assertEquals(14, cs.length());
        assertEquals('J', cs.charAt(0));
        assertEquals('S', cs.charAt(cs.length() / 2));
        assertEquals('.', cs.charAt(cs.length() - 1));
        assertEquals(string, cs.toString());
        CustomString cs1 = cs.subSequence(0, cs.length() / 2);
        assertEquals(string.substring(0, cs.length() / 2), cs1.toString());
    }

    @Test
    public void chunkTest() {
        StringBuilder sb = new StringBuilder();
        for (char i = 0; i < 129; i++) {
            sb.append(i);
        }
        CustomString cs = new CustomString(sb.toString());
        for (char i = 0; i < 129; i++) {
            assertEquals(i, cs.charAt(i));
        }
        assertEquals(0, cs.subSequence(128, 128).length());
        assertEquals(128, cs.subSequence(128, 129).charAt(0));
    }

    @Test
    public void textTest() {
        CustomString cs = new CustomString("Наука? Чепуха! В этой ситуации все одинаково беспомощны. Должен вам сказать, что мы вовсе\n" +
                "не хотим завоевывать никакой Космос. Мы хотим расширить Землю до его границ. Мы не знаем,\n" +
                "что делать с иными мирами. Нам не нужно других миров, нам нужно зеркало. Мы бьемся\n" +
                "над контактом и никогда не найдем его. Мы в глупом положении человека, рвущегося к цели,\n" +
                "которая ему не нужна, которой он боится. Человеку нужен человек!");
        assertEquals(416, cs.length());
        CustomString cs1 = cs.subSequence(127, 166);
        assertEquals(39, cs1.length());
        assertEquals("Мы хотим расширить Землю до его границ.", cs1.toString());
    }
}
