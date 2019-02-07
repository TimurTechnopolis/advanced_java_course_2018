package edu.technopolis.advjava;

import java.io.Serializable;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {
    private final char[][] chunks;

     private final int offset;
     private final int count;
     private final int chunkLength;

    /*
     * todo add complimentary fields if required
     */



    /*
     * todo add constructor or group of constructors
     */

    public CustomString(char[][] chunks, int chuckLength) {
        this.chunks = chunks;
        this.chunkLength = chuckLength;
        this.offset = 0;
        this.count = chunks.length;

    }

    public CustomString(char[][] chunks, int chuckLength, int offset) {
        this.chunks = chunks;
        this.chunkLength = chuckLength;
        this.offset = offset;
        this.count = chunks.length;

    }

    public CustomString(char[][] chunks, int chuckLength, int offset, int count) {
        this.chunks = chunks;
        this.chunkLength = chuckLength;
        this.offset = offset;
        this.count = count;

    }


    @Override
    public int length() {
        return count;
    }

    @Override
    public char charAt(int index) {
        if (index >= 0 && index < count) {
            int chunkLine = (offset + index) / chunkLength;
            int chunkColumn = (offset + index) % chunkLength;
            return chunks[chunkLine][chunkColumn];
        } else {
            throw new IndexOutOfBoundsException("Exception. Out of bounds in " + index);
        }
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        int chunkStart = start / chunkLength;
        int chunkEnd = end % chunkLength;
        char[][] subChunks = new char[chunkEnd - chunkStart][];
        for (int i = chunkStart; i < chunkEnd; i++ ) {
            subChunks[i - chunkStart] = chunks[i];

        }
        return new CustomString(subChunks, chunkLength, start - chunkLength*chunkStart,end - start);

    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i =0; i < count; i++) {
            for (int j = offset; j< chunkLength; j++) {
                s.append(chunks[i][j]);
            }
        }
        return s.toString();
    }
}
