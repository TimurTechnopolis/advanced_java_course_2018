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
        if (start < end){
            int summ = 0;
            int razn = length;

            for (int i = 0; i < chunks.length; i++) {
                summ += chunks[i].length;
                if (start < summ){
                    offsetIndex = i;
                    break;
                }
            }

            for (int i = chunks.length; i > 0; i--) {
                razn -= chunks[i].length;
                if (start > razn){
                    endIndex = i;
                    break;
                }
            }
        }
        return null;
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
