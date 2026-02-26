package com.compi.generadordiagramas

import android.os.Bundle
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity

class ActividadDiagrama : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vista = VistaDiagrama(this, DiagramDataHolder.nodos)

        val verticalScroll = ScrollView(this).apply {
            isFillViewport = true
            addView(vista)
        }

        val horizontalScroll = HorizontalScrollView(this).apply {
            isFillViewport = true
            addView(verticalScroll)
        }

        setContentView(horizontalScroll)
    }
}