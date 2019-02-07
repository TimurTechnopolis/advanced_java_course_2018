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
     * complimentary fields according to Java 6
     */
    private final int offset;
    private final int count;
    private final int chunkLength;

    /*
     * group of constructors
     */
    public CustomString(char chunks[][],int chunkLength, int offset, int count) {
        this.offset = offset % chunkLength;
        this.count = count;
        this.chunkLength = chunkLength;
        int firstChunk = offset/chunkLength;
        int chunkAmount = (offset+count)/chunkLength > 0 ? (offset+count)/chunkLength : 1;
        this.chunks = new char[chunkAmount][chunkLength];
        for (int i = 0; i < chunkAmount; i++) {
            for (int j = 0; j < chunkLength; j++) {
                if (i*chunkLength+j-this.offset < this.count) {
                    this.chunks[i][j] = chunks[firstChunk+i][j];
                }
            }
        }
    }

    public CustomString(String value, int chunkLength) {
        if (chunkLength < 1) {
            throw new IndexOutOfBoundsException("Out of bounds!");
        }
        if (value == null) { throw new NullPointerException("String is null!"); }
        this.offset = 0;
        this.count = value.length();
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
        if (index > count || index < 0) { throw new IndexOutOfBoundsException("Out of bounds!"); }
        return chunks[(offset+index)/chunkLength][(offset+index)%chunkLength];
    }

    @Override
    public CustomString subSequence(int start, int end) {
        //todo implement subSequence here
        if (start < 0 || end < start || end > this.count) { throw new IndexOutOfBoundsException("Out of bounds!"); }

        return new  CustomString(chunks, this.chunkLength, offset+start, offset+end-start);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(this.count);
        for (int i = this.offset; i < this.count+this.offset; i++) {
            str.append(chunks[i/chunkLength][i%chunkLength]);
        }
        return new String(str);
    }
}
