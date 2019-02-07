package edu.technopolis.advjava;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class CustomStringTest {

    private static char[][] chunks(String... args) {
        return Arrays.stream(args)
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    @Test
    public void test1() {
        var full = new CustomString("Война и мир. Том 1. Глава 1.");
        var chapter = full.subSequence(13, full.length());
        var section = full.subSequence(20, full.length());
        assertEquals(full.toString(), "Война и мир. Том 1. Глава 1.");
        assertEquals(chapter.toString(), "Том 1. Глава 1.");
        assertEquals(section.toString(), "Глава 1.");

        assertSame(full.getChunks()[2], section.getChunks()[0]);

        var chunks1 = chunks("Война и мир. ", "Том 1. ", "Глава 1.");
        var chunks2 = chunks("Том 1. Глава 1.");
        var chunks3 = chunks("Глава 1.");
        assertArrayEquals(full.getChunks(), chunks1);
        assertArrayEquals(chapter.getChunks(), chunks2);
        assertArrayEquals(section.getChunks(), chunks3);
    }

    @Test
    public void test2() {
        var str1 = new CustomString("hellothere");
        var str2 = str1.subSequence(5, 8);
        assertEquals(str1.toString(), "hellothere");
        assertEquals(str2.toString(), "the");

        var chunks1 = chunks("hello", "the", "re");
        var chunks2 = chunks("the");
        assertArrayEquals(str1.getChunks(), chunks1);
        assertArrayEquals(str2.getChunks(), chunks2);

        assertEquals(str1.getChunks()[1], str2.getChunks()[0]);

        var str3 = str1.subSequence(0, 3);
        assertEquals(str1.toString(), "hellothere");
        assertEquals(str3.toString(), "hel");

        var chunks12 = chunks("hel", "lo", "the", "re");
        var chunks3 = chunks("hel");
        assertArrayEquals(str1.getChunks(), chunks12);
        assertArrayEquals(str3.getChunks(), chunks3);

        assertEquals(str1.charAt(0), 'h');
        assertEquals(str1.charAt(5), 't');
        assertEquals(str3.charAt(1), 'e');
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void test3() {
        var str1 = new CustomString("hellothere");
        str1.subSequence(0, 100);
    }

}
