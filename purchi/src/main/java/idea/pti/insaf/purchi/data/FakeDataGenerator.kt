package idea.pti.insaf.purchi.data

import idea.pti.insaf.purchi.api.Voter

import kotlin.random.Random

object FakeDataGenerator {
    private val urduNames = listOf("علی", "بلال", "عمران", "امجد", "اسجد", "فہد", "خرم", "محمد", "یوسف", "حمزہ", "نور")

    fun generateFakeData(): List<Voter> {
        val voters = mutableListOf<Voter>()
        for (i in 1..100) {
            voters.add(
                Voter(
                    index = "$i",
                    blockCode = "${Random.nextInt(10000, 99999)}",
                    family = "${Random.nextInt(1, 100)}",
                    national = "NA-${Random.nextInt(1, 267)}",
                    provincial = "PP-${Random.nextInt(1, 298)}",
                    cnic = generateRandomCnic(),
                    name = generateRandomUrduName(),
                    fatherName = generateRandomUrduName(),
                    pollingStationAddress = generateRandomUrduAddress()
                )
            )
        }
        return voters
    }

    private fun generateRandomUrduName(): String {
        // Combine two random names from the urduNames list
        val randomName1 = urduNames.random()
        val randomName2 = urduNames.random()

        return "$randomName1 $randomName2"
    }




    private fun generateRandomCnic(): String {
        // Generate a random 10-digit number
        val randomDigits = Random.nextInt(100000000, 999999999)

        // Append the fixed prefix "2620" to make it a 13-digit CNIC number
        return "2620$randomDigits"
    }

    val cities = listOf(
        "چکوال", "چنیوٹ", "ڈیرہ غازی خان", "فیصل آباد", "گوجرانوالہ", "گجرات",
        "حافظ آباد", "جام پور", "جھنگ", "جہلم", "قصور", "خانیوال", "خوشاب", "لاہور", "لیہ", "لودھراں"
    )

    val streets = listOf(
        "سٹریٹ 1", "سٹریٹ 2", "سٹریٹ 3", "سٹریٹ 4", "سٹریٹ 5", "سٹریٹ 6"
    )

    private fun generateRandomUrduAddress(): String {


        val randomCity = cities.random()
        val randomStreet = streets.random()
        val randomBlock = Random.nextInt(1, 10000)
        val randomHouseNumber = Random.nextInt(1, 1000)

        return "- اسٹیشن نمبر$randomHouseNumber $randomStreet, $randomCity"
    }
}

