package ru.borisov.models

abstract class PhoneStore(
    val cityName: String,
) : ContainingStatistics {
    abstract val hasRepairRoom: Boolean
    abstract val marginPercentage: Int
    val countSales: MutableMap<String, Int> = mutableMapOf()
    var sumSale: Double = 0.0
    val phoneList: List<Phone> = PHONE_LIST

    fun saleProduct(phone: Phone) {
        countSales[phone.modelName]?.let {
            countSales[phone.modelName] = it + 1
        } ?: countSales.put(phone.modelName, 1)
        sumSale += getCostWithMargin(phone)
        println("Вы купили телефон ${phone.modelName}")
    }

    fun getCostWithMargin(phone: Phone) = phone.costBase * (1.0 + marginPercentage.toDouble() / 100.0)

    companion object {
        val PHONE_LIST = listOf(
            Phone(0, "Huawei H30", 25_000),
            Phone(1, "Xiaomi Redmi Note 8T", 15_000),
            Phone(2, "POCO C61 64 ГБ", 7_000),
            Phone(3, "Tecno SPARK 10 128 ГБ", 10_000),
            Phone(4, "Realme Note 50 64 ГБ", 7_800),
            Phone(5, "OPPO A18 128 ГБ", 9_600),
            Phone(6, "Samsung Galaxy A05 64 ГБ", 10_500),
        )
    }
}