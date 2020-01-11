package jp.co.nssol.sysrdc.santa.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.data.Trip;
import jp.co.nssol.sysrdc.santa.utils.Const;

/**
 *
 * 近傍操作のクラス
 *
 * @author onodera.kakeru
 *
 */
public class NeighborhoodOperation {
    // 近傍操作直後の解（新しい解）
    private Solution nextSol;

    // 乱数生成
    private Random random;

    // コンストラクタ
    public NeighborhoodOperation(int seed) {
        nextSol = new Solution();
        this.random = new Random(seed);
    }

    /**
     * trip間のswapの実行メソッド
     *
     * @param prevSol
     * @return
     */

    public Solution swapGiftsBetween(Solution prevSol) {
        Trip trip1;
        Trip trip2;

        do {
            nextSol.copyFrom(prevSol);
            do {
                trip1 = randomSelectSingleTrip(nextSol);
                trip2 = randomSelectSingleTrip(nextSol);
            } while (trip1.getTripID() == trip2.getTripID());

            swapGiftsBetweenLogic(trip1, trip2);
        } while (trip1.calcSumOfWeight() > Const.weightOfConstraints || trip2.calcSumOfWeight() > Const.weightOfConstraints);

        trip1.setEvaluationValue(Calculator.calcTripEval(trip1));
        trip2.setEvaluationValue(Calculator.calcTripEval(trip2));
        double nextEval = Calculator.calcSolutionEval(nextSol);

        nextSol.setAllEval(nextEval);

        return nextSol;

    }

    /**
     * trip間の挿入の実行メソッド
     *
     * @param prevSol
     * @return
     */

    public Solution insertGiftBetween(Solution prevSol) {
        Trip fromTrip;
        Trip toTrip;

        do {
            nextSol.copyFrom(prevSol);
            do {
                fromTrip = randomSelectSingleTrip(nextSol);
                toTrip = randomSelectSingleTrip(nextSol);
            } while (fromTrip.getTripID() == toTrip.getTripID());
            insertGiftBetweenLogic(fromTrip, toTrip);
        } while (toTrip.calcSumOfWeight() > Const.weightOfConstraints);

        // giftの入っていないtripはnextSolから削除
        if (fromTrip.getGiftList().size() == 0) {
            nextSol.getTrips().remove(fromTrip);
        }

        fromTrip.setEvaluationValue(Calculator.calcTripEval(fromTrip));
        toTrip.setEvaluationValue(Calculator.calcTripEval(toTrip));

        double nextEval = Calculator.calcSolutionEval(nextSol);

        nextSol.setAllEval(nextEval);

        return nextSol;

    }

    /**
     * trip内のスワップの実行メソッド
     *
     * @param prevSol
     * @return
     */
    public Solution swapGiftsInside(Solution prevSol) {
        nextSol.copyFrom(prevSol);

        Trip trip;
        do {
            trip = randomSelectSingleTrip(nextSol);
        } while (trip.getGiftList().size() < 2);

        swapGiftsInsideLogic(trip);

        trip.setEvaluationValue(Calculator.calcTripEval(trip));

        double nextEval = Calculator.calcSolutionEval(nextSol);

        nextSol.setAllEval(nextEval);

        return nextSol;
    }

    /**
     * trip内の挿入の実行メソッド
     *
     * @param prevSol
     * @return
     */
    public Solution insertGiftInside(Solution prevSol) {
        nextSol.copyFrom(prevSol);

        Trip trip;

        do {
            trip = randomSelectSingleTrip(nextSol);
        } while (trip.getGiftList().size() < 2);

        insertGiftInsideLogic(trip);

        trip.setEvaluationValue(Calculator.calcTripEval(trip));
        double nextEval = Calculator.calcSolutionEval(nextSol);

        nextSol.setAllEval(nextEval);

        return nextSol;
    }

