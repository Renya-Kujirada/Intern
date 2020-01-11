package jp.co.nssol.sysrdc.santa.logic;

import java.util.ArrayList;
import java.util.Collections;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.data.Trip;

/**
 * trip内でgiftの交換をした近傍解が欲しいときに使う neiborhoodOperation(Solution)メソッドを使うことで近傍解を返す
 *
 * @author onodera.kakeru ,tested by kujirada
 *
 */

public class SwapInside extends AbstractNeiborhoodOperation {

    // コンストラクタ
    public SwapInside(int seed) {
        super(seed);

    }

    /**
     * trip内のスワップの実行メソッド
     *
     * @param prevSol
     * @return
     */
    @Override
    public Solution neighborhoodOperation(Solution prevSol) {
        Solution nextSol = new Solution();

        nextSol.copyFrom(prevSol);

        // Solutionからランダムにswapを行うtripを選択
        Trip trip;
        do {
            trip = randomSelectSingleTrip(nextSol, random);
        } while (trip.getGiftList().size() < 2);

        // swapを行うTrip内のgiftのindexをそれぞれ被りの無いよう取得
        int giftIndex1 = randomSelectGiftIndex(trip, random);
        int giftIndex2 = getIndex2(trip, giftIndex1);

        // 同一trip内で二つのgiftをswapする
        swapGiftsInsideLogic(trip, giftIndex1, giftIndex2);

        // 近傍操作後のtripの評価値の更新とそれに伴うsolutionの評価値の更新
        updateOneTripEval(trip, nextSol);

        return nextSol;
    }

    /**
     * 同一trip内で二つのgiftをswapするロジックのメソッド
     *
     * @param trip
     */
    public void swapGiftsInsideLogic(Trip trip, int giftIndex1, int giftIndex2) {
        ArrayList<Gift> giftList = trip.getGiftList();
        // tripのgiftIndex1番目とgiftIndex2番目のgiftを交換
        Collections.swap(giftList, giftIndex1, giftIndex2);
    }

    // 引数のindexとは異なるgiftのインデックスを取得
    public int getIndex2(Trip trip, int giftIndex1) {
        int giftIndex2 = 0;

        do {
            giftIndex2 = randomSelectGiftIndex(trip, random);
            // index1と被るようであれば再度乱数発生
        } while (giftIndex1 == giftIndex2);

        return giftIndex2;
    }

}
