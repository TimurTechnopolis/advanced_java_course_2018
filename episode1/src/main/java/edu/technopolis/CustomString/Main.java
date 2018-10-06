package edu.technopolis.CustomString;

public class Main {
    public static void main(String[] args) {
        /*String b = "123456789q";
        CustomString myStr=new CustomString(b,3);
        System.out.println(myStr);
        System.out.println(myStr.subCustomString(2,2));
        System.out.println(myStr.subCustomString(2,1));
        System.out.println(myStr.subCustomString(2,4));
        CustomString subMyStr=myStr.subCustomString(2,11);
        System.out.println(subMyStr);
        System.out.println(myStr.charAt(2));
        System.out.println(subMyStr.charAt(0));*/

        //len 52=2*2*13
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        test(alphabet,4);

        //len 260=13*2*2*5
        String alphabet_2_0 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        test(alphabet_2_0,13);

    }
    public static void test(String str,int lenChunk){
        for (int i=0;i<str.length();i++){
            if (i % lenChunk == 0) {
                System.out.println("-----------");
            }
            System.out.println(""+i+" : "+str.charAt(i));
        }
        CustomString myStr=new CustomString(str,lenChunk);
        myStr.showInfo();

        System.out.println();
        CustomString subStr1=myStr.subCustomString(40,49); /*opqrstuvw len=9*/
        subStr1.showInfo();

        System.out.println();
        CustomString subStr2=myStr.subCustomString(29,37); /*defghijk*/
        subStr2.showInfo();

        System.out.println();
        CustomString subStr3=myStr.subCustomString(3,47); /*?!?!*/
        subStr3.showInfo();
    }
}
