package main.java.edu.technopolis.advjava;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
 * Класс хранит в себе индексы конкретный слов в книге,
 * что позволяет хранить в себе только 1 слово и 4 его индекса.
 * Каждый индекс соответсвует следующему:
 * 1 - том в котором находится слово
 * 2 - страница на котором находится это слов
 * 3 - строка где есть это слово
 * 4 - номер слова начиная слева
 * Так же клаас хранит в себе количество слов NumberOfWord
 * и ArrayList, где хранятся не повторяющиеся слова.
 */
public class CustomString implements CharSequence {

    /*
     * Двумерный массив, который хранит в себе индексы слов.
     * В строке массива указываются индексы только конкретного слова.
     * Тоесть 1 строка массива - 1 слово.
     */
    private char[][] chars;
    /*
     * int который хранит колчество слов
     */
    private int NumberOfWords = 0;
    /*
     * ArrazList который хранит уникальные слова
     */
    private ArrayList<String> words = new ArrayList<>();


    public CustomString(String page){
        chars = new char[10][10];
        //Я решил делить текст только по пробелам из за проблем с цитатами и другими символами
        for (String s: page.split("[ ]")) {
            if (s.equals("")){
                continue;
            }
            NumberOfWords++;
            int indexOfWord = this.words.indexOf(s);
            if( indexOfWord== -1){
                this.words.add(s);
                indexOfWord = this.words.indexOf(s);
                //Тут мы создаем индексы в массиве для конкретного слова.
                // Я взял небольшие размеры для страниц, томов и т.д.
                CreateReferenceInWords(indexOfWord,NumberOfWords/200 + 1,NumberOfWords/50 + 1,
                        NumberOfWords/5 + 1,NumberOfWords%20);
            } else {
                CreateReferenceInWords(indexOfWord,NumberOfWords/200 + 1,NumberOfWords/50 + 1,
                        NumberOfWords/5 + 1,NumberOfWords%20);
            }
        }
    }
    /*
     * Создаем индексы для слова в двумерном массиве.
     * lastElementIndex - эта переменная содержит то
     * где находится последний элемент в строке
     */
    private void CreateReferenceInWords(int rowIndex, int tom, int page, int rowCount, int wordCount){
        char[] row = this.chars[rowIndex];
        int lastElementIndex = chars[rowIndex][0];
        row[lastElementIndex + 1] = (char)tom;
        row[lastElementIndex + 2] = (char)page;
        row[lastElementIndex + 3] = (char)rowCount;
        row[lastElementIndex + 4] = (char)wordCount;
        lastElementIndex += 4;
        chars[rowIndex][0] = (char) lastElementIndex;
        if(lastElementIndex > this.chars[rowIndex].length - 5){
            this.resizeRowForNewWord(rowIndex);
        }
        if(rowIndex > this.chars.length - 5){
            this.resizeColumn();
        }
    }

    /*
     * Расширяем строку, если наши индексы станут больше чем размер строки
     */
    private void resizeRowForNewWord(int rowIndex){
        this.chars[rowIndex] = Arrays.copyOf(chars[rowIndex], this.chars[rowIndex].length + 9);
    }

    /*
     * Расширяем количество строк, если слов станет больше чем строчек
     */
    private void resizeColumn(){
        //Создаем новым двумерный массив с запасом в 20 слов и размерность 8
        //чтобы, если попалось совсем уникальное слово, которое встретилось 1 раз
        //не занимало много места
        char[][] newChars = new char[this.chars.length + 20][8];
        for(int i = 0; i < this.chars.length; i++) {
            newChars[i] = Arrays.copyOf(this.chars[i], this.chars[i].length);
        }
        this.chars = newChars;
    }

    /*
     * Метод для проверки, есть ли слово в строке. Используется в toString.
     */
    private boolean checkWord(int numberOfWord, char[] row){
        int tom = numberOfWord/200 + 1;
        int chapter = numberOfWord/50 + 1;
        int rowCount = numberOfWord/5 + 1;
        int wordCount = numberOfWord%20;
        for (int i = 1; i < row.length; i+=3) {
            if(tom == (int)row[i] && chapter == (int)row[i+1]
                    && rowCount == (int)row[i+2] && wordCount == (int)row[i+3]){
                return true;
            }
        }
        return false;
    }

