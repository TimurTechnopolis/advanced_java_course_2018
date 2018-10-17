package edu.technopolis;

import java.util.Arrays;

/**
 * User: victoria.shepard
 * Date: 11/10/2018
 * Time: 18:24
 */
public class CustomString implements CharSequence {

    private static char[][] chunks;
    private static int chunkSize;
    private static int startIndex;
    private static int endIndex;
    private static int chunksCustomLength;

    private static final int CHUNKS_DEFAULT_LENGTH = 128;

    public CustomString() {
        chunks = new char[0][0];
        chunkSize = 0;
        startIndex = 0;
        endIndex = 0;
    }

    public CustomString(CharSequence charSequence) {
        new CustomString(charSequence, CHUNKS_DEFAULT_LENGTH);
    }

    public CustomString(CharSequence charSequence, int chunksCustomLength) {
        startIndex = 0;
        CustomString.chunksCustomLength = chunksCustomLength;
        if (charSequence.length() == 0)  {
            new CustomString();
        } else {
            if (charSequence.length() < chunksCustomLength) {
                endIndex = chunksCustomLength - 1;
                chunkSize = charSequence.length() / chunksCustomLength;
            } else {
                endIndex = charSequence.length() % chunksCustomLength - 1;
                chunkSize = charSequence.length() / chunksCustomLength + 1;
            }

            chunks = new char[chunkSize][chunksCustomLength];
            for (int i = 0; i < chunkSize; i++) {
                for (int j = 0; j < chunksCustomLength; j++) {
                    if (i == chunkSize - 1 && j == endIndex + 1) {
                        break;
                    }
                    chunks[i][j] = charSequence.charAt(i * chunksCustomLength + j);
                }
            }
        }
    }

    @Override
    public int length() {
        if (chunkSize == 0) return 0;
        return chunkSize * chunksCustomLength - startIndex - chunksCustomLength + 1 + endIndex;
    }

    @Override
    public char charAt(int index) {
        return chunks[(index + startIndex) / chunksCustomLength][(index + startIndex) % chunksCustomLength];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (start < 0 || end > length()) {
            throw new IndexOutOfBoundsException();
        }
        if (start == end) {
            return new CustomString();
        }
        return this.toString().substring(start, end);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int height = 0; height < chunkSize; height++) {
            for (int wight = 0; wight < chunksCustomLength; wight++) {
                if (height == 0 && wight == 0) {
                    wight = startIndex;
                }
                if (chunks[height][wight] != '\u0000') {
                    stringBuilder.append(chunks[height][wight]);
                }
                if (height == startIndex - 1 && wight == endIndex) {
                    break;
                }
            }
        }
        return stringBuilder.toString();
    }

    public String getChunk(int index) {
        return Arrays.toString(chunks[index]).replaceAll("\n", "\\\\n");
    }

}
