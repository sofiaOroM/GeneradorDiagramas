package com.compi.compilador;

public class NodoFlujo {

    public String tipo;
    public String texto;
    public int indice;

    public String figura;
    public String colorFondo;
    public String colorTexto;
    public String fuente;
    public float tamañoLetra;

    public NodoFlujo(String tipo, String texto, int indice) {
        this.tipo = tipo;
        this.texto = texto;
        this.indice = indice;
        this.figura = "RECTANGULO";
        this.colorFondo = "#FFFFFF";
        this.colorTexto = "#000000";
        this.fuente = "ARIAL";
        this.tamañoLetra = 14;
    }
}