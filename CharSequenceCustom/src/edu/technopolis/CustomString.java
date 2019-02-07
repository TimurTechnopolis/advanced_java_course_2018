package edu.technopolis;

import java.io.Serializable;
import java.lang.CharSequence;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {
    private final char[][] chunks;
    //Смещение относительно самого последнего используемого чанка
    //(Индекс последнего элемента в последнем массиве)
    private final int offsetRight;
    //Смещение относительно самого первого используемого чанка
    //(Индекс первого элемента в первом массиве)
    private final int offsetLeft;
    private final int length;
    //Размер одного чанка
    private final int chunkLength;
    private final static int DEFAULT_CHUNK_SIZE = 10000;

    public CustomString(String str, int chunkSize) {
        offsetLeft = 0;
        int n = 0;
        chunkLength = chunkSize;
        //Если длина не делится нацело, то останется хвостик,
        //Который при делении на длину чанка обрежется
        if (str.length() % chunkLength != 0) {
            n = str.length() / chunkLength + 1;
            offsetRight = str.length() % chunkLength - 1;
        } else {
            n = str.length() / chunkLength;
            offsetRight = 0;
        }
        chunks = new char[n][chunkLength];
        length = str.length();

        for (int i = 0; i < (n - 1); i++) {
            for (int j = 0; j < chunkLength; j++) {
                chunks[i][j] = str.charAt(i * chunkLength + j);
            }
        }
        //Отдельное заполнение последнего чанка, так как он может быть неполным
        for (int j = 0; j < (str.length() - (n - 1) * chunkLength); j++) {
            chunks[n-1][j] = str.charAt((n-1) * chunkLength + j);
        }
    }

    CustomString(String str) {
        this(str, DEFAULT_CHUNK_SIZE);
    }

    /**
     * @param arr - переиспользуемый массив
     * @param startChunk - первый переиспользуемый чанк
     * @param endChunk - последний переиспользуемый чанк
     */
    private CustomString(char arr[][], int offLeft, int offRight, int startChunk, int endChunk) {
        chunkLength = arr[0].length;
        //Количество переиспользуемых чанков
        int len = endChunk - startChunk + 1;
        chunks = new char[len][chunkLength];
        System.arraycopy(arr, startChunk, chunks, 0, len);

        offsetLeft = offLeft;
        offsetRight = offRight;
        length = chunkLength * (len - 1) - offLeft + offRight + 1;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index > (length - 1)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int tmp = 0;
        index += offsetLeft;
        if (index >= chunkLength) {
            tmp = index / chunkLength;
        }
        return chunks[tmp][index % chunkLength];
    }

    @Override
    public CustomString subSequence(int start, int end) throws ArrayIndexOutOfBoundsException {
        if (start < 0 || start > (length - 1) || end < 0 || end > (length - 1)) {
            throw new ArrayIndexOutOfBoundsException();
        } else if (start > end) {
            return null;
        } else {
            start += offsetLeft;
            end += offsetLeft;
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
        for (char[] chunk : chunks) builder.append(chunk);
        return builder.toString();
    }
}
