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
    //if length becomes less than lengthForCopy, make a new copy of string instead of changing offset
    private int lengthForCopy;
    //Max number of characters in one chunk
    private int maxChunkSize = 1024;

    //Init with empty string
    public CustomString() {
        this("");
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
        characterAmount = chunkAmount * chunkSize;
        chooseLengthForCopy();
    }

    private CustomString(char[] oneChunk, int nOffset, int nLength) {
        offset = nOffset;
        length = nLength;
        characterAmount = oneChunk.length;
        setChunkParameters(length);
        chunks = new char[chunkAmount][chunkSize];

        if (chunkAmount == 1) {
            System.arraycopy(oneChunk, 0, chunks[0], 0, nLength);
        } else {
            for (int i = offset; i < offset + length; i++) {
                chunks[i / chunkSize][i % chunkSize] = oneChunk[offset + i];
            }
        }
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
        }
        chooseLengthForCopy();
    }

    private void chooseLengthForCopy() {
        lengthForCopy = characterAmount * 3 / 4;
    }

    private void chooseChunkAmount(int strLength) {
        if (strLength % chunkSize == 0) {
            chunkAmount = strLength / chunkSize;
        } else {
            chunkAmount = strLength / chunkSize + 1;
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

            } else if (newLength < lengthForCopy) {

                int copyChunksAmount = lastChunkNumber - firstChunkNumber + 1;
                char newChunks[][] = new char[copyChunksAmount][chunkSize];
                for (int i = 0; i < end - start; i++) {
                    newChunks[i / chunkSize][i % chunkSize] = chunks[(start + i) / chunkSize][(start + i) % chunkSize];
                }

                return new CustomString(newChunks, newOffset, newLength);
            } else {
                offset = start;
                length = end - start;

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
