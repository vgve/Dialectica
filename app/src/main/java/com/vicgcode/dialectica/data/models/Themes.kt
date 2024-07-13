package com.vicgcode.dialectica.data.models

import com.vicgcode.dialectica.R
import com.vicgcode.dialectica.data.models.entity.DialectQuestion

object Themes {
    val ruSections = listOf(
        DialectTheme("0", "Знакомство", R.drawable.ic_handshake),
        DialectTheme("1", "Неординарные", R.drawable.ic_idea),
        DialectTheme("2", "Точка зрения", R.drawable.ic_direction),
        DialectTheme("3", "Парадоксы", R.drawable.ic_cognition),
    )

    val enSections = listOf(
        DialectTheme("0", "Intro", R.drawable.ic_handshake),
        DialectTheme("1", "Unusual", R.drawable.ic_idea),
        DialectTheme("2", "Viewpoint", R.drawable.ic_direction),
        DialectTheme("3", "Paradoxes", R.drawable.ic_cognition),
    )

    val ruQuestions = listOf(
        DialectQuestion("0", "Если бы у вас был дополнительный час в сутках, на что бы вам хотелось его потратить?"),
        DialectQuestion("0", "Чему бы вам хотелось научиться до конца своей жизни?"),
        DialectQuestion("0", "Представьте, что вы разговариваете с собой 10 лет назад. Что бы вам хотелось себе посоветовать?"),
        DialectQuestion("0", "Если бы вы могли изменить одно событие в истории, что бы это было?"),
        DialectQuestion("0", "Если бы вы умели заглядывать в будущее, что бы вы делали с такой способностью?"),
        DialectQuestion("0", "Какого принципа вы придерживаетесь уже много лет?"),
        DialectQuestion("0", "Какую новую технологию вы бы хотели застать при жизни?"),
        DialectQuestion("0", "Куда вы давно хотите съездить, но никак не соберётесь?"),
        DialectQuestion("0", "Какие фильмы/книги/произведения искусства оказали на вас сильное влияние?"),
        DialectQuestion("0", "Какая ваша любимая цитата? Откуда она?"),
        DialectQuestion("0", "Из каких песен вы бы составили плейлист для лучшего друга?"),
        DialectQuestion("0", "В какое место в вашем городе вам хочется вернуться?"),
        DialectQuestion("0", "В вашей жизни случалось так, что сделанное доброе дело к вам возвращалось?"),
        DialectQuestion("0", "Самое красивое место на Земле, где вам приходилось бывать?"),
        DialectQuestion("0", "Какая ваша любимая семейная традиция?"),
        DialectQuestion("0", "Какой период в жизни вам запомнился больше всего и почему?"),
        DialectQuestion("0", "Какое блюдо ассоциируется у вас с детством?"),
        DialectQuestion("0", "Какое правильное решение далось вам тяжелее всего?"),

        DialectQuestion("1", "Навсегда улететь с инопланетянами или остаться?"),
        DialectQuestion("1", "Можно ли обмакивать картофель фри в молочный коктейль?"),
        DialectQuestion("1", "Как называется самец божьей коровки?"),
        DialectQuestion("1", "Предположим, вы кошачий президент, какие три вещи вы бы изменили, чтобы улучшить качество жизни кошек в вашей юрисдикции?"),
        DialectQuestion("1", "Владелец земельного участка владеет им до самого центра земли? Какой глубины его собственность?"),
        DialectQuestion("1", "Как написать ноль римскими цифрами?"),
        DialectQuestion("1", "Зачем на матрасах рисунки, если мы все равно накрываем их простынями, пледами и одеялами?"),
        DialectQuestion("1", "Почему люди используют выражение «Я спал как младенец», когда на самом деле младенцы просыпаются каждые два часа?"),
        DialectQuestion("1", "Какое сообщение вы бы отправили жителям другой планеты?"),
        DialectQuestion("1", "Может ли нейросеть сама себе задавать вопросы?"),
        DialectQuestion("1", "Что заставило вас испытывать феномен Баадера-Майнхоф?"),
        DialectQuestion("1", "Что вы могли делать в этот день десять лет назад?"),
        DialectQuestion("1", "Когда в последний раз вас выручала ваша хитрость?"),
        DialectQuestion("1", "Что бы вы предложили древним грекам в качестве нового изобретения или технологии, чтобы удивить их умы?"),
        DialectQuestion("1", "Как бы вы описали жизнь на Земле инопланетянам, которые никогда не видели эту планету?"),
        DialectQuestion("1", "С каким вымышленным персонажем вы себя идентифицируете?"),

        DialectQuestion("2", "Этично ли использовать искусственный интеллект вместо людей?"),
        DialectQuestion("2", "Какие качества нужны человеку для успеха в современном мире?"),
        DialectQuestion("2", "Следует ли разрешить клонирование человека?"),
        DialectQuestion("2", "Можно ли избежать конфликта поколений?"),
        DialectQuestion("2", "Чем фактически является успех: личным достижением или продуктом общественного согласия?"),
        DialectQuestion("2", "Есть ли у нас свобода воли, или все наши действия предопределены внешними обстоятельствами?"),

        DialectQuestion("3", "Представьте, что слепой с рождения человек, который научился распознавать предметы (например, куб и шар) через прикосновения, вдруг прозрел. Сможет ли он одним лишь взглядом понять, что есть шар, а что — куб?"),
        DialectQuestion("3", "Если человек заявит: «Я всегда лгу» — каким будет его утверждение: верным или ложным?"),
        DialectQuestion("3", "Вы путешественник во времени, который перемещается в период жизни Бетховена и передает молодому композитору симфонию 5. Бетховен представляет её публике. Кто в таком случае автор композиции?"),
        DialectQuestion("3", "Корабль Тесея бережно хранился афинянами. С течением времени они меняли износившиеся детали одну за другой. В результате исходных деталей корабля не осталось. Вопрос: перед нами все еще корабль Тесея или новое судно?")
    )

