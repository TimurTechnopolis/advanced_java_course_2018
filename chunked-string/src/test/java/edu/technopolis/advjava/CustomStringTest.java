package edu.technopolis.advjava;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomStringTest {

    @Test
    public void customStringTest() {
        String value = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

        CustomString string = new CustomString(value);

        assertEquals(value, string.toString());
    }

    @Test
    public void substringTest() {
        String value = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

        CharSequence string = new CustomString(value).subSequence(6, 16);

        assertEquals(value.substring(6, 16), string.toString());
    }
}
