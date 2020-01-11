package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.nssol.sysrdc.santa.data.Trip;
import jp.co.nssol.sysrdc.santa.io.InputTripFileReader;
import jp.co.nssol.sysrdc.santa.logic.SwapBetween;
import jp.co.nssol.sysrdc.santa.utils.Const;

public class TestSwapBetween {

    public static void main(String[] args) {
        String inputFilePath1 = Const.TestDirectoryPath + Const.inputTripFileName_1;
        int TripID_1 = Const.TripID_1;

        String inputFilePath2 = Const.TestDirectoryPath + Const.inputTripFileName_2;
        int TripID_2 = Const.TripID_2;

        // 各インスタンス生成
        Trip trip1 = new Trip(TripID_1);
        Trip trip2 = new Trip(TripID_2);

        InputTripFileReader input1 = new InputTripFileReader(inputFilePath1);
        InputTripFileReader input2 = new InputTripFileReader(inputFilePath2);
        // tripの取得
        trip1.copyFrom(input1.readTripFileData(TripID_1));
        trip2.copyFrom(input2.readTripFileData(TripID_2));

        // testメソッドの記述
        SwapBetween sb = new SwapBetween(Const.seed);

        Logger logger = LoggerFactory.getLogger(TestSwapBetween.class);
        logger.info("\ninsert前のtrip1\n");
        logger.info(trip1.toString());
        logger.info("\ninsert前のtrip2\n");
        logger.info(trip2.toString());

        sb.swapGiftsBetweenLogic(trip1, trip2, 0, 0);

        logger.info("\ninsert後のtrip1\n");
        logger.info(trip1.toString());
        logger.info("\ninsert後のtrip2\n");
        logger.info(trip2.toString());

    }

}
