package by.bsuir.murashko.quizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startGame(@Suppress("UNUSED_PARAMETER") view: View?) {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    fun closeApp(@Suppress("UNUSED_PARAMETER") view: View?) {
        val alertBuilder = AlertDialog.Builder(this)

        alertBuilder.setTitle(getString(R.string.confirmation_title_text))
        alertBuilder.setMessage(getString(R.string.confirmation_close_app_msg_text))

        alertBuilder.setPositiveButton(getString(R.string.yes_text)) { _, _ ->
            finish()
            exitProcess(0)
        }

        alertBuilder.setNegativeButton(getString(R.string.no_text)) { _, _ -> }

        alertBuilder.show()
    }

    fun statistics(view: View) {
        val intent = Intent(this, EndGameActivity::class.java)
        startActivity(intent)
    }
}