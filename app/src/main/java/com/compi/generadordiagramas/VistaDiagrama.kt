package com.compi.generadordiagramas

import android.content.Context
import android.graphics.*
import android.text.StaticLayout
import android.text.TextPaint
import android.view.View
import com.compi.compilador.NodoFlujo
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class VistaDiagrama(
    context: Context,
    private val nodos: List<NodoFlujo>
) : View(context) {

    private val BLOCK_HEIGHT = 170f
    private val BLOCK_WIDTH = 440f
    private val HALF_WIDTH = BLOCK_WIDTH / 2
    private val VERTICAL_SPACING = 140f
    private val TEXT_OFFSET_X = 30f

    private val borderPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = Color.BLACK
    }

    private val fillPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightNeeded =
            (nodos.size * (BLOCK_HEIGHT + VERTICAL_SPACING)).toInt() + 600
        setMeasuredDimension(1800, heightNeeded)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)

        var y = 150f
        val xBase = width / 2f

        // ====== MIENTRAS ======
        var decisionMientrasY = 0f
        var ultimoBloqueMientrasY = 0f
        var dentroDeMientras = false

        // ====== SI ======
        var decisionSiY = 0f
        var ultimoBloqueSiY = 0f
        var dentroDeSi = false

        for (i in nodos.indices) {

            val nodo = nodos[i]

            when (nodo.tipo) {

                // ================= BLOQUES NORMALES =================
                "INICIO", "FIN", "PROCESO", "SALIDA", "ENTRADA" -> {

                    drawProcess(canvas, nodo, xBase, y)

                    if (dentroDeMientras) {
                        ultimoBloqueMientrasY = y
                    }

                    if (dentroDeSi) {
                        ultimoBloqueSiY = y
                    }

                    if (i < nodos.lastIndex) {
                        drawArrow(
                            canvas,
                            xBase,
                            y + BLOCK_HEIGHT,
                            xBase,
                            y + BLOCK_HEIGHT + VERTICAL_SPACING
                        )
                    }

                    y += BLOCK_HEIGHT + VERTICAL_SPACING
                }

                // ================= SI =================
                "SI" -> {

                    dentroDeSi = true
                    decisionSiY = y

                    drawDecision(canvas, nodo, xBase, y)

                    val centerY = y + BLOCK_HEIGHT / 2
                    val bottomY = y + BLOCK_HEIGHT

                    // Rama SI ↓
                    drawArrow(
                        canvas,
                        xBase,
                        bottomY,
                        xBase,
                        bottomY + VERTICAL_SPACING
                    )
                    drawLabel(canvas, "SI", xBase - 50f, bottomY + 60f)

                    // Rama NO →
                    val rightX = xBase + HALF_WIDTH
                    canvas.drawLine(
                        rightX,
                        centerY,
                        rightX + 200f,
                        centerY,
                        borderPaint
                    )
                    drawArrowHead(canvas, rightX + 200f, centerY, 0f)
                    drawLabel(canvas, "NO", rightX + 100f, centerY - 20f)

                    y += BLOCK_HEIGHT + VERTICAL_SPACING
                }

                "FIN_SI" -> {

                    val centerDecisionY = decisionSiY + BLOCK_HEIGHT / 2
                    val salidaY = ultimoBloqueSiY + BLOCK_HEIGHT

                    val offset = 80f

                    // Baja desde último bloque
                    canvas.drawLine(
                        xBase,
                        salidaY,
                        xBase,
                        salidaY + offset,
                        borderPaint
                    )

                    // Reconexion NO
                    val rightX = xBase + HALF_WIDTH + 200f

                    canvas.drawLine(
                        rightX,
                        centerDecisionY,
                        rightX,
                        salidaY + offset,
                        borderPaint
                    )

                    canvas.drawLine(
                        rightX,
                        salidaY + offset,
                        xBase,
                        salidaY + offset,
                        borderPaint
                    )

                    drawArrowHead(canvas, xBase, salidaY + offset, Math.PI.toFloat())

                    dentroDeSi = false
                    y = salidaY + VERTICAL_SPACING
                }

                // ================= MIENTRAS =================
                "MIENTRAS" -> {

                    dentroDeMientras = true
                    decisionMientrasY = y

                    drawDecision(canvas, nodo, xBase, y)

                    val centerY = y + BLOCK_HEIGHT / 2
                    val bottomY = y + BLOCK_HEIGHT

                    drawArrow(
                        canvas,
                        xBase,
                        bottomY,
                        xBase,
                        bottomY + VERTICAL_SPACING
                    )
                    drawLabel(canvas, "SI", xBase - 50f, bottomY + 60f)

                    val rightX = xBase + HALF_WIDTH
                    canvas.drawLine(
                        rightX,
                        centerY,
                        rightX + 200f,
                        centerY,
                        borderPaint
                    )
                    drawArrowHead(canvas, rightX + 200f, centerY, 0f)
                    drawLabel(canvas, "NO", rightX + 100f, centerY - 20f)

                    y += BLOCK_HEIGHT + VERTICAL_SPACING
                }

                "FIN_MIENTRAS" -> {

                    val centerDecisionY =
                        decisionMientrasY + BLOCK_HEIGHT / 2

                    val cuerpoSalidaY =
                        ultimoBloqueMientrasY + BLOCK_HEIGHT

                    val offset = 80f

                    // RETORNO (SI)
                    canvas.drawLine(
                        xBase,
                        cuerpoSalidaY,
                        xBase,
                        cuerpoSalidaY + offset,
                        borderPaint
                    )

                    canvas.drawLine(
                        xBase,
                        cuerpoSalidaY + offset,
                        xBase - 300f,
                        cuerpoSalidaY + offset,
                        borderPaint
                    )

                    canvas.drawLine(
                        xBase - 300f,
                        cuerpoSalidaY + offset,
                        xBase - 300f,
                        centerDecisionY,
                        borderPaint
                    )

                    canvas.drawLine(
                        xBase - 300f,
                        centerDecisionY,
                        xBase,
                        centerDecisionY,
                        borderPaint
                    )

                    drawArrowHead(canvas, xBase, centerDecisionY, 0f)

                    // RECONEXIÓN DEL NO

                    val rightX = xBase + HALF_WIDTH + 200f

                    canvas.drawLine(
                        rightX,
                        centerDecisionY,
                        rightX,
                        cuerpoSalidaY + offset,
                        borderPaint
                    )

                    canvas.drawLine(
                        rightX,
                        cuerpoSalidaY + offset,
                        xBase,
                        cuerpoSalidaY + offset,
                        borderPaint
                    )

                    drawArrowHead(canvas, xBase, cuerpoSalidaY + offset, Math.PI.toFloat())

                    dentroDeMientras = false

                    y = cuerpoSalidaY + VERTICAL_SPACING
                }
            }
        }
    }

    // ================= FIGURAS =================

    private fun drawProcess(canvas: Canvas, nodo: NodoFlujo, x: Float, y: Float) {

        val textPaint = TextPaint().apply {
            isAntiAlias = true
            color = Color.BLACK
            textSize = nodo.tamañoLetra * 3
            textAlign = Paint.Align.CENTER
        }

        when (nodo.tipo) {

            "INICIO", "FIN" -> {
                canvas.drawOval(x - HALF_WIDTH, y, x + HALF_WIDTH, y + BLOCK_HEIGHT, fillPaint)
                canvas.drawOval(x - HALF_WIDTH, y, x + HALF_WIDTH, y + BLOCK_HEIGHT, borderPaint)
            }

            "SALIDA", "ENTRADA" -> {
                val path = Path()
                path.moveTo(x - HALF_WIDTH + 60f, y)
                path.lineTo(x + HALF_WIDTH, y)
                path.lineTo(x + HALF_WIDTH - 60f, y + BLOCK_HEIGHT)
                path.lineTo(x - HALF_WIDTH, y + BLOCK_HEIGHT)
                path.close()
                canvas.drawPath(path, fillPaint)
                canvas.drawPath(path, borderPaint)
            }

            else -> {
                canvas.drawRoundRect(
                    x - HALF_WIDTH,
                    y,
                    x + HALF_WIDTH,
                    y + BLOCK_HEIGHT,
                    40f,
                    40f,
                    fillPaint
                )
                canvas.drawRoundRect(
                    x - HALF_WIDTH,
                    y,
                    x + HALF_WIDTH,
                    y + BLOCK_HEIGHT,
                    40f,
                    40f,
                    borderPaint
                )
            }
        }

        drawMultilineText(canvas, nodo.texto ?: "", x, y, textPaint)
    }

    private fun drawDecision(canvas: Canvas, nodo: NodoFlujo, x: Float, y: Float) {

        val fill = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = Color.CYAN
        }

        val path = Path()
        path.moveTo(x, y)
        path.lineTo(x + HALF_WIDTH, y + BLOCK_HEIGHT / 2)
        path.lineTo(x, y + BLOCK_HEIGHT)
        path.lineTo(x - HALF_WIDTH, y + BLOCK_HEIGHT / 2)
        path.close()

        canvas.drawPath(path, fill)
        canvas.drawPath(path, borderPaint)

        val textPaint = TextPaint().apply {
            isAntiAlias = true
            color = Color.BLACK
            textSize = nodo.tamañoLetra * 3
            textAlign = Paint.Align.CENTER
        }

        drawMultilineText(canvas, nodo.texto ?: "", x, y, textPaint)
    }

    private fun drawMultilineText(
        canvas: Canvas,
        texto: String,
        x: Float,
        y: Float,
        paint: TextPaint
    ) {
        val layout = StaticLayout.Builder
            .obtain(texto, 0, texto.length, paint, (BLOCK_WIDTH - 40f).toInt())
            .setAlignment(android.text.Layout.Alignment.ALIGN_CENTER)
            .setIncludePad(false)
            .build()

        canvas.save()
        val textHeight = layout.height
        val textY = y + (BLOCK_HEIGHT - textHeight) / 2

        canvas.translate(
            x - (BLOCK_WIDTH - 40f) / 2 + TEXT_OFFSET_X,
            textY
        )

        layout.draw(canvas)
        canvas.restore()
    }

    private fun drawArrow(canvas: Canvas, sx: Float, sy: Float, ex: Float, ey: Float) {
        canvas.drawLine(sx, sy, ex, ey, borderPaint)
        val angle = atan2(ey - sy, ex - sx)
        drawArrowHead(canvas, ex, ey, angle)
    }

    private fun drawArrowHead(canvas: Canvas, x: Float, y: Float, angle: Float) {
        val size = 25f
        val path = Path()
        path.moveTo(x, y)
        path.lineTo(
            (x - size * cos(angle - Math.PI / 6)).toFloat(),
            (y - size * sin(angle - Math.PI / 6)).toFloat()
        )
        path.lineTo(
            (x - size * cos(angle + Math.PI / 6)).toFloat(),
            (y - size * sin(angle + Math.PI / 6)).toFloat()
        )
        path.close()
        canvas.drawPath(path, borderPaint)
    }

    private fun drawLabel(canvas: Canvas, texto: String, x: Float, y: Float) {
        val paint = Paint().apply {
            isAntiAlias = true
            color = Color.BLACK
            textSize = 40f
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText(texto, x, y, paint)
    }
}