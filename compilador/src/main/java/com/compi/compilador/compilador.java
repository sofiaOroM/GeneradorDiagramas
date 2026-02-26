package com.compi.compilador;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class compilador {

    public static Result analyze(String text) {
        List<EstructuraReporte> estructuras = new ArrayList<>();
        try {

            Lexer lexer = new Lexer(new StringReader(text));
            Parser parser = new Parser(lexer);

            parser.parse();

            List<String> lexErrors = lexer.getLexicalErrors();
            List<String> synErrors = parser.getSyntaxErrors();
            List<OperadorReporte> opeRepo = lexer.getOperadores();

            List<NodoFlujo> nodos = new ArrayList<>();
            if (lexErrors.isEmpty() && synErrors.isEmpty()) {
                nodos = parser.getNodos();

                for (NodoFlujo nodo : nodos) {
                    if (nodo.tipo.equals("SI") || nodo.tipo.equals("MIENTRAS")) {
                        estructuras.add(new EstructuraReporte(
                                nodo.tipo,
                                nodo.texto,
                                nodo.indice
                        ));
                    }
                }

// ===== LOG DE NODOS =====
            System.out.println("===== NODOS OBTENIDOS =====");
            for (NodoFlujo nodo : nodos) {
                System.out.println("Tipo: " + nodo.tipo
                        + ", Texto: " + nodo.texto
                        + ", Indice: " + nodo.indice
                        + ", Figura: " + nodo.figura
                        + ", ColorFondo: " + nodo.colorFondo
                        + ", ColorTexto: " + nodo.colorTexto
                        + ", Fuente: " + nodo.fuente
                        + ", TamañoLetra: " + nodo.tamañoLetra);
            }
            System.out.println("===========================");
        }
            return new Result(
                    lexErrors,
                    synErrors,
                    nodos,
                    opeRepo,
                    estructuras
            );

        } catch (Exception e) {

            List<String> fatal = new ArrayList<>();
            fatal.add("Error inesperado: " + e.getMessage());

            return new Result(
                    new ArrayList<>(),
                    fatal,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>()
            );
        }
    }
}