    /**
     * 二つのtrip間でランダムに選ばれた二つのgiftをスワップするロジックのメソッド
     *
     * @param trip1
     * @param trip2
     */
    private void swapGiftsBetweenLogic(Trip trip1, Trip trip2) {
        ArrayList<Gift> giftList1 = trip1.getGiftList();
        ArrayList<Gift> giftList2 = trip2.getGiftList();

        int giftIndex1 = randomSelectGiftIndex(trip1);
        int giftIndex2 = randomSelectGiftIndex(trip2);

        Gift gift1 = giftList1.get(giftIndex1);
        Gift gift2 = giftList2.get(giftIndex2);

        giftList1.remove(giftIndex1);
        giftList2.remove(giftIndex2);

        giftList1.add(giftIndex1, gift2);
        giftList2.add(giftIndex2, gift1);

    }

    /**
     * ランダムに選ばれた一つのgiftをランダムに選ばれたtripのランダムなインデックスに挿入するロジックのメソッド
     *
     * @param insertFromTrip
     * @param insertToTrip
     */
    private void insertGiftBetweenLogic(Trip insertFromTrip, Trip insertToTrip) {
        ArrayList<Gift> insertFromGiftList = insertFromTrip.getGiftList();
        ArrayList<Gift> insertToGiftList = insertToTrip.getGiftList();

        int insertFromGiftIndex = randomSelectGiftIndex(insertFromTrip);
        Gift insertGift = insertFromGiftList.get(insertFromGiftIndex);
        insertFromGiftList.remove(insertFromGiftIndex);

        int insertToGiftIndex = randomSelectGiftIndex(insertToTrip);
        insertToGiftList.add(insertToGiftIndex, insertGift);

    }

    /**
     * 同一trip内で二つのgiftをswapするロジックのメソッド
     *
     * @param trip
     */
    private void swapGiftsInsideLogic(Trip trip) {
        ArrayList<Gift> giftList = trip.getGiftList();

        int giftIndex1 = randomSelectGiftIndex(trip);
        int giftIndex2 = randomSelectGiftIndex(trip);

        while (giftIndex1 == giftIndex2) {
            giftIndex1 = randomSelectGiftIndex(trip);

        }

        Collections.swap(giftList, giftIndex1, giftIndex2);
    }

    /**
     * 同一trip内でランダムに選んだgiftをランダムなインデックスに挿入するロジックのメソッド
     *
     * @param trip
     */
    private void insertGiftInsideLogic(Trip trip) {
        ArrayList<Gift> giftList = trip.getGiftList();

        // 挿入(insert)されるgiftのインデックス
        int insertFromGiftIndex = randomSelectGiftIndex(trip);
        Gift insertGift = giftList.get(insertFromGiftIndex);
        giftList.remove(insertFromGiftIndex);

        // 挿入先のインデックス
        int insertToGiftIndex = randomSelectInsertToIndex(trip);

        while (insertFromGiftIndex == insertToGiftIndex) {
            insertToGiftIndex = randomSelectGiftIndex(trip);
        }

        giftList.add(insertToGiftIndex, insertGift);
    }

    /**
     * solutionに含まれるtripの集合からランダムに一つのtripを選ぶメソッド
     *
     * @param sol
     * @return
     */
    private Trip randomSelectSingleTrip(Solution sol) {
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
     * @return
     */
    private int randomSelectGiftIndex(Trip trip) {
        ArrayList<Gift> giftList = trip.getGiftList();
        int tripSize = giftList.size();

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < tripSize; i++) {
            list.add(i);
        }

        Collections.shuffle(list, random);

        int randomSelectedIndex = list.get(0);

        return randomSelectedIndex;
    }

    /**
     * 同一trip内での挿入において、挿入先のインデックスをランダムに選ぶためのメソッド
     *
     * @param trip
     * @return
     */
    private int randomSelectInsertToIndex(Trip trip) {
        ArrayList<Gift> giftList = trip.getGiftList();
        // 同一trip内での挿入では、挿入先のインデックスを指定する前にgiftをリムーブするのでそれを考慮
        int size = giftList.size() + 1;

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }

        Collections.shuffle(list, random);

        int insertToIndex = list.get(0);

        return insertToIndex;
    }

}
