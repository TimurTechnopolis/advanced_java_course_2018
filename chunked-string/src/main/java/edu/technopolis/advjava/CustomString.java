package edu.technopolis.advjava;

import java.io.Serializable;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {

    private char[][] chunks;

    public CustomString() {
        this("");
    }

    public CustomString(String str) {
        this(str.toCharArray());
    }

    public CustomString(char[] chars) {
        chunks = new char[1][];
        chunks[0] = chars;
    }

    private CustomString(char[][] chunks) {
        this.chunks = chunks;
    }

    @Override
    public int length() {
        int len = 0;
        for (var chunk : chunks) {
            len += chunk.length;
        }
        return len;
    }

    @Override
    public char charAt(int index) {
        var chunk = 0;
        while (chunk < chunks.length) {
            if (index < chunks[chunk].length) {
                return chunks[chunk][index];
            } else {
                index -= chunks[chunk].length;
                chunk++;
            }
        }
        throw new RuntimeException("Wrong index!");
    }

    @Override
    public CustomString subSequence(int start, int end) {
        var leftChunk = 0;
        while (leftChunk < chunks.length) {
            if (start < chunks[leftChunk].length) {
                break;
            } else {
                start -= chunks[leftChunk].length;
                end -= chunks[leftChunk].length;
                leftChunk++;
            }
        }

        if (start == 0 && end == chunks[leftChunk].length) { // chunk already exists
            return new CustomString(chunks[leftChunk]);
        } else if (end <= chunks[leftChunk].length) { // [start, end) IN one chunk
            // [0, start) + [start, end) + [end, N]
            char[][] split = splitChunk(chunks[leftChunk], start, end);
            if (start == 0) {
                replaceChunk(leftChunk, split[1], split[2]);
                return new CustomString(split[1]);
            } else if (end == chunks[leftChunk].length) {
                replaceChunk(leftChunk, split[0], split[1]);
                return new CustomString(split[1]);
            } else {
                replaceChunk(leftChunk, split);
                return new CustomString(split[1]);
            }
        } else { // [start, chunk_N_end] + [chunk_N+1_start, chunk_N+1_end] + ... + [chunk_M_start, end)
            // [0, start) + [start, N] + []
            char[][] leftSplit = splitChunk(chunks[leftChunk], start, chunks[leftChunk].length);

            var rightChunk = leftChunk;
            while (rightChunk < chunks.length) {
                if (end <= chunks[rightChunk].length) {
                    break;
                } else {
                    end -= chunks[rightChunk].length;
                    rightChunk++;
                }
            }
            // [] + [0, end) + [end, N]
            char[][] rightSplit = splitChunk(chunks[rightChunk], 0, end);

            if (start != 0) {
                replaceChunk(leftChunk, array(leftSplit[0], leftSplit[1]));
                rightChunk++; // because of replacement
            }
            if (end != chunks[rightChunk].length) {
                replaceChunk(rightChunk, array(rightSplit[1], rightSplit[2]));
            }

            var size = rightChunk - leftChunk + 1;
            char[][] newChunks = new char[size][];
            newChunks[0] = leftSplit[1];
            System.arraycopy(chunks, leftChunk + 1, newChunks, 1, size - 2);
            newChunks[size - 1] = rightSplit[1];
            return new CustomString(newChunks);
        }
    }

    @Override
    public String toString() {
        var buffer = new StringBuilder();
        for (var chunk : chunks) {
            buffer.append(chunk);
        }
        return buffer.toString();
    }

    char[][] getChunks() { // package-private for testing
        return chunks;
    }

    private void replaceChunk(int position, char[]... args) {
        var newChunks = new char[chunks.length - 1 + args.length][];
        System.arraycopy(chunks, 0, newChunks, 0, position);
        System.arraycopy(args, 0, newChunks, position, args.length);
        System.arraycopy(chunks, position + 1, newChunks, position + args.length, chunks.length - position - 1);
        chunks = newChunks;
    }

    private static char[][] splitChunk(char[] chunk, int start, int end) {
        var ch1 = chunkCopy(chunk, 0, start);
        var ch2 = chunkCopy(chunk, start, end - start);
        var ch3 = chunkCopy(chunk, end, chunk.length - end);
        return array(ch1, ch2, ch3);
    }

    private static char[][] array(char[]... args) {
        return args;
    }

    private static char[] chunkCopy(char[] src, int from, int length) {
        var chunk = new char[length];
        System.arraycopy(src, from, chunk, 0, length);
        return chunk;
    }

}
