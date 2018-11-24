package edu.technopolis;

import java.util.Arrays;

public class CustomString implements CharSequence {
    public static final int DEFAULT_CHUCK_SIZE = 1000;
    private final char[][] chunks;
    private final int offset;                                  //начало строки (смещение от начала)
    private final int count;                                   //число символов в строке
    private final int chunkSize;                               //число символов в одном chunk

    private CustomString(char[][] chunks, int offset, int count) {
        this.chunks = chunks;
        this.offset = offset;
        this.count = count;
        this.chunkSize = chunks[0].length;
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
        this(string, DEFAULT_CHUCK_SIZE);
    }

    @Override
    public int length() {
        return count;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds. Index must be in [0," + (count - 1) + "]");
        }
        return chunks[(offset + index) / chunkSize][(offset + index) % chunkSize];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (start < 0 || end >= count || start > end) {
            throw new IndexOutOfBoundsException("Start/end is out of bounds. Index must be in [0," + (count - 1) + "]");
        }
        char[][] subChunks = Arrays.copyOfRange(chunks, (start + offset) / chunkSize, (end + offset) / chunkSize + 1);
        return new CustomString(subChunks, offset + start, end - start + 1);
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
