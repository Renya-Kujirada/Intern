package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.data.Trip;
import jp.co.nssol.sysrdc.santa.io.InputFileReader;
import jp.co.nssol.sysrdc.santa.logic.Calculator;
import jp.co.nssol.sysrdc.santa.logic.MakeInitialSolution;
import jp.co.nssol.sysrdc.santa.utils.Const;

class TestSolution_2 {

    @Test
    void testCopyFrom() {
        // Step.1 InputFileReader
        // ギフトを格納するリスト宣言
        List<Gift> giftList = new ArrayList<Gift>();

        // ファイルからデータを読み込み，giftListの取得
        String inputFilePath = Const.DirectoryPath + String.valueOf(Const.StringNumber) + Const.inputFileName;
        InputFileReader input = new InputFileReader(inputFilePath);
        giftList = input.readFileData();

        // リストの中身表示
        for (Gift gift : giftList) {
            System.out.println(gift.getGiftId());
        }

        // Step.2 MakeInitialSolution
        MakeInitialSolution makeInit = new MakeInitialSolution();

        // 初期解生成
        Solution sol = makeInit.makeInitSol(giftList);

        // 解全体の評価値を算出
        sol.setAllEval(Calculator.calcSolutionEval(sol));

        // 初期解表示
        sol.showResult();

        // test用コピー先のインスタンス生成
        Solution tempSolution = new Solution();
        // 確認用に評価値に1000000.0を代入
        tempSolution.setAllEval(1000000.0);
        tempSolution.copyFrom(sol);

        // tempSolution.showResult();

        for (int i = 0; i < sol.getTrips().size(); ++i) {
            Trip trip = sol.getTrips().get(i);
            Trip copyTrip = tempSolution.getTrips().get(i);

            for (int j = 0; j < trip.getGiftList().size(); ++j) {
                Gift gift = trip.getGiftList().get(j);
                Gift copyGift = copyTrip.getGiftList().get(j);

                assertEquals(gift.getGiftId(), copyGift.getGiftId());
            }

            assertEquals(sol.getTrips().size(), tempSolution.getTrips().size());

        }

        // 初期解表示
        tempSolution.showResult();

    }

}
