package main.java.edu.technopolis.advjava;

import java.io.Serializable;
import java.util.Arrays;

/**
 * UTF-8
 *
 * Требование "не дублируют объекты" понял так: не создавать новый объект при создании подстроки,
 * а копировать исходный и указывать смещение.
 *
 * Требование "не содержат ничего лишнего" понял так: смещение нужно указывать только по внешнему
 * массиву, и содержимое внутренних считать элементарной логической единицой, от которой можно
 * брать подстроку. Такой элемнетарной единицей будет "слово"
 */
public class CustomString implements CharSequence, Serializable{

    //Буду резать по пробелам => [слова][буквы слова]
    private final char[][] value;

    //Длина всего текста
    private final int length;

    //Позиция первого слова
    private final int first;

    //Количество слов
    private final int number;

    public CustomString(char[] value) {
        this.value = split(value, ' ');
        this.length = length();
        this.first = 0;
        this.number = number();
    }

    public CustomString(char[] value, int first) {
        this.value = split(value, ' ');
        this.length = length();
        this.first = first;
        this.number = number();
    }

    public CustomString(char[] value, int first, int number) {
        this.value = split(value, ' ');
        this.length = length();
        this.first = first;
        this.number = number;
    }

    public CustomString(char[][] value, int first, int number) {
        this.value = value;
        this.length = length();
        this.first = first;
        this.number = number;
    }

    @Override
    public int length() {
        int n = 0;
        for (char[] arr: value) {
            n += arr.length;
        }
        return n;
    }

    @Override
    public char charAt(int index) {
        if ((index < 0) || (index >= length)) {
            throw new StringIndexOutOfBoundsException(index);
        }
        for(char[] c: value) {
            if (index + 1 > c.length) {
                index -= c.length;
            } else {
                return c[index];
            }
        }
        return 0;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return this.subCustomString(start, end - start);
    }

    public CustomString subCustomString(int first, int number) {
        if ((first < 0) || (first >= this.number)) {
            throw new StringIndexOutOfBoundsException(first);
        } else if ((number < 0) || (number > this.number - first)) {
            throw new StringIndexOutOfBoundsException(number);
        }
        return new CustomString(this.value, this.first +first, number);
    }

    private int number() {
        return value.length - first;
    }

    private char[][] split(char[] in, char key) {
        int n = 0;
        for (char c: in) {
            if (c == key) {
                n++;
            }
        }
        char[][] value = new char[n + 1][];
        int first = 0;
        int last = 0;
        for (int i = 0; i < n; i++) {
            for ( ; in[last++] != key; );
            value[i] = Arrays.copyOfRange(in, first, last);
            first = last;
        }
        value[n] = Arrays.copyOfRange(in, first, in.length);
        return value;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = first; i < first + number; i++) {
            s.append(value[i]);
        }
        return s.toString();
    }

    //Для отладки
    public void show() {
        for (char[] arr: value) {
            for (char c: arr) {
                System.out.print(c);
            }
            //System.out.print('!');
        }
        System.out.println();
    }

    public static void main(String[] args) {
        char[] c = {'A', 'd', 'v', 'a', 'n', 'c', 'e', 'd', ' ',
                'j', 'a', 'v', 'a', ' ', 'c', 'o', 'u', 'r', 's', 'e', ' ',
                '2', '0', '1', '8'};

        CustomString customString = new CustomString(c);
        customString.show();
        System.out.println(customString);
        System.out.println(customString.charAt(2) + " Должно быть 'v'");
        System.out.println(customString.charAt(13) + " Должно быть ' '");
        System.out.println(customString.charAt(18) + " Должно быть 's'");
        CustomString subCS1 = customString.subCustomString(0, 2);
        CustomString subCS2 = customString.subCustomString(1, 2);
        CustomString subCS3 = customString.subCustomString(1, 3);
        CustomString subCS4 = subCS3.subCustomString(1, 2);
        System.out.println(subCS1);
        System.out.println(subCS2);
        System.out.println(subCS3);
        System.out.println(subCS4);
        CustomString subSequence = (CustomString) customString.subSequence(0, 2);
        System.out.println(subSequence);
    }
}
