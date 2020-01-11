package jp.co.nssol.sysrdc.santa.utils;

import jp.co.nssol.sysrdc.santa.data.Gift;

public class Const {
    // そりの重さ
    public static final double weightOfSleigh = 10;

    // 地球の半径
    public static final int radiusOfEarth = 6371;

    // 北極(northPole)の情報をgiftとして追加(giftID = 0, latitude = 90.0, longitude = 0.0, weight = 0.0)
    public static final Gift northPole = new Gift(0, 90.0, 0.0, 0.0);

    // 重みの制約(MakeInitialSolutionで使用)
    public static final double weightOfConstraints = 1000.0;

    // 読み込みディレクトリパス
    public static final String DirectoryPath = ".\\data\\benchmark\\benchmark_";

    // 読み込みPropertyファイルパス
    public static final String inputPropertyFileName = "..\\property.csv";

    // 書き込みディレクトリパス
    public static final String OutputDirectoryPath = ".\\data\\result";

    // TestCalculatorでのみ使用する書き込みディレクトリパス
    public static final String OutputDirectoryPathTest = ".\\data\\calcTestResult";

    // 各入出力ファイルのナンバリング
    public static String StringNumber;

    // 読み込みファイルパス
    public static final String inputFileName = "\\gifts.csv";

    // 書き込みファイルパス
    public static final String outputSolutionFileName = "\\solution.csv";
    public static final String outputEvaluationFileName = "\\evaluation.csv";
    public static final String outputBestEvalOfAllBenchmarkFileName = "\\evaluationOfAllBenchmark.csv";

    // 焼きなまし法の初期温度
    // public static final double initialTemperature = 1000000000;

    // 焼きなまし法のデフォルトの冷却率
    public static final double defaultCoolingRate = 0.99999;

    // プログラム実行時間の制約時間(600秒)
    public static final double timeLimit = 600;

    // 乱数生成器用seed
    // public static final int seed = 10;

    // 試行回数の終了条件（debug用）
    public static final int endIteration = 1000000000;

    // 読み込みディレクトリパス(debug用)
    public static final String TestDirectoryPath = ".\\data\\inputTrip";

    // 読み込みファイルパス(debug用)
    public static final String inputTripFileName_1 = "\\trip_1.csv";

    // 読み込みファイルパス(debug用)
    public static final String inputTripFileName_2 = "\\trip_2.csv";

    // TripID
    public static final int TripID_1 = 9;

    public static final int TripID_2 = 8;

    /**
     * memo
     *
     * public : globalにアクセスできるか否か static : newせずアクセスできる
     */

}
