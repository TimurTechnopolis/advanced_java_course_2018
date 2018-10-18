package edu.technopolis.advjava;

import java.io.Serializable;


public class CustomString implements CharSequence, Serializable {

    int chunkSize;
    private final char[][] chunks;

    int length;
    int offset;
    int count;

    public CustomString(){
        length = 0;
        offset = 0;
        count = 0;
        chunkSize = 0;
        chunks = new char[chunkSize][chunkSize];
    }
    public CustomString(String str) {
        if (str.length() >= 0){
            this.length = str.length();
            this.chunkSize =(int)Math.sqrt(this.length) + 1;
            this.chunks = new char[chunkSize][chunkSize];
            int k = 0;
            for (int i = 0; i < chunkSize; i++){
                for (int j = 0; j < chunkSize; j++) {
                    if (k < length) {
                        chunks[i][j] = str.charAt(k);
                        k++;
                    }
                }
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }
    public CustomString(int offset, int count, char[][] chunks){
        this.chunks = chunks;
        this.chunkSize = chunks.length;
        this.length = count;
        this.offset = offset;
        this.count = count;
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public char charAt(int index) {
        if (index >= 0 && index <= length - 1) {
            return chunks[index/chunkSize][index%chunkSize];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (start >= 0 && end <= this.length - 1 && end >= start){
            return new CustomString(start + offset, end - start + 1, chunks);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(int i = offset; i < offset + length; i++){
            s.append(this.chunks[i/chunkSize][i%chunkSize]);
        }
        return s.toString();
    }


    public static void main(String[] args) {
        CustomString s = new CustomString("qwerty");

        String ss = s.toString();
        System.out.println(ss);
        char c = s.charAt(5);
        System.out.println(c);
        String q = s.subSequence(1,3).toString();
        System.out.println(q);

    }
}