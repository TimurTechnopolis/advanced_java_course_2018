package edu.technopolis;

public class CustomString implements CharSequence{
    int offset;
    int count;
    char[][] data;
    private int chunkLength = 10;
    int dataLength;

    public CustomString(String inputString) {
        if(inputString.length() == 0){
            return;
        }
        this.offset = 0;
        this.count = 0;
        this.dataLength = inputString.length();
        if(inputString.length() % chunkLength == 0){
            this.data = new char[inputString.length()/chunkLength][chunkLength];
            for(int i = 0; i < inputString.length(); i++){
                this.data[i/chunkLength][i%chunkLength] = inputString.charAt(i);
            }
        }
        else{
            this.data = new char[inputString.length()/chunkLength+1][];
            for(int i = 0; i < inputString.length()/chunkLength; i++){
                this.data[i] = new char[chunkLength];
            }
            this.data[inputString.length()/chunkLength] = new char[inputString.length()%chunkLength];
            for(int i = 0; i < inputString.length(); i++){
                this.data[i/chunkLength][i%chunkLength] = inputString.charAt(i);
            }
        }
    }
    public CustomString(int offset, int count, char[][] data, int dataLength) {
        this.offset = offset;
        this.count = count;
        this.data = data;
        this.dataLength = dataLength;
    }

    @Override
    public int length() {
        if(this.count == 0)
            return this.dataLength;
        else
            return this.count;
    }

    @Override
    public char charAt(int index) {
        return this.data[(this.offset+index)/chunkLength][(this.offset+index)%chunkLength];
    }

    @Override
    public CustomString subSequence(int start, int end) {
        return new CustomString(this.offset + start, end - start + 1, this.data, this.dataLength);
    }
    void print(){
        StringBuilder outString = new StringBuilder();
        for(int i = 0; i < this.length(); i++){
            outString.append(this.data[(this.offset + i)/chunkLength][(this.offset + i)%chunkLength]);
        }
        System.out.println(outString.toString());
    }

    public static void main(String[] args) {
        CustomString SomeString = new CustomString("I want to break free");
        CustomString SubString = SomeString.subSequence(2, 15);
        CustomString Word = SubString.subSequence(8, 13);
        SomeString.print();
        SubString.print();
        Word.print();
        System.out.println(SomeString.data);
        System.out.println(SubString.data);
        System.out.println(Word.data);
    }
}