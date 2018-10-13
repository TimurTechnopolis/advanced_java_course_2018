package edu.technopolis.CustomString;

public class CustomString implements CharSequence {
    private char [][] chars;
    private String inputS;
    private int CHUNK_LENGTH;
    private int offset;
    private int tmpLength;

    private CustomString( char[][] chars, int tmpLength, int CHUNK_LENGTH) {
        this.tmpLength = tmpLength;
        this.CHUNK_LENGTH = CHUNK_LENGTH;
        this.chars = chars;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (start < 0 || end < 0 || end > inputS.length() || start > end) {
            throw new IndexOutOfBoundsException("out of border");
        }
        char[][] chars;
        int chunk_start = start / CHUNK_LENGTH; // ищем правый чанк
        this.offset = start - CHUNK_LENGTH * chunk_start; // задаем смещение
        tmpLength = end - start;
        int tmpCHUNK_LENGTH;
        if (tmpLength % 2 == 0) {
            tmpCHUNK_LENGTH = tmpLength / 2;
            chars = new char[tmpLength / tmpCHUNK_LENGTH][];
            for(int i = 0; i < tmpLength / tmpCHUNK_LENGTH; i ++){
                chars[i] = new char[tmpCHUNK_LENGTH];
            }
            for(int i = 0, k = offset; i < tmpLength / tmpCHUNK_LENGTH; i++){
                for(int j = 0; j < tmpCHUNK_LENGTH; j++,k++){
                    if(k == CHUNK_LENGTH){
                        k=0; chunk_start++;
                    }
                    chars[i][j] = this.chars[chunk_start][k];
                }
            }
            return new CustomString(chars, tmpLength, tmpCHUNK_LENGTH);
        }
        else  {
            tmpCHUNK_LENGTH = (tmpLength / 2) + 1;
            chars = new char[tmpLength / tmpCHUNK_LENGTH + 1][];
            for (int i = 0; i < tmpLength / tmpCHUNK_LENGTH + 1; i++) {
                chars[i] = new char[tmpCHUNK_LENGTH];
            }
            for (int i = 0, k = offset; i < tmpLength / tmpCHUNK_LENGTH + 1; i++) {
                for (int j = 0; j < tmpCHUNK_LENGTH; j++,k++) {
                    if (k == CHUNK_LENGTH) {
                        k = 0;
                        chunk_start = chunk_start + 1;
                    }
                    chars[i][j] = this.chars[chunk_start][k];
                }
            }
            return new CustomString(chars, tmpLength, tmpCHUNK_LENGTH);
        }
    }
    public CustomString(String inputS) {
        this.offset = 0;
        this.inputS = inputS;
        this.tmpLength = this.inputS.length();
        this.CHUNK_LENGTH = 10;
        if(this.inputS.length() == 0) {
            return;
        }
        if(inputS.length() % CHUNK_LENGTH == 0) {
            this.chars = new char[this.inputS.length() / CHUNK_LENGTH][];
            for(int i = 0; i < this.inputS.length() / CHUNK_LENGTH; i++){ //
                this.chars[i] = new char[CHUNK_LENGTH]; // выделил чанку память
            }
            //выделил ровно столько, сколько нужно сабстрингу
            for(int i = 0; i < this.inputS.length(); i++){
                this.chars[i/CHUNK_LENGTH][i%CHUNK_LENGTH] = this.inputS.charAt(i);
            }
        }
        else if(inputS.length() % CHUNK_LENGTH != 0) {
            this.chars = new char[(this.inputS.length() / CHUNK_LENGTH) + 1][]; //выделил ровно столько, сколько нужно
            for(int i = 0; i < this.inputS.length() / CHUNK_LENGTH; i++){ //
                this.chars[i] = new char[CHUNK_LENGTH]; // выделил чанку память
            }
            this.chars[this.inputS.length()/CHUNK_LENGTH] = new char[this.inputS.length() % CHUNK_LENGTH]; //выделил ровно столько, сколько нужно сабстрингу
            for(int i = 0; i < this.inputS.length(); i++){
                this.chars[i/CHUNK_LENGTH][i%CHUNK_LENGTH] = this.inputS.charAt(i);
            }
        }
    }


    @Override
    public int length() {
        return this.tmpLength;
    }

    @Override
    public char charAt(int index) {
        if ((index < 0) || (index >= tmpLength)) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return this.chars[index/CHUNK_LENGTH][index%CHUNK_LENGTH];
    }

    @Override
    public String toString(){
        StringBuffer tmp = new StringBuffer();
        int start_chunk = 0;
        for(int i = 0, j = 0; i < tmpLength; i++, j++ ){
            if(j == CHUNK_LENGTH){
                j = 0; start_chunk = start_chunk + 1;
            }
            tmp.append(this.chars[start_chunk][j]);
        }
        return tmp.toString();
    }
}

