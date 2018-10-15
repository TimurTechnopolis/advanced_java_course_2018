package edu.technopolis;

public class Main {
    public static void main(String[] args) {
        CustomString string = new CustomString("ssssss@2321aasad", 4);
        System.out.println(string.getStringLikeSegments());
        System.out.println(string);
        System.out.println("------------------------------------------------");

        CustomString secondString = new CustomString("Мы открыли дверь Ты не сомневаешься, кто говорит за Пермь теперь", 5);//17

        System.out.println(secondString);
        System.out.println(secondString.getStringLikeSegments());

        for (int i = 0; i < 5; i++) {
            System.out.println("---------------------------------------------");
            System.out.print((i + 1) + ") ");
            secondString.setLengthSegment((int) (Math.random() * 30 + 3));
            int i0 = (int) (Math.random() * 10 + 3);
            int ik = i0 + (int) (Math.random() * 20 + 1);
            CustomString subCustomString = secondString.mySubstring(i0, ik);
            System.out.println("beginIndex = " + i0 + " endIndex = " + ik + " lengthSegment = " + secondString.getLengthSegment()
                    + "\n" + subCustomString + "\n" + subCustomString.getStringLikeSegments());

        }
    }
}
