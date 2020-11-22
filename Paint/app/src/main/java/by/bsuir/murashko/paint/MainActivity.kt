package by.bsuir.murashko.paint

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import com.google.android.material.slider.Slider
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener

class MainActivity : AppCompatActivity() {
    private lateinit var drawView: DrawView
    private lateinit var colorPickerDialog: AmbilWarnaDialog
    private lateinit var strokeSlider: Slider
    private lateinit var strokeLine: View
    private lateinit var pencilButton: ImageButton
    private lateinit var shapesButton: ImageButton
    private lateinit var eraserButton: ImageButton
    private var isEraserSelected = false
    private val defaultColor = Color.BLACK
    private val buttonList = ArrayList<ImageButton>()
    private var strokeColor: Int = Color.BLACK
    private var sliderValue: Float = 10f.px
    private var strokeWidth: Float = sliderValue
    private val Float.px: Float get() = (this * Resources.getSystem().displayMetrics.density)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        drawView = findViewById(R.id.draw_view)
        pencilButton = findViewById(R.id.pencil_ib)
        shapesButton = findViewById(R.id.shapes_ib)
        eraserButton = findViewById(R.id.eraser_ib)
        buttonList.add(pencilButton)
        buttonList.add(shapesButton)
        buttonList.add(eraserButton)

        colorPickerDialog = AmbilWarnaDialog(this, Color.BLACK, object : OnAmbilWarnaListener {
            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                strokeColor = color
                drawView.changeColor(color)
            }

            override fun onCancel(dialog: AmbilWarnaDialog) {
            }
        })
    }

    private fun showStrokeWidthDialog() {
        createDialog().show()
    }

    private fun createDialog(): AlertDialog.Builder {
        val dialogBuilder = AlertDialog.Builder(this)

        val view = this.layoutInflater.inflate(R.layout.stroke_dialog, null)

        dialogBuilder.setView(view)
            .setTitle("Ширина линии")
            .setCancelable(false)
            .setPositiveButton("Подтвердить") { _, _ ->
                drawView.changeStrokeWidth(strokeSlider.value)
                sliderValue = strokeSlider.value
                strokeWidth = strokeLine.height.toFloat()
            }

        strokeLine = view.findViewById(R.id.stroke_line)
        strokeLine.setBackgroundColor(strokeColor)
        changeStrokeWidth(strokeWidth)

        strokeSlider = view.findViewById(R.id.stroke_slider)
        strokeSlider.value = sliderValue
        strokeSlider.addOnChangeListener { _, height, _ ->
            changeStrokeWidth(height)
        }

        return dialogBuilder
    }

    private fun changeStrokeWidth(height: Float) {
        val params = strokeLine.layoutParams
        params.height = height.toInt()
        strokeLine.layoutParams = params
    }

    fun chooseColor(view: View) {
        if (isEraserSelected) return
        colorPickerDialog.show()
    }

    fun chooseShape(view: View) {
        resetButtons()
        shapesButton.setActive
        isEraserSelected = false
        //drawView.chooseShape
        showStrokeWidthDialog()
    }

    fun choosePencil(view: View) {
        resetButtons()
        pencilButton.setActive
        isEraserSelected = false
        drawView.choosePencil()
        showStrokeWidthDialog()
    }

    fun chooseEraser(view: View) {
        resetButtons()
        eraserButton.setActive
        isEraserSelected = true
        drawView.chooseEraser()
    }

    fun clearCanvas(view: View) {
        drawView.clearCanvas()
    }

    private val ImageButton.setActive: Unit
        get() = (ImageViewCompat.setImageTintList(
            this,
            ColorStateList.valueOf(Color.WHITE)
        ))

    private fun resetButtons() {
        for (button in buttonList) {
            ImageViewCompat.setImageTintList(
                button,
                ColorStateList.valueOf(defaultColor)
            )
        }
    }
}