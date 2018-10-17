package edu.technopolis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/**
 * User: victoria.shepard
 * Date: 17/10/2018
 * Time: 08:52
 */
class CustomStringTest {

    @Test
    @DisplayName("Check custom `toString`")
    void customToStringTest() {
        String testString = randomAlphabetic(100, 300);
        CustomString customString = new CustomString(testString);

        Assertions.assertEquals(testString, customString.toString());
    }

    @Test
    @DisplayName("Check first chunk")
    void firstChunkTest() {
        int chunkCustomLength = 10;
        String testString = randomAlphabetic(100, 300);
        String actual = Arrays.toString(testString.substring(0, chunkCustomLength).toCharArray());
        CustomString customString = new CustomString(testString, chunkCustomLength);

        Assertions.assertEquals(actual, customString.getChunk(0));
    }

    @Test
    @DisplayName("Check custom `charAt`")
    void charAtTest() {
        String testString = randomAlphabetic(100, 300);
        CustomString customString = new CustomString(testString);

        Assertions.assertEquals(testString.toCharArray()[3] , customString.charAt(3));
    }


    @Test
    @DisplayName("Check custom `length`")
    void lengthTest() {
        String testString = randomAlphabetic(100, 300);
        CustomString customString = new CustomString(testString);

        Assertions.assertEquals(testString.length() , customString.length());
    }

    @Test
    @DisplayName("Check custom `subSequence`")
    void subSequenceTest() {
        String testString = randomAlphabetic(100, 300);
        CustomString customString = new CustomString(testString);

        Assertions.assertEquals(testString.substring(10, 20) , customString.subSequence(10, 20));
    }

}
