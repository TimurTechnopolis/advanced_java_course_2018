package main.java.edu.technopolis;

public class CustomString implements CharSequence {

    private char[][] chunks;
    private final int chunkLength;
    private int cSLength;
    private int chunkIndex;
    private int offset;
    private int count;

    public CustomString(char chars[], int chunkLength) {
        this.chunkLength = chunkLength;
        cSLength = chars.length;
        chunkIndex = 0;
        offset = 0;
        count = chars.length;
        if (chars.length == 0) {
            chunks = null;
        } else {
            int step = 0;
            chunks = new char[chars.length / chunkLength + 1][chunkLength];
            for (int i = 0; i < chars.length / chunkLength + 1; i++) {
                for (int j = 0; j < chunkLength; j++) {
                    chunks[i][j] = chars[step];
                    step++;
                    if (step >= chars.length) break;
                }
                if (step >= chars.length) break;
            }
        }
    }

    private CustomString(char[][] chars, int chunkLength, int offset, int count, int chunkIndex) {
        this.chunkIndex = chunkIndex;
        this.chunks = chars;
        this.chunkLength = chunkLength;
        this.offset = offset;
        this.count = count + chunkIndex * chunkLength;
        cSLength = chars.length;
    }

    @Override
    public int length() {
        return cSLength;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index > cSLength - 1) {
            throw new IndexOutOfBoundsException();
        }
        return chunks[index / chunkLength][index % chunkLength];
    }

    @Override
    public CustomString subSequence(int start, int end) {
        if (start < 0 || end > cSLength || start > end) {
            throw new IndexOutOfBoundsException();
        }
        int chunkIndex = start / chunkLength;
        int offset = start % chunkLength;
        int count = end - start + 1;
        return new CustomString(chunks, chunkLength, offset, offset + count, chunkIndex);
    }

    @Override
    public String toString() {
        int sOffset = offset;
        StringBuilder s = new StringBuilder();
        for (int i = chunkIndex; i < chunkIndex + count / chunkLength + 1; i++) {
            for (int j = sOffset; j < chunkLength; j++) {
                if (j + (i * chunkLength) > count + sOffset - 1) break;
                s.append(this.chunks[i][j]);
            }
            sOffset = 0;
        }
        return s.toString();
    }

}
