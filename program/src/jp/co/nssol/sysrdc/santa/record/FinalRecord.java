package jp.co.nssol.sysrdc.santa.record;

public class FinalRecord {
    Record finalRecord;
    // 終了時のループ数，終了時刻
    private double finalTime;
    private int finalIteration;

    // コンストラクタ（こいつをaddしていくイメージ）
    public FinalRecord(Record rec, double time, int iter) {
        this.finalRecord = rec;
        this.finalTime = time;
        this.finalIteration = iter;
    }

    // public void copyFrom(Record rec) {
    //
    // }

    @Override
    public String toString() {
        return finalRecord.toString() + "," + finalTime + "," + finalIteration;
    }

}
