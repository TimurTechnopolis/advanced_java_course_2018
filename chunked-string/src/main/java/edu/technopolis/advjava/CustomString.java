package edu.technopolis.advjava;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 *
 * Представляет собой класс для хранение строк большой длины
 *     Минимальная структурная единица в таком случае не символ, а чанк
 *     При инициализации строка разбивается на чанки заданной длины (если не указано -- длиной 1/100 от общей)
 *
 *     При выделении подстроки формируется массив ссылок на старые чанки, которые вошли в подстроку
 *             -> не дублируются данные
 *
 *     При сериализации объекта, данные из родительской строки, которые НЕ вошли в дочернюю
 *             -> не участвуют в сериализации
 *
 *     Проверка с большим текстом в main()
 *
 *     @author Roman A. Nosov
 */
public class CustomString implements CharSequence, Serializable {
    private final char[][] value;

    public CustomString(char[][] value){
        this.value = value;
    }

    public CustomString(String value){
        this(value, value.length() / 100);
    }

    public CustomString(String value, int chunkLength){
        if (value == null){
            throw new NullPointerException();
        }
        List<String> splitted = new ArrayList<>();

        for (int start = 0; start < value.length(); start += chunkLength) {
            splitted.add(value.substring(start, Math.min(value.length(), start + chunkLength)));
        }
        char[][] v = new char[splitted.size()][];
        for (int i=0;i<splitted.size();i++){
            v[i] = splitted.get(i).toCharArray();
        }
        this.value = v;
    }

    @Override
    public int length(){
        return value.length;
    }

    @Override
    public char charAt(int index){
        for(char[] c: value) {
            if (index < c.length) {
                return c[index];
            }
            else {
                index -= c.length;
            }
        }
        return 0;
    }

    @Override
    public CustomString subSequence(int start, int end) {
        int len = end - start + 1;
        return this.subCustomString(start,len);
    }

    public CustomString subCustomString(int start, int len){
        char[][] v = new char[len][];
        System.arraycopy(value, start, v, 0, len);
        return new CustomString(v);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (char[] aValue : value) {
            sb.append(aValue);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {

        /*
            Попробуем получить данные из большого текста, замерить размер сериализованного объекта.
            Затем получить подстроку и замерить её сериализоаванный размер
        */
        String text = new String(Files.readAllBytes(Paths.get("book.txt")), StandardCharsets.UTF_8);

        CustomString cs = new CustomString(text, 10000);
        ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream("obj1.dat"));
        oos1.writeObject(cs);
        oos1.close();

        CustomString cs2 = cs.subCustomString(10,50); // 50 чанков начиная с 10
        ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream("obj2.dat"));
        oos2.writeObject(cs2);
        oos2.close();

        File f1 = new File("obj1.dat");
        File f2 = new File("obj2.dat");
        System.out.println("Размер сериализованного объекта с целой книгой: " + f1.length());
        System.out.println("Размер 'подстроки' из 50 чанков: " + f2.length());
    }
}
