package jp.co.nssol.sysrdc.santa.logic;

import java.util.ArrayList;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.sample.LoggerSample;
import jp.co.nssol.sysrdc.santa.record.Record;

/**
 * 初期温度initTenperature, 温度temoerature，冷却係数Beta ．変数の初期パラメータとかはファイルから読み込み（？） 焼きなまし法
 *
 * @author kujirada.renya
 *
 */
public class SimulatedAnnealing {
    // スワップ直前の解(現在の解)
    private Solution prevSolution;

    // 最良解保存用
    private Solution bestSolution;

    // 各世代における評価値・時刻・ループ回数の出力用
    private ArrayList<Record> recordList;

    // 開始時刻
    private double startTime;

    // 乱数発生器
    private Random random;

    // 温度T
    private double temperature;

    // 冷却率
    private double coolingRate;

    // シード
    private int seed;

    // 確率
    private double p;

    // 世代
    private int iteration;

    // loggerインスタンス生成
    Logger logger = LoggerFactory.getLogger(LoggerSample.class);

    private ArrayList<AbstractNeiborhoodOperation> neiborhoodOperationList;

    // コンストラクタ
    public SimulatedAnnealing(double initialTemperature, double defaultCoolingRate, int seed) {
        prevSolution = new Solution();
        bestSolution = new Solution();
        recordList = new ArrayList<Record>();
        startTime = 0;
        this.seed = seed;
        random = new Random(seed);
        temperature = initialTemperature;
        coolingRate = defaultCoolingRate;
        p = 0.0;
        iteration = 0;
        neiborhoodOperationList = new ArrayList<AbstractNeiborhoodOperation>();
    }

    /**
     * 焼きなまし法を実行
     *
     * @param initialSolution 初期解
     */
    public void simulateAnnealingProcess(Solution initialSolution, double timeLimit, int si_rate, int ii_rate, int sb_rate, int ib_rate) {
        // 時間計測
        startTime = System.currentTimeMillis();

        // 近傍操作のインスタンスのリスト作成
        makeNeighborhoodOperationInstanceList(si_rate, ii_rate, sb_rate, ib_rate);

        // 近傍操作で得られた新しい解格納用
        Solution neighborhoodSolution = new Solution();

        // 最初は初期解initialSolutionをsolution，bestSolutionへコピー
        saveBestEvalSolution(initialSolution);

        // 5分間（300秒）更新を続ける
        // while (getCurrenTime(startTime) / 1000 < timeLimit || iteration > Const.endIteration) {
        while (getCurrenTime(startTime) / 1000 < timeLimit) {
            // 近傍操作をランダムに選ぶ
            AbstractNeiborhoodOperation selectedOperation = randomSelectNeiborhoodOperation();

            // 近傍操作により，SA法の次世代解を生成
            neighborhoodSolution = selectedOperation.neighborhoodOperation(prevSolution);

            // 近傍解が最小解である場合，問答無用で更新
            if (bestSolution.getAllEval() - neighborhoodSolution.getAllEval() > 0.0) {
                // 探索中の最良評価値解の更新
                saveBestEvalSolution(neighborhoodSolution);
                // 暫定解の最良評価値，その時の時刻とループ回数を記録
                recordBestEvalData(startTime);
            }
            // 改悪解の場合
            else {
                // 焼きなまし法による解の更新
                updateWithAnnealing(neighborhoodSolution);
            }

            // 温度の更新
            updateTemperature();

            // 世代数更新
            iteration++;

            // logger.info("{}", this.toString());
        }
    }

    /**
     * 最良評価値解の更新
     *
     * @param neighborhoodSolution 近傍操作により得られた解
     */
    private void saveBestEvalSolution(Solution neighborhoodSolution) {
        // 現在の解の更新
        // this.prevSolution.copyFrom(neighborhoodSolution);
        prevSolution = neighborhoodSolution;
        // 最小評価値解の保存
        // this.bestSolution.copyFrom(neighborhoodSolution);
        bestSolution = neighborhoodSolution;
    }

