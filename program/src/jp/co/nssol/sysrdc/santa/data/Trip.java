package jp.co.nssol.sysrdc.santa.data;

import java.util.ArrayList;

import jp.co.nssol.sysrdc.santa.logic.Calculator;
import jp.co.nssol.sysrdc.santa.utils.Const;

/**
 * 経路（trip）を形成するクラス
 *
 * @author kujirada.renya
 *
 */
public class Trip {
    // 評価値
    private double evaluationValue;

    // Giftの重さの合計
    private double sumWeight;

    // 各tripにおける道順（giftリスト）
    private ArrayList<Gift> giftList;

    // tripID
    private int tripID;

    // コンストラクタ
    public Trip(int id) {
        tripID = id;
        giftList = new ArrayList<Gift>();
    }

    // 各IDで初期化
    public Trip initialize(int id) {
        evaluationValue = 0;
        sumWeight = 0;
        giftList.clear();
        tripID = id;

        return this;
    }

    // TripにGiftをadd
    public void addGift(Gift gift) {
        this.getGiftList().add(gift);
        this.sumWeight += gift.getWeight();
    }

    // tripにおけるgiftの重さの合計を返す
    public double calcSumOfWeight() {
        sumWeight = 0.0;
        // 拡張for文
        for (Gift gift : giftList) {
            sumWeight += gift.getWeight();
        }

        return sumWeight;
    }

    // ★testまだしてない
    // tripにおける総距離を返す
    public double getSumOfDistance() {
        double sumDistance = 0.0;
        // 北極とtripList先頭gift間の距離を計算
        sumDistance += Calculator.calcHaversineDistance(Const.northPole, giftList.get(0));

        for (int i = 0; i < giftList.size() - 1; ++i) {
            sumDistance += Calculator.calcHaversineDistance(giftList.get(i), giftList.get(i + 1));
        }

        // tripList末尾と北極間の距離を計算
        sumDistance += Calculator.calcHaversineDistance(giftList.get(giftList.size() - 1), Const.northPole);

        return sumDistance;
    }

    /**
     * deep copy
     */
    public void copyFrom(Trip trip) {
        // Giftリストの初期化
        // giftList.clear(); //←newをしないのであれば必要になる
        // trip内のgiftを一つ一つコピー
        for (Gift gift : trip.getGiftList()) {
            this.giftList.add(gift);
        }
        // this.tripID = trip.getTripID();
        this.sumWeight = trip.getSumWeight();
        this.evaluationValue = trip.getEvaluationValue();
    }

    // 以下はgetterとsetter
    public double getEvaluationValue() {
        return evaluationValue;
    }

    public void setEvaluationValue(double evaluationValue) {
        this.evaluationValue = evaluationValue;
    }

    public ArrayList<Gift> getGiftList() {
        return giftList;
    }

    public void setGiftList(ArrayList<Gift> tripList) {
        this.giftList = tripList;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public double getSumWeight() {
        return sumWeight;
    }

    public void setSumWeight(double sumWeight) {
        this.sumWeight = sumWeight;
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        st.append("\n");
        for (Gift gift : giftList) {
            st.append(gift.toString());
            st.append("\n");
        }
        st.append("Trip [evaluationValue=" + evaluationValue + ", sumWeight=" + sumWeight + ", tripID=" + tripID + "]");
        // st.append("Trip [evaluationValue=" + evaluationValue + ", sumWeight=" + sumWeight + ", tripID=" + tripID + "]\n");

        return st.toString();
    }

}
