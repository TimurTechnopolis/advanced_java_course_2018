package edu.technopolis.advjava.demo;


import java.io.Serializable;
import java.util.Arrays;

import com.sun.xml.internal.fastinfoset.util.CharArray;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {
    private final char[][] chunks;
    private final int length;
    private final int offset;
    private int DEFAULT_CHUNK_SIZE;


    private CustomString(char[][] chunks, int offset, int length) {
        this.chunks = chunks;
        this.offset = offset;
        this.length = length;
    }
    public CustomString(String string,int chunkSize) {
        DEFAULT_CHUNK_SIZE = chunkSize;
        char[] charSequence = string.toCharArray();
        this.length = charSequence.length;
        this.chunks = new char[Math.floorDiv(length, DEFAULT_CHUNK_SIZE) + 1][DEFAULT_CHUNK_SIZE];
        this.offset = 0;
        int chunk = 0;
        int charIndex = 0;
        for (char c: charSequence) {
            if (charIndex == DEFAULT_CHUNK_SIZE) {
                charIndex = 0;
                chunk++;
            }
            this.chunks[chunk][charIndex] = c;
            charIndex++;
        }
    }

    public CustomString(String string) {
        this(string,10000);
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) {
        int chunkIndex = (offset + index) / DEFAULT_CHUNK_SIZE;
        int charIndex = (offset + index) % DEFAULT_CHUNK_SIZE;
        return chunks[chunkIndex][charIndex];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        int startChunk = (offset + start) / DEFAULT_CHUNK_SIZE;
        int startCharIndex = (offset + start) % DEFAULT_CHUNK_SIZE;
        int endChunk = (offset + end) / DEFAULT_CHUNK_SIZE;
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

    public static void main(String[] args) {
        String st = "Java is the best";
        System.out.println(new CustomString(st,8).subSequence(5,16));
        System.out.println(new CustomString(st));
        System.out.println(new CustomString(st,1));

    }

}