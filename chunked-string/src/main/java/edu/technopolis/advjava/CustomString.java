package edu.technopolis.advjava;

import java.io.Serializable;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */

public class CustomString implements CharSequence, Serializable {

    private char[][] chunks;

    private static final int CHUNK_SIZE = 512;

    private final int size;

    private final int offset;

    public CustomString(String s) {
        size = s.length();
        offset = 0;
        chunks = new char[(size / CHUNK_SIZE) + 1][];
        for (int i = 0; i < chunks.length; i++) {
            chunks[i] = new char[CHUNK_SIZE];
        }
        for (int i = 0; i < size; i++) {
            chunks[i / CHUNK_SIZE][i % CHUNK_SIZE] = s.charAt(i);
        }
    }

    private CustomString(char[][] arr, int offset, int length) {
        this.size = length;
        int startingChunk = offset / CHUNK_SIZE;
        this.offset = offset % CHUNK_SIZE;
        int endingChunk = (offset + length) / CHUNK_SIZE;
        this.chunks = new char[endingChunk - startingChunk + 1][];
        for (int i = 0; i < chunks.length; i++) {
            chunks[i] = arr[startingChunk + i];
        }

    }

    @Override
    public int length() {
        return size;
    }

    @Override
    public char charAt(int index) {
        return chunks[offset + index / CHUNK_SIZE][offset + index % CHUNK_SIZE];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return new CustomString(this.chunks, start, end - start);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = offset; i < size; i++) {
            stringBuilder.append(chunks[i / CHUNK_SIZE][i % CHUNK_SIZE]);
        }
        return stringBuilder.toString();
    }
}
