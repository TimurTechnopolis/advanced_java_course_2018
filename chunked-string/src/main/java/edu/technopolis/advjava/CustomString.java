//package edu.technopolis.advjava;

import java.io.Serializable;
import java.security.InvalidParameterException;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {
    private final char[][] chunks;

    private int size;
    private int offset;
    private int chunkSize;
    private int chunkAmount;
    //private int defaultChunk = 80;
    //private final int DEFAULT_CHUNK_SIZE = 80;


    public CustomString(String value, int chSize) {
        if(chSize > 1)
        this.chunkSize = chSize;
        else
            throw new InvalidParameterException("Invalid Chunk Size");
        this.size = value.length();
        this.offset = 0;
        this.chunkAmount = size % chunkSize == 0 ? size/chunkSize : size/chunkSize+1;
        this.chunks = new char[chunkAmount][chunkSize];

        for (int i = 0; i < chunkAmount; i++)
            for(int k = 0; k < chunkSize; k++) {
                if (i*chunkSize+ k < size) {
                    chunks[i][k] = value.charAt(i * chunkSize + k);
                }
            }

    }

    public CustomString(char[][] arr, int chunkSize, int start, int length) {
        this.offset = start;
        this.size = length;
        this.chunkSize = chunkSize;
        this.chunkAmount = this.size % this.chunkSize == 0 ? (this.size/ this.chunkSize) : (this.size/ this.chunkSize + 1);
        this.chunks = new char[chunkAmount][this.chunkSize];
        int index = offset - 1;
        for (int i = 0; i < chunkAmount; i++)

            for(int k = 0; k < this.chunkSize; k++)
            {
                if(i* this.chunkSize + k -(this.offset-1) < this.size) {
                    chunks[i][k] = arr[index / chunkSize][index % chunkSize];
                    index++;
                }
                else
                    break;
            }

    }
