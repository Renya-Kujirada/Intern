package jp.co.nssol.sysrdc.santa.property;

public class Property {

    // 各世代における評価値・時刻・ループ回数の出力用
    private int seed;
    private int swapInsideRate;
    private int insertInsideRate;
    private int swapBetweenRate;
    private int insertBetweenRate;

    public Property() {

    }

    public Property(int seed, int siRate, int iiRate, int sbRate, int ibRate) {
        this.seed = seed;
        swapInsideRate = siRate;
        insertInsideRate = iiRate;
        swapBetweenRate = sbRate;
        insertBetweenRate = ibRate;
    }

    // 以下getter
    public int getSeed() {
        return seed;
    }

    public int getSwapInsideRate() {
        return swapInsideRate;
    }

    public int getInsertInsideRate() {
        return insertInsideRate;
    }

    public int getSwapBetweenRate() {
        return swapBetweenRate;
    }

    public int getInsertBetweenRate() {
        return insertBetweenRate;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public void setSwapInsideRate(int swapInsideRate) {
        this.swapInsideRate = swapInsideRate;
    }

    public void setInsertInside(int insertInsideRate) {
        this.insertInsideRate = insertInsideRate;
    }

    public void setSwapBetween(int swapBetweenRate) {
        this.swapBetweenRate = swapBetweenRate;
    }

    public void setInsertBetween(int insertBetweenRate) {
        this.insertBetweenRate = insertBetweenRate;
    }

    @Override
    public String toString() {
        return "Property [seed=" + seed + ", swapInsideRate=" + swapInsideRate + ", insertInsideRate=" + insertInsideRate + ", swapBetweenRate="
            + swapBetweenRate + ", insertBetweenRate=" + insertBetweenRate + "]";
    }

}
