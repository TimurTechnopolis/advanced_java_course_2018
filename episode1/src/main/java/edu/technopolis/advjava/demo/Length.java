package edu.technopolis.advjava.demo;

public class Length {
    public static void main(String[] args) {
        String str = "\uD835\uDD38";
        System.out.println(str);
        //длина строки не соответсвует кол-ву выведенных символов
        System.out.println(str.length());
        //причина - суррогатные символы. Чтобы они не учитывались, надо использовать codePointCount
        System.out.println(str.codePointCount(0, 2));
    }
}
