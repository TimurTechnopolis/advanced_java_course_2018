package edu.technopolis.advjava;

import java.io.Serializable;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {
    private static final int CHUNK_SIZE = 128;
    private final char[][] chunks;
    private final int chunkCount;
    private final int endChunkIndex;
    private final int startChunkIndex;

    public CustomString() {
        startChunkIndex = 0;
        endChunkIndex = 0;
        chunkCount = 0;
        chunks = new char[0][0];
    }

    public CustomString(CharSequence charSequence) {
        startChunkIndex = 0;
        if (charSequence.length() == 0)  {
            chunkCount = 0;
            endChunkIndex = 0;
            chunks = new char[0][0];
        } else {
            if (charSequence.length() % CHUNK_SIZE == 0) endChunkIndex = CHUNK_SIZE - 1;
            else endChunkIndex = charSequence.length() % CHUNK_SIZE - 1;

            if (endChunkIndex == CHUNK_SIZE - 1) chunkCount = charSequence.length() / CHUNK_SIZE;
            else chunkCount = charSequence.length() / CHUNK_SIZE + 1;

            chunks = new char[chunkCount][CHUNK_SIZE];
            for (int i = 0; i < chunkCount; i++) {
                for (int j = 0; j < CHUNK_SIZE; j++) {
                    if (i == chunkCount - 1 && j == endChunkIndex + 1) break;
                    chunks[i][j] = charSequence.charAt(i * CHUNK_SIZE + j);
                }
            }
        }
    }

    private CustomString(CustomString customString, int start, int end) {
        startChunkIndex = (customString.startChunkIndex + start) % CHUNK_SIZE;
        int startChunkOffset = (customString.startChunkIndex + start) / CHUNK_SIZE;
        endChunkIndex = (customString.startChunkIndex + end - 1) % CHUNK_SIZE;
        if (endChunkIndex == CHUNK_SIZE - 1) chunkCount = ((end - start + startChunkIndex)) / CHUNK_SIZE;
        else chunkCount = ((end - start + startChunkIndex)) / CHUNK_SIZE + 1;
        int endChunkOffset = customString.chunkCount - chunkCount - startChunkOffset;

        chunks = new char[chunkCount][CHUNK_SIZE];
        for (int i = startChunkOffset; i < customString.chunkCount - endChunkOffset; i++) {
            chunks[i - startChunkOffset] = customString.chunks[i];
        }
    }


    @Override
    public int length() {
        if (chunkCount == 0) return 0;
        return chunkCount * CHUNK_SIZE - startChunkIndex - CHUNK_SIZE + 1 + endChunkIndex;
    }

    @Override
    public char charAt(int index) {
        int chunkIndex;
        if (endChunkIndex == CHUNK_SIZE - 1) chunkIndex = (index + startChunkIndex) / CHUNK_SIZE;
        else chunkIndex = (index + startChunkIndex) / CHUNK_SIZE;
        return chunks[chunkIndex][(index + startChunkIndex) % CHUNK_SIZE];
    }

    @Override
    public CustomString subSequence(int start, int end) {
        if (start < 0 || end > length()) throw new IndexOutOfBoundsException();
        if (start == end) return new CustomString();
        return new CustomString(this, start, end);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chunkCount; i++) {
            for (int j = 0; j < CHUNK_SIZE; j++) {
                if (i == 0 && j == 0) j = startChunkIndex;
                stringBuilder.append(chunks[i][j]);
                if (i == chunkCount - 1 && j == endChunkIndex) break;
            }
        }
        return stringBuilder.toString();
    }
}
