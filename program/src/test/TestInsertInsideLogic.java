package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.nssol.sysrdc.santa.data.Trip;
import jp.co.nssol.sysrdc.santa.io.InputTripFileReader;
import jp.co.nssol.sysrdc.santa.logic.InsertInside;
import jp.co.nssol.sysrdc.santa.utils.Const;

public class TestInsertInsideLogic {

    public static void main(String[] args) {
        String inputFilePath = Const.TestDirectoryPath + Const.inputTripFileName_1;
        int TripID = Const.TripID_1;

        // 各インスタンス生成
        Trip trip = new Trip(TripID);
        InputTripFileReader input = new InputTripFileReader(inputFilePath);
        // tripの取得
        // trip = input.readTripFileData(TripID);
        trip.copyFrom(input.readTripFileData(TripID));

        // testメソッドの記述
        InsertInside ii = new InsertInside(Const.seed);

        Logger logger = LoggerFactory.getLogger(InsertInside.class);
        logger.info("\ninsert前のtrip\n");
        logger.info(trip.toString());

        ii.insertGiftInsideLogic(trip, 7, 6);

        logger.info("{}", "\ninsert後のtrip\n");
        logger.info(trip.toString());

    }

}
