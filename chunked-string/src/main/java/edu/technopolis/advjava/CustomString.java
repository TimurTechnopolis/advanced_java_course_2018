package edu.technopolis.advjava;

import java.io.Serializable;
import java.util.Objects;

/**
 * The special class that represents characters string (analog {@link java.lang.String}).
 * This class stores a character string as chunks for better memory usage
 * when creating substrings.
 */
public class CustomString implements CharSequence, Serializable {

    /** The chunks is used for character storage. */
    private final char[][] chunks;

    /** The offset is the first index of the first chunk that is used. */
    private final int offset;

    /** The count is the number of characters in the {@link CustomString}. */
    private final int count;

    /** The size of chunk. */
    private final int chunkSize;

    /**
     * Allocates a new {@link CustomString} which consists of some
     * chunks of {@code cs}. Count of chunks of new {@link CustomString}
     * equals {@code endIndex % chunkSize - beginIndex / chunkSize} where
     * {@code chunkSize} is size of chunk in {@code cs}.
     *
     * @param beginIndex the beginning index, inclusive.
     * @param endIndex the ending index, exclusive.
     * @param cs {@link CustomString} that is the source of characters.
     */
    private CustomString(int beginIndex, int endIndex, CustomString cs){
        int chunkStart = beginIndex / cs.chunkSize;
        int chunkEnd = endIndex / cs.chunkSize;
        char[][] chunks = new char[chunkEnd - chunkStart + 1][];
        System.arraycopy(cs.chunks, chunkStart, chunks, 0, chunkEnd - chunkStart + 1);

        this.chunks = chunks;
        this.offset = beginIndex % cs.chunkSize;
        this.count = endIndex - beginIndex;
        this.chunkSize = cs.chunkSize;
    }

    /**
     * Allocates a new {@link CustomString} that contains characters from a subarray
     * of the character array argument. Characters stores in {@code char[][] chunks }
     * where length of chunk equals log2(count). If {@code log2(count) == 0}
     * ({@code value.length == 1}) then length of chunk equals 1.
     *
     * @param offset the initial offset.
     * @param count the length.
     * @param value array that is the source of characters.
     */
    private CustomString(int offset, int count, char[] value){
        Objects.requireNonNull(value);
        if (offset < 0 || count < 0 || offset > value.length - count) {
            throw new StringIndexOutOfBoundsException(
                    "offset " + offset + ", count " + count + ", length " + value.length);
        }

        int chunkSize = (int)(Math.ceil(Math.log((double)count) / Math.log(2.0)));
        chunkSize = (chunkSize == 0) ? 1 : chunkSize;
        int numOfChunks = (int)Math.ceil((double)count / chunkSize);
        chunks = new char[numOfChunks][];

        for(int i = 0; i < numOfChunks; i++){
            int start = i * chunkSize;
            int length = Math.min(count - start, chunkSize);

            char[] temp = new char[length];
            System.arraycopy(value, offset + start, temp, 0, length);
            chunks[i] = temp;
        }

        this.offset = 0;
        this.count = count;
        this.chunkSize = chunkSize;
    }

    /**
     * Initializes a newly created {@link CustomString} object so that it represents
     * the same sequence of characters as the argument; in other words, the
     * newly created string is a copy of the argument string.
     *
     * @param original a {@link CustomString}.
     */
    public CustomString(CustomString original){
        Objects.requireNonNull(original);
        chunks = original.chunks;
        chunkSize = original.chunkSize;
        offset = original.offset;
        count = original.count;
    }

    /**
     * Initializes a newly created {@link CustomString} object so that it represents
     * the same sequence of characters as the argument; in other words, the
     * newly created string is a copy of the argument string.
     *
     * @param string a {@link java.lang.String}.
     */
    public CustomString(String string){
        this(string.toCharArray());
    }

    /**
     * Initializes a newly created {@link CustomString} object so that it represents
     * the same sequence of characters as the argument; in other words, the
     * newly created string is a copy of the argument string.
     *
     * @param value the sequence of characters.
     */
    public CustomString (char[] value){
        this(0, value.length, value);
    }

    /**
     * Returns a string that is a substring of this string. The
     * substring begins with the character at the specified index and
     * extends to the end of this string.
     *
     * @param beginIndex the beginning index, inclusive.
     * @param endIndex the ending index, exclusive.
     * @return the specified substring.
     */
    public CustomString substring(int beginIndex, int endIndex){
        if (beginIndex < 0 || endIndex > count || beginIndex > endIndex) {
            throw new StringIndexOutOfBoundsException(
                    "begin " + beginIndex + ", end " + endIndex + ", length " + count);
        }
        return new CustomString(beginIndex, endIndex, this);
    }

    /**
     * Returns a character sequence that is a subsequence of this sequence.
     *
     * @param beginIndex the beginning index, inclusive.
     * @param endIndex   the ending index, exclusive.
     * @return the specified subsequence.
     */
    @Override
    public CharSequence subSequence(int beginIndex, int endIndex) {
        return this.substring(beginIndex, endIndex);
    }

    /**
     * Returns the length of this string.
     *
     * @return the length of the sequence of characters represented by this
     *         object.
     */
    @Override
    public int length() {
        return count;
    }

    /**
     * Returns the {@code char} value at the specified index.
     * An index ranges from {@code 0} to {@code length() - 1}.
     *
     * @param index the index of the {@code char} value.
     * @return the specified {@code char} value.
     */
    @Override
    public char charAt(int index) {
        if ((index < 0) || (index >= count)){
            throw new StringIndexOutOfBoundsException(index);
        }
        int offsetIndex = index + offset;
        return chunks[offsetIndex / chunkSize][offsetIndex % chunkSize];
    }

    /**
     * Returns a string containing the characters in this sequence in the same
     * order as this sequence.  The length of the string will be the length of
     * this sequence.
     *
     * @return a string consisting of exactly this sequence of characters.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int numChars = 0;
        for(int i = 0; i < (int)Math.ceil((double)count / chunkSize); i++){
            int j = (i == 0) ? offset : 0;
            for( ; j < chunkSize; j++){
                sb.append(chunks[i][j]);
                if(count == ++numChars) break;
            }
        }
        return sb.toString();
    }

}
