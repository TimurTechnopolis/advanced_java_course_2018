package edu.technopolis;
import java.io.Serializable;

public class CustomString implements CharSequence, Serializable {
    private char[][] chunks;
    int offset;
    int count;
    private int length;
    int chunkSize;

    public CustomString(String str) {
        this.count = str.length();
        this.length = str.length();
        this.chunkSize =(int)Math.sqrt(this.length)+1;
        this.chunks = new char[chunkSize][chunkSize];
        int k = 0;
        for (int i = 0; i < chunkSize; i++){
            for (int j = 0; j < chunkSize; j++) {
                if (k < length) {
                    chunks[i][j] = str.charAt(k);
                    k++;
                }
            }
        }
    }

    public CustomString(char[][] chunks, int offset, int count, int chunckSize) {
        this.chunks = chunks;
        this.offset = offset;
        this.count = count;
        this.chunkSize = chunckSize;

    }

    @Override
    public int length() {
        return count;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Out of bounds");
        }
        return chunks[(index+offset)/chunkSize][(index+offset)%chunkSize];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (start < 0 || start > end || end > length) {
            throw new StringIndexOutOfBoundsException("Out of bounds");
        }
        edu.technopolis.CustomString nc = new edu.technopolis.CustomString(chunks, offset + start, end - start + 1, chunkSize);
        return nc;
    }

    @Override
    public String toString() {
        StringBuilder outString = new StringBuilder();
        for(int i = 0; i < count; i++){
            outString.append(chunks[(offset + i)/chunkSize][(offset + i)%chunkSize]);
        }
        return outString.toString();
    }

    public void printStr(edu.technopolis.CustomString str){
        for (int i = 0; i < count; i++) {
            System.out.print(chunks[(offset + i)/chunkSize][(offset + i)%chunkSize]);
        }
        System.out.println();
    }

    public static void main (String[] args) {
        edu.technopolis.CustomString s = new edu.technopolis.CustomString("Hello World!");
        System.out.print("Начальная строка: ");
        s.printStr(s);
        System.out.println("Количество символов: " + s.length());
        System.out.println("Шестой символ начальной строки: " + s.charAt(6));
        System.out.println("Приведение начальной к String: " + s.toString());
        edu.technopolis.CustomString subStr = (edu.technopolis.CustomString) s.subSequence(2,10);
        System.out.print("Новая строка: ");
        subStr.printStr(subStr);
        System.out.println("Количество символов новой строки: "+subStr.length());
        System.out.println("Шестой символ новой строки: " + subStr.charAt(6));
        System.out.println("Приведение новой к String: " + subStr.toString());
    }
}
