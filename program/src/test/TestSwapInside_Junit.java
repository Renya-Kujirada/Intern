package test;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import jp.co.nssol.sysrdc.santa.data.Gift;
import jp.co.nssol.sysrdc.santa.data.Trip;
import jp.co.nssol.sysrdc.santa.logic.SwapInside;
import jp.co.nssol.sysrdc.santa.utils.Const;

/**
 *
 * @author kujirada.renya
 *
 */
class TestSwapInside_Junit {

    int[] giftIdListBeforeSwap = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    int[] giftIdListAfterSwap = { 0, 3, 2, 1, 4, 5, 6, 7, 8, 9 };

    int giftIndex1 = 1;
    int giftIndex2 = 3;

    Trip tripBeforeSwap = new Trip(Const.seed);
    SwapInside sw = new SwapInside(Const.seed);

    void init() {
        tripBeforeSwap.initialize(Const.seed);
        for (int giftId : giftIdListBeforeSwap) {
            Gift gift = new Gift(giftId, 0.0, 0.0, 0.0);
            tripBeforeSwap.addGift(gift);
        }
    }

    @Test
    void testGetIndex2() {
        init();
        int giftIndex2 = sw.getIndex2(tripBeforeSwap, giftIndex1);
        assertNotEquals(giftIndex1, giftIndex2);
        assertEquals(3, giftIndex2);
        System.out.println(giftIndex2);

    }

    @Test
    void testSwapGiftsInsideLogic() {
        init();
        sw.swapGiftsInsideLogic(tripBeforeSwap, giftIndex1, giftIndex2);

        for (int i = 0; i < tripBeforeSwap.getGiftList().size(); ++i) {
            Gift gift = tripBeforeSwap.getGiftList().get(i);
            assertEquals(giftIdListAfterSwap[i], gift.getGiftId());
        }

    }

}
