package jp.co.nssol.sysrdc.santa.logic;

import java.util.ArrayList;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.data.Trip;
import jp.co.nssol.sysrdc.santa.utils.Const;

/**
 * trip間でgiftの交換をした近傍解が欲しいときに使う neiborhoodOperation(Solution)メソッドを使うことで近傍解を返す
 *
 * @author onodera.kakeru
 *
 */

public class SwapBetween extends AbstractNeiborhoodOperation {

    // コンストラクタ
    public SwapBetween(int seed) {
        super(seed);
    }

    /**
     * trip間のswapの実行メソッド
     *
     * @param prevSol
     * @return nextSol
     */

    @Override
    public Solution neighborhoodOperation(Solution prevSol) {

        Trip trip1;
        Trip trip2;

        Solution nextSol = new Solution();

        do {
            nextSol.copyFrom(prevSol);
            do {
                trip1 = randomSelectSingleTrip(nextSol, random);
                trip2 = randomSelectSingleTrip(nextSol, random);
            } while (trip1.getTripID() == trip2.getTripID());

            int giftIndex1 = randomSelectGiftIndex(trip1, random);
            int giftIndex2 = randomSelectGiftIndex(trip2, random);
            swapGiftsBetweenLogic(trip1, trip2, giftIndex1, giftIndex2);
        } while (trip1.calcSumOfWeight() > Const.weightOfConstraints || trip2.calcSumOfWeight() > Const.weightOfConstraints);

        updateTwoTripsEval(trip1, trip2, nextSol);

        return nextSol;

    }

    /**
     * giftIndexのgiftをスワップするロジックのメソッド
     *
     * @param trip1
     * @param trip2
     */
    public void swapGiftsBetweenLogic(Trip trip1, Trip trip2, int giftIndex1, int giftIndex2) {
        ArrayList<Gift> giftList1 = trip1.getGiftList();
        ArrayList<Gift> giftList2 = trip2.getGiftList();

        Gift gift1 = giftList1.get(giftIndex1);
        Gift gift2 = giftList2.get(giftIndex2);

        giftList1.remove(giftIndex1);
        giftList2.remove(giftIndex2);

        giftList1.add(giftIndex1, gift2);
        giftList2.add(giftIndex2, gift1);

    }

    // /**
    // * swapGiftsBetweenLogicのテスト用メソッド swapするgiftのindexは外側から与える
    // *
    // * @param trip1
    // * @param trip2
    // * @param index1
    // * @param index2
    // */
    // public void testSwapGiftsBetweenLogic(Trip trip1, Trip trip2, int index1, int index2) {
    // ArrayList<Gift> giftList1 = trip1.getGiftList();
    // ArrayList<Gift> giftList2 = trip2.getGiftList();
    //
    // Logger logger = LoggerFactory.getLogger(LoggerSample.class);
    // logger.info("{}", "\nswap前のtrip1\n");
    // logger.info("{}", trip1.toString());
    // logger.info("{}", "\nswap前のtrip2\n");
    // logger.info("{}", trip2.toString());
    //
    // int giftIndex1 = index1;
    // int giftIndex2 = index2;
    //
    // logger.info("{}", "\nswapするgiftのindex\n");
    // logger.info("{} {}", giftIndex1, giftIndex2);
    // logger.info("{}", "\nswapするgiftのID\n");
    // logger.info("{} {}", giftList1.get(giftIndex1).getGiftId(), giftList2.get(giftIndex2).getGiftId());
    //
    // Gift gift1 = giftList1.get(giftIndex1);
    // Gift gift2 = giftList2.get(giftIndex2);
    //
    // giftList1.remove(giftIndex1);
    // giftList2.remove(giftIndex2);
    //
    // giftList1.add(giftIndex1, gift2);
    // giftList2.add(giftIndex2, gift1);
    //
    // logger.info("{}", "\nswap後のtrip1\n");
    // logger.info("{}", trip1.toString());
    // logger.info("{}", "\nswap後のtrip2\n");
    // logger.info("{}", trip2.toString());
    //
    // }

}
