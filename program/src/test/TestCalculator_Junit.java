package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.data.Trip;
import jp.co.nssol.sysrdc.santa.logic.Calculator;
import jp.co.nssol.sysrdc.santa.utils.Const;

class TestCalculator_Junit {

    double gift1Weight = 10.0;
    double gift2Weight = 30.0;
    double gift3Weight = 50.0;

    // calcSolutionEvalのテストにのみ使用
    double tripEval = 10.0;
    double trip1Eval = 20.0;

    Trip trip = new Trip(Const.seed);
    Trip trip1 = new Trip(Const.seed + 1);
    Gift northPole = Const.northPole;
    Gift gift1 = new Gift(1, 30.0, 20.0, gift1Weight);
    Gift gift2 = new Gift(2, 10.0, 20.0, gift2Weight);
    Gift gift3 = new Gift(3, 20.0, 20.0, gift3Weight);

    Solution sol = new Solution();

    void initTestCalcTripEval1() {
        trip.initialize(Const.seed);
        trip.addGift(gift1);
        trip.addGift(gift2);
    }

    void initTestCalcTripEval2() {
        trip.initialize(Const.seed);
        trip.addGift(gift2);
        trip.addGift(gift1);
    }

    void initTestCalcTripEval3() {
        trip.initialize(Const.seed);
        trip.addGift(gift1);
    }

    void initTestCalcSolutionEval() {
        trip.initialize(Const.seed);
        trip.addGift(gift1);
        trip.addGift(gift2);
        trip.setEvaluationValue(tripEval);
        // System.out.println(trip.getEvaluationValue());

        trip1.initialize(Const.seed + 1);
        trip1.addGift(gift3);
        trip1.setEvaluationValue(trip1Eval);

        // System.out.println(trip1.getEvaluationValue());

        sol.getTrips().add(trip);
        sol.getTrips().add(trip1);

        // System.out.println(sol.getTrips().get(0).getEvaluationValue());
    }

    double distNorthToGift1 = Calculator.calcHaversineDistance(northPole, gift1);
    double distNorthToGift2 = Calculator.calcHaversineDistance(northPole, gift2);
    double distGift1ToGift2 = Calculator.calcHaversineDistance(gift1, gift2);

    // 想定しているtripの評価値
    // NorthPole ~ gift1 ~ gift2 ~ NorthPole
    double trueTripEval1 = (Const.weightOfSleigh + gift1Weight + gift2Weight) * distNorthToGift1 + (Const.weightOfSleigh + gift2Weight) * distGift1ToGift2
        + Const.weightOfSleigh * distNorthToGift2;

    // NorthPole ~ gift2 ~ gift1 ~ NorthPole
    double trueTripEval2 = (Const.weightOfSleigh + gift1Weight + gift2Weight) * distNorthToGift2 + (Const.weightOfSleigh + gift1Weight) * distGift1ToGift2
        + Const.weightOfSleigh * distNorthToGift1;

    // NorthPole ~ gift1 ~ NorthPole
    double trueTripEval3 = (Const.weightOfSleigh + gift1Weight) * distNorthToGift1 + Const.weightOfSleigh * distNorthToGift1;

    // 想定しているsolutionの評価値
    double trueSolutionEval = tripEval + trip1Eval;

    // calcTripEvalのテスト
    @Test
    void testCalcTripEval1() {
        initTestCalcTripEval1();
        assertEquals(trueTripEval1, Calculator.calcTripEval(trip));
    }

    @Test
    void testCalcTripEval2() {
        initTestCalcTripEval2();
        assertEquals(trueTripEval2, Calculator.calcTripEval(trip));
    }

    @Test
    void testCalcTripEval3() {
        initTestCalcTripEval3();
        assertEquals(trueTripEval3, Calculator.calcTripEval(trip));
    }

    // calcSolutionEvalのテスト
    @Test
    void testCalcSolutionEval() {
        initTestCalcSolutionEval();
        // System.out.println(trip.getEvaluationValue());
        assertEquals(trueSolutionEval, Calculator.calcSolutionEval(sol));
    }

}