    val enQuestions = listOf(
        DialectQuestion("0", "If you had an extra hour in the day, what would you like to spend it on?"),
        DialectQuestion("0", "What would you like to learn before the end of your life?"),
        DialectQuestion("0", "Imagine you are talking to yourself 10 years ago. What advice would you give yourself?"),
        DialectQuestion("0", "If you could change one event in history, what would it be?"),
        DialectQuestion("0", "If you could foresee the future, what would you do with that ability?"),
        DialectQuestion("0", "What principle have you adhered to for many years?"),
        DialectQuestion("0", "What new technology would you like to see in your lifetime?"),
        DialectQuestion("0", "Where have you wanted to go for a long time but haven't gotten around to?"),
        DialectQuestion("0", "Which movies/books/artworks have had a strong influence on you?"),
        DialectQuestion("0", "What is your favorite quote? Where is it from?"),
        DialectQuestion("0", "Which songs would you include in a playlist for your best friend?"),
        DialectQuestion("0", "Which place in your city do you want to return to?"),
        DialectQuestion("0", "Has it ever happened in your life that a good deed you did came back to you?"),
        DialectQuestion("0", "What is the most beautiful place on Earth you have visited?"),
        DialectQuestion("0", "What is your favorite family tradition?"),
        DialectQuestion("0", "Which period in your life do you remember the most and why?"),
        DialectQuestion("0", "What dish reminds you of your childhood?"),
        DialectQuestion("0", "What was the hardest right decision for you to make?"),

        DialectQuestion("1", "Would you rather fly away with aliens forever or stay?"),
        DialectQuestion("1", "Is it okay to dip French fries in a milkshake?"),
        DialectQuestion("1", "What is a male ladybug called?"),
        DialectQuestion("1", "Suppose you are a feline president; what three things would you change to improve the quality of life for cats in your jurisdiction?"),
        DialectQuestion("1", "Does a landowner own the land down to the center of the earth? How deep is their property?"),
        DialectQuestion("1", "How to write zero in Roman numerals?"),
        DialectQuestion("1", "Why do mattresses have patterns when we cover them with sheets, blankets, and quilts anyway?"),
        DialectQuestion("1", "Why do people use the expression 'I slept like a baby' when babies actually wake up every two hours?"),
        DialectQuestion("1", "What message would you send to residents of another planet?"),
        DialectQuestion("1", "Can a neural network ask itself questions?"),
        DialectQuestion("1", "What made you experience the Baader-Meinhof phenomenon?"),
        DialectQuestion("1", "What could you have been doing on this day ten years ago?"),
        DialectQuestion("1", "When was the last time your cleverness bailed you out?"),
        DialectQuestion("1", "What would you propose to ancient Greeks as a new invention or technology to astonish their minds?"),
        DialectQuestion("1", "How would you describe life on Earth to aliens who have never seen this planet?"),
        DialectQuestion("1", "Which fictional character do you identify with?"),

        DialectQuestion("2", "Is it ethical to use artificial intelligence instead of humans?"),
        DialectQuestion("2", "What qualities does a person need for success in the modern world?"),
        DialectQuestion("2", "Should human cloning be allowed?"),
        DialectQuestion("2", "Can the generation gap be avoided?"),
        DialectQuestion("2", "What is success actually: a personal achievement or a product of social agreement?"),
        DialectQuestion("2", "Do we have free will, or are all our actions predetermined by external circumstances?"),

        DialectQuestion("3", "Imagine that a person who was born blind and learned to recognize objects (such as a cube and a sphere) through touch suddenly gains sight. Will he be able to understand just by looking which object is the sphere and which is the cube?"),
        DialectQuestion("3", "If a person claims: 'I always lie'—is their statement true or false?"),
        DialectQuestion("3", "You are a time traveler who goes back to the time of Beethoven and gives the young composer the 5th Symphony. Beethoven presents it to the public. Who, in this case, is the author of the composition?"),
        DialectQuestion("3", "The ship of Theseus was carefully preserved by the Athenians. Over time, they replaced the worn-out parts one by one. As a result, none of the original parts remained. The question is: is this still the ship of Theseus or a new vessel?")
    )
}
