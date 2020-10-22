package by.bsuir.murashko.quizapp

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.bsuir.murashko.quizapp.adapter.RecyclerAdapter

class StatsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        recyclerAdapter = RecyclerAdapter(getUsers())
        viewManager = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.users_list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = recyclerAdapter
        }
    }

    private fun getUsers(): Set<Map.Entry<String, Any?>> {
        val sPref = getSharedPreferences("stats", Context.MODE_PRIVATE)
        return sPref
                .all
                .toList()
                .sortedByDescending { it.second as Comparable<Any> }
                .toMap()
                .entries
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun resetProgress(view: View) {
        val sPref = getSharedPreferences("stats", Context.MODE_PRIVATE)
        val alertBuilder = AlertDialog.Builder(this)

        alertBuilder.setTitle(getString(R.string.confirmation_title_text))
        alertBuilder.setMessage(getString(R.string.confirmation_reset_stats_msg_text))

        alertBuilder.setPositiveButton(getString(R.string.yes_text)) { _, _ ->
            sPref.edit().clear().apply()
            recyclerView.removeAllViewsInLayout()
            Toast.makeText(this, "Прогресс сброшен", Toast.LENGTH_SHORT).show()
        }

        alertBuilder.setNegativeButton(getString(R.string.no_text)) { _, _ -> }

        alertBuilder.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
