package edu.technopolis;

import java.util.Arrays;

public class CustomString implements CharSequence {
    static final char NO_CHAR = '$'; //запрещённый символ
    private int lengthSegment; // длина отрезка
    StringBuilder stringBuilder, realSB; // для того чтобы красиво и быстро вывести
    private MyChars[] chars; //непосредственно сам массив
    private int size; // количество char  в нем


    public CustomString(char[] chars, int lengthSegment) {
        stringBuilder = new StringBuilder();
        this.lengthSegment = lengthSegment;
        createChars(chars);
    }

    public CustomString(Character[] chars, int lengthSegment) {
        char charsArr[] = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            charsArr[i] = chars[i] == null ? NO_CHAR : chars[i];
        }
        stringBuilder = new StringBuilder();
        this.lengthSegment = lengthSegment;
        createChars(charsArr);
    }

    public CustomString(String s, int lengthSegment) {
        this(s.toCharArray(), lengthSegment);
    }

    /**
     * Создание структурв
     * @param chars на вход массив char
     */
    private void createChars(char[] chars) {
        size = chars.length;
        this.chars = new MyChars[size / lengthSegment + (size % lengthSegment == 0 ? 0 : 1)];
        MyChars myChars = new MyChars(lengthSegment);
        myChars.add(0, chars[0]);
        stringBuilder.append("[").append(chars[0]);
        realSB = new StringBuilder();
        realSB.append(chars[0]);
        for (int i = 1; i < chars.length; i++) {
            realSB.append(chars[i]);
            if (i % lengthSegment == 0 || i == chars.length - 1) {
                if (i == chars.length - 1) {
                    if (i % lengthSegment == 0) {
                        stringBuilder.append("] [");
                        this.chars[(i - 1) / lengthSegment] = myChars;
                        myChars = new MyChars(lengthSegment);
                        myChars.add(i % lengthSegment, chars[i]);
                        stringBuilder.append(chars[i]).append("]");
                        this.chars[i / lengthSegment] = myChars;
                        return;
                    }
                    myChars.add(i % lengthSegment, chars[i]);
                    stringBuilder.append(chars[i]).append("]");
                    this.chars[(i - 1) / lengthSegment] = myChars;
                    return;
                }
                stringBuilder.append("] [");
                this.chars[(i - 1) / lengthSegment] = myChars;
                myChars = new MyChars(lengthSegment);
                myChars.add(i % lengthSegment, chars[i]);
                stringBuilder.append(chars[i]);


            } else {
                myChars.add(i % lengthSegment, chars[i]);
                stringBuilder.append(chars[i]);
            }

        }
    }

    @Override
    public int hashCode() {
        return realSB.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        CustomString ms = (CustomString) obj;
        return (ms.hashCode() != hashCode()) && ms.length() == size && ms.lengthSegment == lengthSegment;
    }

    @Override
    public int length() {
        return size;
    }

    @Override
    public char charAt(int index) {
        return myCharAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return mySubstring(start, end);
    }

    public String getStringLikeSegments() {
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return realSB.toString();
    }


    /**
     * обрезание строки от начального индекса до конца
     *
     * @param beginIndex - индекс с котрого начинается новая строка
     * @return обрезанная строка
     */
    public CustomString mySubstring(int beginIndex) {
        return mySubstring(beginIndex, size);
    }

    /**
     * обрезание строки от начального индекса до конечного
     *
     * @param beginIndex - индекс с котрого начинается новая строка
     * @param endIndex   - индекс перед которым кончается новая строка
     * @return обрезанная строка
     */
    public CustomString mySubstring(int beginIndex, int endIndex) {
        Character[] chars = new Character[endIndex - beginIndex];
        int start = beginIndex / lengthSegment;
        int finish = endIndex / lengthSegment;

        for (int i = start, index = 0; i <= finish && index < size; i++) {
            for (int j = i == start ? beginIndex % lengthSegment : 0; (j < lengthSegment) && (index < chars.length); j++, index++)
                chars[index] = myCharAt(i, j);
        }
        return new CustomString(chars, lengthSegment);
    }

    /**
     * символ по двум индексам
     *
     * @param i - номер сегмента
     * @param j - номер его индекса в сегменте
     * @return символ
     */
    public Character myCharAt(int i, int j) {
        return chars[i].get(j);
    }

    /**
     * символ по индексу
     *
     * @param index - номер символа, если бы массив был одномерный
     * @return символ
     */
    public Character myCharAt(int index) {
        return chars[index / lengthSegment].get(index % lengthSegment);
    }


    private void setChars(MyChars[] chars) {
        Arrays.fill(this.chars, null);
        this.chars = chars;
    }

    /**
     * устанавливает новую длину отрезков за O(n)
     *
     * @param newLengthSegment - длина новых отрезков
     * @throws NullPointerException - если предыдущий массив не инициализирован
     */
    public void setLengthSegment(int newLengthSegment)  {
        if (lengthSegment == newLengthSegment) return;
        MyChars[] chars = new MyChars[size / newLengthSegment + (size % newLengthSegment == 0 ? 0 : 1)];

        for (int i = 0; i < chars.length; i++) {
            chars[i] = new MyChars(newLengthSegment);
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        for (int index = 0; index < size; index++) {
            int i0 = index / lengthSegment;
            int j0 = index % lengthSegment;
            int i1 = index / newLengthSegment;
            int j1 = index % newLengthSegment;

            if (index != 0 && j1 == 0) {
                stringBuilder.append("] [");
            }
            chars[i1].add(j1, (char) myCharAt(i0, j0));
            stringBuilder.append((char) myCharAt(i0, j0)).append(" ");
        }

        stringBuilder.append("]");
        setChars(chars);
        lengthSegment = newLengthSegment;
    }

    public int getLengthSegment() {
        return lengthSegment;
    }

}


class MyChars {
    private Character[] chars;

    MyChars(Character[] chars) {
        this.chars = chars;
    }

    MyChars(int lengthSegment) {
        chars = new Character[lengthSegment];
        Arrays.fill(chars, CustomString.NO_CHAR);
    }


    Character[] getChars() {
        return chars;
    }

    void add(int index, char ch) {
        chars[index] = ch;
    }

    char get(int index) {
        return chars[index] == null ? CustomString.NO_CHAR : chars[index];
    }
}
