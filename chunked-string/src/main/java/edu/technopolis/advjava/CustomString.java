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
    private static final int CHUNKS_LENGTH = 10;
    private int offset;
    private int flang;

    /*
     * todo add constructor or group of constructors
     */
    public CustomString(char[][] chunks) {
        this.chunks = chunks;
        this.offset = 0;
        this.flang = CHUNKS_LENGTH - 1;
    }

    CustomString(String inputString) {
        this.offset = 0;
        chunks = new char[(inputString.length() / 10) + 1][CHUNKS_LENGTH];
        int iterator = 0;
        for (int i = 0; i < chunks.length; i++) {
            for (int j = 0; j < CHUNKS_LENGTH; j++) {
                if (iterator == inputString.length()) {
                    break;
                }
                chunks[i][j] = inputString.charAt(iterator);
                this.offset = j;
                iterator++;
            }
        }
    }

    @Override
    public int length() {
        return chunks.length * CHUNKS_LENGTH - (offset + 1) - (CHUNKS_LENGTH - flang + 1);
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
        StringBuilder stringBuilder = new StringBuilder();
        for (char[] chars : chunks) {
            for (char a : chars){
                if (a != '\u0000'){
                    stringBuilder.append(a);
                }
            }
        }
        return stringBuilder.toString();
    }

    void printChunks() {
        for (char[] chars : chunks){
            System.out.println(chars);
        }
    }

    public int getFlang() {
        return flang;
    }

    public int getOffset() {
        return offset;
    }
}