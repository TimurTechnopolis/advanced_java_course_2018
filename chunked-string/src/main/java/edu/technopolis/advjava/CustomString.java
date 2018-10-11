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
     * complimentary according to Java 6
     */
    private final int offset;
    private final int count;
    private final int chunkLength;

    /*
     * group of constructors
     */
    public CustomString(char chunks[][],int chunkLength, int offset, int count) {
        this.offset = offset;
        this.count = count;
        this.chunks = chunks;
        this.chunkLength = chunkLength;
    }

    public CustomString(String value, int chunkLength) {
        this(value, chunkLength, 0, value.length());
    }

    public CustomString(String value, int chunkLength, int offset, int count) {
        this.offset = offset;
        this.count = count;
        this.chunkLength = chunkLength;
        int amountOfChunks = this.count%chunkLength==0 ? this.count/chunkLength : this.count/chunkLength+1;
        this.chunks = new char[amountOfChunks][chunkLength];
        for (int i = 0; i < amountOfChunks; i++) {
            for (int j = 0; j < chunkLength; j++) {
                if (i*chunkLength+j < this.count) {
                    chunks[i][j] = value.charAt(i * chunkLength + j);
                }
            }
        }

    }

    public CustomString(String value) {
        this(value, 100);
    }

    @Override
    public int length() {
        return count;
    }

    @Override
    public char charAt(int index) {
        return chunks[(offset+index)/chunkLength][(offset+index)%chunkLength];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }

    public CustomString subCustomString(int start, int end) {
        return new  CustomString(chunks, this.chunkLength, offset+start, offset+end-start);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(this.count);
        for (int i = offset; i < this.count; i++) {
            str.append(chunks[i/chunkLength][i%chunkLength]);
        }
        return new String(str);
    }
}
