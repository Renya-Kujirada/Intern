package jp.co.nssol.sysrdc.santa.data;

import java.util.ArrayList;

import jp.co.nssol.sysrdc.santa.logic.Calculator;

/**
 * tripの集合(trips)とその評価値(allEval)を管理するクラス
 *
 * @author onodera.kakeru
 *
 */

public class Solution {

    // tripのリスト
    private ArrayList<Trip> trips;

    // trip全体の評価値
    private double allEval;

    // コンストラクタ
    public Solution() {
        trips = new ArrayList<Trip>();
        allEval = Double.NaN;
    }

    /**
     * show results :each trips's giftID, sum of weight, eval, ID
     *
     * @author kujirada.renya
     */
    // 結果を出力
    public void showResult() {
        // 各trip
        for (int i = 0; i < trips.size(); ++i) {
            Trip trip = trips.get(i);
            for (int j = 0; j < trip.getGiftList().size(); ++j) {
                // GiftIDを出力
                System.out.println(trip.getGiftList().get(j).getGiftId());
            }
            // 重さの合計を出力
            System.out.println("SumOfWeight:" + trip.calcSumOfWeight());
            // Tripの評価値を出力
            System.out.println("EvalOfTrip:" + Calculator.calcTripEval(trip));
            // tripIDを出力
            System.out.println("tripID:" + trip.getTripID());
        }
        // trip全体の評価値を出力
        System.out.println("allEval:" + allEval);
    }

    /**
     * deep copy
     *
     * @author kujirada.renya
     */
    public void copyFrom(Solution sol) {
        // Tripリストの初期化
        this.initSolution();// ←変数を使いまわすなら必要．ただ，newをしたものに対してのcopuFromなら，newをした時点で初期化は実行されているため，不要．

        ArrayList<Trip> copyTripList = sol.getTrips();

        // sol内のTripを一つ一つコピー
        for (Trip copyTrip : copyTripList) {
            // 新しくnewをしたtempTripにデータをコピー
            Trip tempTrip = new Trip(copyTrip.getTripID());
            tempTrip.copyFrom(copyTrip);
            this.trips.add(tempTrip);
        }
        this.allEval = sol.getAllEval();
    }

    /**
     * 解の初期化
     *
     * @author kujirada.renya
     */
    public void initSolution() {
        this.trips.clear();
        this.allEval = Double.NaN;
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(ArrayList<Trip> trips) {
        this.trips = trips;
    }

    public double getAllEval() {
        return allEval;
    }

    public void setAllEval(double allEval) {
        this.allEval = allEval;
    }

    double calcAllEval() {
        // 計算処理書く

        return allEval;
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        st.append("\n");
        for (Trip trip : trips) {
            st.append(trip.toString());
            st.append("\n");
        }
        st.append("Solution [allEval=" + allEval + "]\n");
        // st.append("Solution [trips=" + trips + ", allEval=" + allEval + "]\n");

        return st.toString();
    }

}
