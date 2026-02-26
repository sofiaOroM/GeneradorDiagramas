package com.compi.generadordiagramas

import android.graphics.Typeface
import android.os.Bundle
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ActividadReportes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = TextView(this).apply {
            typeface = Typeface.MONOSPACE
            textSize = 14f
            setPadding(30, 30, 30, 30)
        }

        val scroll = ScrollView(this).apply {
            addView(textView)
        }
        println(DiagramDataHolder.operadores)
        val builder = StringBuilder()

        /* ================= REPORTE OPERADORES ================= */

        builder.append("═════════════════════════════════════════\n")
        builder.append("             REPORTE DE OPERADORES\n")
        builder.append("═════════════════════════════════════════\n\n")
        builder.append("Cantidad operadores: ${DiagramDataHolder.operadores.size}\n\n")
        if (DiagramDataHolder.operadores.isEmpty()) {

            builder.append("No se encontraron operadores.\n\n")

        } else {

            builder.append(
                String.format(
                    "%-18s %-8s %-8s %-10s\n",
                    "Operador", "Línea", "Col", "Ocurr."
                )
            )

            builder.append("----------------------------------------------\n")

            DiagramDataHolder.operadores.forEach {
                builder.append(
                    String.format(
                        "%-18s %-8d %-8d %-10d\n",
                        it.operador,
                        it.linea,
                        it.columna,
                        it.ocurrencia
                    )
                )
            }

            builder.append("\n\n")
        }
/*
        /* ================= REPORTE ESTRUCTURAS ================= */
        builder.append("═══════════════════════════════════════════════\n")
        builder.append("          REPORTE DE ESTRUCTURAS\n")
        builder.append("═══════════════════════════════════════════════\n\n")

       if (DiagramDataHolder.nodos.isEmpty()) {

            builder.append("No se encontraron estructuras de control.\n")

        } else {

            builder.append(
                String.format("%-15s %-25s\n",
                    "Objeto", "Condición"
                )
            )

            builder.append("--------------------------------------------------------------\n")

            DiagramDataHolder.nodos.forEach {
                builder.append(
                    String.format(
                        "%-15s %-8d %-25s\n",
                        it.tipo,
                        it.texto
                    )
                )
            }
        }
*/
        textView.text = builder.toString()

        setContentView(scroll)

    }
}