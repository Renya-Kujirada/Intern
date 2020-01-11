package jp.co.nssol.sysrdc.santa.sample;

/**
 * クラス実装のサンプルです.<br>
 * このクラスの役割をここに記述します.
 *
 * @author shiomi
 *
 */
public class ClassSample {
    // 日本語の項目名を書いてください
    private String id;

    // メンバ変数は基本的にprivate宣言にして、getter/setterを用意してください
    private int price;

    /**
     * コンストラクタ.
     *
     * @param id 識別子
     * @param price 価格
     */
    public ClassSample(String id) {
        // 不変のものは生成時に設定してください.
        this.id = id;
    }

    // getter, setterの説明は自動生成で作られたもので構いません.
    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @return value
     */
    public int getPrice() {
        return price;
    }

    // 生成後に設定or更新するもの以外はsetterは作らない.
    /**
     * @param price セットする price
     */
    public void setPrice(int price) {
        this.price = price;
    }

}
