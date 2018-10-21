package edu.technopolis.advjava.demo;


import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;

import com.sun.xml.internal.fastinfoset.util.CharArray;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {
    private final char[][] chunks;
    private final int length;



    CustomString(char[] arr){
        length = arr.length;
        int rounded = (int)Math.ceil(Math.sqrt(length));
        chunks = new char[rounded][rounded];
        for(int i = 0;i < length;i++){
                chunks[i / rounded][i % rounded] = arr[i];

        }
    }

    CustomString(String string){
        length = string.length();
        int rounded = (int)Math.ceil(Math.sqrt(length));
        chunks = new char[rounded][rounded];
        for(int i = 0;i < length;i++){
                chunks[i / rounded][i % rounded] = string.charAt(i);

        }
    }
    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) {
        int rounded = (int)Math.ceil(Math.sqrt(length));
        return chunks[index / rounded][index % rounded];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        int rounded = (int)Math.ceil(Math.sqrt(length));
        char[] chars = new char[end-start];
        for(int i = start; i < end;i++){
            chars[i-start] = chunks[i / rounded][i % rounded];
        }
        return new CustomString(chars);
    }


    @Override
    public String toString() {
        char[] arr = new char[length];
        int rounded = (int)Math.ceil(Math.sqrt(length));
        for(int i = 0;i < length;i++) {
            arr[i] = chunks[i / rounded][i % rounded];
        }
        return new String(arr);
    }

    public static void main(String[] args) {
       System.out.println(new CustomString(new char[]{'c','d','c','e'}));
       String st = "Java is the best";
       System.out.println(new CustomString(st).subSequence(0,st.length()));


    }

}