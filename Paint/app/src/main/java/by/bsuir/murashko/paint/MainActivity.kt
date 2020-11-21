package by.bsuir.murashko.paint

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener

class MainActivity : AppCompatActivity() {
    private lateinit var drawView: DrawView
    private lateinit var dialog: AmbilWarnaDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        drawView = findViewById(R.id.draw_view)

        dialog = AmbilWarnaDialog(this, Color.BLACK, object : OnAmbilWarnaListener {
            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                drawView.changeColor(color)
            }

            override fun onCancel(dialog: AmbilWarnaDialog) {
            }
        })
    }

    fun clearCanvas(view: View) {
        drawView.clearCanvas()
    }

    fun chooseColor(view: View) {
        dialog.show()
    }
}