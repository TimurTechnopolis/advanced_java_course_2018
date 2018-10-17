package edu.technopolis.advjava;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {

    private static final int SIZE_CHUNK = 16;

    private char[][] chunks;
    private int startOffset;
    private int length;

    public CustomString() {
    }

    public CustomString(char[] string) {
        this.startOffset = 0;
        this.length = string.length;
        int chunkQuantity;
        if (length % SIZE_CHUNK == 0) {
            chunkQuantity = length / SIZE_CHUNK;
        } else {
            chunkQuantity = length / SIZE_CHUNK + 1;
        }
        chunks = new char[chunkQuantity][SIZE_CHUNK];
        for (int i = 0; i < chunkQuantity - 1; i++) {
            chunks[i] = Arrays.copyOfRange(string, i * SIZE_CHUNK, i * SIZE_CHUNK + SIZE_CHUNK);
        }
        chunks[chunkQuantity - 1] = Arrays.copyOfRange(
                string,
                (chunkQuantity - 1) * SIZE_CHUNK,
                (chunkQuantity - 1) * SIZE_CHUNK + SIZE_CHUNK - (length - SIZE_CHUNK * chunkQuantity)
        );
    }

    private CustomString(char[][] string, int startOffset, int length) {
        this.chunks = string;
        this.startOffset = startOffset;
        this.length = length;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) {
        if (index >= length || index < 0) {
            throw new StringIndexOutOfBoundsException(index);
        }

        int currentIndex = index + startOffset;
        int line = currentIndex / SIZE_CHUNK;
        int column = currentIndex % SIZE_CHUNK;
        return chunks[line][column];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (start < 0 || start > end || end > length) {
            throw new StringIndexOutOfBoundsException(
                    "start " + start + ", end " + end + ", length " + length);
        }

        if (start == 0 && end == length) {
            return this;
        }

        char[][] subChunks;
        int currentStart = start + startOffset;
        int currentEnd = end + startOffset - 1;
        int length = currentEnd - currentStart + 1;
        int chunkQuantity = currentEnd / SIZE_CHUNK - currentStart / SIZE_CHUNK + 1;
        int startOffset = currentStart % SIZE_CHUNK;
        int startChunk = currentStart / SIZE_CHUNK;
        subChunks = new char[chunkQuantity][];
        int i = 0;
        while (i <= chunkQuantity - 1) {
            subChunks[i] = chunks[startChunk];
            startChunk++;
            i++;
        }

        return new CustomString(subChunks, startOffset, length);
    }

    @Override
    public String toString() {
        char[] string = new char[length];
        int line, column;
        for (int i = 0; i < length; i++) {
            line = (i + startOffset) / SIZE_CHUNK;
            column = (i + startOffset) % SIZE_CHUNK;
            string[i] = chunks[line][column];
        }
        return new String(string);
    }
}
