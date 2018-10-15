package edu.technopolis;

import java.nio.CharBuffer;

public class CustomString implements CharSequence {

    private int length;
    private int chunksCount;
    private int chunkLength;

    private char[][] storage;

    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) {

        if (index < 0 || index >= length) {
            throw new StringIndexOutOfBoundsException();
        }

        int n = index / chunkLength;
        int m = index - n * chunkLength;

        return storage[n][m];
    }

    @Override
    public CharSequence subSequence(int start, int end) {

        if (start < 0 || start > length ||
                end < 0 || end > length ||
                start > end) {
            throw new StringIndexOutOfBoundsException();
        }

        CharBuffer res = CharBuffer.allocate(end - start);

        for (int i = (start / chunkLength); i <= (end / chunkLength); i++) {

            for (int j = 0; j < chunkLength; j++) {

                if (j == (end - i * chunkLength)) {
                    res.rewind();
                    return res;
                }

                if (j >= (start - i * chunkLength)) {
                    res.append(storage[i][j]);
                }
            }
        }

        return null;
    }

    public CustomString(CharSequence input) {

        length = input.length();
        chunksCount = 30_000;

        chunkLength = length / chunksCount;

        if (length % chunksCount != 0) {
            chunkLength++;
        }

        chunksCount = length / chunkLength;

        if (length % chunkLength != 0) {
            chunksCount++;
        }

        storage = new char[chunksCount][chunkLength];

        for (int i = 0, n = 0, m = 0; i < length; i++) {

            storage[n][m] = input.charAt(i);

            m++;

            if (m == chunkLength) {
                n++;
                m = 0;
            }
        }
    }

    public void print() {

        if (length == 0) {
            return;
        }

        System.out.println(subSequence(0, length));
    }
}
