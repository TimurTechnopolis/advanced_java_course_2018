package edu.technopolis.advjava;

import java.io.Serializable;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {
    private final char[][] chunks;

    /*
     * todo add complimentary fields if required
     */


    /*
     * todo add constructor or group of constructors
     */
    public CustomString(char[][] chunks){
        this.chunks = chunks;
    }

    CustomString(String inputString){
        this.chunks = new char[1][1];
    }

    @Override
    public int length() {
        //todo implement length here
        return 0;
    }

    @Override
    public char charAt(int index) {
        //todo implement charAt here
        return 0;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        //todo implement subSequence here
        return null;
    }

    @Override
    public String toString() {
        //todo fold chunks into single char array
        return new String("");
    }
}