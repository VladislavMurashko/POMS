package by.bsuir.murashko.quizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import by.bsuir.murashko.quizapp.util.NetworkMonitorUtil
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    lateinit var mAdView: AdView
    private val networkMonitor = NetworkMonitorUtil(this)
    private var isNetworkAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        networkMonitor.result = { isAvailable, _ ->
            runOnUiThread {
                isNetworkAvailable = isAvailable
            }
        }
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }

    fun startGame(view: View?) {
        if (isNetworkAvailable) {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        } else {
            Snackbar.make(view!!, "Отсутствует подключение к Интернету :(", Snackbar.LENGTH_SHORT).show()
        }
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

    fun openStats(@Suppress("UNUSED_PARAMETER") view: View) {
        val intent = Intent(this, StatsActivity::class.java)
        startActivity(intent)
    }

    fun openSettings(@Suppress("UNUSED_PARAMETER") view: View) {
        val intent = Intent(this, InDevelopmentActivity::class.java)
        startActivity(intent)
    }
}