import java.util.stream.IntStream;

/*
Данная реализация String подходит для строк любой длинны,
но при условии что нам ничего не известно про начальную строку
и ничего не известно про subString которые мы извлекаем оттуда.
Так как моя строка разбивается на чанки одинаковой длины,где длина это наибольший
делитель числа, больший чем корень из числа, то у меня не использовааной остаётся максимум одна
ячейка (в случае если число простое). При условии, что мы знаем что то про начальную строку
и подстроки еоторые будем извлекать, можно придумать более оптимальный алгоритм (например в чанке
хранить почти подстроки так, чтобы применяя функцию substring мы всё время захватывали чанк полностью.
Так например можно хранить книги по страницам, если мы будем точно знать что пользователь всегда будет
запрашивать целое число страниц), но в общем случае, когда про строку ничего не известно,
мой алгорит разбиения на чанки довольно оптимальный.
 */

public class CustomString implements CharSequence {
    private char[][] ch;
    private int offset;
    private int count;
    private int lengthChunk = 0;

    //Нахожу максимальный делитель, меньший корня из длины строки,
    //если число простое, то нахожу делитель из следующего числа
    //Так как два соседних числа не могут быть простыми
    //кроме 2 и 3, то хотя бы одно из чисел будет иметь нужный нам делитель

    private int isPrimeNumbers (int i) {
        int j = (int)Math.sqrt(i);
        for (; j > 0; j--) {
            if (i/j == 0) {
                return j;
            }
        }
        return 1;
    }

    public CustomString(char[][] ch, int offset, int count, int lengthChunk) {
        this.ch = ch;
        this.offset = offset;
        this.count = count;
        this.lengthChunk = lengthChunk;
    }

    //Начальный конструктор

    public CustomString(String value) {
        offset = 0;
        count = value.length();
        if(count < 4){
            lengthChunk = count;

        } else {
            int tmp = isPrimeNumbers(count);
            if (tmp == 1) {
                lengthChunk = tmp;
            } else {
                lengthChunk = isPrimeNumbers(count + 1);
            }
        }
        int amountChunk = count / lengthChunk;
        ch = new char[amountChunk][lengthChunk];
        for (int i = 0, m = 0; i < amountChunk; i++) {
            for (int j = 0; (j < lengthChunk) && (m < count); j++) {
                ch[i][j] = value.charAt(m);
                m++;
            }
        }
    }

    @Override
    public int length() {
        return count;
    }

    @Override
    public char charAt(int index) {
        if ((offset + index >= count) || (offset + index < count)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int chunk = (offset + index) / lengthChunk;
        int in = (offset + index) % lengthChunk;
        return ch[chunk][in];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }

    public CustomString subString(int start, int end) {
        if ((end >= count) || (start < offset) || (start > end)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int tmpLen = ((offset + end - start)/lengthChunk)-((offset + start) / lengthChunk);
        char [][] copArray =  new char[tmpLen][];
        for(int i = 0 ; i  < tmpLen; i++){
            copArray[i] = ch[(offset + start) / lengthChunk + i];
        }
        return new CustomString(copArray, (offset + start)/lengthChunk, (offset + end - start + 1) / lengthChunk , lengthChunk);
    }

    public String toString() {
        StringBuffer res = new StringBuffer();
        //int amountChunk = count/ lengthChunk;
        int i = offset / lengthChunk;
        for (int j = offset % lengthChunk; j < count; j++) {
            if (j == lengthChunk) {
                i++;
            }
            res.append(ch[i][j % lengthChunk]);

        }
        return res.toString();
    }

    @Override
    public IntStream chars() { return null; }

    @Override
    public IntStream codePoints() {
        return null;
    }

}
