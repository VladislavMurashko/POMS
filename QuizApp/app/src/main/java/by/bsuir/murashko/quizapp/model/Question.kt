package by.bsuir.murashko.quizapp.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Question(
        val question: String = "",
        var imageUrl: String = "",
        val optionOne: String = "",
        val optionTwo: String = "",
        val optionThree: String = "",
        val optionFour: String = "",
        val correctAnswer: Int = 0
)