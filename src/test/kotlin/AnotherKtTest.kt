import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class CommissionCalculatorTest {
    @Test
    fun testVKPayCommissionWithinLimits() {
        val commission = calculateCommission("VK Pay", 10000.0, 5000.0)
        assertEquals(0.0, commission, "VK Pay commission should be 0 within limits.")
    }

    @Test
    fun testVKPayCommissionExceedsMonthlyLimit() {
        val commission = calculateCommission("VK Pay", 30000.0, 20000.0)
        assertEquals(150.0, commission, "VK Pay commission should be calculated correctly.")
    }

    @Test
    fun testVKPayCommissionExceedsSingleLimit() {
        val commission = calculateCommission("VK Pay", 0.0, 20000.0)
        assertEquals(20000.0 * 0.0075, commission, "VK Pay commission should be calculated correctly.")
    }

    @Test
    fun testMastercardCommissionWithinLimits() {
        val commission = calculateCommission("Mastercard", 50000.0, 75000.0)
        assertEquals(0.0, commission, "Mastercard commission should be 0 within limits.")
    }

    @Test
    fun testMastercardCommissionExceedsLimit() {
        val commission = calculateCommission("Mastercard", 50000.0, 80000.0)
        assertEquals(80000.0 * 0.006 + 20.0, commission, "Mastercard commission should be calculated correctly.")
    }

    @Test
    fun testVisaCommissionWithinLimits() {
        val commission = calculateCommission("Visa", 40000.0, 10000.0)
        assertEquals(75.0, commission, "Visa commission should be the minimum commission.")
    }

    @Test
    fun testVisaCommissionExceedsMinimum() {
        val commission = calculateCommission("Visa", 0.0, 150000.0)
        assertEquals(150000.0 * 0.0075, commission, "Visa commission should be calculated correctly.")
    }

    @Test
    fun testUnknownCardType() {
        assertThrows(IllegalArgumentException::class.java) {
            calculateCommission("American Express", 0.0, 10000.0)
        }
    }

    @Test
    fun testNoMonthlyLimitForUnknownCardType() {
        val commission = calculateCommission("American Express", 600000.0, 10000.0)
        assertEquals(0.0, commission, "Commission should be calculated for unknown card types.")
    }

    @Test
    fun testNoDailyLimitForUnknownCardType() {
        val commission = calculateCommission("American Express", 140000.0, 200000.0)
        assertEquals(0.0, commission, "Commission should be calculated for unknown card types.")
    }
}
