package edu.technopolis.advjava;

import java.io.Serializable;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {
    private final char[][] chunks;

    public CustomString(char[][] chunks) {
        this.chunks = chunks;
        this.length = length();
    }

    /*
     * todo add complimentary fields if required
     */
    private int length;

    /*
     * todo add constructor or group of constructors
     */
    public CustomString(char[][] chunks, int offset, int end) {
        int count = 0;

        this.chunks = chunks;
    }

    public CustomString(String inputText){
        int divideCount;
        divideCount = inputText.length() - inputText.replaceAll("\n","").length();
        chunks = new char[divideCount][];
        int offset = 0;
        for (int i = 0; i < chunks.length; i++) {
            int indOfDivide = inputText.indexOf('\n',offset);
            chunks[i] = new char[indOfDivide];
            chunks[i] = inputText.substring(offset,indOfDivide + 1).toCharArray();
            offset = indOfDivide + 1;
        }

        this.length = length();
    }


    @Override
    public int length() {
        int resultLength = 0;
        for (char[] a : chunks) {
            resultLength += a.length;
        }
        return resultLength;
    }

    @Override
    public char charAt(int index) {
        int k = 0;
        boolean wasFind = false;
        char j = 0;

        if (length() < index || index < 0) {
            throw new StringIndexOutOfBoundsException(index);
        }

        for (char[] chunk : chunks) {
            if (!wasFind) {
                for (char a : chunk) {
                    j = a;
                    if (k == index) {
                        wasFind = true;
                        break;
                    } else {
                        k++;
                    }
                }
            }
        }
        return j;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        //todo implement subSequence here
        int offsetIndex = 0;
        int endIndex = 0;

        char[][] result;

        char[] firstChunk = new char[0];
        char[] lastChunk = new char[0];

        if (start < end){
            int endOfChunk = 0;
            int beginOfChunk = length;


            int positionInChunkStart = 0;
            int positionInChunkEnd = 0;
            for (int i = 0; i < chunks.length; i++) {
                endOfChunk += chunks[i].length;
                if (start <= endOfChunk){
                    offsetIndex = i;
                    positionInChunkStart = chunks[i].length - 1 - (endOfChunk - start);
                    firstChunk = new String(chunks[i]).substring(positionInChunkStart).toCharArray();
                    break;
                }
            }

            for (int i = chunks.length - 1; i > 0; i--) {
                beginOfChunk -= (chunks[i].length - 1);
                if (start >= beginOfChunk){
                    endIndex = i;
                    positionInChunkEnd = start - beginOfChunk;
                        lastChunk = new String(chunks[i]).substring(0, positionInChunkEnd).toCharArray();
                    break;
                }
            }

            result = new char[endIndex - offsetIndex + 1][];

            if (result.length == 1){
                result[0] = new char[positionInChunkEnd - positionInChunkStart + 1];
                result[0] = new String(lastChunk).substring(positionInChunkStart).toCharArray();
            }else {
                result[0] = firstChunk;
                offsetIndex++;
                for (int i = 1; i < result.length - 1; i++){
                    result[i] = chunks[offsetIndex++];
                }
                result[result.length - 1] = lastChunk;
            }
            return new CustomString(result);
        }else {
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (char[] a : chunks){
            stringBuilder.append(a);
        }
        return new String(stringBuilder);
    }
}
