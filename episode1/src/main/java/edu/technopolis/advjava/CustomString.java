package edu.technopolis.advjava;

import java.io.Serializable;

public class CustomString implements CharSequence, Serializable {

    /*
    нарезка по словам
    была сначала идея хранить массив слов
    и чтобы у каждого экземпляра класса был внутри массив индексов слов
    в таком случае, "мама мыла раму" и "мама раму мыла" -- не выделяли бы много памяти, а в
    экземплярах хранилось бы [0,1,2] и [0,2,1]

    Но потом понял, что конструктор был бы очень нагружен и будет нужно слишком долго над ним думать.
    Мб потом переделаю так

    Но сейчас будем хранить на экземплярах индекс первого слова и длину
    */

    private final char[][] value;
    private final int count; //длина в словах
    private final int length; // длина в символах для length()
    private final int wordIndex; // индекс первого слова

    public CustomString(char[][] value, int wordIndex, int count){
        this.value = value;
        this.wordIndex = wordIndex;
        this.length = calcLength();
        this.count = count;
    }

    public CustomString(char[][] value, int count){
        this(value,0,count);
    }

    public CustomString(String value){
        if (value == null){
            throw new NullPointerException();
        }
        String[] splitted = value.split(" ");
        char[][] v = new char[splitted.length][];
        for (int i=0;i<splitted.length;i++){
            v[i] = splitted[i].toCharArray();
        }
        this.value = v;
        this.wordIndex = 0;
        this.length = calcLength();
        this.count = v.length;
    }

    @Override
    public int length(){
        return length;
    }

    private int calcLength(){
        int len = 0;
        for (char[] a : value){
            len += a.length;
        }
        return len;
    }

    public int wordCount(){
        return count;
    }

    @Override
    public char charAt(int index){
        for(char[] c: value) {
            if (index > c.length){
                index -= (c.length + 1);
            } else if (index == c.length){
                return ' ';
            }
            else {
                return c[index];
            }
        }
        return 0;
    }

    @Override
    public CustomString subSequence(int start, int end) {
        return new CustomString(this.value, start + this.wordIndex, end - start);
    }

    public CustomString subString(int index, int count){
        return new CustomString(this.value,this.wordIndex + index, count);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = wordIndex; i < count + wordIndex; i++){
            sb.append(value[i]);
            sb.append(' ');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        CustomString cs = new CustomString("Здравствуй, безумный и бренный мир!");
        System.out.println(cs);
        System.out.println(cs.charAt(11)); // поймался пробел
        System.out.println(cs.charAt(12)); // б
        System.out.println(cs.wordCount()); // 5 слов
        System.out.println(cs.length()); //31 символ

        CustomString sub = cs.subString(1,3); // три слова начиная со второго "безумный и бренный"
        System.out.println(sub);

        System.out.println(sub.subString(2,1)); // соответственно, бренный
    }
}
