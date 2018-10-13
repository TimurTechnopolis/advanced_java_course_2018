package com.company;
//package edu.technopolis.advjava;

import java.io.Serializable;

/**
 * Используются все Unchecked исключения, поэтому пометка в методах и catch не нужен
 * Скобки расставлены на удобный манер, т.к. египетские непривычны
 * Если приложение однопоточное, то использовать StringBuilder, в противном случае раскомментировать
 * SrtingBuffer и закомменить StringBuilder
 * Используется StringBuilder т.к. не создается новый объект при добавлении частей
 */
public class CustomString implements CharSequence, Serializable
{
    private final char[][] chunks;
    private int offset;
    private int count;
    private int chunkSize;

    CustomString(char[][] chunks_Arg, int offset_Arg, int count_Arg, int chunkSize_Arg)
    {
        if (chunks_Arg == null)
        {
            throw new NullPointerException("Text does not exist!");
        }
        if (count_Arg <= 0)
        {
            throw new IllegalArgumentException("Text must be more then 0 symbols!");
        }
        if (chunkSize_Arg <= 0)
        {
            throw new IllegalArgumentException("Chunks must be more then 0 symbols!");
        }
        count = count_Arg;
        offset = offset_Arg % chunkSize_Arg;
        chunkSize = chunkSize_Arg;
        int startingChunk = offset / chunkSize;
        int numberOfChunks;
        if ((count + offset) / chunkSize > 0)
        {
            numberOfChunks = (count + offset) / chunkSize;
        }
        else
        {
            numberOfChunks = 1;
        }
        chunks = new char[numberOfChunks][chunkSize];
        for(int i = 0; i < numberOfChunks; i++)
        {
            for (int j = 0; j < chunkSize; j++)
            {
                if (i * chunkSize + j - offset < count)
                {
                    chunks[i][j] = chunks_Arg[i + startingChunk][j];
                }
            }
        }
    }

    @Override
    public int length()
    {
        return count;
    }

    @Override
    public char charAt(int index)
    {
        if (index < 0 || index > count - 1)
        {
            throw new IndexOutOfBoundsException("Index must be in range of array!");
        }
        return chunks[(index + offset) / chunkSize][(index + offset) % chunkSize];
    }

    @Override
    public CustomString subSequence(int start, int end)
    {
        if (start < 0 || end > count - 1 || start > end)
        {
            throw new IndexOutOfBoundsException("Invalid start or end");
        }
        return new CustomString(chunks, offset + start, end - start + offset, chunkSize);
    }

    @Override
    public String toString()
    {
        StringBuilder text = new StringBuilder(count);
        //StringBuffer text = new StringBuffer(count);
        for (int i = offset; i < count + offset; i++)
        {
            text.append(chunks[i / chunkSize][i % chunkSize]);
        }
        return new String(text);
    }
}
