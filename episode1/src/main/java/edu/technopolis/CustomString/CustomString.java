package edu.technopolis.CustomString;

import java.util.Arrays;

public class CustomString implements CharSequence {
    private final char values[][];

    private final int lengthChunk;

    private final int offset;

    private final int count;

    public CustomString(char[][] values, int lengthChunk, int offset, int count) {
        this.values=values;
        this.lengthChunk = lengthChunk;
        this.offset = offset;
        this.count = count;
    }

    //Default length Chunk - 1000
    public CustomString(String value) {
        this.offset=0;
        this.count=value.length();
        this.lengthChunk=1000;
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

    private void arrayCopier(char[][] copy,char[][] original){
        for(int index=0;index<original.length;index++){
            copy[index]= Arrays.copyOf(original[index],this.lengthChunk);
        }
    }

    @Override
    public int length() {
        return this.count;
    }

    @Override
    public char charAt(int index) {
        int chunkNumber=(this.offset+index)/this.lengthChunk;
        int number = (this.offset+index)%this.lengthChunk;
        return this.values[chunkNumber][number];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }

    public CustomString subCustomString(int start,int end){

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

    public void showInfo(){
        System.out.println("Значение : "+this.toString());
        System.out.println("Смещение : "+this.offset);
        System.out.println("Длина всей строки: "+this.length());
        System.out.println("Длина куска : "+this.lengthChunk);
        System.out.println("Кол-во кусков : "+this.values.length);
    }
}
