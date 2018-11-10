package edu.technopolis.advjava;

import java.io.Serializable;

/**
 * Реализованная специальным образом строка (аналог {@link java.lang.String}),
 * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном
 * создании подстрок.
 */
public class CustomString implements CharSequence, Serializable {
    private final char values[][];

    private final int lengthChunk;

    private final int offset;

    private final int count;

    private CustomString(char[][] values, int lengthChunk, int offset, int count) {
        this.values=values;
        this.lengthChunk = lengthChunk;
        this.offset = offset;
        this.count = count;
    }

    public CustomString(String value) {
        this.offset=0;
        this.count=value.length();
        this.lengthChunk=optimalLenght(value.length());
        int amountOfChunk = this.count%this.lengthChunk==0 ? this.count/this.lengthChunk : this.count/this.lengthChunk+1 ;
        this.values=new char[amountOfChunk][this.lengthChunk];
        for (int chunk=0,mainIndex=0;chunk<amountOfChunk;chunk++){
            for(int index=0;index<this.lengthChunk;index++,mainIndex++){
                if (mainIndex<this.count){
                    this.values[chunk][index]=value.charAt(mainIndex);
                }else{
                    index=this.lengthChunk;
                }
            }
        }
    }
    private int optimalLenght(int length) {
        int sqrtLen=(int)Math.sqrt(length);
        int rightLenght;
        if (length%sqrtLen==0&&sqrtLen!=1){
            rightLenght=((int)Math.sqrt(length));

        }else{
            rightLenght=((int)Math.sqrt(length))+1;
        }
        int leftLenght=rightLenght;

        int rightRemainder=length%rightLenght;
        int next = length%(++rightLenght);

        while (next>rightRemainder&&rightRemainder!=0){
            rightRemainder=next;
            next=length%(++rightLenght);
        }
        rightLenght--;
        int leftRemaider=length%leftLenght;
        int previouse=length%(--leftLenght);
        while (previouse>leftRemaider&&leftRemaider!=0){
            leftRemaider=previouse;
            previouse=length%(--leftLenght);
        }
        leftLenght++;
        if (leftLenght==1) return rightLenght;
        if (rightRemainder>leftRemaider){
            return rightLenght;
        }else
            return leftLenght;
    }

    public CustomString(String value,int lengthChunk) {
        this.offset=0;
        this.count=value.length();
        this.lengthChunk=lengthChunk;
        int amountOfChunk = this.count%this.lengthChunk==0 ? this.count/this.lengthChunk : this.count/this.lengthChunk+1 ;
        this.values=new char[amountOfChunk][this.lengthChunk];
        for (int chunk=0,mainIndex=0;chunk<amountOfChunk;chunk++){
            for(int index=0;index<this.lengthChunk;index++,mainIndex++){
                if (mainIndex<this.count){
                    this.values[chunk][index]=value.charAt(mainIndex);
                }else{
                    index=this.lengthChunk;
                }
            }
        }
    }

    @Override
    public int length() {
        return this.count;
    }

    @Override
    public char charAt(int index) {
        if (index<0||index>count-1){
            throw new ArrayIndexOutOfBoundsException();
        }
        int chunkNumber=(this.offset+index)/this.lengthChunk;
        int number = (this.offset+index)%this.lengthChunk;
        return this.values[chunkNumber][number];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if(start<offset||end>=count){
            throw  new ArrayIndexOutOfBoundsException();
        }
        int chunkNumberStart=start/this.lengthChunk;
        int chunkNumberEnd=end%this.lengthChunk==0 ? end/this.lengthChunk: end/this.lengthChunk + 1;
        char[][] subValues = new char[chunkNumberEnd-chunkNumberStart][];
        for(int chunk=chunkNumberStart;chunk<chunkNumberEnd;chunk++){
            subValues[chunk-chunkNumberStart]=this.values[chunk];
        }
        return new CustomString(subValues,this.lengthChunk,start-this.lengthChunk*chunkNumberStart,end-start);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(this.count);
        int chunkNumberStart=0;//==0
        int numberStart = this.offset;
        int chunkNumberEnd=(this.count+this.offset)%this.lengthChunk==0 ? (this.count+this.offset)/this.lengthChunk : (this.count+this.offset)/this.lengthChunk+1 ;
        int numberEnd = (this.count+this.offset)%this.lengthChunk;
        for(int chunk=chunkNumberStart;chunk<chunkNumberEnd;chunk++){
            int index= chunk==chunkNumberStart ? numberStart : 0;
            int endIndex= chunk==(this.count+this.offset)/this.lengthChunk ? numberEnd : this.lengthChunk;
            for(;index<endIndex;index++){
                result.append(this.values[chunk][index]);
            }
        }
        return result.toString();
    }
}
