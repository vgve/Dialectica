package com.example.dialectica.models

import com.example.dialectica.R

class Themes {
    val themeList = listOf(
        DialectTheme("0", "Мировоззрение", R.drawable.ic_handshake),
        DialectTheme("1", "Культура", R.drawable.ic_book),
        DialectTheme("2", "Истории из жизни", R.drawable.ic_idea)
    )

    val questionList = listOf(
        DialectQuestion(0, "0", "Если бы у вас был дополнительный час в сутках, на что бы вам хотелось его потратить?"),
        DialectQuestion(1, "0", "Чему бы вам хотелось научиться до конца своей жизни?"),
        DialectQuestion(2, "0", "Представьте, что вы разговариваете с собой 10 лет назад. Что бы вам хотелось себе посоветовать?"),
        DialectQuestion(3, "0", "Если бы вы могли изменить одно событие в истории, что бы это было?"),
        DialectQuestion(4, "0", "Если бы вы умели заглядывать в будущее, что бы вы делали с такой способностью?"),
        DialectQuestion(5, "0", "Какого принципа вы придерживаетесь уже много лет?"),
        DialectQuestion(6, "0", "Какую новую технологию вы бы хотели застать при жизни?"),
        DialectQuestion(7, "0", "Куда вы давно хотите съездить, но никак не соберётесь?"),
        DialectQuestion(8, "1", "Какие фильмы/книги/произведения искусства оказали на вас сильное влияние?"),
        DialectQuestion(9, "1", "Какая ваша любимая цитата? Откуда она?"),
        DialectQuestion(10, "1", "Из каких песен вы бы составили плейлист для лучшего друга?"),
        DialectQuestion(11, "1", "В какое место в вашем городе вам хочется вернуться?"),

        DialectQuestion(12, "2", "Когда в последний раз вас выручала ваша хитрость?"),
        DialectQuestion(13, "2", "В вашей жизни случалось так, что сделанное доброе дело к вам возвращалось?"),
        DialectQuestion(14, "2", "Самое красивое место на Земле, где вам приходилось бывать?"),
        DialectQuestion(15, "2", "Что вы могли делать в этот день десять лет назад?"),
        DialectQuestion(16, "2", "Какая ваша любимая семейная традиция?"),
        DialectQuestion(17, "2", "Какой период в жизни вам запомнился больше всего и почему?"),
        DialectQuestion(18, "2", "Какое блюдо ассоциируется у вас с детством?"),
        DialectQuestion(19, "2", "Какое правильное решение далось вам тяжелее всего?")
    )

    fun addQuestionToFavourite(question: DialectQuestion?) {
        questionList.forEach {
            if (it.id == question?.id) {
                it.isFavourite = true
            }
        }
    }
}
