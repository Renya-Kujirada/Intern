package jp.co.nssol.sysrdc.santa.data;

/**
 *
 * giftを形成するクラス
 *
 * @author onodera.kakeru
 *
 */

public class Gift {

    // ギフトのid
    private int giftId;

    // 緯度
    private double latitude;

    // 経度
    private double longitude;

    // 重さ
    private double weight;

    // giftの初期化
    public Gift(int giftId, double latitude, double longitude, double weight) {
        this.giftId = giftId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.weight = weight;
    }

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Gift [giftId=" + giftId + "]";
        // return "Gift [giftId=" + giftId + ", latitude=" + latitude + ", longitude=" + longitude + ", weight=" + weight + "]";
    }

}
