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



    public CustomString(String value){
        this(value, 4);
    }

    @Override
    public int length() {
        return this.size;
    }

    @Override
    public char charAt(int index) {
        if (index < size && index >= 0)
            return chunks[index/chunkSize][index % chunkSize];
        else
            throw new IndexOutOfBoundsException("Invalid Index");
        //todo implement charAt here
    }

    @Override
    public CharSequence subSequence(int start, int length) {
        if (start < 0 || length > this.size){
            throw new InvalidParameterException("Invalid Index Parameters");
        }
        return new CustomString(this.chunks, this.chunkSize , start, length);
        //todo implement subSequence here
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder(this.size);
        for (int i = this.offset; i < this.size+this.offset; i++) {
            build.append(chunks[i/chunkSize][i%chunkSize]);
        }
        return new String(build);
    }

    public void print(){
        for (int i = 0; i < this.chunkAmount; i++)
            for(int k = 0; k < this.chunkSize; k++)
            {
                if(i*chunkSize + k - (offset -1) < size)
                System.out.print(chunks[i][k]);
            }
        System.out.println();
    }

    public static void main(String[] args) {
        String s = "Привет. Ок.";
        CustomString test = new CustomString(s);
        System.out.println(test.length());
        System.out.println("s value:");
        CustomString test2 = (CustomString) test.subSequence(3, 5);
        test2.print();
        System.out.println("to string " + test2.toString());
        for (int i = 0; i <= test2.length(); i++)
        System.out.println(i + " " + test2.charAt(i));
        System.out.println(test2.length());
    }
}
