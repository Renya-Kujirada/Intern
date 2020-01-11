package jp.co.nssol.sysrdc.santa.logic;

import java.util.ArrayList;
import java.util.Random;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.data.Trip;

/**
 * 暫定解(prevSol)を受け取り近傍操作をして、近傍解(nextSol)を返す ・prevSolを受け取る ・prevSolをdeepcopy(nextSol) ・nextSolからランダムに対象tripを選ぶ ・そのtripの中から対象giftを選ぶ ・近傍操作
 * ・近傍操作後のtripのevaluationValueの更新 ・nextSolのallevalの更新 ・nextSol返す
 *
 * @author onodera.kakeru
 *
 */

abstract class AbstractNeiborhoodOperation {

    // 近傍操作直後の解（新しい解）
    // protected Solution nextSol;

    // 乱数生成
    protected Random random;

    // コンストラクタ
    public AbstractNeiborhoodOperation(int seed) {
        this.random = new Random(seed);
    }

    abstract Solution neighborhoodOperation(Solution prevSol);

    /**
     * solutionに含まれるtripの集合からランダムに一つのtripを選ぶメソッド
     *
     * @param sol
     * @param random TODO
     * @return
     */
    public Trip randomSelectSingleTrip(Solution sol, Random random) {
        ArrayList<Trip> trips = sol.getTrips();
        int solutionSize = trips.size();
        int selectedIndex = random.nextInt(solutionSize);
        Trip trip = trips.get(selectedIndex);

        return trip;
    }

    /**
     * tripからランダムに選ぶgiftのインデックスとなる値を返すメソッド
     *
     * @param trip
     * @param random TODO
     * @return
     */
    public int randomSelectGiftIndex(Trip trip, Random random) {
        ArrayList<Gift> giftList = trip.getGiftList();
        int tripSize = giftList.size();
        int randomSelectedIndex = random.nextInt(tripSize);

        return randomSelectedIndex;
    }

    /**
     * trip間での挿入において、挿入先のインデックスをランダムに選ぶためのメソッド
     *
     * @param trip
     * @param random TODO
     * @return
     */
    public int randomSelectInsertToIndex(Trip trip, Random random) {
        ArrayList<Gift> giftList = trip.getGiftList();
        // trip間の挿入操作では、挿入後のサイズで乱数発生させる
        int size = giftList.size() + 1;
        int insertToIndex = random.nextInt(size);

        return insertToIndex;
    }

    /**
     * trip内近傍操作が行われた時の評価値更新メソッド trip内での近傍操作が行われた後に、そのtripの評価値の更新とそれに伴うsolutionの評価値の更新を行うためのメソッド
     *
     * @param trip
     * @param nextSol
     */
    public void updateOneTripEval(Trip trip, Solution nextSol) {
        trip.setEvaluationValue(Calculator.calcTripEval(trip));
        double nextAllEval = Calculator.calcSolutionEval(nextSol);
        nextSol.setAllEval(nextAllEval);
    }

    /**
     * trip間近傍操作が行われた時の評価値更新メソッド trip間での近傍操作が行われた後に、その二つのtripの評価値の更新とそれに伴うsolutionの評価値の更新を行うためのメソッド
     *
     * @param trip1
     * @param trip2
     * @param nextSol
     */
    public void updateTwoTripsEval(Trip trip1, Trip trip2, Solution nextSol) {
        trip1.setEvaluationValue(Calculator.calcTripEval(trip1));
        trip2.setEvaluationValue(Calculator.calcTripEval(trip2));
        double nextAllEval = Calculator.calcSolutionEval(nextSol);
        nextSol.setAllEval(nextAllEval);
    }

}
