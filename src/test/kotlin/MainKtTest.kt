
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*


class MainKtTest {

    @Test
    fun calculateCommission() {
        val cardType = "Mastercard"
        val totalPreviousTransfers = 1000.1
        val transferAmount = 1234.1
        val result = calculateCommission(cardType, totalPreviousTransfers, transferAmount)
        assertEquals(0.0,result)
    }
    @Test
    fun calculateMinCommissionForVisa() {
        val cardType = "Visa"
        val totalPreviousTransfers = 10000.0
        val transferAmount = 10.0
        val result = calculateCommission(cardType, totalPreviousTransfers, transferAmount)
        assertEquals(35.0,result)
    }

    @Test
    fun DailyTransferLimitReached() {
        val cardType = "asdfr"
        val totalPreviousTransfers = 700000.0
        val transferAmount = 10.0
        val result = calculateCommission(cardType, totalPreviousTransfers, transferAmount)
        assertEquals(0.0,result)
    }

}