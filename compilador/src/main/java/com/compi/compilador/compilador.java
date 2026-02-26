package com.compi.compilador;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class compilador {

    public static Result analyze(String text) {
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
            }

            return new Result(
                    lexErrors,
                    synErrors,
                    nodos,
                    opeRepo
            );

        } catch (Exception e) {

            List<String> fatal = new ArrayList<>();
            fatal.add("Error inesperado: " + e.getMessage());

            return new Result(
                    new ArrayList<>(),
                    fatal,
                    new ArrayList<>(),
                    new ArrayList<>()
            );
        }
    }
}