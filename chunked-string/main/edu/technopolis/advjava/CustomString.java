package edu.technopolis.advjava;

import java.io.Serializable;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {
    private final char[][] chunks;
    private final int chunkSize;
    private final int length;
    private final int offset;

    public CustomString(String str) throws NullPointerException{
        if (str == null) throw new NullPointerException();
        this.length = str.length();
        this.offset = 0;
        this.chunkSize = (int) Math.sqrt(str.length());
        this.chunks = new char[(this.length % this.chunkSize) == 0
                ? (this.length/this.chunkSize) : (this.length/this.chunkSize + 1)][this.chunkSize];
        for (int i = 0; i < str.length(); i++) {
            chunks[i/chunkSize][i%chunkSize] = str.charAt(i);
        }
    }

    private CustomString(char[][] chunks, int start, int length)
            throws NullPointerException, ArrayIndexOutOfBoundsException {
        if (chunks == null) throw new NullPointerException();
        this.chunks = chunks;
        this.chunkSize = chunks[0].length;
        this.offset = (start % chunkSize);
        this.length = length;
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public char charAt(int index) throws IndexOutOfBoundsException{
        return chunks[(offset + index)/chunkSize][(offset + index) % chunkSize];
    }

    @Override
    public CustomString subSequence(int start, int end) throws IndexOutOfBoundsException{
        if (start < 0 || end > this.length) throw new ArrayIndexOutOfBoundsException();
        char subChunks[][] = new char[(((end - start) % chunkSize == 0) && ((end - start) != 0)) ? ((end - start) / chunkSize) : ((end - start) / chunkSize + 1)][chunkSize];
        for (int i = 0, k = ((start + offset) / chunkSize); i < subChunks.length; i++, k++) {
            subChunks[i] = chunks[k];
        }
        return new CustomString(subChunks, start + offset, end - start);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = offset; i < this.length + offset; i++) {
            builder.append(chunks[i/chunkSize][i%chunkSize]);
        }
        return builder.toString();
    }
}