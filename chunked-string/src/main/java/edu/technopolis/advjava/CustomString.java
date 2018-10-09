package edu.technopolis.advjava;

import java.io.Serializable;
import java.util.*;
import java.io.*;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {
    private final char[][] chunks;
    /*
     * params according to Java 6 have been added
     */
    private final int offset;
    private final int count;

    /*
     * Group of constructors has been added
     */
    public CustomString(char[][] chunks) {
        this(chunks, 0, chunks.length);
    }

    public CustomString(char[][] chunks, int count) {
        this(chunks, 0, count);
    }

    public CustomString(char[][] chunks, int offset, int count) {
        this.chunks = chunks;
        this.offset = offset;
        this.count = count;
    }

    @Override
    public int length() {
        return count;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= count) {
            throw IndexOutOfBoundsException("Out of bounds!");
        }
        return chunks[offset + index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (start < 0 || end < 0) || end > length() || start > end) {
            throw IndexOutOfBoundsException("Out of bounds!");
        }
        if ((start == 0) && (end == length())) {
            return this;
        }
        else {
            int low_bound = offset + start;
            int up_bound = end - start;
            return CustomString(chunks, low_bound, up_bound);
        }
//        return toString().subSequence(start, end);
    }

    @Override
    public String toString() {
        return new String(this.chunks, this.offset, this.count);
    }
}
