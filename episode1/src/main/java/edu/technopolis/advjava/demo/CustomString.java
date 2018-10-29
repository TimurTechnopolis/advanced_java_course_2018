package edu.technopolis.advjava.demo;
import java.util.Arrays;
import java.util.stream.IntStream;

public class CustomString implements CharSequence {

    private int offset;
    private int count;
    private int lengthChunk = 0;
    private char[][] ch;


    public CustomString(char[][] ch, int offset, int count, int lengthChunk) {
        this.ch = ch;
        this.offset = offset;
        this.count = count;
        this.lengthChunk = lengthChunk;
    }

    private int MaxPrimeNumbers (int c) {
        int k = (int)Math.sqrt(c);
        while (k > 0) {
            if ((c%k) == 0) return k;
            k--;
        }
        return 1;
    }
    //Начальный конструктор

    public CustomString(String s) {
        offset = 0;
        count = s.length();
        if(count < 4)lengthChunk = count;
        else {
            int t = MaxPrimeNumbers(count);
            if (t != 1) lengthChunk = t;
            else lengthChunk = MaxPrimeNumbers(count + 1);
        }

        int amountChunk = count / lengthChunk;
        ch = new char[amountChunk][lengthChunk];
        for (int i = 0, m = 0; i < amountChunk; i++) {
            for (int j = 0; (j < lengthChunk) && (m < count); j++) {
                ch[i][j] = s.charAt(m);
                m++;
            }
        }
    }

    @Override
    public char charAt(int index) {
        return ch[((offset + index) / (ch[0].length))][((offset + index) % (ch[0].length))];
    }


    public CustomString subString(int start, int end) {
        if ((end >= count) || (start < offset) || (start > end)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int tmpLen = ((offset + end)/lengthChunk)-((offset + start) / lengthChunk);
        char [][] copArray =  new char[tmpLen+1][lengthChunk];
        for(int i = 0 ; i  <= tmpLen; i++){
            copArray[i] = ch[(offset + start) / lengthChunk + i];
        }
        return new CustomString(copArray, (offset + start)%lengthChunk, end - start + 1, lengthChunk);
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        int i = 0;
        for (int j = offset % lengthChunk; j < count + offset % lengthChunk; j++) {
            if (((j%lengthChunk == 0)&&(j!=0))) i+= 1;
            s.append(ch[i][j % lengthChunk]);
        }
        return s.toString();
    }

    @Override
    public int length() {
        return count;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }

    @Override
    public IntStream chars() {
        return null;
    }

    @Override
    public IntStream codePoints() {
        return null;
    }

}