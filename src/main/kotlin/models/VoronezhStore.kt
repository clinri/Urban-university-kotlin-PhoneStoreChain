package ru.borisov.models

class VoronezhStore(
    override val hasRepairRoom: Boolean = false,
    override val marginPercentage: Int = 7,
) : PhoneStore("Воронеж") {
    override fun getStatisticsPhoneSelling() {
        println("= Статистика покупки телефонов в городе $cityName - ${countSales.map { it.value }.sum()} телефонов =")
        countSales.forEach {
            println("Куплено телефонов модели ${it.key} – ${it.value} шт.")
        }

    }

    override fun getStatisticsSumSale() {
        println("! Общая сумма покупок телефонов в городе $cityName - $sumSale руб.\n")
    }
}