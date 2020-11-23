package by.bsuir.murashko.paint

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import by.bsuir.murashko.paint.model.Shape
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private const val STROKE_WIDTH = 12f

class DrawView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var path = Path()
    private var drawColor = Color.BLACK
    private val backgroundColor = Color.WHITE
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap
    private var shape = Shape.NONE

    private val paint = Paint().apply {
        color = drawColor
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = STROKE_WIDTH
    }

    /**
     * Don't draw every single pixel.
     * If the finger has has moved less than this distance, don't draw. scaledTouchSlop, returns
     * the distance in pixels a touch can wander before we think the user is scrolling.
     */
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    //start points
    private var x1 = 0f
    private var y1 = 0f

    //end points
    private var x2 = 0f
    private var y2 = 0f

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
    }

    fun changeStrokeWidth(strokeWidth: Float) {
        paint.strokeWidth = strokeWidth
    }

    fun changeColor(color: Int) {
        drawColor = color
        paint.color = drawColor
    }

    fun choosePencil() {
        shape = Shape.NONE
        paint.color = drawColor
    }

    fun chooseShape(shape: Shape) {
        this.shape = shape
    }

    fun chooseEraser() {
        shape = Shape.NONE
        paint.color = backgroundColor
    }

    fun clearCanvas() {
        extraCanvas.drawColor(backgroundColor, PorterDuff.Mode.CLEAR)
        invalidate()
    }

    fun getBitmap(): Bitmap {
        return extraBitmap
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        x2 = event.x
        y2 = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }

        return true
    }

    private fun touchStart() {
        path.reset()
        path.moveTo(x2, y2)
        x1 = x2
        y1 = y2
    }

    private fun touchMove() {
        val dx = abs(x2 - x1)
        val dy = abs(y2 - y1)

        if (dx >= touchTolerance || dy >= touchTolerance) {
            when (shape) {
                Shape.NONE -> drawLine()
                Shape.RECTANGLE -> drawRectangle()
                Shape.TRIANGLE -> drawTriangle()
                Shape.CIRCLE -> drawCircle()
            }

            // Draw the path in the extra bitmap to save it.
            extraCanvas.drawPath(path, paint)
        }

        invalidate()
    }

    private fun drawLine() {
        // QuadTo() adds a quadratic bezier from the last point,
        // approaching control point (x1,y1), and ending at (x2,y2).
        path.quadTo(x1, y1, (x2 + x1) / 2, (y2 + y1) / 2)

        x1 = x2
        y1 = y2
    }

    private fun drawRectangle() {
        val startX = min(x1, x2)
        val startY = min(y1, y2)
        val endX = max(x1, x2)
        val endY = max(y1, y2)

        path.addRect(startX, startY, endX, endY, Path.Direction.CW)
    }

    private fun drawCircle() {
        path.addCircle(x2, y2, abs(x1 - x2), Path.Direction.CCW)
    }

    private fun drawTriangle() {
        path.moveTo(x2, y2)

        if (x2 < x1 && y2 < y1) {
            path.lineTo(x1, y2)
        } else {
            path.lineTo(x2, y1)
        }

        path.lineTo(x1, y1)
        path.lineTo(x2, y2)
        path.close()
    }

    private fun touchUp() {
        path.reset()
    }
}