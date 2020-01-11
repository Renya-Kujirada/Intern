package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.data.Trip;
import jp.co.nssol.sysrdc.santa.logic.InsertBetween;
import jp.co.nssol.sysrdc.santa.utils.Const;

public class TestInsertBetween {

    public static void main(String[] args) {
        String inputFilePath1 = Const.TestDirectoryPath + Const.inputTripFileName_1;
        int TripID_1 = Const.TripID_1;

        String inputFilePath2 = Const.TestDirectoryPath + Const.inputTripFileName_2;
        int TripID_2 = Const.TripID_2;

        // 各インスタンス生成
        // Trip trip1 = new Trip(TripID_1);
        // Trip trip2 = new Trip(TripID_2);
        Logger logger = LoggerFactory.getLogger(TestInsertBetween.class);

        double gift1Weight = 10.0;
        double gift2Weight = 30.0;
        double gift3Weight = 50.0;

        Trip trip3 = new Trip(3);
        Trip trip4 = new Trip(4);

        Gift gift1 = new Gift(1, 30.0, 20.0, gift1Weight);
        Gift gift2 = new Gift(2, 10.0, 20.0, gift2Weight);
        Gift gift3 = new Gift(3, 20.0, 20.0, gift3Weight);

        trip3.initialize(3);
        trip4.initialize(4);

        trip3.addGift(gift1);
        trip4.addGift(gift2);
        trip4.addGift(gift3);

        Solution sol = new Solution();
        sol.getTrips().add(trip3);
        sol.getTrips().add(trip4);

        logger.info("insert前のsol");
        logger.info("{}", sol.getTrips());

        // InputTripFileReader input1 = new InputTripFileReader(inputFilePath1);
        // InputTripFileReader input2 = new InputTripFileReader(inputFilePath2);
        // // tripの取得
        // trip1.copyFrom(input1.readTripFileData(TripID_1));
        // trip2.copyFrom(input2.readTripFileData(TripID_2));

        // testメソッドの記述
        InsertBetween ib = new InsertBetween(3);

        // logger.info("\ninsert前のtrip1\n");
        // logger.info(trip3.toString());
        // logger.info("\ninsert前のtrip2\n");
        // logger.info(trip4.toString());
        //
        // logger.info("{} {}", trip3.getGiftList().size(), trip4.getGiftList().size());

        // ib.insertGiftBetweenLogic(trip3, trip4, 0, 0);
        Solution nextSol = ib.neighborhoodOperation(sol);
        logger.info("insert後のsol");
        logger.info("{}", nextSol.getTrips());

        // logger.info("\ninsert後のtrip1\n");
        // logger.info(trip3.toString());
        // logger.info("\ninsert後のtrip2\n");
        // logger.info(trip4.toString());

        logger.info("{} {}", trip3.getGiftList().size(), trip4.getGiftList().size());

    }

}
