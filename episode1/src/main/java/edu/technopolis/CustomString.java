package edu.technopolis;

import java.io.Serializable;
import java.util.stream.IntStream;

public class CustomString implements CharSequence, Serializable { // Имплементим нужный нам интерфейс
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
        copy:
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (c >= s.length()) // Обрываем, если дошли до конца
                {
                    break copy;
                }
                m[i][j] = s.charAt(c); // Записываем в наш чанк символ
                c++;
            }

        }
    }

    // Конструктор для создания новой строки на основе старой
    private CustomString(char[][] m, int offset, int count) {
        this.count = count;
        this.offset = offset;
        this.m = m;
        this.length = offset + count;

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
    public CharSequence subSequence(int start, int end) {
        if (start > end) // Смотрим, что левая граница меньше правой
        {
            System.out.println("Left index can't be more than right index!");
            return null;
        }

        if (end >= length) // Смотрим, что правая граница (итератор находится на последнем элементе) не вышла за длину строки
        {
            System.out.println("The end index is too big!");
            return null;
        }
        if (start < 0) {
            System.out.println("Left index can't be less than 0!"); // Смотрим, что левая граница неотрицательна
            return null;
        }
        start += offset; // Учитываем начальные значения в чанке
        end += offset; // Учитываем начальные значения в чанке
        int length = end / (m[0].length) - start / (m[0].length) + 1; // Вычисляем количество чанков
        char[][] newM = new char[length][]; // Создаем подмассив чанков
        int st = start / m[0].length; // Создаём итератор, который будет перемещатся по чанкам (не по символам!)
        int tmp = st * m[0].length; // Вычисляем сколько символов останется позади нашего первого чанка
        System.arraycopy(m, st, newM, 0, end / m[0].length - st + 1); //Копируем чанки
        return new CustomString(newM, start - tmp, end - start + 1); // Возвращаем новый экземпляр нашей строки

    }

    //Получение строки из чанков
    @Override
    public String toString() {
        if (m.length == 0) { //Проверка на пустую строку
            return "";
        }
        StringBuilder s = new StringBuilder(); // Для оптимизации
        int tmp = m[0][0] == '\uFEFF' ? 1 : 0; // В начале файла стоит \uFEFF, поэтому отбрасываем его, если читаем введенную строку
        for (int i = 0; i < count; i++) {
            s.append(charAt(tmp)); // Вместо того, чтобы заного перебирать массив, использвуем готовую функцию
            tmp++;
            if (tmp >= count) // Обрываем, если дошли до конца
            {
                break;
            }

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
}
