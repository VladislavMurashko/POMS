package by.bsuir.murashko.paint

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import by.bsuir.murashko.paint.model.Shape
import by.bsuir.murashko.paint.util.PermissionsHelper
import com.google.android.material.slider.Slider
import com.himanshurawat.imageworker.Extension
import com.himanshurawat.imageworker.ImageWorker
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var drawView: DrawView
    private lateinit var colorPickerDialog: AmbilWarnaDialog
    private lateinit var thicknessSlider: Slider
    private lateinit var line: View
    private lateinit var pencilButton: ImageButton
    private lateinit var shapesButton: ImageButton
    private lateinit var eraserButton: ImageButton
    private var shape = Shape.RECTANGLE
    private var isEraserSelected = false
    private val buttonList = ArrayList<ImageButton>()
    private var sliderValue: Float = 10f.px
    private var strokeWidth: Float = sliderValue
    private var currentColor: Int = Color.BLACK
    private val defaultColor = Color.BLACK
    private val dateFormat = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
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
        pencilButton.setActive
        buttonList.add(pencilButton)
        buttonList.add(shapesButton)
        buttonList.add(eraserButton)

        colorPickerDialog = AmbilWarnaDialog(this, currentColor, object : OnAmbilWarnaListener {
            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                currentColor = color
                drawView.changeColor(color)
            }

            override fun onCancel(dialog: AmbilWarnaDialog) {
            }
        })
    }

    private fun showLineThicknessDialog() {
        createDialog().show()
    }

    private fun createDialog(): AlertDialog.Builder {
        val dialogBuilder = AlertDialog.Builder(this)

        val view = this.layoutInflater.inflate(R.layout.thickness_dialog, null)

        dialogBuilder.setView(view)
            .setTitle("Толщина линии")
            .setCancelable(false)
            .setPositiveButton("Подтвердить") { _, _ ->
                drawView.changeStrokeWidth(thicknessSlider.value)
                sliderValue = thicknessSlider.value
                strokeWidth = line.height.toFloat()
            }

        line = view.findViewById(R.id.line)
        line.setBackgroundColor(defaultColor)
        changeLineThickness(strokeWidth)

        thicknessSlider = view.findViewById(R.id.thickness_slider)
        thicknessSlider.value = sliderValue
        thicknessSlider.addOnChangeListener { _, height, _ ->
            changeLineThickness(height)
        }

        return dialogBuilder
    }

    private fun changeLineThickness(height: Float) {
        val params = line.layoutParams
        params.height = height.toInt()
        line.layoutParams = params
    }

    fun choosePencil(view: View) {
        resetButtons()
        pencilButton.setActive
        isEraserSelected = false
        drawView.choosePencil()
    }

    fun chooseShape(view: View) {
        resetButtons()
        shapesButton.setActive
        isEraserSelected = false
        setShape()
    }

    private fun setShape() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Выберите фигуру").setCancelable(false)

        val shapes = arrayOf("Прямоугольник", "Треугольник", "Круг")
        val checkedItem = 0
        shape = Shape.RECTANGLE

        builder.setSingleChoiceItems(shapes, checkedItem) { _, itemIndex ->
            when (itemIndex) {
                1 -> shape = Shape.TRIANGLE
                2 -> shape = Shape.CIRCLE
            }
        }

        builder.setPositiveButton(R.string.submit_dialog_button_text) { _, _ ->
            drawView.chooseShape(shape)
        }

        val dialog = builder.create()
        dialog.show()
    }

    fun chooseColor(view: View) {
        if (isEraserSelected) return
        colorPickerDialog.show()
    }

    fun chooseLineThickness(view: View) {
        showLineThicknessDialog()
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

    fun saveDrawing(view: View) {
        if (PermissionsHelper.askForPermissions(this, this)) {
            val currentDateTime: String = dateFormat.format(Date())
            ImageWorker.to(this)
                .directory("Download")
                .subDirectory("PaintDrawings")
                .setFileName("drawing_${currentDateTime}")
                .withExtension(Extension.PNG)
                .save(drawView.getBitmap(), 100)

            showToast(R.string.drawing_successfully_saved)
        }
    }

    private fun showToast(msg: Int) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
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

    /*fun setImageBackground(view: View) {
        if (PermissionsHelper.askForPermissions(this, this)) {
            FileHelper.pickFile(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == RESULT_OK) {
            val imagePath = FileHelper.getImagePath(this, data)

            drawView.setImageBackground(imagePath!!)
        }
    }*/
}