    /**
     * 焼きなまし法による解の更新
     *
     * @param neighborhoodSolution 近傍操作により得られた解（改悪解）
     */
    private void updateWithAnnealing(Solution neighborhoodSolution) {
        // 解の選択確率を計算
        p = Math.exp((prevSolution.getAllEval() - neighborhoodSolution.getAllEval()) / temperature);
        p = Math.min(p, 1.0);

        // p > 乱数(0 ~ 1の小数)となれば最良解を更新
        if (p > random.nextDouble()) {
            // 解・評価値を更新
            // prevSolution.copyFrom(neighborhoodSolution);
            prevSolution = neighborhoodSolution;
        } else {
            // 解を採択しない
        }
        // System.out.println("prevSolution.getAllEval():" + prevSolution.getAllEval());
        // System.out.println("neighborhoodSolution.getAllEval():" + neighborhoodSolution.getAllEval());
        // System.out.println("分子:" + (prevSolution.getAllEval() - neighborhoodSolution.getAllEval()) + "\tp:" + p);
    }

    /**
     * 現在かかっている時間を返す
     *
     * @param startTime 開始時刻
     * @return
     */
    public double getCurrenTime(double startTime) {
        // millisecondsでtimesに格納
        double time = System.currentTimeMillis() - startTime;
        // secondに変換して返す
        return time;
    }

    /**
     * 温度の変化
     */
    private void updateTemperature() {
        temperature *= coolingRate;
    }

    /**
     * 近傍操作のインスタンスを生成してneiborhoodOperationListに格納 近傍操作のインスタンスを生成してneiborhoodOperationListに格納
     */
    private void makeNeighborhoodOperationInstanceList(int si_rate, int ii_rate, int sb_rate, int ib_rate) {
        for (int i = 0; i < si_rate; i++) {
            SwapInside si = new SwapInside(seed);
            neiborhoodOperationList.add(si);
        }
        for (int i = 0; i < ii_rate; i++) {
            InsertInside ii = new InsertInside(seed);
            neiborhoodOperationList.add(ii);
        }
        for (int i = 0; i < sb_rate; i++) {
            SwapBetween sb = new SwapBetween(seed);
            neiborhoodOperationList.add(sb);
        }
        for (int i = 0; i < ib_rate; i++) {
            InsertBetween ib = new InsertBetween(seed);
            neiborhoodOperationList.add(ib);
        }

    }

    /**
     * 近傍操作のリストからランダムに近傍操作を選んで返す
     *
     * @param list
     * @return
     */
    private AbstractNeiborhoodOperation randomSelectNeiborhoodOperation() {
        int index = random.nextInt(neiborhoodOperationList.size());
        AbstractNeiborhoodOperation selectedOperation = neiborhoodOperationList.get(index);
        // System.out.println("index" + index);
        return selectedOperation;
    }

    /**
     * 暫定解の最良評価値，その時のループ回数，時刻を記録
     */
    private void recordBestEvalData(double startTime) {
        Record record = new Record(this.bestSolution.getAllEval(), getCurrenTime(startTime), this.iteration);
        // System.out.println(iteration + record.toString());
        // logger.info("{}, {}, {}", "iteration", iteration, record.toString());
        recordList.add(record);
    }

    // --------getter/setter--------
    public Solution getBestSolution() {
        return bestSolution;
    }

    public void setBestSolution(Solution bestSolution) {
        this.bestSolution = bestSolution;
    }

    public Solution getpervSolution() {
        return prevSolution;
    }

    public void setprevSolution(Solution prevSolution) {
        this.prevSolution = prevSolution;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int generation) {
        this.iteration = generation;
    }

    public ArrayList<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(ArrayList<Record> recordList) {
        this.recordList = recordList;
    }

    @Override
    public String toString() {
        // return prevSolution.toString();
        return "SimulatedAnnealing [iteration =" + iteration + "\tp=" + p + "\ttemperature=" + temperature + "]";
    }

    public double getStartTime() {
        return startTime;
    }

}
