package main.java.edu.technopolis.advjava;
import java.io.Serializable;
import java.util.Arrays;

public class CustomString implements CharSequence , Serializable {
    private final char[][] chunks;
    
    private final int length; // размер всего объекта

    private final int offset; // смещение от начального символа

    private final int lineLength; // длина одной строки

    /** Планируется, что длина чанка задастся пользователем
     * в ином случае задается дефолтная длина строки в тексте
     * - 128 символов(просто это не так чтоб слишком много, но в то же время
     * и не слишком мало для одной строки*/
    private final static int defaultLength = 128;

    public CustomString(String str) {
        this.offset = 0;
        this.length = str.length();
        if (length % defaultLength == 0){
            lineLength = defaultLength;
        } else {
            lineLength = defaultLength + 1;
        }
        this.chunks = new char[length / lineLength + 1][lineLength];
        for(int i = 0; i < length; i++){
            this.chunks[i / lineLength][i % lineLength] = str.charAt(i);
        }
    }
    
    public CustomString(char[][] chunks, int lineLength, int offset, int length) {
        this.offset = offset;
        this.chunks = chunks;
        this.length = length;
        this.lineLength = lineLength;
    }

    public CustomString(char[][] chunks, int offset, int length) {
        this(chunks, offset, length, defaultLength);
    }

    public CustomString(CustomString original) {
        this.chunks = original.chunks;
        this.offset = original.offset;
        this.lineLength = original.lineLength;
        this.length = length();
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) {
        int line = (offset + index) / lineLength;
        int elem = (offset + index) % lineLength;
        return chunks[line][elem];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (start < 0 || start > end || end > length) {
            throw new StringIndexOutOfBoundsException(
                    "Error");
        }
        if (start == 0 && end == length) {
            return this;
        }
        int from = (start + this.offset) / lineLength;
        int to = 1 + (end + this.offset) / lineLength;
        char[][] newString = Arrays.copyOfRange(chunks, from, to);
        return new CustomString(newString, lineLength,(start + this.offset) % lineLength, end - start);
    }

    @Override
    public String toString() {
        var buffer = new StringBuilder();
        for(int i = offset; i < length + offset; i++){
            buffer.append(this.chunks[i / lineLength][i % lineLength]);
        }
        return buffer.toString();
    }
}
