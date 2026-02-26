package com.compi.compilador;

public class EstructuraReporte {
        public String tipo;   // "SI" o "MIENTRAS"
        public String texto;  // condición o descripción
        public int indice;    // índice del nodo en el diagrama
        // opcional: colores o fuente
        public String colorFondo;
        public String colorTexto;

        public EstructuraReporte(String tipo, String texto, int indice) {
            this.tipo = tipo;
            this.texto = texto;
            this. indice = indice;
        }
    }