package com.compi.generadordiagramas

import android.content.Intent
import android.os.Bundle
import android.graphics.Typeface
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.compi.compilador.compilador

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val codeInput = findViewById<EditText>(R.id.textInput)
        val analyzeButton = findViewById<Button>(R.id.buttonAnalizar)
        val outputView = findViewById<TextView>(R.id.outputView)
        val buttonLimp = findViewById<Button>(R.id.buttonLimp)
        val buttonRepo = findViewById<Button>(R.id.buttonRepo)

        // Fuente monoespaciada para tablas
        outputView.typeface = Typeface.MONOSPACE

        // Deshabilitado por defecto
        buttonRepo.isEnabled = false

        /* ================= LIMPIAR ================= */

        buttonLimp.setOnClickListener {
            codeInput.text.clear()
            outputView.text = ""
            buttonRepo.isEnabled = false
        }

        /* ================= ANALIZAR ================= */

        analyzeButton.setOnClickListener {

            val code = codeInput.text.toString()

            if (code.isBlank()) {
                outputView.text = "Ingresa código para analizar"
                buttonRepo.isEnabled = false
                return@setOnClickListener
            }

            val result = compilador.analyze(code)

            if (result.lexicalErrors.isEmpty() && result.syntaxErrors.isEmpty()) {

                // Guardamos datos para reporte y diagrama
                DiagramDataHolder.nodos = result.nodos
                DiagramDataHolder.operadores = result.operadores
                DiagramDataHolder.estructuras = result.estructuras

                outputView.text = "Analisis exitoso "
                buttonRepo.isEnabled = true   // Se activa

                val intent = Intent(this, ActividadDiagrama::class.java)
                startActivity(intent)

            } else {

                buttonRepo.isEnabled = false

                val builder = StringBuilder()

                builder.append("ERRORES DETECTADOS\n")
                builder.append("═════════════════════════════════\n\n")

                if (result.lexicalErrors.isNotEmpty()) {

                    builder.append("ERRORES LÉXICOS\n")
                    builder.append("-----------------------------------------\n")
                    builder.append(String.format("%-5s %-50s\n", "#", "DESCRIPCIÓN"))
                    builder.append("-----------------------------------------\n")

                    result.lexicalErrors.forEachIndexed { index, error ->
                        builder.append(
                            String.format("%-5d %-50s\n", index + 1, error)
                        )
                    }

                    builder.append("\n\n")
                }

                if (result.syntaxErrors.isNotEmpty()) {

                    builder.append("ERRORES SINTÁCTICOS\n")
                    builder.append("----------------------------------------\n")
                    builder.append(String.format("%-5s %-50s\n", "#", "DESCRIPCIÓN"))
                    builder.append("----------------------------------------\n")

                    result.syntaxErrors.forEachIndexed { index, error ->
                        builder.append(
                            String.format("%-5d %-50s\n", index + 1, error)
                        )
                    }

                    builder.append("\n")
                }

                outputView.text = builder.toString()
            }
        }

        /* ================= VER REPORTE ================= */

        buttonRepo.setOnClickListener {
            val intent = Intent(this, ActividadReportes::class.java)
            startActivity(intent)
        }
    }
}

object DiagramDataHolder {
    var nodos = listOf<com.compi.compilador.NodoFlujo>()
    var operadores = listOf<com.compi.compilador.OperadorReporte>()
    var estructuras = listOf<com.compi.compilador.EstructuraReporte>()
}

