package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.io.InputFileReader;
import jp.co.nssol.sysrdc.santa.logic.Calculator;
import jp.co.nssol.sysrdc.santa.logic.MakeInitialSolution;
import jp.co.nssol.sysrdc.santa.logic.SimulatedAnnealing;
import jp.co.nssol.sysrdc.santa.utils.Const;

class TestSimulatedAnnealing_2 {

    // @Test
    // void test() {
    // // Step.1 InputFileReader
    // // ギフトを格納するリスト宣言
    // List<Gift> giftList = new ArrayList<Gift>();
    //
    // // ファイルからデータを読み込み，giftListの取得
    // InputFileReader input = new InputFileReader(Const.inputFilePath);
    // giftList = input.readFileData();
    //
    // // リストの中身表示
    // for (Gift gift : giftList) {
    // System.out.println(gift.getGiftId());
    // }
    //
    // // Step.2 MakeInitialSolution
    // MakeInitialSolution makeInit = new MakeInitialSolution();
    //
    // // 初期解生成
    // Solution sol = makeInit.makeInitSol(giftList);
    //
    // // 解全体の評価値を算出
    // sol.setAllEval(Calculator.calcSolutionEval(sol));
    //
    // // 初期解表示
    // sol.showResult();
    //
    // // SA法実行
    // SimulatedAnnealing sa = new SimulatedAnnealing(Const.initialTemperature, Const.defaultCoolingRate, Const.seed);
    // sa.simulateAnnealingProcess(sol, Const.timeLimit);
    // // 最良評価値を保存
    // sol.copyFrom(sa.getBestSolution());
    //
    // // Step.3 SolutionFileWriter
    // // ファイル出力
    // SolutionFileWriter output = new SolutionFileWriter();
    // output.write(Const.outputSolutionFilePath, Const.outputEvaluationFilePath, sol);
    //
    // }

    @Test
    void testUpdateWithAnnealing() {
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

        // SA法実行
        SimulatedAnnealing sa = new SimulatedAnnealing(Const.initialTemperature, Const.defaultCoolingRate, Const.seed);
        // sa.updateWithAnnealing(sol);
    }

}
