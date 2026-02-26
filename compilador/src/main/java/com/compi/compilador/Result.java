package com.compi.compilador;

import java.util.List;

public class Result {

    public List<String> lexicalErrors;
    public List<String> syntaxErrors;
    public List<NodoFlujo> nodos;
    public List<OperadorReporte> operadores;
    public List<EstructuraReporte> estructuras;

    public Result(List<String> lexicalErrors,
                  List<String> syntaxErrors,
                  List<NodoFlujo> nodos,
                  List<OperadorReporte> operadores,
                  List<EstructuraReporte> estructuras) {

        this.lexicalErrors = lexicalErrors;
        this.syntaxErrors = syntaxErrors;
        this.nodos = nodos;
        this.operadores = operadores;
        this.estructuras = estructuras;
    }
}