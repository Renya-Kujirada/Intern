package jp.co.nssol.sysrdc.santa.main;

import java.util.ArrayList;
import java.util.List;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.io.InputFileReader;
import jp.co.nssol.sysrdc.santa.io.SolutionFileWriter;
import jp.co.nssol.sysrdc.santa.logic.MakeInitialSolution;
import jp.co.nssol.sysrdc.santa.logic.SimulatedAnnealing;
import jp.co.nssol.sysrdc.santa.property.Property;
import jp.co.nssol.sysrdc.santa.record.FinalRecord;
import jp.co.nssol.sysrdc.santa.record.Record;
import jp.co.nssol.sysrdc.santa.utils.Const;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

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
        String inputPropertyFilePath = Const.inputPropertyFileName;
        InputFileReader input = new InputFileReader(inputFilePath, inputPropertyFilePath);
        giftList = input.readFileData();

        // propertyファイルから各設定データをマップに読み込む
        Property pr = input.readPropertyData();
        // System.out.println(pr.toString());

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

        // 初期温度表示（プレゼン用）
        logger.info("{}, {}", "初期解の評価値", sol.getAllEval());
        logger.info("{}, {}", "初期解の評価値をtrip数で割った値", sol.getAllEval() / sol.getTrips().size());

        // 初期温度変更
        // SimulatedAnnealing sa = new SimulatedAnnealing(sol.getAllEval(), Const.defaultCoolingRate, pr.getSeed());
        SimulatedAnnealing sa = new SimulatedAnnealing(sol.getAllEval() / sol.getTrips().size(), Const.defaultCoolingRate, pr.getSeed());
        sa.simulateAnnealingProcess(sol, Const.timeLimit, pr.getSwapInsideRate(), pr.getInsertInsideRate(), pr.getSwapBetweenRate(), pr.getInsertBetweenRate());

        // 最良解を保存
        sol = sa.getBestSolution();
        // sol.copyFrom(sa.getBestSolution());
        // 暫定的な最良解の評価値・時刻・ループ回数を取得
        List<Record> recordList = sa.getRecordList();
        System.out.println(recordList.size() + "recordListSize");

        // 各ベンチマークにおける最良評価値，その時のループ回数，その時の時刻，終了時のループ数，終了時刻
        // 初期解の情報をセット
        Record record = new Record(sol.getAllEval(), 0.0, 0);
        if (recordList.size() != 0) {
            record = recordList.get(recordList.size() - 1);
        }

        double finalTime = sa.getCurrenTime(sa.getStartTime());
        int finalIteration = sa.getIteration();
        FinalRecord finalRecord = new FinalRecord(record, finalTime, finalIteration);

        // Step.4 SolutionFileWriter
        // ファイル出力
        SolutionFileWriter output = new SolutionFileWriter();
        String outputSolutionFilePath = Const.DirectoryPath + Const.StringNumber + Const.outputSolutionFileName;
        String outputEvaluationFilePath = Const.DirectoryPath + Const.StringNumber + Const.outputEvaluationFileName;
        String outputBestEvalOfBenchmarkFileName = Const.OutputDirectoryPath + Const.outputBestEvalOfAllBenchmarkFileName;

        // ファイル名が長いため，ファイル名を格納したString配列を渡す
        String[] filePath = { outputSolutionFilePath, outputEvaluationFilePath, outputBestEvalOfBenchmarkFileName };
        // 出力
        output.write(filePath, Const.StringNumber, sol, recordList, finalRecord);

        // loggerで解の取得.
        logger.info("\n\nsolver'sSolution");
        logger.info(sol.toString()); // {}は不要．loggerに渡す引数は自身のクラス

    }

}
