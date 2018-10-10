
import java.util.stream.IntStream;

public class CustomString implements CharSequence {
    private char [][] ch;
    private int offset;
    private int count;
    private int lengthChunk = 0;

    private boolean isSqrt(int i ){
        int j =0;
        while (j*j<i){
            j++;
        }
        if(j*j == i)
            return true;
        return false;
    }

    public CustomString(char [][] ch, int offset, int count, int lengthChunk){
        this.ch = ch;
        this.offset = offset;
        this.count = count;
        this.lengthChunk = lengthChunk;
    }

    public CustomString(String value){
        offset  = 0;
        count = value.length();
        lengthChunk = (int) Math.sqrt(count);
        int amountChunk = isSqrt(count)? count/lengthChunk : count/lengthChunk + 1;
        ch  = new char[amountChunk][lengthChunk];
        for(int i = 0, m = 0; i<amountChunk ; i++){
            for(int j = 0; (j<lengthChunk) && (m<count); j++){
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
        if((offset + index >= count)||(offset + index < count))
            throw new NullPointerException();
        int chunk = (offset + index)/lengthChunk;
        int in =  (offset + index)%lengthChunk;
        return ch[chunk][in];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }

    public CustomString subString(int start, int end){
        if((end >= count)||(start< offset)||(start>end))
            throw new NullPointerException();
        return new CustomString(ch, offset+start, offset+end-start+1, lengthChunk);
    }

    public String toString (){
        StringBuffer res = new StringBuffer();
        int amountChunk = isSqrt(count)? count/lengthChunk : count/lengthChunk + 1;
        int i = offset/lengthChunk;
        for(int j = offset%lengthChunk; j<count ; j++){
            if(j == lengthChunk)
                i++;
            res.append(ch[i][j%lengthChunk]);

        }
        return res.toString();
    }

    @Override
    public IntStream chars() {
        return null;
    }

    @Override
    public IntStream codePoints() {
        return null;
    }

}
