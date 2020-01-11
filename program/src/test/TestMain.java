package test;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.io.InputFileReader;
import jp.co.nssol.sysrdc.santa.io.SolutionFileWriter;
import jp.co.nssol.sysrdc.santa.logic.MakeInitialSolution;
import jp.co.nssol.sysrdc.santa.logic.SimulatedAnnealing;
import jp.co.nssol.sysrdc.santa.sample.LoggerSample;
import jp.co.nssol.sysrdc.santa.utils.Const;

public class TestMain {

    public static void main(String[] args) {
        // Step.0 コマンドライン引数(入力・出力ファイルナンバー)を受け取る
        Const.StringNumber = args[0];
        // loggerインスタンス生成
        Logger logger = LoggerFactory.getLogger(LoggerSample.class);

        // Step.1 InputFileReader
        // ギフトを格納するリスト宣言
        List<Gift> giftList = new ArrayList<Gift>();

        // ファイルからデータを読み込み，giftListの取得
        String inputFilePath = Const.DirectoryPath + Const.StringNumber + Const.inputFileName;
        InputFileReader input = new InputFileReader(inputFilePath);
        giftList = input.readFileData();

        // // リストの中身表示
        // for (Gift gift : giftList) {
        // System.out.println(gift.getGiftId());
        // }

        // Step.2 MakeInitialSolution
        MakeInitialSolution makeInit = new MakeInitialSolution();
        // 初期解生成(評価値もセット)
        Solution sol = makeInit.makeInitSol(giftList);
        // loggerで解の取得
        logger.info("{}", "\ninitSolution");
        logger.info("{}", sol.toString());

        // Step3. 近傍操作とアニーリング
        // SA法実行
        // SimulatedAnnealing sa = new SimulatedAnnealing(Const.initialTemperature, Const.defaultCoolingRate, Const.seed);
        SimulatedAnnealing sa = new SimulatedAnnealing(sol.getAllEval(), Const.defaultCoolingRate, Const.seed);
        sa.simulateAnnealingProcess(sol, Const.timeLimit);
        // 最良評価値を保存
        sol.copyFrom(sa.getBestSolution());

        // Step.4 SolutionFileWriter
        // ファイル出力
        SolutionFileWriter output = new SolutionFileWriter();
        String outputSolutionFilePath = Const.DirectoryPath + Const.StringNumber + Const.outputSolutionFileName;
        String outputEvaluationFilePath = Const.DirectoryPath + Const.StringNumber + Const.outputEvaluationFileName;
        // output.write(outputSolutionFilePath, outputEvaluationFilePath, sol);

        // loggerで解の取得.
        logger.debug("{}", "\n\nsolver'sSolution");
        logger.debug("{}", sol.toString());

        // 温度表示
        logger.debug("{}", sa.toString());

    }

}
