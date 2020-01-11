package jp.co.nssol.sysrdc.santa.logic;

import java.util.List;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Solution;
import jp.co.nssol.sysrdc.santa.data.Trip;
import jp.co.nssol.sysrdc.santa.utils.Const;

/**
 * 初期解（Trip型のリスト）生成クラス 入力データの入っているリストgiftListの先頭から順番に重量オーバーしない ようにTripを形成し、それらをリストに格納した初期解を返すクラス
 *
 * @author kujirada.renya
 *
 */
public class MakeInitialSolution {

    // コンストラクタ
    public MakeInitialSolution() {

    }

    /**
     * 初期解生成メソッド
     *
     * @param giftList : InputFileReaderで読み込んだgiftデータが格納されているリスト
     * @param weightOfConstraints : 重みの制約
     * @return
     */
    public Solution makeInitSol(List<Gift> giftList) {
        // tripIDは0から開始
        int tripID = 0;

        // 初期解（経路の集まり）インスタンス生成
        Solution sol = new Solution();

        // ---------- loop開始(tripの生成)----------
        int indices = 0;
        while (indices < giftList.size()) {
            // Tripインスタンス生成
            Trip trip = new Trip(tripID);

            // tripのgiftの重さの合計が990以下になるまでtripにgiftを追加
            do {
                // 新しく作成したtripへ，indices番目のgiftListの要素を追加
                trip.addGift(giftList.get(indices));
                // 次のindicesを指す
                indices++;

                // リスト内の全ての要素を選択し終えたらloopを抜ける
                if (indices >= giftList.size()) {
                    break;
                }

            } while (trip.getSumWeight() + giftList.get(indices).getWeight() <= Const.weightOfConstraints);

            // 生成したtripの評価値を計算しセット
            trip.setEvaluationValue(Calculator.calcTripEval(trip));

            // tripをsolへadd
            sol.getTrips().add(trip);

            // tripIDの更新
            tripID++;
        }

        // 解全体の評価値を算出
        sol.setAllEval(Calculator.calcSolutionEval(sol));

        return sol;
    }

}
