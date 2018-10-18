package main.java.edu.technopolis.advjava;

import java.io.Serializable;
import java.util.Arrays;

public class CustomString implements CharSequence, Serializable {

    private char[][] chunks;
    private int chunkLength = 40;
    private final int chunkCount;
    private final int offset;
    private final int count;

    //Constructor with default chunk length
    public CustomString(String s) {
        count = s.length();
        chunkCount = calculateChunkCount(s);
        init(chunkCount, chunkLength);
        fillChunkArray(s);
        offset = 0;
    }
    
    //Constructor with custom chunk length
    public CustomString(String s, int chunkLength) {
        count = s.length();
        this.chunkLength = chunkLength;
        chunkCount = calculateChunkCount(s);
        init(chunkCount, chunkLength);
        fillChunkArray(s);
        offset = 0;
    }
    
    //Constructor to create substring
    private CustomString(char[][] chunks, int chunkLength, int offset, int count) {
        this.chunks = chunks;
        this.chunkLength = chunkLength;
        chunkCount = chunks.length;
        this.offset = offset;
        this.count = count;
    }

    private void init(int chunkCount, int chunkLength) {
        this.chunks = new char[chunkCount][chunkLength];
    }

    private void fillChunkArray(String s) {
        int charIndex;
        
        for (int i = 0; i < chunkCount; i++) {
            for (int j = 0; j < chunkLength; j++) {
                
                charIndex = i*chunkLength + j;
                
                if (charIndex < count) {
                    chunks[i][j] = s.charAt(charIndex);
                }
            }
        }
    }

    private int calculateChunkCount(String s) {
        return s.length() % chunkLength == 0 ?
                (s.length() / chunkLength) :
                (s.length() / chunkLength) + 1;
    }

    public int getChunkLength() {
        return chunkLength;
    }

    public void setChunkLength(int chunkLength) {
        this.chunkLength = chunkLength;
    }

    public int getOffset() {
        return offset;
    }

    public int getCount() {
        return count;
    }

    public char[][] getChunks() {
        return chunks;
    }

    @Override
    public int length() {
        return count;
    }

    @Override
    public char charAt(int index) {
        if (index >= count || index < 0) {
            throw new IndexOutOfBoundsException("String index bounds: 0 - " + (count - 1));
        }
        return chunks[(index + offset) / chunkLength][(index + offset) % chunkLength];
    }

    @Override
    public CustomString subSequence(int start, int end) {
        if (start >= count || start < 0 ||
              end >= count ||   end < 0 ||
              end <= start) {
            throw new IndexOutOfBoundsException("String index bounds: 0 - " + (count - 1));
        }
        int count = end - start + 1;

        int firstChunk = start / chunkLength;
        int lastChunk = (end / chunkLength) + 1;
        int subChunkCount = lastChunk - firstChunk;
        //System.out.println((chunkCount - subChunkCount) + " " + subChunkCount);

        int offset = start % chunkLength;
        
        //if amount of chunks of substring larger than half amount of chunks of
        //parent string, we use chunks array from parent string
        if (subChunkCount > chunkCount - subChunkCount) {
            return new CustomString(this.chunks, 
                                    this.getChunkLength(), 
                                    offset, 
                                    count); 
        }
        //else we use copy of parent subArray chunks
        return new CustomString(Arrays.copyOfRange(chunks, firstChunk, lastChunk), 
                                this.getChunkLength(), 
                                offset, 
                                count);
    }

    @Override
    public String toString() {
        String s = "";
        
        //concat first chunk with offset
        int last1 = offset + count > chunkLength ? chunkLength : offset + count;
        for (int j = offset; j < last1; j++) {
            s += chunks[0][j];
        }
        
        //concat full chunks, if count of chunks will be larger than 2 (if CustomString has middle chunks)
        for (int i = 1; i < chunks.length - 1; i++) {
            for (int j = 0; j < chunkLength; j++) {
                s += chunks[i][j];
            }
        }
        
        //concat last chunk, if count of chunks will be larger than 1
        if (chunks.length > 1) {
            int last2 = (offset + count) % chunkLength == 0 ? chunkLength : (offset + count) % chunkLength;
            for (int j = 0; j < last2; j++) {
                s += chunks[chunks.length - 1][j];
            }
        }
        
        return s;
    }
}
