package test;

import java.util.ArrayList;
import java.util.List;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.io.InputFileReader;
import jp.co.nssol.sysrdc.santa.io.SolutionFileWriter;
import jp.co.nssol.sysrdc.santa.logic.Calculator;
import jp.co.nssol.sysrdc.santa.logic.MakeInitialSolution;
import jp.co.nssol.sysrdc.santa.logic.NeighborhoodOperation;
import jp.co.nssol.sysrdc.santa.logic.SimulatedAnnealing;
import jp.co.nssol.sysrdc.santa.utils.Const;

public class TestSimulatedAnnealing {

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

        // 近傍操作インスタンス生成
        NeighborhoodOperation nho = new NeighborhoodOperation(Const.seed);

        // SA法実行
        SimulatedAnnealing sa = new SimulatedAnnealing(Const.initialTemperature, Const.defaultCoolingRate, Const.seed);

        sa.simulateAnnealingProcess(sol, Const.timeLimit);
        // 最良評価値を保存
        sol.copyFrom(sa.getBestSolution());

        // Step.3 SolutionFileWriter
        // ファイル出力
        SolutionFileWriter output = new SolutionFileWriter();
        // output.write(Const.outputSolutionFileName, Const.outputEvaluationFileName, sol);

    }

}
