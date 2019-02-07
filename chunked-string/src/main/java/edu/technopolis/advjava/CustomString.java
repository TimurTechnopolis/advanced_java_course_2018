package edu.technopolis.advjava;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {
    private final char[][] chunks;
    private final int CHUNKS_LENGTH;
    private static int defaultChunksLength = 10;
    private int offset;
    private int flang;

    CustomString(char[][] chunks) {
        this.CHUNKS_LENGTH = chunks[0].length;
        for (char[] a : chunks) {
            if (a.length != CHUNKS_LENGTH) {
                throw new IllegalStateException("Чанки не фиксированной длинны");
            }
        }
        this.chunks = chunks;
        this.offset = 0;
        this.flang = CHUNKS_LENGTH - 1;
    }

    CustomString(String inputSting) {
        this(inputSting, defaultChunksLength);
    }

    CustomString(String inputString, int chunksLength) {
        if (chunksLength < 1) {
            throw new IndexOutOfBoundsException();
        }
        this.CHUNKS_LENGTH = chunksLength;
        this.offset = 0;
        int tmp = (inputString.length() / CHUNKS_LENGTH);
        int size = (chunksLength == 1 || inputString.length() % CHUNKS_LENGTH == 0) ? tmp : tmp + 1;
        this.chunks = new char[size][CHUNKS_LENGTH];
        int iterator = 0;
        for (int i = 0; i < chunks.length; i++) {
            for (int j = 0; j < CHUNKS_LENGTH; j++) {
                if (iterator == inputString.length()) {
                    break;
                }
                chunks[i][j] = inputString.charAt(iterator);
                this.flang = j;
                iterator++;
            }
        }
    }

    private CustomString(char[][] chunks, int chunksLength, int offset, int flang) {
        this.chunks = chunks;
        this.CHUNKS_LENGTH = chunksLength;
        this.offset = offset;
        this.flang = flang;
    }

    @Override
    public int length() {
        int edge = CHUNKS_LENGTH - (flang + 1);
        return chunks.length * CHUNKS_LENGTH - offset - edge;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= this.length()) {
            throw new IndexOutOfBoundsException();
        }

        int i =  (index + offset) / CHUNKS_LENGTH;
        int j = (index + offset) % CHUNKS_LENGTH;
        return chunks[i][j];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        char[][] resultChunks;
        int resultOffset;
        int resultFlang;

        int inIndex = (start + offset) / CHUNKS_LENGTH;
        int outIndex = (end + offset) / CHUNKS_LENGTH;

        resultChunks = new char[outIndex - inIndex + 1][];

        for (int i = inIndex, j = 0; i < outIndex + 1; i++, j++) {
            resultChunks[j] = this.chunks[i];
        }


        resultOffset = start % CHUNKS_LENGTH;
        resultFlang = end % CHUNKS_LENGTH - 1;

        return new CustomString(resultChunks, CHUNKS_LENGTH, resultOffset, resultFlang);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chunks.length; i++) {
            int start = i == 0 ? offset : 0;
            int end = i == chunks.length - 1 ? flang + 1 : CHUNKS_LENGTH;
            for (int j = start; j < end; j++) {
                if (chunks[i][j] != '\u0000') {
                    stringBuilder.append(chunks[i][j]);
                }
            }
        }
        return stringBuilder.toString();
    }

    void printChunks() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        for (char[] chars : chunks) {
            stringBuilder.append(i++).append(": ").append(Arrays.toString(chars).replaceAll("\n", "\\\\n")).append('\n');
        }
        System.out.println(stringBuilder.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof CustomString)) {
            return false;
        }

        CustomString otherObject = (CustomString) obj;

        boolean content = Arrays.deepEquals(this.chunks, otherObject.chunks);
        boolean chunksSize = this.CHUNKS_LENGTH == otherObject.CHUNKS_LENGTH;
        boolean offset = this.offset == otherObject.offset;
        boolean flang = this.flang == otherObject.flang;
        return content && chunksSize && offset && flang;
    }

    @Override
    public int hashCode() {
        BigInteger bigInteger = BigInteger.valueOf(0);
        for (char[] chunk : chunks) {
            bigInteger = bigInteger.add(BigInteger.valueOf(Arrays.hashCode(chunk)));
        }
        bigInteger = bigInteger.divide(BigInteger.valueOf(CHUNKS_LENGTH));
        return bigInteger.hashCode();
    }

    int getFlang() {
        return flang;
    }

    int getOffset() {
        return offset;
    }
}