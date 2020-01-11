package jp.co.nssol.sysrdc.santa.logic;

import java.util.ArrayList;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.data.Trip;

/**
 * trip内でgiftの挿入をした近傍解が欲しいときに使う neiborhoodOperation(Solution)メソッドを使うことで近傍解を返す
 *
 * @author onodera.kakeru
 *
 */

public class InsertInside extends AbstractNeiborhoodOperation {

    // コンストラクタ
    public InsertInside(int seed) {
        super(seed);
    }

    /**
     * trip内の挿入の実行メソッド
     *
     * @param prevSol
     * @return
     */
    @Override
    public Solution neighborhoodOperation(Solution prevSol) {
        Trip trip;

        Solution nextSol = new Solution();
        nextSol.copyFrom(prevSol);
        do {
            trip = randomSelectSingleTrip(nextSol, random);
        } while (trip.getGiftList().size() < 2);

        int fromIndex = getInsertFromIndex(trip);
        int toIndex = getInsertToIndex(trip, fromIndex);

        insertGiftInsideLogic(trip, fromIndex, toIndex);

        updateOneTripEval(trip, nextSol);

        return nextSol;
    }

    /**
     * 同一trip内で giftをfromIndexからtoIndexに挿入するメソッド
     *
     * @param trip insertするtrip
     * @param fromIndex 挿入(insert)されるgiftのインデックス
     * @param toIndex 挿入先のgiftのIndexを取得
     */
    public void insertGiftInsideLogic(Trip trip, int fromIndex, int toIndex) {
        ArrayList<Gift> giftList = trip.getGiftList();

        // 挿入(insert)されるgiftのインデックス
        Gift insertGift = giftList.get(fromIndex);

        giftList.remove(fromIndex);

        giftList.add(toIndex, insertGift);
    }

    // 挿入(insert)されるgiftのインデックスを取得
    public int getInsertFromIndex(Trip trip) {
        int insertFromGiftIndex = randomSelectGiftIndex(trip, random);
        return insertFromGiftIndex;
    }

    // 挿入先のgiftのIndexを取得
    public int getInsertToIndex(Trip trip, int insertFromGiftIndex) {
        int insertToGiftIndex = randomSelectGiftIndex(trip, random);

        while (insertFromGiftIndex == insertToGiftIndex) {
            insertToGiftIndex = randomSelectGiftIndex(trip, random);
        }
        return insertToGiftIndex;
    }

}
