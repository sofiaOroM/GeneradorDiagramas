package com.compi.compilador;

public class OperadorReporte {

    public String operador;
    public int linea;
    public int columna;
    public int ocurrencia;

    public OperadorReporte(String operador, int linea, int columna, int ocurrencia) {
        this.operador = operador;
        this.linea = linea;
        this.columna = columna;
        this.ocurrencia = ocurrencia;
    }
}