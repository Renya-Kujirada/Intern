package jp.co.nssol.sysrdc.santa.record;

/**
 * ファイル出力用のデータ保持クラス
 *
 * @author kujirada.renya
 *
 */
public class Record {
    // 各世代における評価値・時刻・ループ回数の出力用
    private double evalation;
    private double time;
    private int iteration;

    // コンストラクタ（こいつをaddしていくイメージ）
    public Record(double eval, double time, int iter) {
        this.evalation = eval;
        this.time = time;
        this.iteration = iter;
    }

    @Override
    public String toString() {
        return evalation + "," + time + "," + iteration;
    }

}
