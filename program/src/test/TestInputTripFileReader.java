package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.nssol.sysrdc.santa.data.Trip;
import jp.co.nssol.sysrdc.santa.io.InputTripFileReader;
import jp.co.nssol.sysrdc.santa.sample.LoggerSample;
import jp.co.nssol.sysrdc.santa.utils.Const;

public class TestInputTripFileReader {

    // test用mainメソッド
    public static void main(String[] args) {

        String inputFilePath = Const.TestDirectoryPath + Const.inputTripFileName_1;
        int TripID = Const.TripID_1;

        // 各インスタンス生成
        Logger logger = LoggerFactory.getLogger(LoggerSample.class);
        Trip trip = new Trip(TripID);
        InputTripFileReader input = new InputTripFileReader(inputFilePath);

        // tripの取得
        trip.copyFrom(input.readTripFileData(TripID));

        // loggerで解の取得.
        logger.info("{}", trip.toString());
    }

}