    /*
     * Метод просто выведет состояние массива, чтобы наглядно посмотреть индексы
     */
    public void sout(){
        for (int i = 0; i < this.words.size(); i++) {
            System.out.print(this.words.get(i) + " ");
            for (int j = 0; j < this.chars[i].length ; j++) {
                System.out.print((int)this.chars[i][j] + " ");
            }
            System.out.println();
        }
    }

    /*
     * Метод CharSequence, но в моей реалзиации он бесполезен,
     * Так как за слово у меня отвечает 4 поля. А если взять
     * index как номер слова, то само слово я не могу вернуть.
     */
    @Override
    public char charAt(int index) {
        if(index > this.NumberOfWords){
            return ' ';
        }
        else {
            return (char) index;
        }
    }

    /*
     * Возвращение подстроки из моего класса.
     * За startWord принимается номер слова откуда начинается подстрока
     * А за endWord принимается номер слова где заканчивается подстрока
     */
    @Override
    public CharSequence subSequence(int startWord, int endWord) {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for (int i = startWord; i < endWord; i++) {
            for (int j = 0; j < this.words.size(); j++) {
                if (flag) {
                    break;
                }
                else {
                    if (checkWord(i,this.chars[j])) {
                        sb.append(this.words.get(j) + " ");
                        flag = true;
                        break;
                    }
                }
            }
            flag = false;
        }
        return new CustomString(sb.toString());
    }

    /*
     * Вернет количество уникальных слов.
     */
    @Override
    public int length() {
        return words.size();
    }

    /*
     * Реализация метода toString, где начиная с 1 слова
     * я начинаю распутывать свой массив.
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for (int i = 1; i < this.NumberOfWords + 1 ; i++) {
            for (int j = 0; j < this.words.size(); j++) {
                if (flag) {
                    break;
                }
                else {
                    if (checkWord(i,this.chars[j])) {
                        sb.append(this.words.get(j) + " ");
                        flag = true;
                        break;
                    }
                }
            }
            flag = false;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        //Example 1 with small text
        System.out.println("Example 1");
        StringBuilder largetext1 = new StringBuilder();
        try (Scanner sc = new Scanner(new File("page.txt"), "UTF-8")) {
            while (sc.hasNextLine()) {
                largetext1.append(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CustomString customString = new CustomString(largetext1.toString());
        System.out.println("How array look like:");
        customString.sout();
        System.out.println("Method to String:");
        System.out.println(customString.toString());
        System.out.println("Method to subSequence:");
        System.out.println(customString.subSequence(1,100).toString());

        //Example 2 with medium text
        System.out.println("Example 2");
        StringBuilder largetext2 = new StringBuilder();
        try (Scanner sc = new Scanner(new File("chapter.txt"), "UTF-8")) {
            while (sc.hasNextLine()) {
                largetext2.append(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CustomString customString2 = new CustomString(largetext2.toString());
        System.out.println("How array look like:");
        customString2.sout();
        System.out.println("Method to String:");
        System.out.println(customString2.toString());
        System.out.println("Method to subSequence:");
        System.out.println(customString2.subSequence(1,500).toString());

        //Example 3 with large text
        System.out.println("Example 3");
        StringBuilder largetext3 = new StringBuilder();
        try (Scanner sc = new Scanner(new File("book.txt"), "UTF-8")) {
            while (sc.hasNextLine()) {
                largetext3.append(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CustomString customString3 = new CustomString(largetext3.toString());
        System.out.println("How array look like:");
        customString3.sout();
        System.out.println("Method to String:");
        System.out.println(customString3.toString());
        System.out.println("Method to subSequence:");
        System.out.println(customString3.subSequence(1,1000).toString());
    }
}
