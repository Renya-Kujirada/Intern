package test;

import java.util.ArrayList;
import java.util.List;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.io.InputFileReader;

public class TestInputFileReader {

    // test用mainメソッド
    public static void main(String[] args) {

        String inputFilePath = "gifts.csv";

        // ギフトを格納しているリスト
        List<Gift> giftList = new ArrayList<Gift>();

        // インスタンス生成
        InputFileReader input = new InputFileReader(inputFilePath);

        // Listの取得
        giftList = input.readFileData();

        // リストの要素数取得
        int size = giftList.size();

        // リスト内の中身表示
        for (int i = 0; i < size; i++) {
            System.out.println(giftList.get(i).getGiftId());
            // System.out.println(giftList.get(i).getWeight());
            // System.out.println(giftList.get(i).getLatitude());
        }
    }

}
