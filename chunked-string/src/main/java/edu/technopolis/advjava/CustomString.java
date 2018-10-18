package edu.technopolis.advjava;

import java.io.Serializable;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {

    //Max number of characters in one chunk
    private final int MAX_CHUNK_SIZE = 512;
    //Max amount of chunks for copying, otherwise use offset
    private final int MAX_CHUNKS_FOR_COPY = 32;

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

    //Init with empty string
    public CustomString() {
        this("");
    }

    public CustomString(String str) {
        length = str.length();
        if (length == 0) {
            chunks = new char[0][0];
            zeroLengthInit();
            return;
        }

        //If sqrt(length) < MAX_CHUNK_SIZE, use chunks[sqrt(length)][sqrt(length)]
        //Else -- use chunks[chunkAmount][MAX_CHUNK_SIZE]
        int sqrStringLength = getSqrtLength(length);
        characterAmount = length;
        chooseChunkSize(sqrStringLength);
        chooseChunkAmount();
        chunks = new char[chunkAmount][chunkSize];

        for (int i = 0; i < str.length(); i++) {
            chunks[i / chunkSize][i % chunkSize] = str.charAt(i);
        }
    }

    //Same as CustomString(String str), but with CharSequence as parameter
    public CustomString(CharSequence chars) {
        length = chars.length();
        if (length == 0) {
            chunks = new char[0][0];
            zeroLengthInit();
            return;
        }

        characterAmount = length;
        int sqrStringLength = getSqrtLength(length);
        chooseChunkSize(sqrStringLength);
        chooseChunkAmount();
        chunks = new char[chunkAmount][chunkSize];

        for (int i = 0; i < length; i++) {
            chunks[i / chunkSize][i % chunkSize] = chars.charAt(i);
        }
    }

    //Create new CustomString from chunksArray with custom Offset and Length
    private CustomString(char[][] chunksArray, int nOffset, int nLength) {
        chunks = chunksArray;
        offset = nOffset;
        length = nLength;
        chunkAmount = chunks.length;
        chunkSize = chunks[0].length;
        characterAmount = chunkAmount * chunkSize;
    }

    //One-dimensional array as parameter + custom offset and length
    private CustomString(char[] oneChunk, int nOffset, int nLength) {
        int sqrStringLength = getSqrtLength(nLength);

        offset = nOffset;
        length = nLength;
        chunkSize = sqrStringLength;
        chooseChunkAmount();
        characterAmount = oneChunk.length;
        chunks = new char[chunkAmount][chunkSize];

        for (int i = 0; i < length; i++) {
            chunks[i / chunkSize][i % chunkSize] = oneChunk[offset + i];
        }
    }

    //Additional functions
    private int getSqrtLength(int l) {
        return (int) Math.ceil(Math.sqrt(l));
    }

    private void zeroLengthInit() {
        length = 0;
        offset = 0;
        chunkAmount = 0;
        chunkSize = 0;
        characterAmount = 0;
    }

    private void chooseChunkSize(int l) {
        if (l <= MAX_CHUNK_SIZE) {
            chunkSize = l;
        } else {
            chunkSize = MAX_CHUNK_SIZE;
        }
    }

    private void chooseChunkAmount() {
        if (length % chunkSize == 0) {
            chunkAmount = length / chunkSize;
        } else {
            chunkAmount = length / chunkSize + 1;
        }
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) {
        int chunkNumber = (offset + index) / chunkSize;
        int indexAtChunk = (offset + index) % chunkSize;
        return chunks[chunkNumber][indexAtChunk];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (start >= 0 && start <= end && end <= characterAmount) {
            if (start == end) {
                return new CustomString();
            }

            int firstChunkNumber = start / chunkSize;
            int lastChunkNumber = end / chunkSize;
            int newOffset = 0;
            int newLength = end - start;
            char newChunks[][];

            if (firstChunkNumber == lastChunkNumber) {

                char newChunk[] = new char[chunkSize];
                for (int i = 0; i < end - start; i++) {
                    newChunk[i] = chunks[firstChunkNumber][offset + start % chunkSize + i];
                }

                return new CustomString(newChunk, newOffset, newLength);
            } else if (firstChunkNumber + MAX_CHUNKS_FOR_COPY > lastChunkNumber) {

                int copyChunksAmount = lastChunkNumber - firstChunkNumber + 1;
                newChunks = new char[copyChunksAmount][chunkSize];
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
            System.out.println("Incorrect input: Start: " + start + " End: " + end + " Offset: " + offset);
            return new CustomString();
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
