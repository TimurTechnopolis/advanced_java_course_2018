package edu.technopolis.advjava;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {

    private static final int DEFAULT_CHUNK_SIZE = 16;

    private final char[][] chunks;
    private int offset;
    private int lineLen;
    private int length;

    public CustomString(String string) {
        this.offset = 0;
        this.length = string.length();
        this.lineLen = DEFAULT_CHUNK_SIZE;
        this.chunks = new char[(length - 1) / lineLen + 1][lineLen];
        for (int i = 0; i < length; i++) {
            this.chunks[i / lineLen][i % lineLen] = string.charAt(i);
        }
    }

    public CustomString(char[][] chunks, int offset, int length) {
        this.chunks = chunks;
        this.offset = offset;
        this.lineLen = DEFAULT_CHUNK_SIZE;
        this.length = length;
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public char charAt(int index) {
        return chunks[(offset + index) / lineLen][(offset + index) % lineLen];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (start > end) {
            throw new StringIndexOutOfBoundsException("start > end");
        }
        if (start < 0) {
            throw new StringIndexOutOfBoundsException("start < 0");
        }
        if (end > length) {
            throw new StringIndexOutOfBoundsException("end > length");
        }
        int startCopy = (start + offset) / lineLen;
        int endCopy = (end + offset - 1) / lineLen + 1;
        return new CustomString(Arrays.copyOfRange(chunks, startCopy, endCopy), (start + this.offset) % lineLen, end - start + 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = offset; i < offset + length; i++) {
            sb.append(chunks[i / lineLen][i % lineLen]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        CustomString customString = new CustomString("Отличное утро перед парой по физре");
        System.out.println(customString.toString());
        System.out.println(customString.length());
        System.out.println(customString.charAt(0));
        System.out.println(customString.charAt(customString.length() - 1));
        System.out.println(customString.subSequence(0, 8));
        System.out.println(customString.subSequence(9, 12));
        System.out.println(customString.subSequence(14, 18));
        System.out.println(customString.subSequence(0, 18));
        System.out.println(customString.subSequence(9, 18));
        System.out.println("Передаю привет " + customString.subSequence(customString.length() - 5, customString.length()));
    }
}