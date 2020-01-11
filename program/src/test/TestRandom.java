package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import jp.co.nssol.sysrdc.santa.utils.Const;

public class TestRandom {

    public static void main(String[] args) {
        Random random = new Random(Const.seed);
        ArrayList<Integer> randList = new ArrayList<Integer>();
        int rand = 0;

        for (int i = 0; i < 140; ++i) {
            rand = random.nextInt(14);
            System.out.println(rand);
            randList.add(rand);
        }
        Collections.sort(randList);
        System.out.println(randList);
    }
}

/**
 * result: 9,8,9,12,0,6,13...
 *
 *
 *
 */
