package edu.technopolis.advjava;

import java.io.Serializable;
import java.util.Arrays;

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
    private final int CHUNKS_LENGTH;
    private static int defaultChunksLength = 10;
    private int offset;
    private int flang;

    /*
     * todo add constructor or group of constructors
     */
    CustomString(char[][] chunks) {
        this.CHUNKS_LENGTH = chunks[0].length;
        for (char[] a : chunks){
            if (a.length != CHUNKS_LENGTH){
                throw new IllegalStateException("Чанки не фиксированной длинны");
            }
        }
        this.chunks = chunks;
        this.offset = 0;
        this.flang = CHUNKS_LENGTH - 1;
    }

    CustomString(String inputSting){
        this(inputSting, defaultChunksLength);
    }

    CustomString(String inputString, int chunksLength) {
        if (chunksLength < 1){
            throw new IndexOutOfBoundsException();
        }
        this.CHUNKS_LENGTH = chunksLength;
        this.offset = 0;
        int tmp = (inputString.length() / CHUNKS_LENGTH);
        int size = chunksLength > 1 ? tmp + 1 : tmp;
        this.chunks = new char[size][CHUNKS_LENGTH];
        int iterator = 0;
        for (int i = 0; i < chunks.length; i++) {
            for (int j = 0; j < CHUNKS_LENGTH; j++) {
                if (iterator == inputString.length()) {
                    break;
                }
                chunks[i][j] = inputString.charAt(iterator);
                this.flang = j;
                iterator++;
            }
        }
    }

    @Override
    public int length() {
        int edge = CHUNKS_LENGTH - (flang + 1);
        return chunks.length * CHUNKS_LENGTH - offset - edge;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= this.length()){
            throw new IndexOutOfBoundsException();
        }
        //todo implement charAt here
        int i = index == 0 ? index : ((index - offset) / CHUNKS_LENGTH);
        int j = (index + offset) % CHUNKS_LENGTH;
        return chunks[i][j];
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
            for (char a : chars) {
                if (a != '\u0000') {
                    stringBuilder.append(a);
                }
            }
        }
        return stringBuilder.toString();
    }

    void printChunks() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        for (char[] chars : chunks) {
            stringBuilder.append(i++).append(": ").append(Arrays.toString(chars).replaceAll("\n", "\\\\n")).append('\n');
        }
        System.out.println(stringBuilder.toString());
    }

    public int getFlang() {
        return flang;
    }

    public int getOffset() {
        return offset;
    }
}