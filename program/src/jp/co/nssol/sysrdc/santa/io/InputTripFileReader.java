package jp.co.nssol.sysrdc.santa.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Trip;
import jp.co.nssol.sysrdc.santa.logic.Calculator;

/**
 * tripの情報のみが記述されたファイルから一つのtripを形成するクラス
 *
 * @author kujirada.renya
 *
 */
public class InputTripFileReader {
    public String inputFilePath;

    // コンストラクタ生成
    public InputTripFileReader(String filename) {
        inputFilePath = filename;
    }

    /**
     * ファイルから，trip型のリストgiftListへデータを格納し，giftListを返す
     *
     * @param TripID
     * @return trip
     */
    public Trip readTripFileData(int TripID) {

        String line; // ファイルから一行保存するための変数
        String data[] = new String[4]; // 一行分のデータを格納

        // ギフトを格納しているリスト
        List<Gift> giftList = new ArrayList<Gift>();
        Trip trip = new Trip(TripID); // return用trip
        int giftId; // ギフトのid
        double latitude; // 緯度
        double longitude; // 経度
        double weight; // 重さ

        // tryのすぐ後にクローズの対象となるリソースの生成処理を記述(try-with-resource構文)
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            // ファイルの一行目は不要なのでスキップ
            br.readLine();

            while ((line = br.readLine()) != null) {
                // lineをカンマで分割した内容を配列に格納
                data = line.split(",");

                // 各配列の要素をGift用に型変換する
                giftId = Integer.parseInt(data[0]);
                latitude = Double.parseDouble(data[1]);
                longitude = Double.parseDouble(data[2]);
                weight = Double.parseDouble(data[3]);

                // giftインスタンス生成
                Gift gift = new Gift(giftId, latitude, longitude, weight);

                // tripにGiftを追加
                trip.addGift(gift);
            }
            // tripの評価理をセット
            trip.setEvaluationValue(Calculator.calcTripEval(trip));

            // tripの重さの合計値をセット
            trip.setSumWeight(trip.calcSumOfWeight());

            // return trip;

        } catch (FileNotFoundException e) {
            System.out.println(inputFilePath + "が見つかりません。");

        } catch (IOException e) {
            System.out.println(e);
        }
        return trip;
    }

}
