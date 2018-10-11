package edu.technopolis;

import java.util.Arrays;
import java.util.stream.IntStream;

public class CustomString implements CharSequence { // Имплементим нужный нам интерфейс
    private final int offset; // Начало строки
    private final int count; // Кол-во символов

    private final char[][] m; // Массив чанков
    private final int length; // Длина всех чанков

    // Конструктор для создания новой строки
    CustomString(String s) {
        length = s.length();
        offset = 0; // Так как находимся в начале этой строки
        count = length;
        m = new char[(int) Math.ceil(Math.sqrt(length))][(int) Math.ceil(Math.sqrt(length))]; // вычисляем размеры прямоугольника, куда положим наш текст
        int c = 0; // Счётчик символов. Если он больше длины строки, ничего более не записываем
        for (int i = 0; i < m.length; i++) {
            if (c >= s.length()) // Обрываем, если дошли до конца
                break;
            for (int j = 0; j < m[i].length; j++) {
                if (c >= s.length()) // Обрываем, если дошли до конца
                    break;
                m[i][j] = s.charAt(c); // Записываем в наш чанк символ
                c++;
            }

        }
    }

    // Конструктор для создания новой строки на основе старой
    private CustomString(char[][] m, int length, int offset, int count) {
        this.count = count;
        this.offset = offset;
        this.m = m;
        this.length = length;

    }

    // Получение длины строки
    @Override
    public int length() {
        return length;
    }

    // Получение символа по индексу
    @Override
    public char charAt(int index) {
        return m[((offset + index) / (m[0].length))][((offset + index) % (m[0].length))]; // Получаем символ исходя из нашего индекста со сдвигом вправо (так как начальное значение - offset)
    }

    // Получение новой строки на основе старой
    public CustomString subCustomString(int start, int end) throws Exception {
        if (start > end) // Смотрим, что левая граница меньше правой
            throw new Exception("Left index can't be more than right index!");
        if (end >= length) // Смотрим, что правая граница (итератор находится на последнем элементе) не вышла за длину строки
            throw new Exception("The end index is too big!");
        if (start < 0)
            throw new Exception("Left index can't be less than 0!"); // Смотрим, что левая граница неотрицательна
        start += offset; // Учитываем начальные значения в чанке
        end += offset; // Учитываем начальные значения в чанке
        int length = end / (m[0].length) - start / (m[0].length) + 1; // Вычисляем количество чанков
        char[][] newM = new char[length][]; // Создаем подмассив чанков
        int st = start / m[0].length; // Создаём итератор, который будет перемещатся по чанкам (не по символам!)
        int tmp = st * m[0].length; // Вычисляем сколько символов останется позади нашего первого чанка
        for (int i = 0; i < newM.length; i++) {
            if (st > end / m[0].length) // Обрываем, если дошли до конца
                break;
            newM[i] = m[st];
            st++;
        }

        return new CustomString(newM, length(), start - tmp, end - start + 1); // Возвращаем новый экземпляр нашей строки

    }

    //Получение строки из чанков
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(); // Для оптимизации
        int tmp = m[0][0] == '\uFEFF' ? 1 : 0; // В начале файла стоит \uFEFF, поэтому отбрасываем его, если читаем введенную строку
        for (int i = 0; i < count; i++) {
            s.append(charAt(tmp)); // Вместо того, чтобы заного перебирать массив, использвуем готовую функцию
            tmp++;
            if (tmp >= count) // Обрываем, если дошли до конца
                break;

        }
        return s.toString(); // Возвращаем строку
    }

    //Useless
    @Override
    public IntStream chars() {
        return null;
    }

    //Useless
    @Override
    public IntStream codePoints() {
        return null;
    }

    //Useless
    @Override
    public CharSequence subSequence(int offset, int count) {
       /* offset += offset;
        int length = (offset + count) / (m[0].length) - offset / (m[0].length) + 1;
        char[][] newM = new char[length][];
        int st = offset / m[0].length;
        int tmp = st * m[0].length;
        for (int i = 0; i < newM.length; i++) {
            if (st > (offset + count) / m[0].length)
                break;
            newM[i] = m[st];
            st++;
        }

        return new CustomString(newM, length(), offset - tmp, count);
*/
        return null;
    }

}
