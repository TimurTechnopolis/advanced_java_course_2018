package edu.technopolis;

import java.util.Arrays;

public class CustomString implements CharSequence {
    char[][] chunks;
    int offset;
    int count;
    int chunkSize;

    private CustomString(char[][] chunks, int offset, int count) {
        this.chunks = chunks;
        this.offset = offset;
        this.count = count;
        this.chunkSize = chunks[0].length;
    }

    public CustomString() {
        this(new char[0][0], 0, 0);
    }

    public CustomString(String string, int chunkSize) {
        if (string == null) {
            throw new NullPointerException();
        }
        this.chunks = new char[(string.length() + chunkSize - 1) / chunkSize][chunkSize];
        this.offset = 0;
        this.count = string.length();
        this.chunkSize = chunkSize;
        for (int i = 0; i < chunks.length; i++) {
            for (int j = 0; i * chunkSize + j < string.length() && j < chunkSize; j++) {
                chunks[i][j] = string.charAt(i * chunkSize + j);
            }
        }
    }

    public CustomString(String string) {
        this(string, 8);
    }

    @Override
    public int length() {
        return count;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        return chunks[(offset + index) / chunkSize][(offset + index) % chunkSize];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (start < 0 || end >= count) {
            throw new IndexOutOfBoundsException("Start/end is out of bounds");
        }
        if (start > end) {
            throw new IllegalArgumentException("Start must be more then end");
        }
        return new CustomString(chunks, offset + start, end - start);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(count);
        for (int i = offset; i < count + offset; i++) {
            string.append(chunks[i / chunkSize][i % chunkSize]);
        }
        return string.toString();
    }
}
