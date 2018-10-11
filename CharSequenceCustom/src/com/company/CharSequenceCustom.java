package com.company;

import java.lang.CharSequence;

public class CharSequenceCustom implements CharSequence {
    private int length[] = new int[100];
    //0 - смещение по строкам сверху
    //1 - смещение по строкам  снизу
    //2 - смещение в строке справа
    //3 - смещение в строке слева
    private int offset[] = new int[4];
    private int maxSize[] = new int[100];
    private char[][] data = new char[100][100];

    CharSequenceCustom() {
        length[0] = 0;
        offset[0] = 0;
        offset[1] = 1;
        offset[2] = 0;
        offset[3] = 0;
        maxSize[0] = 2;
    }

    CharSequenceCustom(String str) {
        length[0] = str.length();
        offset[0] = 0;
        offset[1] = 1;
        offset[2] = 0;
        offset[3] = str.length();
        for (int i = 0; i < str.length(); i++) {
            data[0][i] = str.charAt(i);
        }
    }

    CharSequenceCustom(char data[][], int length[], int offset[]) {
        this.data = data;
        this.length = length;
        this.offset = offset;
    }

    public int lines() {
        return offset[1] - offset[0];
    }

    public int length() {
        int size = 0;
        for (int i = offset[0]; i < offset[1]; i++) {
            size += lengthLine(i);
        }
        return size;
    }

    public int lengthLine(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index > lines() - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (offset[0] == (offset[1] - 1)) {
            return offset[3] - offset[2];
        } else if (index == 0) {
            return length[(offset[0] + index)] - offset[2];
        } else if (index == (lines() - 1)) {
            return offset[3];
        } else {
            return length[(offset[0] + index)];
        }
    }

    public char charAt(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }

        int startLine = offset[0];
        int endLine = offset[1];
        while (index > lengthLine(startLine) && startLine < endLine) {
            index = index - lengthLine(startLine);
            startLine++;
        }
        if (startLine == endLine || ((startLine - endLine) == 1) && index < lengthLine(startLine)) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            return data[startLine][index];
        }
    }

    public char charAt(int line, int index) throws ArrayIndexOutOfBoundsException {
        if (line < 0 || line > lines() || index > lengthLine(line)) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            if (index == 0) {
                return data[(offset[0] + index)][index + offset[3]];
            } else if (index == lines() - 1) {
                return data[(offset[0] + index)][index + offset[4]];
            } else {
                return data[(offset[0] + index)][index];
            }
        }
    }

    public CharSequenceCustom subSequence(int start, int end) {
        int startLine = offset[0];
        while (start > lengthLine(startLine) && startLine < offset[1]) {
            start -= lengthLine(startLine);
            end -= lengthLine(startLine);
            startLine++;
        }
        if (startLine == offset[0]) {
            start += offset[2];
        }
        int endLine = startLine;
        while (end > lengthLine(endLine) && startLine < offset[1]) {
            end -= lengthLine(startLine);
            endLine++;
        }

        int off[] = {startLine, endLine + 1, start, end};
        return new CharSequenceCustom(data, length, off);
    }

    public void addLIne(String str) {
        for (int i = 0; i < str.length(); i++) {
            data[offset[1]][i] = str.charAt(i);
        }
        offset[3] = str.length();
        offset[1]++;
    }

    public void print() {
        for (int i = offset[0]; i < offset[1]; i++) {
            if (offset[0] == (offset[1] - 1)) {
                for (int j = offset[2]; j < offset[3]; j++)
                    System.out.print(data[i][j]);
                System.out.println();
            } else if (i == offset[0]) {
                for (int j = offset[2]; j < (offset[2] + lengthLine(i)); j++)
                    System.out.print(data[i][j]);
                System.out.println();
            } else if (i == (offset[1] - 1)) {
                for (int j = 0; j < offset[3]; j++) {
                    System.out.print(data[i][j]);
                }
                System.out.println();
            } else {
                System.out.println(data[i]);
            }
        }
    }
}
