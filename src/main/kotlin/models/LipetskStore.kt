package ru.borisov.models

class LipetskStore(
    override val hasRepairRoom: Boolean = true,
    override val marginPercentage: Int = 5,
) : PhoneStore("Липецк") {
    override fun getStatisticsPhoneSelling() {
        println("Статистика покупки телефонов в городе $cityName - ${countSales.map { it.value }.sum()} телефон(ов)")
        countSales.forEach {
            println("Куплено телефонов модели ${it.key} – ${it.value} шт.")
        }
    }

    override fun getStatisticsSumSale() {
        println("Общая сумма покупок телефонов в городе $cityName - $sumSale руб.\n")
    }
}