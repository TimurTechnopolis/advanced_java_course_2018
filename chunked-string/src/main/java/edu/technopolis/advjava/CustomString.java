package main.java.edu.technopolis.advjava;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements Serializable, Comparable<CustomString>, CharSequence {

    /**
     * Значение размера чанков по умолчанию.
     **/
    private static final int DEFAULT_CHUNK_SIZE = 8;

    /**
     * Массив для хранения "чанков" с данными.
     **/
    private final char[][] chunks;
    /**
     * Размер чанка. По умолчанию равен DEFAULT_CHUNK_SIZE.
     **/
    private final int chunkSize;
    /**
     * Длина CharSequence.
     **/
    private final int length;
    /**
     * Отступ от начала первого чанка.
     **/
    private final int frontOffset;
    /**
     * Отступ от конца последнего чанка.
     **/
    private final int backOffset;
    /**
     * Первый чанк, содержащий строку. (Может изменятся после при создании подстрок)
     **/
    private final int startChunk;

    /**
     * Конструктор для создания пустой строки
     **/
    public CustomString() {
        this(DEFAULT_CHUNK_SIZE);
    }

    /**
     * Конструктор для создания пустой строки с очевидным указанием размера "чанка".
     **/
    public CustomString(int chunkSize) {
        this.chunkSize = chunkSize;
        length = 0;
        frontOffset = 0;
        backOffset = 0;
        chunks = null;
        startChunk = 0;
    }

//    public CustomString(StringBuffer buffer){
//        TODO MAYBE?
//    }
//
//    public CustomString(StringBuilder buffer){
//        TODO MAYBE?
//    }


    /**
     * Конструктор для создания последовательности символов из массива символов.
     **/
    public CustomString(char[] chars) {
        this(chars, DEFAULT_CHUNK_SIZE);
    }

    /**
     * Конструктор для создания последовательности символов из массива символов.
     * При этом явно указывается размер "чанка".
     **/
    public CustomString(char[] chars, int chunkSize) {
        this.chunkSize = chunkSize;
        length = chars.length;
        frontOffset = 0;
        startChunk = 0;
        if (chars.length == 0) {
            backOffset = 0;
            chunks = null;
            return;
        }

        int chunksQuantity;
        if (chars.length % chunkSize == 0) {
            chunksQuantity = chars.length / chunkSize;
        } else {
            chunksQuantity = chars.length / chunkSize + 1;
        }
        backOffset = chunkSize - (chars.length % chunkSize);

        chunks = new char[chunksQuantity][chunkSize];

        for (int i = 0; i < chunksQuantity - 1; i++) {
            chunks[i] = Arrays.copyOfRange(chars, i * chunkSize, i * chunkSize + (chunkSize));
        }
        chunks[chunksQuantity - 1] = Arrays.copyOfRange(
                chars,
                (chunksQuantity - 1) * chunkSize,
                (chunksQuantity - 1) * chunkSize + (chunkSize - backOffset)
        );
    }

    /**
     * Приватный конструктор, используемый при создании подстрок
     **/
    private CustomString(char[][] chars, int frontOffset, int backOffset, int lenght, int chunkSize, int startChunk) {
        this.chunkSize = chunkSize;
        this.chunks = chars;
        this.frontOffset = frontOffset;
        this.backOffset = backOffset;
        this.length = lenght;
        this.startChunk = startChunk;
    }

    /**
     * Длина CharSequence.
     **/
    @Override
    public int length() {
        return length;
    }

    /**
     * Возвращает символ, стоящий на указанном месте от начала последовательности символов.
     **/
    @Override
    public char charAt(int index) {
        if ((index < 0) || (index >= length)) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return chunks[index / chunkSize][index % chunkSize];
    }

    /**
     * Возвращает новый экземпляр CustomString, ссылающийся на тот же массив "чанков",
     * но имеющий другие отступы.
     **/
    @Override
    public CharSequence subSequence(int start, int end) {
        if (start < 0) {
            throw new StringIndexOutOfBoundsException(start);
        }
        if (end > length) {
            throw new StringIndexOutOfBoundsException(end);
        }
        int subLen = end - start - 1;
        if (subLen < 0) {
            throw new StringIndexOutOfBoundsException(subLen);
        }
        return ((start == 0) && (end == length)) ? this
                : getSubString(start + frontOffset, end + frontOffset);
    }

    /**
     * То же, что и метод subSequence, но возвращает напрямую CustomString. (для удобства)
     **/
    public CustomString substring(int start, int end) {
        return (CustomString) this.subSequence(start, end);
    }

    /**
     * Данный метод отвечает за логику создания подстроки. Считает отступы для подстроки и
     * возвращает новый экземпляр CustomString с необходимыми параметрами.
     **/
    private CustomString getSubString(int start, int end) {
        int startChunk = start / chunkSize;
        int frontOffset = start % chunkSize;
        int backOffset = chunkSize - (end % chunkSize);
        if (backOffset == 8) {
            backOffset = 0;
        }

        return new CustomString(chunks, frontOffset, backOffset, end - start, chunkSize, startChunk);
    }

    /**
     * Возвращает стринговое представление последовательности символов.
     **/
    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();

        int chunkIndex = startChunk;
        int charIndex = frontOffset;

        for (int i = 0; i < length; i++) {
            if (charIndex == chunkSize) {
                chunkIndex++;
                charIndex = 0;
            }
            buff.append(chunks[chunkIndex][charIndex]);
            charIndex++;
        }

        return buff.toString();
    }

    /**
     * Сравнение CustomString'ов.
     **/
    @Override
    public int compareTo(CustomString o) {
        if (o == this) {
            return 0;
        }

        if (this.length > o.length) {
            return 1;
        }
        if (this.length < o.length) {
            return -1;
        }
        for (int i = 0; i < this.length; i++) {
            char our = charAt(i);
            char other = o.charAt(i);
            if (our > other) {
                return 1;
            }
            if (our < other) {
                return -1;
            }
        }
        return 0;
    }

}