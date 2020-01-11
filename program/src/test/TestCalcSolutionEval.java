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
import jp.co.nssol.sysrdc.santa.main.Main;
import jp.co.nssol.sysrdc.santa.record.FinalRecord;
import jp.co.nssol.sysrdc.santa.record.Record;
import jp.co.nssol.sysrdc.santa.utils.Const;

public class TestCalcSolutionEval {
    public static void main(String[] args) {
        // Step.0 コマンドライン引数(入力・出力ファイルナンバー)を受け取る
        Const.StringNumber = args[0];
        // loggerインスタンス生成
        Logger logger = LoggerFactory.getLogger(Main.class);

        // Step.1 InputFileReader
        // ギフトを格納するリスト宣言
        List<Gift> giftList = new ArrayList<Gift>();

        // ファイルからデータを読み込み，giftListの取得
        String inputFilePath = Const.DirectoryPath + Const.StringNumber + Const.inputFileName;
        InputFileReader input = new InputFileReader(inputFilePath);
        giftList = input.readFileData();

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

        // 最良解を保存
        // sol.copyFrom(sa.getBestSolution());
        sol = sa.getBestSolution();
        // 暫定的な最良解の評価値・時刻・ループ回数を取得
        List<Record> recordList = sa.getRecordList();

        // 各ベンチマークにおける最良評価値，その時のループ回数，その時の時刻，終了時のループ数，終了時刻
        Record record = recordList.get(recordList.size() - 1);
        double finalTime = sa.getCurrenTime(sa.getStartTime());
        int finalIteration = sa.getIteration();
        FinalRecord finalRecord = new FinalRecord(record, finalTime, finalIteration);

        // Step.4 SolutionFileWriter
        // ファイル出力
        SolutionFileWriter output = new SolutionFileWriter();
        String outputSolutionFilePath = Const.OutputDirectoryPathTest + Const.outputSolutionFileName;
        String outputEvaluationFilePath = Const.OutputDirectoryPathTest + Const.outputEvaluationFileName;
        String outputBestEvalOfBenchmarkFileName = Const.OutputDirectoryPathTest + Const.outputBestEvalOfAllBenchmarkFileName;

        // ファイル名が長いため，ファイル名を格納したString配列を渡す
        String[] filePath = { outputSolutionFilePath, outputEvaluationFilePath, outputBestEvalOfBenchmarkFileName };
        // 出力
        output.write(filePath, Const.StringNumber, sol, recordList, finalRecord);

        // loggerで解の取得.
        logger.info("\n\nsolver'sSolution");
        logger.info(sol.toString()); // {}は不要．loggerに渡す引数は自身のクラス

    }

}
