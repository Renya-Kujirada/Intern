package test;

import java.util.ArrayList;
import java.util.List;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.io.InputFileReader;
import jp.co.nssol.sysrdc.santa.logic.MakeInitialSolution;

public class TestTrip {
    // test用mainメソッド
    public static void main(String[] args) {

        // 読み込みファイル名
        String filename = "gifts.csv";

        // 重みの制約
        double weightOfConstraints = 990.0;

        // ギフトを格納しているリスト
        List<Gift> giftList = new ArrayList<Gift>();

        // インスタンス生成
        InputFileReader input = new InputFileReader(filename);

        // Listの取得
        giftList = input.readFileData();

        // // リスト内の中身表示
        // for (int i = 0; i < giftList.size(); i++) {
        // System.out.println(giftList.get(i).getGiftId());
        //
        // }

        MakeInitialSolution makeInit = new MakeInitialSolution();
        Solution sol = makeInit.makeInitSol(giftList);
        // sol.showResult();

        // TripID = 0のTripの総距離を算出
        int TripID = 0;
        System.out.println(sol.getTrips().get(TripID).getSumOfDistance());

        // System.out.println(calc.calcDistance(tripList.get(0), tripList.get(1)));
    }

}
