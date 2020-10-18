package by.bsuir.murashko.quizapp

import by.bsuir.murashko.quizapp.model.Question

object Constants {
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"

    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        val que1 = Question(
                1, "Чей флаг изображен на рисунке?",
                R.drawable.ic_flag_of_argentina,
                "Аргентина", "Австралия",
                "Армения", "Австрия", 1
        )

        questionsList.add(que1)

        val que2 = Question(
                2, "Чей флаг изображен на рисунке?",
                R.drawable.ic_flag_of_australia,
                "Республика Ангола", "Австрия",
                "Австралия", "Армения", 3
        )

        questionsList.add(que2)

        val que3 = Question(
                3, "Чей флаг изображен на рисунке?",
                R.drawable.ic_flag_of_brazil,
                "Беларусь", "Белиз",
                "Бруней", "Бразилия", 4
        )

        questionsList.add(que3)

        val que4 = Question(
                4, "Чей флаг изображен на рисунке?",
                R.drawable.ic_flag_of_belgium,
                "Багамские острова", "Бельгия",
                "Барбадос", "Белиз", 2
        )

        questionsList.add(que4)

        val que5 = Question(
                5, "Чей флаг изображен на рисунке?",
                R.drawable.ic_flag_of_fiji,
                "Габон", "Франция",
                "Фиджи", "Финляндия", 3
        )

        questionsList.add(que5)

        val que6 = Question(
                6, "Чей флаг изображен на рисунке?",
                R.drawable.ic_flag_of_germany,
                "Германия", "Грузия",
                "Греция", "не один из перечисленных", 1
        )

        questionsList.add(que6)

        val que7 = Question(
                7, "Чей флаг изображен на рисунке?",
                R.drawable.ic_flag_of_denmark,
                "Доминикана", "Египет",
                "Дания", "Эфиопия", 3
        )

        questionsList.add(que7)

        val que8 = Question(
                8, "Чей флаг изображен на рисунке?",
                R.drawable.ic_flag_of_india,
                "Ирландия", "Иран",
                "Венгрия", "Индия", 4
        )

        questionsList.add(que8)

        val que9 = Question(
                9, "Чей флаг изображен на рисунке?",
                R.drawable.ic_flag_of_new_zealand,
                "Австралия", "Новая Зеландия",
                "Тувалу", "США", 2
        )

        questionsList.add(que9)

        val que10 = Question(
                10, "Чей флаг изображен на рисунке?",
                R.drawable.ic_flag_of_kuwait,
                "Кувейт", "Иордан",
                "Судан", "Палестина", 1
        )

        questionsList.add(que10)

        return questionsList
    }
}