package edu.technopolis.advjava.demo;
import java.util.Arrays;
import java.util.stream.IntStream;

public class CustomString implements CharSequence {

    private int offset;
    private int count;
    private int lengthCh = 0;
    private char[][] ch;


    public CustomString(char[][] ch, int offset, int count, int lengthChunk) {
        this.ch = ch;
        this.offset = offset;
        this.count = count;
        this.lengthCh = lengthChunk;
    }

    //Начальный конструктор
    public CustomString(String s) {
        offset = 0;
        count = s.length();
        if(count < 4)lengthCh = count;
        else {
            int t = MaxPrimeNumbers(count);
            if (t != 1) lengthCh = t;
            else lengthCh = MaxPrimeNumbers(count + 1);
        }
        int t = count / lengthCh;
        ch = new char[t][lengthCh];
        for (int i = 0, n = 0; i < t; i++)
            for (int j = 0; j < lengthCh && n < count; j++) { ch[i][j] = s.charAt(n);n++; }
    }

    public CustomString subString(int start, int end) {
        if ((end >= count) || (start < offset) || (start > end)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int tmpLen = ((offset + end)/lengthCh)-((offset + start) / lengthCh);
        char [][] copArray =  new char[tmpLen+1][lengthCh];
        for(int i = 0 ; i  <= tmpLen; i++){
            copArray[i] = ch[(offset + start) / lengthCh + i];
        }
        return new CustomString(copArray, (offset + start)%lengthCh, end - start + 1, lengthCh);
    }

    private int MaxPrimeNumbers (int c) {
        int k = (int)Math.sqrt(c);
        while (k > 0) {
            if ((c%k) == 0) return k;
            k--;
        }
        return 1;
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        int i = 0;
        for (int j = offset % lengthCh; j < count + offset % lengthCh; j++) {
            if (((j%lengthCh == 0)&&(j!=0))) i+= 1;
            s.append(ch[i][j % lengthCh]);
        }
        return s.toString();
    }

    @Override
    public char charAt(int index) {
        return ch[((offset + index) / (ch[0].length))][((offset + index) % (ch[0].length))];
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