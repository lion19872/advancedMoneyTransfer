fun main() {
    val cardType = readCardType()
    val totalPreviousTransfers = readTotalPreviousTransfers()
    val transferAmount = readTransferAmount()

    val commission = calculateCommission(cardType, totalPreviousTransfers, transferAmount)
    println("Комиссия за перевод: $commission рублей")
}

fun readCardType(): String {
    println("Введите тип карты/счета (Mastercard, Maestro, Visa, Мир, VK Рау):")
    return readLine()!!.trim()
}

fun readTotalPreviousTransfers(): Double {
    println("Введите сумму предыдущих переводов в этом месяце в рублях (по умолчанию 0 рублей):")
    return readLine()?.toDoubleOrNull() ?: 0.0
}

fun readTransferAmount(): Double {
    println("Введите сумму совершаемого перевода в рублях:")
    return readLine()!!.toDouble()
}

fun calculateCommission(cardType: String = "VK Рау", totalPreviousTransfers: Double = 0.0, transferAmount: Double): Double {
    val maxDailyTransferAmount = 150000.0
    val maxMonthlyTransferAmount = 600000.0
    val vkPayMaxSingleTransferAmount = 15000.0
    val vkPayMaxMonthlyTransferAmount = 40000.0

    if (cardType == "VK Рау") {
        if (transferAmount <= vkPayMaxSingleTransferAmount &&
            totalPreviousTransfers + transferAmount <= vkPayMaxMonthlyTransferAmount
        ) {
            return 0.0
        } else {
            return transferAmount * 0.0075 // 0.75% для переводов на счет VK Рау
        }
    } else {
        val monthlyTransferLimitReached = totalPreviousTransfers + transferAmount > maxMonthlyTransferAmount
        if (monthlyTransferLimitReached) {
            return 0.0 // Комиссия не взимается, так как превышен лимит переводов в месяц
        }

        val dailyTransferLimitReached = transferAmount > maxDailyTransferAmount
        if (dailyTransferLimitReached) {
            return 0.0 // Комиссия не взимается, так как превышен дневной лимит переводов
        }

        when (cardType) {
            "Mastercard", "Maestro" -> {
                if (transferAmount in 300.0..75000.0) {
                    return 0.0
                } else {
                    return 0.6 / 100 * transferAmount + 20.0
                }
            }
            "Visa", "Мир" -> {
                val minCommission = 25.0 // Минимальная комиссия для Visa и Мир - 25 рублей
                val commissionPercentage = 0.75 / 100

                val commission = transferAmount * commissionPercentage
                return if (commission < minCommission) minCommission else commission
            }
            else -> throw IllegalArgumentException("Неизвестный тип карты/счета")
        }
    }
}
