package com.company;

import java.io.Serializable;
import java.lang.CharSequence;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {
    private final char[][] chunks;
    private int offsetRight;
    private int offsetLeft;
    private int length;
    private int chunkLength;

    CustomString() {
        offsetLeft = 0;
        offsetRight = 0;
        length = 0;
        chunkLength = 0;
        chunks = new char[1][1];
    }

    CustomString(String str) {
        offsetRight = 0;
        offsetLeft = 0;
        int n = 0;
        chunkLength = (int) Math.sqrt(str.length());
        if (str.length() % chunkLength != 0) {
            n = str.length() / chunkLength + 1;
        } else {
            n = str.length() / chunkLength;
        }
        chunks = new char[n][chunkLength];
        length = str.length();

        for (int i = 0; i < n; i++) {
            if (i < (n - 1)) {
                for (int j = 0; j < chunkLength; j++) {
                    chunks[i][j] = str.charAt(i * chunkLength + j);
                }
            } else {
                for (int j = 0; j < (str.length() - (n - 1) * chunkLength); j++) {
                    chunks[i][j] = str.charAt(i * chunkLength + j);
                }
            }
        }
    }

    CustomString(char arr[][], int offLeft, int offRight, int startChunk, int endChunk) {
        chunkLength = arr[0].length;
        chunks = new char[(endChunk - startChunk + 1)][chunkLength];
        System.arraycopy(arr, startChunk, chunks, 0, (endChunk - startChunk + 1));
        offsetLeft = offLeft;
        offsetRight = offRight;
        int len = endChunk - startChunk + 1;
        length = chunkLength * len - offLeft - (chunkLength - offRight - 1);
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index > (length - 1)) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            int tmp = 0;
            index = index + offsetLeft;
            if (index >= chunkLength) {
                tmp = index / chunkLength;
            }
            return chunks[tmp][index % chunkLength];
        }
    }

    @Override
    public CustomString subSequence(int start, int end) throws ArrayIndexOutOfBoundsException {
        if (start < 0 || start > (length - 1) || end < 0 || end > (length - 1)) {
            throw new ArrayIndexOutOfBoundsException();
        } else if (start > end) {
            return new CustomString();
        } else {
            int startChunk = start / chunkLength;
            int endChunk = end / chunkLength;
            int offLeft = start % chunkLength;
            int offRight = end % chunkLength - 1;
            return new CustomString(chunks, offLeft, offRight, startChunk, endChunk);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chunks.length; i++) {
            builder.append(chunks[i]);
        }
        return builder.toString();
    }
}
