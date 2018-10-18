package edu.technopolis;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) throws Exception {
        //Чтение книги с файла
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("res/voina.txt"), StandardCharsets.UTF_8));
        StringBuilder s = new StringBuilder();
        while (reader.ready()) {
            s.append(String.valueOf((char) reader.read()));
        }
        //Вывод в качестве экземпляра всей книги "Война и Мир"
        BufferedWriter writer1 = new BufferedWriter(new FileWriter("res/out1.txt"));
        CharSequence string1 = new CustomString(s.toString());
        writer1.write(string1.toString());
        writer1.close();


        //Вывод в качестве экземпляра второго тома всей книги "Война и Мир"
        BufferedWriter writer2 = new BufferedWriter(new FileWriter("res/out2.txt"));
        CharSequence string2 = string1.subSequence(277602, 516065);
        writer2.write(string2.toString());
        writer2.close();


        //Вывод в качестве экземпляра предпоследней главы последнего тома всей книги "Война и Мир"
        BufferedWriter writer3 = new BufferedWriter(new FileWriter("res/out3.txt"));
        CharSequence string3 = string1.subSequence(3249716, 3269740);
        writer3.write(string3.toString());
        writer3.close();

        //Вывод в качестве экземпляра предпоследней главы второго тома всей книги "Война и Мир"
        BufferedWriter writer4 = new BufferedWriter(new FileWriter("res/out4.txt"));
        CharSequence string4 = string2.subSequence(208784, 221711);
        writer4.write(string4.toString());
        writer4.close();
        //Работа с простым примером
        CustomString string5 = new CustomString("Hello_world!");
        System.out.println(string5.toString());
        //Работа с простым примером #2
        CharSequence string6 = string5.subSequence(5, 8);
        System.out.println(string6.toString());
        //Работа с простым примером #3
        CharSequence string7 = string6.subSequence(1, 2);
        System.out.println(string7.toString());
    }
}
