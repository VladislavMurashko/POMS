package by.bsuir.murashko.quizapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_end_game.*

class EndGameActivity : AppCompatActivity() {

    private val mStatsSharedPref = "stats"
    private var correctAnswers: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

        tv_score.text = String.format("Вы набрали %d из %d очков", correctAnswers, totalQuestions)
    }

    fun saveResult(@Suppress("UNUSED_PARAMETER") view: View) {
        val username = et_username.text

        if (username.isNullOrBlank()) {
            Toast.makeText(this, "Вы не ввели имя!", Toast.LENGTH_SHORT).show()
        } else {
            val sharedPref = getSharedPreferences(mStatsSharedPref, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPref.edit()

            editor.putInt(username.toString(), correctAnswers)
            editor.apply()

            Toast.makeText(this, "Результат успешно сохранен!", Toast.LENGTH_SHORT).show()

            returnToMain()
        }
    }

    private fun returnToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}