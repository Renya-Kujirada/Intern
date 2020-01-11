package jp.co.nssol.sysrdc.santa.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.property.Property;

/**
 * 入力ファイルの情報（GiftId, Latitude, Longitude, Weight）を Gift型のリストgiftListへ格納し、そのリストを返すクラス
 *
 * @author kujirada.renya
 *
 */

public class InputFileReader {

    public String inputFilePath;
    public String inputPropertyFile;

    // コンストラクタ生成
    public InputFileReader(String filename, String propertyFilename) {
        inputFilePath = filename;
        inputPropertyFile = propertyFilename;
    }

    /**
     * ファイルから読み込み，Gift型のリストgiftListへデータを格納し，giftListを返す
     *
     * @return : giftList
     */
    public List<Gift> readFileData() {

        String line; // ファイルから一行保存するための変数
        String data[] = new String[4]; // 一行分のデータを格納

        // ギフトを格納しているリスト
        List<Gift> giftList = new ArrayList<Gift>();
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

                // リストに追加
                giftList.add(gift);
            }
            return giftList;

        } catch (FileNotFoundException e) {
            System.out.println(inputFilePath + "が見つかりません。");

        } catch (IOException e) {
            System.out.println(e);
        }
        return giftList;
    }

    public Property readPropertyData() {
        String line; // ファイルから一行保存するための変数
        String data[] = new String[4]; // 一行分のデータを格納
        Property pr = new Property();

        // tryのすぐ後にクローズの対象となるリソースの生成処理を記述(try-with-resource構文)
        try (BufferedReader br = new BufferedReader(new FileReader(inputPropertyFile))) {
            // ファイルの一行目は不要なのでスキップ
            br.readLine();

            while ((line = br.readLine()) != null) {
                // lineをカンマで分割した内容を配列に格納
                data = line.split(",");

                // 各配列の要素をGift用に型変換する
                pr.setSeed(Integer.parseInt(data[0]));
                pr.setSwapInsideRate(Integer.parseInt(data[1]));
                pr.setInsertInside(Integer.parseInt(data[2]));
                pr.setSwapBetween(Integer.parseInt(data[3]));
                pr.setInsertBetween(Integer.parseInt(data[4]));

            }

        } catch (FileNotFoundException e) {
            System.out.println(inputPropertyFile + "が見つかりません。");

        } catch (IOException e) {
            System.out.println(e);
        }
        return pr;
    }
}
