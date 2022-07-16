package com.spigot.casestudy.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet

class CustomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    private var  strokeWidth: Float = 2.toFloat()
    private var  strokeColor: Int = 0
    internal var solidColor: Int = 0
    private var  strokePaint: Paint? = null
    private var circlePaint: Paint? = null

    init {
        circlePaint = Paint()
        circlePaint?.color = Color.GREEN
        circlePaint?.flags = Paint.ANTI_ALIAS_FLAG

        strokePaint = Paint()
        strokePaint?.color = Color.BLUE
        strokePaint?.flags = Paint.ANTI_ALIAS_FLAG

    }
    fun setSolidColor(color: String) {
        solidColor = Color.parseColor(color)
        circlePaint?.color = solidColor

    }

    fun setStrokeWidth(dp: Int) {
        val scale = context.resources.displayMetrics.density
        strokeWidth = dp * scale

    }

    fun setStrokeColor(color: String) {
        strokeColor = Color.parseColor(color)
        strokePaint?.color = strokeColor
    }

    override fun onDraw(canvas: Canvas?) {

        val h = this.height
        val w = this.width

        val diameter = if (h > w) h else w
        val radius = diameter / 2

        this.height = diameter
        this.width = diameter

        canvas?.drawCircle((diameter / 2).toFloat(), (diameter / 2).toFloat(), radius.toFloat(), strokePaint!!)

        canvas?.drawCircle((diameter / 2).toFloat(), (diameter / 2).toFloat(), radius - strokeWidth,
            circlePaint!!)

        super.onDraw(canvas)
    }

}