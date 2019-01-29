package edu.technopolis.advjava;

import java.io.Serializable;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {
    private int len, start, CHUNK_LEN = 16;

    private char[][] data;

    public CustomString(String input) {
        if (input.length() == 0)
            return;

        this.start = 0;
        this.len = input.length();

        if (input.length() % CHUNK_LEN == 0)
            this.data = new char[input.length() / CHUNK_LEN][CHUNK_LEN];
        else
            this.data = new char[input.length() / CHUNK_LEN + 1][];

        for (int i = 0; i < input.length() / CHUNK_LEN; i++) {
            this.data[i] = new char[CHUNK_LEN];
        }
        this.data[input.length() / CHUNK_LEN] = new char[input.length() % CHUNK_LEN];

        for (int i = 0; i < input.length(); i++) {
            this.data[i / CHUNK_LEN][i % CHUNK_LEN] = input.charAt(i);

        }
    }

    public CustomString(int start, int amount, char[][] data) {
        this.start = start;
        this.len = amount;
        this.data = data;
    }

    @Override
    public int length() {
        return this.len;
    }

    @Override
    public char charAt(int index) {
        return this.data[(this.start + index) / CHUNK_LEN][(this.start + index) % CHUNK_LEN];
    }

    @Override
    public CustomString subSequence(int start, int end) {
        return new CustomString(this.start + start, end - start, this.data);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.length(); i++) {
            result.append(this.data[(this.start + i) / CHUNK_LEN][(this.start + i) % CHUNK_LEN]);
        }
        return result.toString();
    }

    private void write() {
        System.out.println(toString());
    }

    public static void main(String[] args) {
        CustomString test = new CustomString("It was in July, 1805, and the speaker was the well-known Anna " +
                "Pavlovna Scherer, maid of honor and favorite of the Empress Marya Fedorovna.");

        CustomString subStr = test.subSequence(3, 15);
        CustomString subStr2 = test.subSequence(50, 60);
        CustomString subStr3 = test.subSequence(70, 60);


        test.write();
        System.out.println(test.len);
        subStr.write();
        System.out.println(subStr.len);
        subStr2.write();
        System.out.println(subStr2.len);
        subStr3.write();
    }
}
