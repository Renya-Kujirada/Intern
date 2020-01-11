package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.io.InputFileReader;
import jp.co.nssol.sysrdc.santa.logic.Calculator;
import jp.co.nssol.sysrdc.santa.logic.MakeInitialSolution;
import jp.co.nssol.sysrdc.santa.utils.Const;

public class TestSolution {

    public static void main(String[] args) {
        // Step.1 InputFileReader
        // ギフトを格納するリスト宣言
        List<Gift> giftList = new ArrayList<Gift>();

        // ファイルからデータを読み込み，giftListの取得
        InputFileReader input = new InputFileReader(Const.inputFileName);
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

        Solution tempSolution = new Solution();
        tempSolution.copyFrom(sol);


        tempSolution.showResult();

        assertEquals(sol.getTrips().size(), tempSolution.getTrips().size());

        System.out.println(sol.getTrips().size());
        System.out.println(tempSolution.getTrips().size());

        // // Step.3 SolutionFileWriter
        // // ファイル出力
        // SolutionFileWriter output = new SolutionFileWriter();
        // output.write(Const.outputSolutionFilePath, Const.outputEvaluationFilePath, sol);

    }

}
