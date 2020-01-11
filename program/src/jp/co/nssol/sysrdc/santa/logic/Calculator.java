package jp.co.nssol.sysrdc.santa.logic;

import java.util.ArrayList;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.data.Trip;
import jp.co.nssol.sysrdc.santa.utils.Const;

/**
 * solution(tripの集合)の評価値やtripの評価値を計算するクラス
 *
 *
 * @author onodera.kakeru
 *
 */

public class Calculator {
    /**
     * solutionの評価値を返すメソッド
     *
     * @param sol
     * @return solutionEval
     */
    public static double calcSolutionEval(Solution sol) {
        ArrayList<Trip> tripList = sol.getTrips();

        double solutionEval = 0;

        for (int i = 0; i < tripList.size(); i++) {
            solutionEval += tripList.get(i).getEvaluationValue();
        }

        return solutionEval;
    }

    /**
     * tripの評価値を返すメソッド
     *
     * @param trip
     * @return tripEval
     */
    public static double calcTripEval(Trip trip) {
        // tripからgiftのリスト取り出す
        ArrayList<Gift> giftList = trip.getGiftList();
        // tripに含まれるgiftのweightの和を取り出す
        double weights = trip.calcSumOfWeight();

        // giftListの末尾に北極の情報を追加
        giftList.add(Const.northPole);
        // ソリの重さを足す
        weights += Const.weightOfSleigh;

        // 初期値
        double tripEval = 0.0;
        Gift prevGift = Const.northPole;
        double prevWeight = weights;

        // 評価値の計算
        for (int i = 0; i < giftList.size(); i++) {
            tripEval += calcHaversineDistance(prevGift, giftList.get(i)) * prevWeight;
            prevGift = giftList.get(i);
            prevWeight -= prevGift.getWeight();
        }

        // giftListの末尾（北極）を削除
        giftList.remove(giftList.size() - 1);

        return tripEval;

    }

    /**
     * 二つのgift間のhaversine distanceを返すメソッド
     *
     * @param g1
     * @param g2
     * @return haversineDistance
     */
    public static double calcHaversineDistance(Gift g1, Gift g2) {
        // 2地点間の緯度(lat)と経度(lon)を取得
        double lat1 = g1.getLatitude();
        double lon1 = g1.getLongitude();
        double lat2 = g2.getLatitude();
        double lon2 = g2.getLongitude();

        // 緯度, 経度それぞれの差分をラジアンに
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        // Haversin formula
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double haversineDistance = Const.radiusOfEarth * c;
        return haversineDistance;
    }

}
