package test;

import java.util.ArrayList;

public class TestNullTrip {

    public static void main(String[] args) {
        // TODO 自動生成されたメソッド・スタブ
        ArrayList<Integer> list1 = new ArrayList<Integer>();
        list1.add(1);
        list1.add(2);
        list1.add(3);

        ArrayList<Integer> list2 = new ArrayList<Integer>();

        for (int i = 0; i < list1.size(); i++) {
            System.out.println(list1.get(i));
        }

        for (int i = 0; i < list2.size(); i++) {
            System.out.println(list2.get(i));
        }

        System.out.println("finished");

        // nullなリストに対してfor文回しても問題ない
        // nullなtrip（giftがないtrip）があったとき出力の問題なし

    }

}
