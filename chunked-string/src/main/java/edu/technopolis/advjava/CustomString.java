import java.io.Serializable;
import java.util.*;
import java.io.*;
import java.util.Arrays;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящая содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {
    private final char[][] chunks;
    /*
     * params according to Java 6 have been added
     */
    private final int offset;
    private final int count;

    private final int CHUNK_SIZE;

    /*
     * Group of constructors has been added
     */
    public CustomString(char[][] chunks) {
        this(chunks, 0, chunks.length);
    }

    public CustomString(char[][] chunks, int count) {
        this(chunks, 0, count);
    }

    private CustomString(char[][] chunks, int offset, int count) {
        this.chunks = chunks;
        this.offset = offset;
        this.count = count;
        CHUNK_SIZE = 8;
    }

    private CustomString(char[] input) {
        CHUNK_SIZE = 8;
        this.offset = 0;
        this.count = input.length;
        int amount;
        if (count % CHUNK_SIZE == 0) {
            amount = count / CHUNK_SIZE;
        }
        else {
            amount = count / CHUNK_SIZE+1;
        }
        chunks = new char[amount][CHUNK_SIZE];
        for (int i = 0, l = 0; i < amount; i++) {
            for (int j=0; (j < CHUNK_SIZE) && (l < count); j++) {
                chunks[i][j] = input[l];
                l++;
            }
        }
    }

    @Override
    public int length() {
        return count;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Out of bounds!");
        }
        int new_index = offset + index;
        int row = new_index / CHUNK_SIZE;
        int column = new_index % CHUNK_SIZE;
        return chunks[row][column];
    }

    @Override
    public CustomString subSequence(int start, int end) {
        if (start < 0 || end < 0 || end > length() || start > end) {
            throw new IndexOutOfBoundsException("Out of bounds!");
        }
        if ((start == 0) && (end == length())) {
            return this;
        }
        else {
            int low_bound = offset + start;
            int up_bound = end + offset;
            char [][]chunkies;
            int count = up_bound-low_bound+1;
            int amount = up_bound/CHUNK_SIZE - low_bound/CHUNK_SIZE + 1;
            int offset = low_bound % CHUNK_SIZE;
            int first_chunk = low_bound / CHUNK_SIZE;
            chunkies = new char [amount][count];
            for (int i=0; i<amount; i++) {
                for (int j=0; j<count; j++) {
                    chunkies[i] = chunks[first_chunk];
                }
                first_chunk++;
            }
            return new CustomString(chunkies, offset, end-start+1);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int count = 0;
        while (count < length()){
            result.append(charAt(count));
            count++;
        }
        return result.toString();
    }

    public static void main(String[] args) {
	//example of usage
        String input = "abcdefghijklmnopqrstuvwxyz";
        CustomString full_str = new CustomString(input.toCharArray());
        System.out.println(full_str.charAt(4));
        System.out.println(full_str.length());
        CustomString full_str_seq = full_str.subSequence(6, 10);
        System.out.println(full_str_seq);
    }
}
