package edu.technopolis.advjava;

import java.io.Serializable;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */

public class CustomString implements CharSequence, Serializable {

    private final char[][] chunks;

    //Amount of characters for reading
    private int length;
    //Amount of characters in chunks
    private int characterAmount;
    //Offset of first element in chunks
    private int offset;
    //Amount of chunks
    private int chunkAmount;
    //Size of one chunk
    private int chunkSize;
    //Min amount for chunks to make a new CustomString (in subSequence)
    private int minCopyChunkAmount;
    //Max number of characters in one chunk
    private int maxChunkSize = 1024;

    //Init with empty string
    public CustomString() {
        this("");
    }

    public void setMaxChunkSize(int maxChunkSize) {
        this.maxChunkSize = maxChunkSize;
    }

    //Additional functions
    private int getSqrtLength(int l) {
        return (int) Math.ceil(Math.sqrt(l));
    }

    private void setChunkParameters(int strLength) {
        if (strLength <= maxChunkSize) {
            chunkSize = strLength;
            chunkAmount = 1;
        } else {
            if (strLength / (128 * maxChunkSize) > 1) {
                chunkSize = getSqrtLength(strLength) * 2;
            } else {
                chunkSize = maxChunkSize;
            }
            chooseChunkAmount(strLength);
            chooseMinCopyChunkAmount();
        }
    }

    private void chooseMinCopyChunkAmount() {
        minCopyChunkAmount = chunkAmount / 4 * 3;
    }

    private void chooseChunkAmount(int strLength) {
        if (strLength % chunkSize == 0) {
            chunkAmount = strLength / chunkSize;
        } else {
            chunkAmount = strLength / chunkSize + 1;
        }
    }

    public CustomString(CharSequence chars) {
        length = chars.length();

        characterAmount = length;
        setChunkParameters(length);
        chunks = new char[chunkAmount][chunkSize];

        for (int i = 0; i < length; i++) {
            chunks[i / chunkSize][i % chunkSize] = chars.charAt(i);
        }
    }

    private CustomString(char[][] chunksArray, int nOffset, int nLength) {
        chunks = chunksArray;
        length = nLength;
        offset = nOffset;
        chunkAmount = chunksArray.length;
        chunkSize = chunksArray[0].length;
        characterAmount = length;
        characterAmount = chunkAmount * chunkSize;
        chooseMinCopyChunkAmount();
    }

    private CustomString(char[] oneChunk, int nOffset, int nLength) {
        int sqrStringLength = getSqrtLength(nLength);

        offset = nOffset;
        length = nLength;
        chunkSize = sqrStringLength;
        chooseChunkAmount(length);
        characterAmount = oneChunk.length;
        chunks = new char[chunkAmount][chunkSize];

        for (int i = offset; i < offset + length; i++) {
            chunks[i / chunkSize][i % chunkSize] = oneChunk[offset + i];
        }
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) throws IndexOutOfBoundsException {
        int chunkNumber = (offset + index) / chunkSize;
        int indexAtChunk = (offset + index) % chunkSize;
        if (chunkNumber < 0 || chunkNumber >= chunkAmount || indexAtChunk < 0) {
            throw new IndexOutOfBoundsException();
        }
        return chunks[chunkNumber][indexAtChunk];
    }

    @Override
    public CustomString subSequence(int start, int end) throws IndexOutOfBoundsException {
        if (start >= 0 && start <= end && end <= characterAmount) {
            if (start == end) {
                return new CustomString();
            }
            int firstChunkNumber = start / chunkSize;
            int lastChunkNumber = (end - 1) / chunkSize;
            int newOffset = 0;
            int newLength = end - start;

            if (firstChunkNumber == lastChunkNumber) {

                char newChunk[] = new char[chunkSize];
                System.arraycopy(chunks[firstChunkNumber], offset, newChunk, 0, end - start);
                return new CustomString(newChunk, newOffset, newLength);

            } else if (firstChunkNumber + minCopyChunkAmount < lastChunkNumber) {

                int copyChunksAmount = lastChunkNumber - firstChunkNumber + 1;
                char newChunks[][] = new char[copyChunksAmount][chunkSize];
                for (int i = 0; i < end - start; i++) {
                    newChunks[i / chunkSize][i % chunkSize] = chunks[(start + i) / chunkSize][(start + i) % chunkSize];
                }

                return new CustomString(newChunks, newOffset, newLength);
            } else {
                offset = start;
                length = end - start + 1;

                return this;
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            strBuilder.append(charAt(i));
        }

        return strBuilder.toString();
    }

}
