package jp.co.nssol.sysrdc.santa.logic;

import java.util.ArrayList;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.data.Trip;
import jp.co.nssol.sysrdc.santa.utils.Const;

/**
 * trip間でgiftの挿入をした近傍解が欲しいときに使う neiborhoodOperation(Solution)メソッドを使うことで近傍解を返す
 *
 * @author onodera.kakeru
 *
 */

public class InsertBetween extends AbstractNeiborhoodOperation {

    // コンストラクタ
    public InsertBetween(int seed) {
        super(seed);
    }

    /**
     * trip間の挿入の実行メソッド
     *
     * @param prevSol
     * @return
     */

    @Override
    public Solution neighborhoodOperation(Solution prevSol) {
        Trip fromTrip;
        Trip toTrip;

        Solution nextSol = new Solution();

        do {
            nextSol.copyFrom(prevSol);
            do {
                fromTrip = randomSelectSingleTrip(nextSol, random);
                toTrip = randomSelectSingleTrip(nextSol, random);
            } while (fromTrip.getTripID() == toTrip.getTripID());

            int fromIndex = randomSelectGiftIndex(fromTrip, random);
            int toIndex = randomSelectInsertToIndex(toTrip, random);
            insertGiftBetweenLogic(fromTrip, toTrip, fromIndex, toIndex);
            // System.out.println("nextSol.getTrips()" + nextSol.getTrips());
            // System.out.println("nextSol.getTrips().size()" + nextSol.getTrips().size());

        } while (toTrip.calcSumOfWeight() > Const.weightOfConstraints);

        // giftの入っていないtripはnextSolから削除
        if (fromTrip.getGiftList().size() == 0) {
            nextSol.getTrips().remove(fromTrip);
        }

        updateTwoTripsEval(fromTrip, toTrip, nextSol);

        return nextSol;

    }

    /**
     * insertFromTripのfromIndexにあるgiftをinsertToTripのtoIndexに挿入するロジックのメソッド
     *
     * @param insertFromTrip
     * @param insertToTrip
     */
    public void insertGiftBetweenLogic(Trip insertFromTrip, Trip insertToTrip, int fromIndex, int toIndex) {
        ArrayList<Gift> insertFromGiftList = insertFromTrip.getGiftList();
        ArrayList<Gift> insertToGiftList = insertToTrip.getGiftList();

        Gift insertGift = insertFromGiftList.get(fromIndex);
        insertFromGiftList.remove(fromIndex);

        insertToGiftList.add(toIndex, insertGift);

    }

    // /**
    // * insertGiftBetweenLogicのテスト用メソッド 外から挿入先、挿入元のindexを与える
    // *
    // * @param insertFromTrip
    // * @param insertToTrip
    // * @param fromIndex
    // * @param toIndex
    // */
    // public void testInsertGiftBetweenLogic(Trip insertFromTrip, Trip insertToTrip, int fromIndex, int toIndex) {
    // ArrayList<Gift> insertFromGiftList = insertFromTrip.getGiftList();
    // ArrayList<Gift> insertToGiftList = insertToTrip.getGiftList();
    //
    // Logger logger = LoggerFactory.getLogger(InsertBetween.class);
    // logger.info("\ninsert前のinsertFromTrip\n");
    // logger.info(insertFromTrip.toString());
    // logger.info("\ninsert前のinsertFromTripのサイズ\n");
    // logger.info("{}", insertFromTrip.getGiftList().size());
    // logger.info("\ninsert前のinsertToTrip\n");
    // logger.info(insertToTrip.toString());
    // logger.info("\ninsert前のinsertToTripのサイズ\n");
    // logger.info("{}", insertToTrip.getGiftList().size());
    //
    // logger.info("\ninsert元とinsert先のindex\n");
    // logger.info("{} {}", fromIndex, toIndex);
    // logger.info("\ninsertされるgiftのID\n");
    // logger.info("{} {}", insertFromGiftList.get(fromIndex).getGiftId());
    //
    // int insertFromGiftIndex = fromIndex;
    // Gift insertGift = insertFromGiftList.get(insertFromGiftIndex);
    // insertFromGiftList.remove(insertFromGiftIndex);
    //
    // int insertToGiftIndex = toIndex;
    // insertToGiftList.add(insertToGiftIndex, insertGift);
    //
    // logger.info("\ninsert後のinsertFromTrip\n");
    // logger.info(insertFromTrip.toString());
    // logger.info("\ninsert後のinsertFromTripのサイズ\n");
    // logger.info("{}", insertFromTrip.getGiftList().size());
    // logger.info("\ninsert後のinsertToTrip\n");
    // logger.info(insertToTrip.toString());
    // logger.info("\ninsert後のinsertToTripのサイズ\n");
    // logger.info("{}", insertToTrip.getGiftList().size());
    //
    // }

}
