package edu.technopolis.advjava;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {

    private final int CHUNK_SIZE = 8;

    private final char[][] chunks;
    private final int offset;
    private final int length;


    public CustomString(String string) {
        char[] charSequence = string.toCharArray();
        this.length = charSequence.length;
        this.chunks = new char[Math.floorDiv(length, CHUNK_SIZE) + 1][CHUNK_SIZE];
        this.offset = 0;

        int chunk = 0;
        int charIndex = 0;
        for (char c: charSequence) {
            this.chunks[chunk][charIndex] = c;

            if (++charIndex == CHUNK_SIZE) {
                charIndex = 0;
                chunk++;
            }
        }
    }

    private CustomString(char[][] chunks, int offset, int length) {
        this.chunks = chunks;
        this.offset = offset;
        this.length = length;
    }


    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) {
        int chunkIndex = (offset + index) / CHUNK_SIZE;
        int charIndex = (offset + index) % CHUNK_SIZE;
        return chunks[chunkIndex][charIndex];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        int startChunk = getChunkIndex(start);
        int startCharIndex = getCharIndexInChunk(start);

        int endChunk = getChunkIndex(end);

        char[][] newChunks;

        newChunks = Arrays.copyOfRange(chunks, startChunk, endChunk);
        return new CustomString(newChunks, startCharIndex, end - start);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] chunk: chunks) {
            sb.append(chunk);
        }
        return sb.substring(offset, offset + length);
    }

    private int getChunkIndex(int index) {
        return (offset + index) / CHUNK_SIZE;
    }

    private int getCharIndexInChunk(int index) {
        return (offset + index) % CHUNK_SIZE;
    }
}