package ru.borisov

import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.borisov.models.LipetskStore
import ru.borisov.models.Phone
import ru.borisov.models.PhoneStore
import ru.borisov.models.VoronezhStore

val storeList: MutableList<PhoneStore> = mutableListOf(
    LipetskStore(),
    VoronezhStore()
)
var indexSelectedStore: Int? = null
var job: Job? = null
var askedForRepairPhone = false

fun main() = runBlocking {
    job = launch {
        while (true) {
            greeting()
            launch {
                selectCity(
                    onSelectExit = { job?.cancel() }
                )
            }.join()
            if (!askedForRepairPhone && storeList[indexSelectedStore!!].hasRepairRoom) {
                val random = (0..1).random()
                println(random)
                if (random == 1) {
                    repairPhone()
                    askedForRepairPhone = true
                } else selectPhone()
            } else {
                selectPhone()
            }
        }
    }
    job?.join()
    println("Программа завершена")
}

fun repairPhone() {
    var variantIsSelected = false
    do {
        println(
            "В этом магазине имеется ремонтная мастерская, вам требуется ремонт сломавшегося телефона?\n" +
                    "1 - Да,\n0 - Нет;\nВыберите вариант"
        )
        try {
            when (readln().toInt()) {
                0 -> {
                    println("Не требуется ремонт\n")
                    variantIsSelected = true
                }

                1 -> {
                    println("Телефон отремонтирован\n")
                    variantIsSelected = true
                }

                else -> throw Exception()
            }
        } catch (e: Exception) {
            println("Ошибка, введено недопустимое значение")
        }
    } while (!variantIsSelected)
}

private fun selectCity(
    onSelectExit: () -> Unit,
) {
    println("Наши магазины присутствуют в следующих городах:")
    storeList.forEachIndexed() { index, item ->
        println("${index + 1}. ${item.cityName}")
    }
    var cityIsSelected = false
    do {
        println("Введите номер города, или введите 0 чтобы выйти (закончить работу).")
        try {
            when (val inputNumber = readln().toInt()) {
                0 -> {
                    onSelectExit()
                    cityIsSelected = true
                }

                in 1..storeList.size -> {
                    indexSelectedStore = inputNumber - 1
                    println("Вы выбрали город ${storeList[inputNumber - 1].cityName}")
                    cityIsSelected = true
                }

                else -> throw Exception()
            }
        } catch (e: Exception) {
            println("Ошибка, введено недопустимое значение")
        }
    } while (!cityIsSelected)
}

private fun selectPhone() {
    println("Предлагаем выбрать модель телефона для покупки")
    indexSelectedStore?.let { indexStore ->
        val store = storeList[indexStore]
        val phoneList = store.phoneList
        phoneList.forEachIndexed { index, item -> printPhoneItem(index, item, store) }
        var phoneIsSelected = false
        do {
            println("Введите порядковый номер телефона или введите 0 для показа статистики покупок в этом магазине")
            when (val inputNumber = readln().toInt()) {
                0 -> {
                    with(store) {
                        getStatisticsPhoneSelling()
                        getStatisticsSumSale()
                    }
                    phoneIsSelected = true
                }

                in 1..phoneList.size -> {
                    val phone = phoneList[inputNumber - 1]
                    store.saleProduct(phone)
                    storeList[indexStore] = store
                    phoneIsSelected = true
                }

                else -> println("Ошибка, введено недопустимое значение")
            }
        } while (!phoneIsSelected)
    }
}

private fun printPhoneItem(index: Int, item: Phone, store: PhoneStore) {
    println("${index + 1}. ${item.modelName}, цена: ${store.getCostWithMargin(item)}")
}

private fun greeting() {
    println("Приветствуем ваc в \"Сети магазинов по продаже телефонов\"")
}