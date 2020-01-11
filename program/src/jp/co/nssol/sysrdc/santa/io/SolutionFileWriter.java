package jp.co.nssol.sysrdc.santa.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.data.Trip;
import jp.co.nssol.sysrdc.santa.record.FinalRecord;
import jp.co.nssol.sysrdc.santa.record.Record;

/**
 * 「tripIdとgiftIdの組み合わせ」のファイルと「その組み合わせでの評価値」のファイルを書き込むクラス
 *
 * @author onodera.kakeru
 *
 */

public class SolutionFileWriter {

    // コンストラクタ生成
    public SolutionFileWriter() {

    }

    /**
     * 「探索下中での最良solutionのtripIdとgiftIdの組み合わせ」のファイルと 「各世代の評価値」のファイルを それぞれ任意のパス、ファイル名で書き込むメソッド
     */

    public void write(String[] filePath, String StringNumber, Solution sol, List<Record> recordList, FinalRecord finalRecord) {
        writeSolution(filePath[0], sol);
        writeBestEvalData(filePath[1], recordList);
        writeBestEvaluationOfAllBenchmark(filePath[2], StringNumber, finalRecord);
        // writeEvaluation(evaluationFilePath, sol);
    }

    /**
     * 受け取ったsolution（tripの集合）の「tripIdとgiftIdの組み合わせ」のファイルを作り 任意のパス、ファイル名で書き込むメソッド
     */
    public void writeSolution(String outputFilePath, Solution sol) {
        try (FileWriter fw = new FileWriter(outputFilePath);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);) {

            // ヘッダーを設定
            pw.print("GiftId");
            pw.print(",");
            pw.print("TripId");
            pw.println();

            ArrayList<Trip> tripList = sol.getTrips();

            int tripListSize = tripList.size();

            // TripIdとGiftIdを書き込む
            for (int i = 0; i < tripListSize; i++) {
                Trip trip = tripList.get(i);

                int tripSize = trip.getGiftList().size();
                for (int j = 0; j < tripSize; j++) {
                    Gift gift = trip.getGiftList().get(j);
                    pw.print(gift.getGiftId());
                    pw.print(",");
                    pw.print(trip.getTripID());
                    pw.println();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暫定解の最良評価値，その時のループ回数，時刻をファイルに書き込むメソッド
     *
     * @author kujirada.renya
     */
    public void writeBestEvalData(String outputFilePath, List<Record> RecordList) {
        try (FileWriter fw = new FileWriter(outputFilePath);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);) {

            // ヘッダーを設定
            pw.print("Evaluation");
            pw.print(",");
            pw.print("Time");
            pw.print(",");
            pw.print("Iteration");
            pw.println();

            // 評価値の書き込み
            for (Record record : RecordList) {
                pw.print(record.toString());
                pw.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 各ベンチマークにおける最終世代の最良評価値をファイルに追記するメソッド
     *
     * @author kujirada.renya
     */
    public void writeBestEvaluationOfAllBenchmark(String outputFilePath, String StringNumber, FinalRecord finalRecord) {
        // ファイルに追記する場合は、FileWriterクラスの引数にtrueを指定
        try (FileWriter fw = new FileWriter(outputFilePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);) {

            // ヘッダーを設定
            if (Integer.parseInt(StringNumber) == 0) {
                pw.print("BenchmarkNumber");
                pw.print(",");
                pw.print("Evaluation");
                pw.print(",");
                pw.print("Time");
                pw.print(",");
                pw.print("Iteration");
                pw.print(",");
                pw.print("FinalTime");
                pw.print(",");
                pw.print("FinalIteration");
                pw.println();

            }

            // 評価値の書き込み
            pw.print(StringNumber);
            pw.print(",");
            pw.print(finalRecord.toString());
            pw.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // /**
    // * 受け取ったsolution（tripの集合）の「評価値」のファイルを 任意のパス、ファイル名で書き込むメソッド
    // */
    // public void writeEvaluation(String outputFilePath, Solution sol) {
    // try (FileWriter fw = new FileWriter(outputFilePath);
    // BufferedWriter bw = new BufferedWriter(fw);
    // PrintWriter pw = new PrintWriter(bw);) {
    //
    // // ヘッダーを設定
    // pw.print("Score");
    // pw.println();
    //
    // // solution（tripの集合）がもつ評価値を取得
    // double score = sol.getAllEval();
    //
    // // 評価値の書き込み
    // pw.print(score);
    // pw.println();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

}
