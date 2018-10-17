package main.java.edu.technopolis.advjava;
import java.io.Serializable;
import java.util.Arrays;

public class CustomString implements CharSequence , Serializable {
    private char[][] string;
    private int offset;
    private final static int defaultLength = 8;//16;
    private int lineLength;
    private int length;


    public CustomString() {
        this.string = new char[0][0];
        this.offset = 0;
        this.lineLength = defaultLength;
        this.length = 0;
    }

    public CustomString(String str) {
        this.offset = 0;
        this.length = str.length();
        if (length % defaultLength == 0){
            lineLength = defaultLength;
        } else {
            lineLength = defaultLength + 1;
        }
        this.string = new char[length / lineLength + 1][lineLength];
        for(int i = 0; i < length; i++){
            this.string[i / lineLength][i % lineLength] = str.charAt(i);
        }
    }
    public CustomString(char[][] string, int lineLength, int offset, int length) {
        this.offset = offset;
        this.string = string;
        this.length = length;
        this.lineLength = lineLength;
    }

    public CustomString(char[][] string, int offset, int length) {
        this.string = string;
        this.offset = offset;
        this.length = length;
        this.lineLength = defaultLength;
    }

    public CustomString(CustomString original) {
        this.string = original.string;
        this.offset = original.offset;
        this.lineLength = original.lineLength;
        this.length = length();
    }

    public CustomString(char value[]) {
        this.offset = 0;
        this.length = value.length;
        if (length % defaultLength == 0){
            lineLength = defaultLength;
        } else {
            lineLength = defaultLength + 1;
        }
        this.string = new char[length / lineLength][lineLength];
        for(int i = 0; i < length; i++){
            this.string[i / lineLength][i % lineLength] = value[i];
        }
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) {
        int line = (offset + index) / lineLength;
        int elem = (offset + index) % lineLength;
        return string[line][elem];
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
        char[][] newString = Arrays.copyOfRange(string, from, to);
        return new CustomString(newString, lineLength,(start + this.offset) % lineLength, end - start);
    }

    @Override
    public String toString() {
        var buffer = new StringBuilder();
        for(int i = offset; i < length + offset; i++){
            buffer.append(this.string[i / lineLength][i % lineLength]);
        }
        return buffer.toString();
    }
}
