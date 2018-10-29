package edu.technopolis.advjava.demo;
import java.util.Arrays;
import java.util.stream.IntStream;

public class CustomString implements CharSequence {

    private int offset;
    private int count;
    private int lengthCh = 0;
    private char[][] ch;

    //Начальный конструктор
    public CustomString(String s) {
        offset = 0;
        count = s.length();
        if(count < 4)lengthCh = count;
        else lengthCh = MaxlengthCh(count);

        int t = count / lengthCh;
        ch = new char[t][lengthCh];
        for (int i = 0, n = 0; i < t; i++)
            for (int j = 0; j < lengthCh && n < count; j++) { ch[i][j] = s.charAt(n);n++; }
    }

    public CustomString(char[][] ch, int offset, int count, int lengthChunk) {
        this.ch = ch;
        this.offset = offset;
        this.count = count;
        this.lengthCh = lengthChunk;
    }

    private int MaxlengthCh (int c) {
        int k = (int)Math.sqrt(c);
        while (k > 0) { if ((c%k) == 0) break;k--;}
        if (k<=0) k=1;
        if (k != 1) return k;
        else return MaxlengthCh(c + 1);
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
    public char charAt(int i) { return ch[((offset + i) / (ch[0].length))][((offset + i) % (ch[0].length))]; }

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